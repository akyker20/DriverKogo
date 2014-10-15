package gui.scenes;

import menus.MenuFeature;
import gui.GUIController;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * This scene is displayed when none of the videos have any unused views remaining.
 * @author Austin Kyker
 */
public class NotEnoughVideosScene extends Scene {

	public NotEnoughVideosScene(BorderPane pane, MenuFeature menu) {
		super(pane, GUIController.SCREEN_WIDTH, GUIController.SCREEN_HEIGHT);
		pane.setCenter(new Label("There are not enough videos for you to drive. "
								+ "You will not be paid for continued driving."));
		pane.setTop(menu);
	}

}
