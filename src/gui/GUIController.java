package gui;

import gui.scenes.DragFileScene;
import gui.scenes.FinishedDrivingScene;
import gui.scenes.ProfileSetupScene;
import gui.scenes.DrivingScene;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import video.Video;
import control.Controller;

/**
 * This is the controller of the front-end. It has reference to the controller
 * and is responsible for initializing and modifying the GUI by switching 
 * between different scenes when appropriate.
 * @author Austin Kyker
 *
 */
public class GUIController {

	private static final String EXIT_FULLSCREEN_HINT = "Press spacebar to pause and complete ride";
	public static final int SCREEN_WIDTH = 600;
	public static final int SCREEN_HEIGHT = 300;

	private static final String AD_PLAYER_TITLE = "Ad Player";
	private static final String START_RIDE_TITLE = "Start Ride";
	private static final String FINISHED_DRIVING_TITLE = "Finished Driving";
	private static final String NO_MORE_PLAYABLE_VIDEOS_TITLE = "No More Videos";
	private static final String DRAG_AND_DROP_TITLE = "Drag Kogo File";
	private static final String PROFILE_SETUP_TITLE = "Kogo Setup";

	private Controller myController;
	private DrivingScene myDrivingScene;
	private Stage myStage;

	public GUIController(Stage stage, Controller control){
		myStage = stage;
		myController = control;
		configureStageAndDisplayInitialScene();
	}

	private void configureStageAndDisplayInitialScene(){
		if(myController.isDriverProfileInitialized())
			showDragAndDropScene();
		else
			showProfileSetupScene();
		myStage.setResizable(false);
		myStage.show();
	}

	public void showVideo(Video video){
		myDrivingScene.showVideo(video);
		myStage.setTitle(AD_PLAYER_TITLE);
		makeWindowFullScreen();
	}

	public void makeWindowFullScreen() {
		if(!myStage.isFullScreen()){
			myStage.setFullScreenExitHint(EXIT_FULLSCREEN_HINT);
			myStage.setFullScreen(true);
		}
	}
	
	public void setupDrivingEnvironment() {
		myDrivingScene = new DrivingScene(new BorderPane(), myController, this);
		showStartRideScreen();
	}
	
	private void showProfileSetupScene() {
		showScene(new ProfileSetupScene(new BorderPane(), myController), PROFILE_SETUP_TITLE);
	}

	public void showDragAndDropScene() {
		showScene(new DragFileScene(new Group(), myController), DRAG_AND_DROP_TITLE);
	}

	public void showStartRideScreen(){
		myStage.setFullScreen(false);
		myDrivingScene.showStartRideScreen();
		showScene(myDrivingScene, START_RIDE_TITLE);
	}

	public void showNoMorePlayableVideosScene() {
		myDrivingScene.showNoMorePlayableVideosScreen();
		showScene(myDrivingScene, NO_MORE_PLAYABLE_VIDEOS_TITLE);
	}

	public void showFinishedDrivingScreen() {
		showScene(new FinishedDrivingScene(new BorderPane()), FINISHED_DRIVING_TITLE);
	}
	
	private void showScene(Scene scene, String sceneTitle) {
		myStage.setScene(scene);
		myStage.setTitle(sceneTitle);
	}
}