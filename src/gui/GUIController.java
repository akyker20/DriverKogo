package gui;

import gui.scenes.DragFileScene;
import gui.scenes.FinishedDrivingScene;
import gui.scenes.ProfileSetupScene;
import gui.scenes.DrivingScene;
import javafx.scene.Group;
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

	public static final int SCREEN_WIDTH = 600;
	public static final int SCREEN_HEIGHT = 300;

	private static final String AD_PLAYER_TITLE = "Ad Player";
	private static final String START_RIDE_TITLE = "Start Ride";
	private static final String FINISHED_RIDING_TITLE = "Finished Riding";
	private static final String NO_MORE_VIDEOS_TITLE = "No More Videos";
	private static final String DRAG_AND_DROP_TITLE = "Drag Kogo File";
	private static final String PROFILE_SETUP_TITLE = "Kogo Setup";

	private Controller myController;
	private DrivingScene myDrivingScene;
	private Stage myStage;

	public GUIController(Stage stage, Controller control){
		myStage = stage;
		myController = control;
		configureStageAndDisplayDragFileScene();
	}

	/**
	 * On load, the drag and drop screen is displayed to the user. The program needs
	 * the driver XML File before it can start showing advertisement clips.
	 */
	private void configureStageAndDisplayDragFileScene(){
		myStage.setResizable(false);
		if(myController.isProfileInitialized()){
			showDragAndDropScene();
		}
		else {
			showProfileSetupScene();
		}
		myStage.show();
	}

	private void showProfileSetupScene() {
		myStage.setTitle(PROFILE_SETUP_TITLE);
		myStage.setScene(new ProfileSetupScene(new BorderPane(), myController));
	}

	public void showDragAndDropScene() {
		myStage.setTitle(DRAG_AND_DROP_TITLE);
		myStage.setScene(new DragFileScene(new Group(), myController));
	}

	/**
	 * Adds the video playing scene to the stage. Sets the stage to be full screen
	 * and presents the driver with a hint on how to pause and complete rides.
	 * @param video
	 */
	public void showVideo(Video video){
		myDrivingScene.showVideo(video);
		myStage.setTitle(AD_PLAYER_TITLE);
		myStage.setFullScreenExitHint("Press spacebar to pause and complete ride");
		myStage.setFullScreen(true);
	}

	/**
	 * If the stage is not full screen already, it is made full screen and a hint
	 * is displayed to driver reminding them how to pause a video and complete a ride.
	 */
	public void makeFullScreen() {
		if(!myStage.isFullScreen()){
			myStage.setFullScreenExitHint("Press spacebar to pause and complete ride");
			myStage.setFullScreen(true);
		}
	}

	/**
	 * Displays the start ride screen to the driver which asks for the number of
	 * students in the ride.
	 */
	public void showStartRideScreen(){
		myDrivingScene.showStartRideScreen();
		myStage.setScene(myDrivingScene);
		myStage.setTitle(START_RIDE_TITLE);
	}

	/**
	 * If no videos exist with unused views, the driver is shown the
	 * "Not Enough Videos Screen" and instructed that they will not be 
	 * paid for continued driving. Hopefully, drivers won't see this screen!
	 */
	public void notEnoughVideos() {
		myDrivingScene.showNotEnoughVideos();
		myStage.setScene(myDrivingScene);
		myStage.setTitle(NO_MORE_VIDEOS_TITLE);
	}

	/**
	 * The Finished Driving Scene is displayed to the user after they select
	 * "Finish Driving" from the file menu. They are instructed to send their
	 * XML file to the owners.
	 */
	public void showFinishedDrivingScreen() {
		myStage.setTitle(FINISHED_RIDING_TITLE);
		myStage.setScene(new FinishedDrivingScene(new BorderPane()));
	}

	public void setupDrivingEnvironment() {
		myDrivingScene = new DrivingScene(new BorderPane(), myController, this);
		myStage.setScene(myDrivingScene);
	}
}