package control;

import gui.GUIController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import video.Video;
import video.VideoSelector;
import xmlcontrol.XMLController;

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
	private File myDeliverableDirectory;

	public static void main(String[] args){ launch(args); }

	@Override
	public void start(Stage stage) throws Exception {
		myXMLController = new XMLController();
		myGUIController = new GUIController(stage, this);
	}

	/**
	 * This method is called after the driver drags and drops the XML File.
	 */
	public void initializeDrivingEnvironment(File deliverableDirectory, File xmlFile) 
			throws FileNotFoundException, ParserConfigurationException, 
			SAXException, IOException, TransformerException {
		myDeliverableDirectory = deliverableDirectory;
		myVideoList = new ArrayList<Video>();
		myXMLController.initializeVideoXMLControl(myVideoList, xmlFile);
		myVideoSelector = new VideoSelector(this);	
		myGUIController.setupDrivingEnvironment();
	}

	public void selectAndPlayVideo(int numPassengers){
		myNumPassengers = numPassengers;
		myGUIController.showVideo(myVideoSelector.selectVideoFrom(getPlayableVideos()));
	}

	public void playAnotherVideo() {
		if(canPlayVideos())
			selectAndPlayVideo(myNumPassengers);		
		else
			myGUIController.showNoMorePlayableVideosScene();
	}
	
	public boolean canPlayVideos() {
		return getPlayableVideos().length > 0;
	}

	/**
	 * If there any videos that have not been played this ride, a random
	 * video is selected from this set. Otherwise, if there are any videos
	 * that have views remaining, a random video from this set is selected.
	 */
	public Object[] getPlayableVideos(){
		Object[] playableUnplayedVideosThisRide = myVideoList.stream()
				.filter(Video::canPlay).toArray();
		if (playableUnplayedVideosThisRide.length > 0)
			return playableUnplayedVideosThisRide;
		else
			return myVideoList.stream()
					.filter(Video::hasPlaysRemaining).toArray();
	}

	/**
	 * Called when a video is completed during a ride.
	 */
	public void completedVideoDuringRide(Video videoCompleted) throws TransformerException {
		videoCompleted.addViews(myNumPassengers);
		myXMLController.updateXML(videoCompleted);
		playAnotherVideo();
	}

	public void finishDriving() {
		myGUIController.showFinishedDrivingScreen();
		myXMLController.appendInitialsToFile();
	}

	public void endRide() {
		for(int i = 0; i < myVideoList.size(); i++){
			myVideoList.get(i).prepareForNewRide();
		}
		myGUIController.showStartRideScreen();
	}

	public boolean isDriverProfileInitialized() {
		return myXMLController.isDriverProfileInitialized();
	}

	public void submitProfileInformation(String initials) throws TransformerException {
		myXMLController.initializeProfile(initials);
		myGUIController.showDragAndDropScene();
	}

	public String getVideoDirPath() {
		return myDeliverableDirectory.getAbsolutePath().concat("/videos/");
	}
}