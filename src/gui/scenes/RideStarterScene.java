package gui.scenes;

import menus.MenuFeature;
import gui.GUIController;
import control.Controller;
import video.VideoSelector;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

public class RideStarterScene extends Scene {

	private Controller myControl;
    public static final String STYLESHEET_PACKAGE = "Stylesheets/";
	
	public RideStarterScene(BorderPane parent, Controller control, MenuFeature menu) {
		super(parent, GUIController.SCREEN_WIDTH, GUIController.SCREEN_HEIGHT);
		this.getStylesheets().add(STYLESHEET_PACKAGE + "style.css");
		myControl = control;
		VBox container = new VBox(10);
		HBox buttonHolder = new HBox(10);
		buttonHolder.setId("ButtonHolder");
		buttonHolder.getChildren().addAll(makeButton(1), makeButton(2), makeButton(3));
		Label label = new Label("Number of Students");
		label.setId("NumStudentsLabel");
		container.getChildren().addAll(label, buttonHolder);
		parent.setCenter(container);
		parent.setTop(menu);
	}

	private Button makeButton(int numPassengers) {
		Button button = new Button(""+numPassengers);
		button.setOnAction(event -> myControl.playVideo(numPassengers));
		button.getStyleClass().add("numPassengersButton");
		return button;
	}
}
