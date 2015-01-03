package utilities;

import java.util.Observable;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Validator extends Observable {
	

	private static final int FIELD_WIDTH = 100;
	private static final int VALIDATOR_WIDTH = 250;
	private static final int VALIDATOR_HEIGHT = 200;
	
	public Validator(String key, String message, double stageX, double stageY) {
		Stage stage = new Stage();
		stage.setTitle("Validation");
		VBox box = new VBox(10);
		box.setAlignment(Pos.CENTER);
		box.setPadding(new Insets(10));
		Label msg = new Label(message);
		msg.setWrapText(true);
		Label prompt = new Label("Enter initials:");
		TextField textField = new TextField();
		textField.setAlignment(Pos.CENTER);
		textField.setMaxWidth(FIELD_WIDTH);
		textField.setOnAction(event-> {
			if(textField.getText().equalsIgnoreCase(key)) {
				this.setChanged();
				this.notifyObservers();
				stage.close();
			}
		});
		box.getChildren().addAll(msg, prompt, textField);
		Scene scene = new Scene(box, VALIDATOR_WIDTH, VALIDATOR_HEIGHT);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setX(stageX);
		stage.setY(stageY);
		stage.show();
	}
}
