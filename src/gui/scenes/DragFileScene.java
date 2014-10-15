package gui.scenes;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import control.Controller;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;

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

	public DragFileScene(Group root, Controller control) {
		super(root);
		
		LocalDateTime now = LocalDateTime.now(); 
	    String date = now.getMonthValue() + "_" + now.getDayOfMonth() + "_" + now.getYear();
	    String fileName = "kogo_" + date + ".xml";

		Label label = new Label(DROP_INSTRUCTIONS);
		label.setLayoutX(210);
		label.setLayoutY(170);
		root.getChildren().add(label);
		

		// When a file is dragged over the scene, the background becomes
		// green and a copy message is displayed near the mouse.
		this.setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				if (db.hasFiles()) {
					event.acceptTransferModes(TransferMode.COPY);
					DragFileScene.this.setFill(Color.LIGHTGREEN);
				} else {
					event.consume();
				}
			}
		});
		
		// Upon exiting, the background of the scene returns to White.
		this.setOnDragExited(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				DragFileScene.this.setFill(Color.WHITE);
			}
		});

		// When a file is actually dropped it is validated to
		// ensure it has the correct name. The controller is 
		// then called to initialize the driving environment.
		this.setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				boolean success = false;
				if (db.hasFiles()) {
					success = true;
					String filePath = null;
					for (File file:db.getFiles()) {
						filePath = file.getAbsolutePath();
						if(filePath.contains(fileName)){
							try {
								control.initializeDrivingEnvironment(file);
							} catch (ParserConfigurationException
									| SAXException | IOException
									| TransformerException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
				event.setDropCompleted(success);
				event.consume();
			}
		});
	}
}
