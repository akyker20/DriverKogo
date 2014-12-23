package gui.control;

import java.io.File;
import java.time.LocalDateTime;

import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import utilities.ErrorPopup;
import control.Controller;

/**
 * Scene for dragging and dropping master XML File. This screen is shown to the
 * user when the application begins. The user will only be able to drag and drop
 * an XML File with the correct title pertaining to the date of the session.
 * 
 * @author Austin Kyker
 *
 */
public class DragFileScene extends ControlScene {
	
	private static final String WRONG_DEL_DAY_ERROR_MSG = 
			"Deliverable for wrong day driving day. Contact Austin at 317-979-7549 "
					+ "for more information";
	private static final String DROP_INSTRUCTIONS = "Drop the kogo file here...";
	private static final String TITLE = "Drag and Drop";

	private Controller myControl;
	private BorderPane myPane;

	public DragFileScene(BorderPane root, Controller control) {
		super(root, TITLE);
		myPane = root;
		myPane.setStyle("-fx-background-color: white");
		myControl = control;
		setupEventListeners();
		addInstructionsToBottomRight();
	}

	private void setupEventListeners() {
		this.setOnDragOver(event -> handleDragOver(event));
		this.setOnDragExited(event -> myPane.setStyle("-fx-background-color: white"));
		this.setOnDragDropped(event -> handleDropFile(event));
	}

	private void addInstructionsToBottomRight() {
		Label label = new Label(DROP_INSTRUCTIONS);
		myPane.setCenter(label);
	}

	private void handleDropFile(DragEvent event) {
		Dragboard db = event.getDragboard();
		boolean success = false;
		if (db.hasFiles()) {
			success = true;
			for (File directory : db.getFiles()) {
				if (isValidKogoDirectory(directory)) {
					File jsonFile = getVideoJsonFileFromDirectory(directory);
					if(!myControl.isDirectoryTerminated(jsonFile)){
						myControl.initializeDrivingEnvironment(directory, jsonFile);
					}
				}
			}
		}
		event.setDropCompleted(success);
		event.consume();
	}

	private boolean isValidKogoDirectory(File directory) {
		String filePath = directory.getPath();
		return directory.isDirectory() && 
				isCorrectDrivingDay(filePath);
	}

	private boolean isCorrectDrivingDay(String filePath) {
		boolean isCorrectDay = filePath.contains(getRequiredDirName());
		if (!isCorrectDay) {
			new ErrorPopup(WRONG_DEL_DAY_ERROR_MSG);
		}
		return isCorrectDay;
	}

	private File getVideoJsonFileFromDirectory(File directory) {
		for (File file : directory.listFiles()) {
			if (file.getAbsolutePath().contains("kogo")) {
				return file;
			}
		}
		return null;
	}

	private void handleDragOver(DragEvent event) {
		Dragboard db = event.getDragboard();
		if (db.hasFiles()) {
			event.acceptTransferModes(TransferMode.COPY);
			myPane.setStyle("-fx-background-color: lightgreen");
		} else {
			event.consume();
		}
	}

	private CharSequence getRequiredDirName() {
		LocalDateTime now = LocalDateTime.now();
		String date = now.getMonthValue() + "-"
				+ formatDayToHaveTwoDigits(now.getDayOfMonth()) + "-"
				+ now.getYear();
		return "deliverable_".concat(date);
	}

	private String formatDayToHaveTwoDigits(int dayOfMonth) {
		if (dayOfMonth < 10)
			return "0" + dayOfMonth;
		return "" + dayOfMonth;
	}
}
