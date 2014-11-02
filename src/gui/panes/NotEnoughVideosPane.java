package gui.panes;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * This scene is displayed when none of the videos have any unused views remaining.
 * @author Austin Kyker
 */
public class NotEnoughVideosPane extends BorderPane {

	public NotEnoughVideosPane() {
		this.setCenter(new Label("There are not enough videos for you to drive. "
								+ "You will not be paid for continued driving."));
	}

}
