package gui;

import menus.MenuFeature;
import video.Video;
import gui.scenes.DragFileScene;
import gui.scenes.FinishedDrivingScene;
import gui.scenes.NotEnoughVideosScene;
import gui.scenes.RideStarterScene;
import gui.scenes.VideoScene;
import javafx.scene.Group;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import control.Controller;

public class GUIController {

	public static final int SCREEN_WIDTH = 600;
	public static final int SCREEN_HEIGHT = 300;
	
	private static final String AD_PLAYER_TITLE = "Ad Player";
	private static final String START_RIDE_TITLE = "Start Ride";
	private static final String FINISHED_RIDING_TITLE = "Finished Riding";
	private static final String NO_MORE_VIDEOS_TITLE = "No More Videos";
	
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
		myVideoScene = new VideoScene(new BorderPane(), myController, this);
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
		myStage.setTitle(AD_PLAYER_TITLE);
		myStage.setFullScreenExitHint("Press spacebar to pause and complete ride");
		myStage.setFullScreen(true);
	}
	
	public void makeFullScreen() {
		myStage.setFullScreenExitHint("Press spacebar to pause and complete ride");
		myStage.setFullScreen(true);
	}
	
	public boolean isFullScreen(){
		return myStage.isFullScreen();
	}

	public void showStartRideScreen(){
		myStage.setScene(myRideStarterScene);
		myStage.setTitle(START_RIDE_TITLE);
	}

	public void notEnoughVideos() {
		myStage.setScene(myNotEnoughVideosScene);
		myStage.setTitle(NO_MORE_VIDEOS_TITLE);
	}

	public void showFinishedDrivingScreen() {
		myStage.setScene(myFinishedDrivingScene);
		myStage.setTitle(FINISHED_RIDING_TITLE);
	}

	public void configureDrivingEnvironment() {
		if(myController.canPlayVideos())
			showStartRideScreen();
		else
			notEnoughVideos();
	}
}
