package control;
import java.util.ArrayList;

import gui.GUIController;
import video.Video;
import video.VideoSelector;
import xmlcontrol.XMLParser;
import xmlcontrol.XMLWriter;
import javafx.application.Application;
import javafx.stage.Stage;

public class Controller extends Application {

	public static final int NUM_DRIVERS = 8;
	
	private GUIController myGUIController;
	private VideoSelector myVideoSelector;
	private ArrayList<Video> myVideoList;
	private int myNumPassengers;
	
	public static void main(String[] args){ launch(args); }

	@Override
	public void start(Stage stage) throws Exception {
		myVideoList = new ArrayList<Video>();
		XMLParser videoParser = new XMLParser(myVideoList);
		XMLWriter xmlWriter = new XMLWriter();	
		myGUIController = new GUIController(stage, this);
		myVideoSelector = new VideoSelector(this);	
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

	public void completedVideo(Video videoCompleted) {
		videoCompleted.subtractViews(myNumPassengers);
		
	}
}