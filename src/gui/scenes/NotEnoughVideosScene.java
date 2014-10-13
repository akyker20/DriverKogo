package gui.scenes;

import gui.GUIController;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class NotEnoughVideosScene extends Scene {

	public NotEnoughVideosScene(BorderPane pane) {
		super(pane, GUIController.SCREEN_WIDTH, GUIController.SCREEN_HEIGHT);
		pane.setCenter(new Label("There are not enough videos for you to drive. "
								+ "You will not be paid for continued driving."));
	}

}
