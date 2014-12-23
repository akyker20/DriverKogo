package control;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import gson.GSONFileReader;
import gson.GSONFileWriter;
import gui.control.ControlStage;
import gui.player.VideoStage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;
import javafx.stage.Stage;
import utilities.ErrorPopup;
import utilities.Validator;
import video.ActiveVideo;
import video.VideoManager;

/**
 * This class serves as a controller between Video (the backend) and the
 * GUIController (the head of the front-end). It promotes a seperation between
 * front-end and back-end.
 * 
 * @author Austin Kyker
 *
 */
public class Controller extends Application implements Observer {

	private static final String COPY_DRIVER_SESSION_ERROR = "Could not copy driver session file to desktop. "
			+ "Don't panic, just call Austin at 317-979-7549";
	public static final GSONFileReader GSON_READER = new GSONFileReader();
	public static final GSONFileWriter GSON_WRITER = new GSONFileWriter();

	private File myDeliverableDirectory;
	private VideoStage myVideoStage;
	private ControlStage myControlStage;
	private VideoManager myVideoManager;
	private int myNumPassengers;
	private File myJsonVideoFile;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		myVideoStage = new VideoStage(this);
		myVideoStage.setTitle("Video Stage");
		myControlStage = new ControlStage(this);
	}

	/**
	 * This method is called after the driver drags and drops the XML File.
	 */
	public void initializeDrivingEnvironment(File deliverableDirectory,
			File jsonVideoFile) {
		myDeliverableDirectory = deliverableDirectory;
		myVideoManager = new VideoManager(jsonVideoFile);
		myJsonVideoFile = jsonVideoFile;
		myControlStage.selectDrivingScene(myVideoManager.canSelectVideo());
	}

	public void startRide(int numPassengers) {
		myNumPassengers = numPassengers;
		String initialsKey = GSON_READER.getProfileInfo().getInitials();
		Validator val = new Validator(initialsKey, "Please validate this ride has " +
		numPassengers + " passengers.");
		val.addObserver(this);
	}


	private void selectAndPlayVideo() {
		myVideoStage.playVideo(myVideoManager.selectVideo());
		myControlStage.setupVideoControl();
	}

	public void playAnotherVideo() {
		if (myVideoManager.canSelectVideo()) {
			selectAndPlayVideo();
			return;
		}
		else if(!myVideoManager.videoViewsStillExist()) {
			myControlStage.showNoMorePlayableVideosScene();
		}
		myVideoManager.resetVideosForNewRide();
		myVideoStage.showKogoScene();
	}

	/**
	 * Called when a video is completed during a ride.
	 */
	public void completedVideoDuringRide(ActiveVideo videoCompleted) {
		videoCompleted.addViews(myNumPassengers);
		myVideoManager.updateVideoJson();
		playAnotherVideo();
	}

	public void finishDriving() {
		myVideoManager.terminateVideoDeliverable();
		saveDriverSessionFileToDesktop();
		myControlStage.showFinishedDrivingScene();
	}

	public boolean isDriverProfileInitialized() {
		ProfileInfo info = GSON_READER.getProfileInfo();
		return info != null;
	}

	private void saveDriverSessionFileToDesktop() {
		File desktopFile = new File(System.getProperty("user.home")
				+ "/Desktop/" + getNewFileEnding());
		if (desktopFile.exists())
			desktopFile.setWritable(true);
		Path desktopPath = desktopFile.toPath();
		try {
			Files.copy(myJsonVideoFile.toPath(), desktopPath, REPLACE_EXISTING);
		} catch (IOException e) {
			new ErrorPopup(COPY_DRIVER_SESSION_ERROR);
		}
	}

	private String getNewFileEnding() {
		String originalPath = myJsonVideoFile.getPath();
		String originalPathFileEnding = originalPath.substring(originalPath
				.indexOf("kogo_"));
		String initials = GSON_READER.getProfileInfo().getInitials();
		return originalPathFileEnding
				.replace("kogo_", "kogo_" + initials + "_");
	}

	public void submitProfileInformation(String initials) {
		GSON_WRITER.initializeDriverProfile(initials);
		myControlStage.showDragAndDropScene();
	}

	public String getVideoDirPath() {
		return myDeliverableDirectory.getAbsolutePath().concat("/videos/");
	}

	public boolean isDirectoryTerminated(File jsonFile) {
		return GSON_READER.isDeliverableTerminated(jsonFile);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		this.selectAndPlayVideo();		
	}

	public void endCurrentRide() {
		myVideoManager.resetVideosForNewRide();
		myVideoStage.stopPlayingVideos();
	}
}