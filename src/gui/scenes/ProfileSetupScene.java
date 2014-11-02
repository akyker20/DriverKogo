package gui.scenes;

import gui.GUIController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import javax.xml.transform.TransformerException;

import control.Controller;

public class ProfileSetupScene extends Scene {
	
	private static final String INITIALS_REGEX = "[a-zA-Z]{3}";
	private static final String WELCOME_MESSAGE = "Welcome to Kogo, driver!";
	private static final String INITIALS = "Initials";
	private static final String INITIALS_CONFIRMED = "Initials Confirmation";

	private TextField myInitials;
	private TextField myInitialsConfirmation;
	private Controller myControl;

	public ProfileSetupScene(BorderPane pane, Controller control) {
		super(pane, GUIController.SCREEN_WIDTH, GUIController.SCREEN_HEIGHT);
		myControl = control;
		VBox centerContainer = new VBox(15);
		Label label = new Label(WELCOME_MESSAGE);

		myInitials = makeTextField(INITIALS);
		myInitialsConfirmation = makeTextField(INITIALS_CONFIRMED);
		Button button = new Button("Done");

		button.setOnAction(event->submitProfileInformation());
		centerContainer.getChildren().addAll(label, myInitials, myInitialsConfirmation, button);
		pane.setCenter(centerContainer);
	}

	private void submitProfileInformation() {
		String initials = myInitials.getText();
		String initialsConfirmed = myInitialsConfirmation.getText();
		Matcher matcher = Pattern.compile(INITIALS_REGEX).matcher(initials);
		if(matcher.find() && initials.equalsIgnoreCase(initialsConfirmed)){
			try {
				myControl.submitProfileInformation(initials.toLowerCase());
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
