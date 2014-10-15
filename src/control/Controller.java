package control;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import gui.GUIController;
import video.Video;
import video.VideoSelector;
import xmlcontrol.XMLController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
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
	private File myFile;

	public static void main(String[] args){ launch(args); }

	@Override
	public void start(Stage stage) throws Exception {
		myStage = stage;
		myVideoList = new ArrayList<Video>();
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
	public void initializeDrivingEnvironment(File file) 
			throws FileNotFoundException, ParserConfigurationException, 
			SAXException, IOException, TransformerException {
		myFile = file;
		myXMLController = new XMLController(myVideoList, myFile);
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
		Video videoToBePlayed = myVideoSelector.selectVideo();
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
	public Object[] playableVideos(){
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
		return playableVideos().length > 0;
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
		myXMLController.updateXML(videoCompleted, myNumPassengers);
	}

	/**
	 * Adds the finished driving scene to the stage. This method is called when
	 * the driver clicks File -> Finished Driving.
	 */
	public void finishDriving() {
		myGUIController.showFinishedDrivingScreen();
	}

	/**
	 * Removes the video playing scene from the stage and replaces it with
	 * the start ride screen so the driver can start a new ride. This method is
	 * called when the driver clicks File -> Ride Completed.
	 */
	public void endRide() {
		myGUIController.showStartRideScreen();
	}
}