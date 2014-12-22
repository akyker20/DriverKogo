package gui.control;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import control.Controller;

public class ControlStage extends Stage {
	private ProfileSetupScene myProfileSetupScene;
	private NotEnoughVideosScene myNotEnoughVideosScene;
	private RideStarterScene myRideStarterScene;
	private FinishedDrivingScene myFinishedDrivingScene;
	private Controller myControl;
	private DragFileScene myDragFileScene;
	private KogoControlScene myKogoControlScene;

	public ControlStage(Controller control) {
		myControl = control;
		initializeScenes();
		this.setScene(myControl.isDriverProfileInitialized() ? 
				myDragFileScene : myProfileSetupScene);
		this.setResizable(false);
		this.show();
	}

	private void initializeScenes() {
		myKogoControlScene = new KogoControlScene(new BorderPane(), this);
		myDragFileScene = new DragFileScene(new BorderPane(), myControl);
		myProfileSetupScene = new ProfileSetupScene(new BorderPane(), myControl);
		myNotEnoughVideosScene = new NotEnoughVideosScene(new BorderPane());
		myRideStarterScene = new RideStarterScene(new BorderPane(), myControl);
		myFinishedDrivingScene = new FinishedDrivingScene(new BorderPane());
	}

	public void showNoMorePlayableVideosScene() {
		this.setScene(myNotEnoughVideosScene);
	}

	public void showDragAndDropScene() {
		this.setScene(myDragFileScene);
	}

	public void setupVideoControl() {
		this.setScene(myKogoControlScene);
	}

	public void selectDrivingScene(boolean canSelectVideo) {
		this.setScene(canSelectVideo ? myRideStarterScene
				: myNotEnoughVideosScene);
	}

	public void showFinishedDrivingScene() {
		this.setScene(myFinishedDrivingScene);
	}

	public void showRideStarterScene() {
		this.setScene(myRideStarterScene);
	}

}
