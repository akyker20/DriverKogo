package gui.control;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import control.Controller;

/**
 * This screen allows the user to select the number of passengers that will be in riding.
 * This screen is displayed before every ride.
 * @author Austin Kyker
 */
public class RideStarterScene extends ControlScene {

    private static final String BTN_CSS_CLASS = "numPassengersButton";
	private static final String STYLESHEET_PACKAGE = "Stylesheets/";
    private static final String BUTTON_INSTRUCTIONS = "Number of Students Riding...";
	private static final int SPACE_BTW_BTNS = 10;
	private static final String TITLE = "Start Ride";
    
	private Controller myControl;
	private HBox myButtonHolder;
	private Label myInstructions;
	private BorderPane myPane;
	
	public RideStarterScene(BorderPane pane, Controller control) {
		super(pane, TITLE);
		myPane = pane;
		myControl = control;
		createGraphicalComponents();
		addGraphicalComponentsToPane();
		this.getStylesheets().add(STYLESHEET_PACKAGE + "style.css");
	}

	private void addGraphicalComponentsToPane() {
		VBox container = new VBox(5);
		container.setPadding(new Insets(10));
		container.setAlignment(Pos.BASELINE_RIGHT);
		container.getChildren().addAll(myButtonHolder, myInstructions);
		myPane.setCenter(container);
	}

	private void createGraphicalComponents() {
		createButtonHolderWithButtons();
		createInstructionsLabel();
	}
	
	private void createButtonHolderWithButtons() {
		myButtonHolder = new HBox(SPACE_BTW_BTNS);
		myButtonHolder.setId("ButtonHolder");
		myButtonHolder.getChildren().addAll(makeButton(1), makeButton(2), makeButton(3));
	}

	private void createInstructionsLabel() {
		myInstructions = new Label(BUTTON_INSTRUCTIONS);
	}

	private Button makeButton(int numPassengers) {
		Button button = new Button(""+numPassengers);
		button.setOnAction(event -> myControl.startRide(numPassengers));
		button.getStyleClass().add(BTN_CSS_CLASS);
		return button;
	}
}
