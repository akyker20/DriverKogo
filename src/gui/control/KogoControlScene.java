package gui.control;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class KogoControlScene extends ControlScene {
	
	private static final String TITLE = "Ride in Progress";

	public KogoControlScene(BorderPane root) {
		super(root, TITLE);
		this.setFill(Color.BLACK);
		Label label = new Label("Kogo");
		label.setStyle("-fx-font-size: 40px");
		label.setAlignment(Pos.CENTER);
		root.setCenter(label);
	}

}
