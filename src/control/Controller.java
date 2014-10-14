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

public class Controller extends Application {

	public static final int NUM_DRIVERS = 8;

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

	public void initializeDrivingEnvironment(File file) 
			throws FileNotFoundException, ParserConfigurationException, 
			SAXException, IOException, TransformerException {
		myFile = file;
		myXMLController = new XMLController(myVideoList, myFile);
		myVideoSelector = new VideoSelector(this);	
		myGUIController.configureDrivingEnvironment();
	}

	public void playVideo(int numPassengers){
		myNumPassengers = numPassengers;
		Video videoToBePlayed = myVideoSelector.selectVideo();
		myGUIController.playVideo(videoToBePlayed);
	}

	public void playAnotherVideo() {
		if(canPlayVideos())
			playVideo(myNumPassengers);		
		else
			myGUIController.notEnoughVideos();
	}

	public Object[] playableVideos(){
		return myVideoList.stream()
				.filter(Video::canPlay).toArray();
	}

	public boolean canPlayVideos() {
		return playableVideos().length > 0;
	}

	public void completedVideo(Video videoCompleted) throws TransformerException {
		videoCompleted.addViews(myNumPassengers);
		myXMLController.updateXML(videoCompleted, myNumPassengers);
	}


	/**
	 * Method used for debugging.
	 */
	private void printVideoList(){
		for(int i = 0; i < myVideoList.size(); i++){
			System.out.println("Video---------------");
			System.out.println("company: " + myVideoList.get(i).getMyCompany());
			System.out.println("name: " + myVideoList.get(i).getMyName());
			System.out.println("length: " + myVideoList.get(i).getMyLength());
			System.out.println("plays: " + myVideoList.get(i).getMyPlays());
			System.out.println("Max plays: " + myVideoList.get(i).getMyMaxPlays());
			System.out.println();
		}
	}

	public void finishDriving() {
		myGUIController.showFinishedDrivingScreen();
	}
}