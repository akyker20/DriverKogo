package gui.scenes;

import java.io.File;

import video.Video;
import control.Controller;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 * The purpose of this class is to create the scene that will play videos.
 * @author Austin Kyker
 *
 */
public class VideoScene extends Scene {

	private Controller myControl;
	private MediaPlayer myMediaPlayer;
	private MediaView myMediaView;
	
	
	public VideoScene(BorderPane parent, Controller control) {
		super(parent);
		myControl = control;
		myMediaView = new MediaView(myMediaPlayer);
		parent.setCenter(myMediaView);
	}

	/**
	 * Sets up the video in the media player and plays it.
	 * @param video
	 */
	public void setUpVideo(Video video) {
		File videoFile = new File(video.getPath());
		Media media = new Media(videoFile.toURI().toString());
		myMediaPlayer = new MediaPlayer(media);
		myMediaPlayer.setOnEndOfMedia(new Runnable(){
			@Override
			public void run() {
				myControl.completedVideo(video);
				myControl.playAnotherVideo();			
			}
		});
		myMediaView.setMediaPlayer(myMediaPlayer);
		myMediaPlayer.play();
	}
}
