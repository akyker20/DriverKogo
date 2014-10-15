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

	/**
	 * On load, the drag and drop screen is displayed to the user. The program needs
	 * the driver XML File before it can start showing advertisement clips.
	 */
	private void configureStageAndDisplayDragFileScene(){
		myStage.setTitle(DRAG_AND_DROP_TITLE);
		myStage.setResizable(false);
		myStage.setScene(myDragFileScreen);
		myStage.show();
	}

	/**
	 * Adds the video playing scene to the stage. Sets the stage to be full screen
	 * and presents the driver with a hint on how to pause and complete rides.
	 * @param video
	 */
	public void playVideo(Video video){
		myVideoScene.setUpVideo(video);
		myStage.setScene(myVideoScene);
		myStage.setTitle(AD_PLAYER_TITLE);
		myStage.setFullScreenExitHint("Press spacebar to pause and complete ride");
		myStage.setFullScreen(true);
	}
	
	/**
	 * Makes the stage full screen. This method is called when the driver hits escape
	 * to exit full screen and then hits SPACE to play the video again.
	 */
	public void makeFullScreen() {
		myStage.setFullScreenExitHint("Press spacebar to pause and complete ride");
		myStage.setFullScreen(true);
	}
	
	/**
	 * Called when driver hits SPACE to play the video. If the stage is not
	 * in full screen, then it is set to be full screen with makeFullScreen.
	 * @return true if the stage is in full screen.
	 */
	public boolean isFullScreen(){
		return myStage.isFullScreen();
	}

	/**
	 * Displays the start ride screen to the driver which asks for the number of
	 * students in the ride.
	 */
	public void showStartRideScreen(){
		myStage.setScene(myRideStarterScene);
		myStage.setTitle(START_RIDE_TITLE);
	}

	/**
	 * If no videos exist with unused views, the driver is shown the
	 * "Not Enough Videos Screen" and instructed that they will not be 
	 * paid for continued driving. Hopefully, drivers won't see this screen!
	 */
	public void notEnoughVideos() {
		myStage.setScene(myNotEnoughVideosScene);
		myStage.setTitle(NO_MORE_VIDEOS_TITLE);
	}

	/**
	 * The Finished Driving Scene is displayed to the user after they select
	 * "Finish Driving" from the file menu. They are instructed to send their
	 * XML file to the owners.
	 */
	public void showFinishedDrivingScreen() {
		myStage.setScene(myFinishedDrivingScene);
		myStage.setTitle(FINISHED_RIDING_TITLE);
	}

	/**
	 * If there are videos to play, the start ride screen is displayed.
	 * Otherwise, the "Not Enough Videos" screen is shown.
	 */
	public void configureDrivingEnvironment() {
		if(myController.canPlayVideos())
			showStartRideScreen();
		else
			notEnoughVideos();
	}
}