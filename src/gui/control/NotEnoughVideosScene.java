package gui.control;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * This scene is displayed when there are no playable video remaining.
 * @author Austin Kyker
 */
public class NotEnoughVideosScene extends ControlScene {

	private static final String TITLE = "Not Enough Videos";

	public NotEnoughVideosScene(BorderPane pane) {
		super(pane, TITLE);
		pane.setCenter(new Label("There are not enough videos for you to drive. "
								+ "You will not be paid for continued driving."));
	}

}
