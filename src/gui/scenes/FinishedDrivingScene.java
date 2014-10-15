package gui.scenes;

import menus.MenuFeature;
import gui.GUIController;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * This scene is displayed to user after they select to Finish Driving
 * from the file menu.
 * @author Austin Kyker
 *
 */
public class FinishedDrivingScene extends Scene {
	public FinishedDrivingScene(BorderPane pane, MenuFeature menu) {
		super(pane, GUIController.SCREEN_WIDTH, GUIController.SCREEN_HEIGHT);
		Label label = new Label("You have successfully completed a Kogo Driving\n"
				+ " session. Please send Austin the file that was\n"
				+ " saved to your desktop.");
		pane.setCenter(label);
		menu.enableFinishDrivingItem();
		pane.setTop(menu);
	}
}
