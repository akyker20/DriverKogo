package gui.panes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import control.Controller;

/**
 * This scene allows the user to select the number of passengers in a ride.
 * This scene is displayed before every ride.
 * @author Austin Kyker
 */
public class RideStarterPane extends BorderPane {

    private static final String STYLESHEET_PACKAGE = "Stylesheets/";
    private static final String BUTTON_INSTRUCTIONS = "Number of Students Riding...";
    
	private Controller myControl;
	
	public RideStarterPane(Controller control) {
		myControl = control;
		this.getStylesheets().add(STYLESHEET_PACKAGE + "style.css");
		
		HBox buttonHolder = new HBox(10);
		buttonHolder.setId("ButtonHolder");
		buttonHolder.getChildren().addAll(makeButton(1), makeButton(2), makeButton(3));
		
		VBox container = new VBox(5);
		container.setPadding(new Insets(10));
		Label label = new Label(BUTTON_INSTRUCTIONS);
		container.getChildren().addAll(buttonHolder, label);
		container.setAlignment(Pos.BASELINE_RIGHT);
		
		this.setCenter(container);
	}

	/**
	 * Helper method to create buttons. When a button is clicked the controller
	 * is called with the number of passengers to play videos.
	 * @param numPassengers
	 * @return
	 */
	private Button makeButton(int numPassengers) {
		Button button = new Button(""+numPassengers);
		button.setOnAction(event -> myControl.playVideo(numPassengers));
		button.getStyleClass().add("numPassengersButton");
		return button;
	}
}
