package gui.control;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import control.Controller;

/**
 * This scene is displayed when there are no playable video remaining.
 * 
 * @author Austin Kyker
 */
public class NotEnoughVideosScene extends ControlScene {

	private static final String TITLE = "Not Enough Videos";

	public NotEnoughVideosScene(BorderPane pane, Controller control) {
		super(pane, TITLE);
		Label label = new Label(
				"There are not enough videos for you to drive. "
						+ "You will not be paid for continued driving.");
		Button button = new Button("Finish Session");
		button.setOnAction(event -> control.finishDriving());
		VBox box = new VBox(10);
		box.getChildren().addAll(label, button);
		box.setAlignment(Pos.CENTER);
		pane.setCenter(box);
	}

}
