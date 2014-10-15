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
 * When a video is playing and the user hits SPACE, the video is paused. If the video 
 * is paused and the user presses SPACE, the video is played. When the video is paused, 
 * the file menu is displayed that allows the user to complete a ride. If the user
 * hits the ESC key, full screen is exited, the file menu is displayed, and the
 * video is paused. From here, if the user presses SPACE, the stage becomes full screen
 * again and the video plays.
 * @author Austin Kyker
 * 
 */
public class VideoScene extends Scene {

	private Controller myControl;
	private MediaPlayer myMediaPlayer;
	private MediaView myMediaView;
	private MenuFeature myMenuFeature;
	private VBox myMenuContainer;
	private GUIController myGUIController;
	private boolean isMediaViewInitialized;

	public VideoScene(BorderPane parent, Controller control, GUIController controller) {
		super(parent);

		myMenuFeature = new MenuFeature(control);
		myMenuFeature.enableEndRideItem();
		myControl = control;
		myMediaView = new MediaView(myMediaPlayer);
		myGUIController = controller;

		parent.setCenter(myMediaView);

		myMenuContainer = new VBox();
		parent.setTop(myMenuContainer);

		this.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle (KeyEvent e) {
				if (e.getCode().equals(KeyCode.SPACE)) {
					if(myMediaPlayer.getStatus() == Status.PLAYING){
						pauseAndDisplayMenu();
					}
					else{             	   
						myGUIController.makeFullScreen();
						playAndRemoveMenu();
					}
				}
				else if(e.getCode().equals(KeyCode.ESCAPE)){
					pauseAndDisplayMenu();
				}
			}
		});
	}

	/**
	 * Plays the player and removes the menu.
	 */
	protected void playAndRemoveMenu() {
		myMediaPlayer.play();
		if(myMenuContainer.getChildren().contains(myMenuFeature)){
			myMenuContainer.getChildren().remove(myMenuFeature);
		}
	}

	/**
	 * Pauses the player and displays the menu.
	 */
	protected void pauseAndDisplayMenu() {
		myMediaPlayer.pause();
		if(!myMenuContainer.getChildren().contains(myMenuFeature)){
			myMenuContainer.getChildren().add(myMenuFeature);
		}
	}

	/**
	 * Sets up the video in the media player and plays it. When a video ends,
	 * the controller is notified so that changes can be made to the XML File.
	 * Another video is then selected to be played.
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
		if(!isMediaViewInitialized){
			initializeMediaViewSize();
		}
		myMediaPlayer.play();
	}

	/**
	 * Allows the scene to be full sized. Only happens one time.
	 */
	private void initializeMediaViewSize() {
		DoubleProperty width = myMediaView.fitWidthProperty();
		DoubleProperty height = myMediaView.fitHeightProperty();
		width.bind(Bindings.selectDouble(myMediaView.sceneProperty(), "width"));
		height.bind(Bindings.selectDouble(myMediaView.sceneProperty(), "height"));
		myMediaView.setPreserveRatio(true);
	}
}
