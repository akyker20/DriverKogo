package gui.player;

import video.ActiveVideo;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import control.Controller;

public class VideoStage extends Stage {

	private Controller myControl;
	private KogoVideoScene myKogoScene;
	private VideoScene myVideoScene;

	public VideoStage(Controller control) {
		myControl = control;
		myKogoScene = new KogoVideoScene(new BorderPane());
		myVideoScene = new VideoScene(new BorderPane(), myControl);
		this.setScene(myKogoScene);
		this.show();
	}
	
	public void playVideo(ActiveVideo video) {
		this.setScene(myVideoScene);
		myVideoScene.playVideo(video);
	}

	public void showKogoScene() {
		this.setScene(myKogoScene);
	}
}	