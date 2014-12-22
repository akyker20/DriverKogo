package utilities;

import java.util.Observable;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Validator extends Observable {
	
	public Validator(String key, String message) {
		Stage stage = new Stage();
		VBox box = new VBox(10);
		box.setAlignment(Pos.CENTER);
		Label msg = new Label(message);
		Label prompt = new Label("Enter initials:");
		TextField textField = new TextField();
		textField.setOnAction(event-> {
			if(textField.getText().equalsIgnoreCase(key)) {
				this.setChanged();
				this.notifyObservers();
				stage.close();
			}
		});
		box.getChildren().addAll(msg, prompt, textField);
		Scene scene = new Scene(box);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
}
