package gui.control;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class KogoControlScene extends ControlScene {
	
	private static final String TITLE = "Ride in Progress";

	public KogoControlScene(BorderPane root, ControlStage control) {
		super(root, TITLE);
		this.setFill(Color.BLACK);
		Label label = new Label("Kogo");
		label.setStyle("-fx-font-size: 40px");
		label.setAlignment(Pos.CENTER);
		VBox container = new VBox(20);
		Button button = new Button("Start a new Ride");
		button.setOnAction(event->control.showRideStarterScene());
		container.getChildren().addAll(label, button);
		root.setCenter(container);
	}

}
