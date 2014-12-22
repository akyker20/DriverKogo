package gui.control;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import control.Controller;

public class ProfileSetupScene extends Scene {

	private static final String INITIALS_REGEX = "[a-zA-Z]{3}";
	private static final String WELCOME_MESSAGE = "Welcome to Kogo, driver!";
	private static final String INITIALS = "Initials";
	private static final String INITIALS_CONFIRMED = "Initials Confirmation";

	private TextField myInitialsField;
	private TextField myInitialsConfirmedField;
	private Button mySubmitButton;
	private Controller myControl;

	public ProfileSetupScene(BorderPane pane, Controller control) {
		super(pane);
		myControl = control;
		createInitialsTextFieldsAndSubmitBtn();
		VBox centerContainer = new VBox(15);
		centerContainer.getChildren().addAll(getGraphicalComponents());
		pane.setCenter(centerContainer);
	}

	private Node[] getGraphicalComponents() {
		return new Node[]{	createWelcomeLabel(), myInitialsField, 
							myInitialsConfirmedField, mySubmitButton };
	}

	private Label createWelcomeLabel() {
		return new Label(WELCOME_MESSAGE);
	}

	private void createInitialsTextFieldsAndSubmitBtn() {
		myInitialsField = makeTextField(INITIALS);
		myInitialsConfirmedField = makeTextField(INITIALS_CONFIRMED);
		mySubmitButton = new Button("Done");
		mySubmitButton.setOnAction(event->submitProfileInformation());
	}

	private void submitProfileInformation() {
		if(initialsAreValid())
			myControl.submitProfileInformation(getCleanedInitials());
	}
	
	private boolean initialsAreValid() {
		String initials = myInitialsField.getText();
		String initialsConfirmed = myInitialsConfirmedField.getText();
		Matcher matcher = Pattern.compile(INITIALS_REGEX).matcher(initials);
		return matcher.find() && initials.equalsIgnoreCase(initialsConfirmed);
	}

	private String getCleanedInitials() {
		return myInitialsField.getText().toLowerCase();
	}

	private TextField makeTextField(String name) {
		TextField field = new TextField();
		field.setPromptText(name);
		return field;
	}
}
