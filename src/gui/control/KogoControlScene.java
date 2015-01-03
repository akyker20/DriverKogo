package gui.control;

import java.util.Observable;
import java.util.Observer;

import utilities.Validator;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import control.Controller;

public class KogoControlScene extends ControlScene implements Observer {

	private static final String VALIDATION_MSG = "Validate that you are finished driving. This will lock the folder and you will no longer be able to show videos.";

	private static final String TITLE = "Ride in Progress";

	private Button myEndRideButton;
	private Controller myControl;
	private ControlStage myStage;
	private HBox myOptionsHolder;

	public KogoControlScene(BorderPane root, ControlStage stage, Controller control) {
		super(root, TITLE);
		myStage = stage;
		myControl = control;
		Label label = new Label(TITLE);
		label.setStyle("-fx-font-size: 50px");
		VBox container = new VBox(20);
		container.setAlignment(Pos.CENTER);
		myEndRideButton = new Button("End Current Ride");
		myEndRideButton.setOnAction(event->this.endCurrentRide());
		myOptionsHolder = new HBox(30);
		myOptionsHolder.setAlignment(Pos.CENTER);
		myOptionsHolder.getChildren().add(myEndRideButton);
		container.getChildren().addAll(label, myOptionsHolder);
		root.setCenter(container);
	}

	private void endCurrentRide() {
		myControl.endCurrentRide();
		this.showDriverOptions();
	}

	private void showDriverOptions() {
		myOptionsHolder.getChildren().clear();
		Button startNewRideBtn = new Button("Start New Ride");
		startNewRideBtn.setOnAction(event -> myStage.showRideStarterScene());
		Button finishDrivingBtn = new Button("Finish Driving");
		finishDrivingBtn.setOnAction(event -> this.handleFinishDrivingClick());
		myOptionsHolder.getChildren().addAll(startNewRideBtn, finishDrivingBtn);
	}

	private void handleFinishDrivingClick() {
		Validator finishedValidator = new Validator(myControl.getInitials(), VALIDATION_MSG,
				myStage.getX(), myStage.getY());
		finishedValidator.addObserver(this);
	}

	/**
	 * Called before this scene is shown to the user (after every ride).
	 */
	public void reset() {
		myOptionsHolder.getChildren().clear();
		myOptionsHolder.getChildren().add(myEndRideButton);
	}

	/**
	 * Called when the driver validates that they would like to finish driving.
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		myControl.finishDriving();	
	}

}
