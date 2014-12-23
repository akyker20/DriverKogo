package gui.player;

import java.io.File;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import video.ActiveVideo;
import control.Controller;

public class VideoScene extends Scene {

	private Controller myControl;
	private MediaPlayer myMediaPlayer;
	private MediaView myMediaView;
	private boolean isMediaViewInitialized;

	public VideoScene(BorderPane root, Controller control) {
		super(root);
		myControl = control;
		myMediaView = new MediaView(myMediaPlayer);
		root.setCenter(myMediaView);
	}

	public void playVideo(ActiveVideo video) {
		setUpMediaPlayerWith(video);
		myMediaView.setMediaPlayer(myMediaPlayer);
		if(!isMediaViewInitialized){
			initializeMediaViewSize();
		}
		play();
	}

	private void setUpMediaPlayerWith(ActiveVideo video) {
		myMediaPlayer = new MediaPlayer(getVideoMedia(video));
		myMediaPlayer.setOnEndOfMedia(new Runnable(){
			@Override
			public void run() {
				myControl.completedVideoDuringRide(video);			
			}
		});
	}

	private Media getVideoMedia(ActiveVideo video) {
		File videoFile = new File(getPathToVideo(video));
		return new Media(videoFile.toURI().toString());
	}

	private String getPathToVideo(ActiveVideo video) {
		return myControl.getVideoDirPath() + video.getMyCompany().replace(" ", "") + 
				"_" + video.getMyName() + ".mp4";
	}

	public void play() {
		myMediaPlayer.play();
	}

	public void pause() {
		myMediaPlayer.pause();
	}

	public boolean isPlaying() {
		return myMediaPlayer.getStatus() == Status.PLAYING;
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



