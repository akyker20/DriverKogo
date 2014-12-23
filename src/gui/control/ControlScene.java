package gui.control;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class ControlScene extends Scene {
	
	private static final int SCREEN_WIDTH = 600;
	private static final int SCREEN_HEIGHT = 270;
	
	private String myTitle;

	public ControlScene(BorderPane root, String title) {
		super(root, SCREEN_WIDTH, SCREEN_HEIGHT);
		myTitle = title;
	}
	
	public String getTitle() {
		return myTitle;
	}
}