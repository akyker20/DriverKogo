package gui.player;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import video.ActiveVideo;
import control.Controller;

public class VideoStage extends Stage {

	private static final String NO_MSG = "";
	private Controller myControl;
	private KogoVideoScene myKogoScene;
	private VideoScene myVideoScene;

	public VideoStage(Controller control) {
		myControl = control;
		myKogoScene = new KogoVideoScene(new BorderPane());
		myVideoScene = new VideoScene(new BorderPane(), myControl);
		this.setScene(myKogoScene);
		this.enterFullScreenMode();
		this.show();
	}
	
	private void enterFullScreenMode() {
		this.setFullScreen(true);
		this.setFullScreenExitHint(NO_MSG);		
	}

	public void playVideo(ActiveVideo video) {
		this.setScene(myVideoScene);
		myVideoScene.playVideo(video);
		this.enterFullScreenMode();
	}

	public void showKogoScene() {
		this.setScene(myKogoScene);
		this.enterFullScreenMode();
	}

	public void stopPlayingVideos() {
		myVideoScene.pause();
		this.showKogoScene();
		
	}
}	