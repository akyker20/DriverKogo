package control;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import static java.nio.file.StandardCopyOption.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import gui.GUIController;
import video.Video;
import video.VideoSelector;
import xmlcontrol.XMLController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class serves as a controller between Video (the backend) and the
 * GUIController  (the head of the front-end). It promotes a seperation
 * between front-end and back-end.
 * @author Austin Kyker
 *
 */
public class Controller extends Application {

	private GUIController myGUIController;
	private VideoSelector myVideoSelector;
	private ArrayList<Video> myVideoList;
	private int myNumPassengers;
	private XMLController myXMLController;
	private Stage myStage;
	private File myDeliverableDirectory;
	private File myXMLFile;

	public static void main(String[] args){ launch(args); }

	@Override
	public void start(Stage stage) throws Exception {
		myStage = stage;
		myVideoList = new ArrayList<Video>();
		myXMLController = new XMLController();
		myGUIController = new GUIController(myStage, this);
	}

	/**
	 * This method is called after the driver drags and drops the XML File.
	 * It initializes the XMLController and allows the GUIController to
	 * configure the ride screen.
	 * @param file
	 * @throws FileNotFoundException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public void initializeDrivingEnvironment(File deliverableDirectory, File xmlFile) 
			throws FileNotFoundException, ParserConfigurationException, 
			SAXException, IOException, TransformerException {
		myDeliverableDirectory = deliverableDirectory;
		myXMLFile = xmlFile;
		myXMLController.initializeVideoXMLControl(myVideoList, myXMLFile);
		myVideoSelector = new VideoSelector(this);	
		myGUIController.configureDrivingEnvironment();
	}

	/**
	 * When a video is played, myNumPassengers is set so that when another
	 * video is played this class has access to the number of passengers.
	 * A video is selected and that video is passed to the GUIController
	 * so that the stage will show the video-playing scene.
	 * @param numPassengers
	 */
	public void playVideo(int numPassengers){
		myNumPassengers = numPassengers;
		Video videoToBePlayed = myVideoSelector.selectVideo(getPlayableVideos());
		myGUIController.playVideo(videoToBePlayed);
	}

	/**
	 * If any videos exist that still have views, the playVideo function
	 * is called. Otherwise, the GUIController is called to display the
	 * not enough videos scene.
	 */
	public void playAnotherVideo() {
		if(canPlayVideos())
			playVideo(myNumPassengers);		
		else
			myGUIController.notEnoughVideos();
	}

	/**
	 * If there any videos that have not been played this ride, a random
	 * video is selected from this set. Otherwise, if there are any videos
	 * that have views remaining, a random video from this set is selected.
	 * @return
	 */
	public Object[] getPlayableVideos(){
		Object[] playableUnplayedVideos = myVideoList.stream()
				.filter(Video::canPlay).toArray();
		if (playableUnplayedVideos.length > 0)
			return playableUnplayedVideos;
		else{
			return myVideoList.stream()
					.filter(Video::hasPlaysRemaining).toArray();
		}
	}

	/**
	 * If there are videos that have not been played this ride, or
	 * if there are videos that have remaining views.
	 * @return
	 */
	public boolean canPlayVideos() {
		return getPlayableVideos().length > 0;
	}

	/**
	 * Called when a video is completed. The video's play attribute is incremented
	 * according to the number of passengers.
	 * The master XML File is edited to reflect these changes.
	 * @param videoCompleted
	 * @throws TransformerException
	 */
	public void completedVideo(Video videoCompleted) throws TransformerException {
		videoCompleted.addViews(myNumPassengers);
		myXMLController.updateXML(videoCompleted);
	}

	/**
	 * Adds the finished driving scene to the stage. This method is called when
	 * the driver clicks File -> Finished Driving.
	 */
	public void finishDriving() {
		myGUIController.showFinishedDrivingScreen();
		appendInitialsToFile();
	}

	/**
	 * Removes the VideoScene from the stage and replaces it with
	 * the RideStarterScene so the driver can start a new ride. The method
	 * also sets the alreadyPlayedThisRide attribute of each video to false.
	 * This method is called when the driver clicks File -> Ride Completed.
	 */
	public void endRide() {
		for(int i = 0; i < myVideoList.size(); i++){
			myVideoList.get(i).prepareForNewRide();
		}
		myGUIController.showStartRideScreen();
	}

	public boolean isProfileInitialized() {
		return myXMLController.isProfileInitialized();
	}

	public void submitProfileInformation(String initials) throws TransformerException {
		myXMLController.initializeProfile(initials);
	}

	public void appendInitialsToFile() {
		String originalPath = myXMLFile.getAbsolutePath();
		String newName = originalPath.substring(originalPath.indexOf("kogo_")).replace("kogo_", myXMLController.getInitials().concat("_"));
		File desktopFile = new File(System.getProperty("user.home") + "/Desktop/"+newName);
		if(desktopFile.exists()) desktopFile.setWritable(true);
		Path desktopPath = desktopFile.toPath();
		try {
			Files.copy(myXMLFile.toPath(), desktopPath, REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public String getVideoDirPath() {
		return myDeliverableDirectory.getAbsolutePath()+"/videos/";
	}
}