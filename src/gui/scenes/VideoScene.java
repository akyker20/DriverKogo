package gui.scenes;

import gui.GUIController;

import java.io.File;

import javax.xml.transform.TransformerException;

import menus.MenuFeature;
import video.Video;
import xmlcontrol.XMLController;
import control.Controller;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;

/**
 * The purpose of this class is to create the scene that will play videos.
 * @author Austin Kyker
 *
 */
public class VideoScene extends Scene {

	private Controller myControl;
	private MediaPlayer myMediaPlayer;
	private MediaView myMediaView;
	private Button endRideButton;
	private MenuFeature myMenuFeature;
	private VBox myMenuContainer;
	private GUIController myGUIController;
	
	
	public VideoScene(BorderPane parent, Controller control, GUIController controller) {
		super(parent);
		myMenuFeature = new MenuFeature(control);
		myMenuFeature.enableEndRideItem();
		myControl = control;
		myMediaView = new MediaView(myMediaPlayer);
		myGUIController = controller;
		
		
		 DoubleProperty width = myMediaView.fitWidthProperty();
		 DoubleProperty height = myMediaView.fitHeightProperty();
		    
		 width.bind(Bindings.selectDouble(myMediaView.sceneProperty(), "width"));
		 height.bind(Bindings.selectDouble(myMediaView.sceneProperty(), "height"));
		    
		 myMediaView.setPreserveRatio(true);
		
		parent.setCenter(myMediaView);
		
		myMenuContainer = new VBox();
		parent.setTop(myMenuContainer);
		this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle (KeyEvent e) {
                if (e.getCode().equals(KeyCode.SPACE)) {
                   if(myMediaPlayer.getStatus() == Status.PLAYING){
                	   myMediaPlayer.pause();
                	   myMenuContainer.getChildren().add(myMenuFeature);
                   }
                   else{
                	   
                	   if(!myGUIController.isFullScreen()){
                		   myGUIController.makeFullScreen();
                	   }
                	   myMediaPlayer.play();
                	   myMenuContainer.getChildren().remove(myMenuFeature);
                   }
                }
                if(e.getCode().equals(KeyCode.ESCAPE)){
                	myMediaPlayer.pause();
                	if(!myMenuContainer.getChildren().contains(myMenuFeature))
                		myMenuContainer.getChildren().add(myMenuFeature);
                }
            }
        });
	}

	/**
	 * Sets up the video in the media player and plays it.
	 * @param video
	 */
	public void setUpVideo(Video video) {
		myMenuContainer.getChildren().remove(myMenuFeature);
		File videoFile = new File(video.getPath());
		Media media = new Media(videoFile.toURI().toString());
		myMediaPlayer = new MediaPlayer(media);
		myMediaPlayer.setOnEndOfMedia(new Runnable(){
			@Override
			public void run() {
				try {
					myControl.completedVideo(video);
				} catch (TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				myControl.playAnotherVideo();			
			}
		});
		myMediaView.setMediaPlayer(myMediaPlayer);
		myMediaPlayer.play();
	}
}
