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

public class DragFileScene extends Scene {

	public DragFileScene(Group root, Controller control) {
		super(root);
		
		LocalDateTime now = LocalDateTime.now(); 
	    String date = now.getMonthValue() + "_" + now.getDayOfMonth() + "_" + now.getYear();
	    String fileName = "kogo_" + date + ".xml";

		Label label = new Label("Drop the kogo file here...");
		label.setLayoutX(210);
		label.setLayoutY(170);
		root.getChildren().add(label);
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
		
		this.setOnDragExited(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				DragFileScene.this.setFill(Color.WHITE);
			}
		});

		// Dropping over surface
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
						System.out.println(fileName);
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
