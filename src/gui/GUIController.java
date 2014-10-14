package gui;

import menus.MenuFeature;
import video.Video;
import gui.scenes.DragFileScene;
import gui.scenes.FinishedDrivingScene;
import gui.scenes.NotEnoughVideosScene;
import gui.scenes.RideStarterScene;
import gui.scenes.VideoScene;
import javafx.scene.Group;
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
	private FinishedDrivingScene myFinishedDrivingScene;
	private DragFileScene myDragFileScreen;
	private Stage myStage;

	public GUIController(Stage stage, Controller control){
		myStage = stage;
		myController = control;
		myDragFileScreen = new DragFileScene(new Group(), myController);
		myVideoScene = new VideoScene(new BorderPane(), myController);
		myRideStarterScene = new RideStarterScene(new BorderPane(), myController, new MenuFeature(control));
		myNotEnoughVideosScene = new NotEnoughVideosScene(new BorderPane(), new MenuFeature(control));
		myFinishedDrivingScene = new FinishedDrivingScene(new BorderPane(), new MenuFeature(control));
		configureStageAndDisplayDragFileScene();
	}

	private void configureStageAndDisplayDragFileScene(){
		myStage.setTitle("Drag Kogo File");
		myStage.setResizable(false);
		myStage.setScene(myDragFileScreen);
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

	public void showFinishedDrivingScreen() {
		myStage.setScene(myFinishedDrivingScene);
	}

	public void configureDrivingEnvironment() {
		if(myController.canPlayVideos())
			showStartRideScreen();
		else
			notEnoughVideos();
	}
}
