package gui.player;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class KogoVideoScene extends Scene {

	public KogoVideoScene(BorderPane root) {
		super(root);
		Label label = new Label("Kogo");
		label.setStyle("-fx-font-size: 200px");
		root.setCenter(label);	
	}	
}