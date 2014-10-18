package gui.scenes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.TransformerException;

import gui.GUIController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ProfileSetupScene extends Scene {
	
	private static final String INITIALS_REGEX = "[a-zA-Z]{3}";
	private static final String WELCOME_MESSAGE = "Welcome to Kogo, driver!";

	private TextField myInitials;
	private TextField myInitialsConfirmation;
	private GUIController myGUIController;
	private Pattern myPattern;

	public ProfileSetupScene(BorderPane pane, GUIController guiController) {
		super(pane, GUIController.SCREEN_WIDTH, GUIController.SCREEN_HEIGHT);
		myGUIController = guiController;
		VBox centerContainer = new VBox(15);
		Label label = new Label(WELCOME_MESSAGE);
		myPattern = Pattern.compile(INITIALS_REGEX);

		myInitials = makeTextField("Initials");
		myInitialsConfirmation = makeTextField("Initials Confirmation");
		Button button = new Button("Done");

		button.setOnAction(event->submitProfileInformation());
		centerContainer.getChildren().addAll(label, myInitials, myInitialsConfirmation, button);
		pane.setCenter(centerContainer);

	}




	private void submitProfileInformation() {
		String initials = myInitials.getText();
		String initialsConfirmed = myInitialsConfirmation.getText();
		Matcher matcher = myPattern.matcher(initials);
		if(matcher.find() && initials.equalsIgnoreCase(initialsConfirmed)){
			try {
				myGUIController.submitProfileInformation(initials.toLowerCase());
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}




	/**
	 * Helper method to make creation of text fields DRYer
	 * @param name
	 * @return a textfield
	 */
	private TextField makeTextField(String name) {
		TextField field = new TextField();
		field.setPromptText(name);
		return field;
	}
}
