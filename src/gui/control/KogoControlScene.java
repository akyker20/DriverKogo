package gui.control;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import control.Controller;

public class KogoControlScene extends ControlScene {
	
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
		finishDrivingBtn.setOnAction(event -> myControl.finishDriving());
		myOptionsHolder.getChildren().addAll(startNewRideBtn, finishDrivingBtn);
	}
	
	public void reset() {
		myOptionsHolder.getChildren().clear();
		myOptionsHolder.getChildren().add(myEndRideButton);
	}

}
