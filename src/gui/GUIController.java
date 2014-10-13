package gui;

import video.Video;
import gui.scenes.NotEnoughVideosScene;
import gui.scenes.RideStarterScene;
import gui.scenes.VideoScene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import control.Controller;

public class GUIController {
	
    public static final int SCREEN_WIDTH = 600;
    public static final int SCREEN_HEIGHT = 300;
    public static final String TITLE = "Kogo Driver";
    public static final String STYLESHEET_PACKAGE = "Stylesheets/";
    
	private Controller myController;
	private RideStarterScene myRideStarterScene;
	private VideoScene myVideoScene;
	private NotEnoughVideosScene myNotEnoughVideosScene;
	private Stage myStage;
	
	public GUIController(Stage stage, Controller control){
		myStage = stage;
		myController = control;
		myVideoScene = new VideoScene(new BorderPane(), myController);
		
		myRideStarterScene = new RideStarterScene(new BorderPane(), myController);
		myNotEnoughVideosScene = new NotEnoughVideosScene(new BorderPane());
		configureAndDisplayStage();
	}
		
	private void configureAndDisplayStage(){
        myStage.setTitle(TITLE);
        myStage.setResizable(false);
        if(myController.canPlayVideos())
        	myStage.setScene(myRideStarterScene);
        else
        	notEnoughVideos();
        myStage.show();
	}
	
	public void playVideo(Video video){
		myVideoScene.setUpVideo(video);
		myStage.setScene(myVideoScene);
		myStage.setFullScreen(true);
	}
	
	public void showStartRideScreen(){
		myStage.setScene(myRideStarterScene);
	}

	public void notEnoughVideos() {
		myStage.setScene(myNotEnoughVideosScene);
		
	}
}
