package gui.control;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * This scene is displayed to user after they select to Finish Driving
 * from the file menu.
 * @author Austin Kyker
 *
 */
public class FinishedDrivingScene extends ControlScene {
	
	private static final String TITLE = "Finished Driving";
	
	public FinishedDrivingScene(BorderPane pane) {
		super(pane, TITLE);
		Label label = new Label("You have successfully completed a Kogo Driving\n"
				+ " session. Please send Austin the file that was\n"
				+ " saved to your desktop.");
		pane.setCenter(label);
	}
}
