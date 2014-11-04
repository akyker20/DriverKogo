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

	public void setUpVideo(Video video) {
		setUpMediaPlayerWith(video);
		myMediaView.setMediaPlayer(myMediaPlayer);
		if(!isMediaViewInitialized){
			initializeMediaViewSize();
		}
		play();
	}

	private void setUpMediaPlayerWith(Video video) {
		myMediaPlayer = new MediaPlayer(getVideoMedia(video));
		myMediaPlayer.setOnEndOfMedia(new Runnable(){
			@Override
			public void run() {
				try {
					myControl.completedVideoDuringRide(video);
				} catch (TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}
		});
	}
	
	private Media getVideoMedia(Video video) {
		File videoFile = new File(getPathToVideo(video));
		return new Media(videoFile.toURI().toString());
	}
	
	private String getPathToVideo(Video video) {
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
