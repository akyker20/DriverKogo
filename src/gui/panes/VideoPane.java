package gui.panes;

import java.io.File;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;

import javax.xml.transform.TransformerException;

import video.Video;
import control.Controller;

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
public class VideoPane extends BorderPane {

	private Controller myControl;
	private MediaPlayer myMediaPlayer;
	private MediaView myMediaView;
	private boolean isMediaViewInitialized;

	public VideoPane(Controller control) {
		myControl = control;
		myMediaView = new MediaView(myMediaPlayer);
		this.setCenter(myMediaView);
	}

	public boolean isPlaying() {
		return myMediaPlayer.getStatus() == Status.PLAYING;
	}

	/**
	 * Plays the player and removes the menu.
	 */
	public void play() {
		myMediaPlayer.play();
	}

	/**
	 * Pauses the player and displays the menu.
	 */
	public void pause() {
		myMediaPlayer.pause();
	}

	/**
	 * Sets up the video in the media player and plays it. When a video ends,
	 * the controller is notified so that changes can be made to the XML File.
	 * Another video is then selected to be played.
	 * @param video
	 */
	public void setUpVideo(Video video) {
		File videoFile = new File(getPath(video));
		Media media = new Media(videoFile.toURI().toString());
		myMediaPlayer = new MediaPlayer(media);
		myMediaPlayer.setOnEndOfMedia(new Runnable(){
			@Override
			public void run() {
				try {
					myControl.completedVideoDuringRide(video);
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

	private String getPath(Video video) {
		return myControl.getVideoDirPath() + video.getMyCompany().replace(" ", "") + 
				"_" + video.getMyName() + ".mp4";
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
		isMediaViewInitialized = true;
	}
}
