package gui.scenes;

import java.io.File;
import java.time.LocalDateTime;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import control.Controller;

/**
 * Scene for dragging and dropping master XML File.
 * This screen is shown to the user when the application begins.
 * The user will only be able to drag and drop an XML File with
 * the correct title pertaining to the date of the session.
 * @author Austin Kyker
 *
 */
public class DragFileScene extends Scene {

	private static final String DROP_INSTRUCTIONS = "Drop the kogo file here...";

	private Controller myControl;
	private Group myGroup;

	public DragFileScene(Group root, Controller control) {
		super(root);
		myGroup = root;
		myControl = control;
		setupEventListeners();
		addInstructionsToBottomRight();
	}

	private void setupEventListeners() {
		this.setOnDragOver(event->handleDragOver(event));
		this.setOnDragExited(event->DragFileScene.this.setFill(Color.WHITE));
		this.setOnDragDropped(event->handleDropFile(event));	
	}

	private void addInstructionsToBottomRight() {
		Label label = new Label(DROP_INSTRUCTIONS);
		label.setLayoutX(210);
		label.setLayoutY(170);
		myGroup.getChildren().add(label);
	}

	private void handleDropFile(DragEvent event) {
		Dragboard db = event.getDragboard();
		boolean success = false;
		if (db.hasFiles()) {
			success = true;
			for (File directory:db.getFiles()) {
				if(isValidKogoDirectory(directory)){
					File xmlFile = getXMLFileFromDirectory(directory);
					myControl.initializeDrivingEnvironment(directory, xmlFile);
				}
			}
		}
		event.setDropCompleted(success);
		event.consume();
	}

	private boolean isValidKogoDirectory(File directory) {
		String filePath = directory.getAbsolutePath();
		return directory.isDirectory() && filePath.contains(getAppropriateFileName());
	}

	private File getXMLFileFromDirectory(File directory) {
		for(File file:directory.listFiles()){
			if(file.getAbsolutePath().contains("kogo")){
				return file;
			}
		}
		return null;
	}

	private void handleDragOver(DragEvent event) {
		Dragboard db = event.getDragboard();
		if (db.hasFiles()) {
			event.acceptTransferModes(TransferMode.COPY);
			DragFileScene.this.setFill(Color.LIGHTGREEN);
		} else {
			event.consume();
		}
	}

	private CharSequence getAppropriateFileName() {
		LocalDateTime now = LocalDateTime.now(); 
		String date = now.getMonthValue() + "-" + formatDayToHaveTwoDigits(now.getDayOfMonth()) + 
				"-" + now.getYear();
		return "deliverable_".concat(date);
	}

	private String formatDayToHaveTwoDigits(int dayOfMonth) {
		if(dayOfMonth < 10)
			return "0" + dayOfMonth;
		return ""+dayOfMonth;
	}
}
