package gui.player;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class KogoVideoScene extends Scene {

	public KogoVideoScene(BorderPane root) {
		super(root, 600, 400);
		Label label = new Label("Kogo");
		root.setCenter(label);	
	}	
}