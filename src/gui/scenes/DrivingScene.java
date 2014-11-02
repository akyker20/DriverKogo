package gui.scenes;

import gui.GUIController;
import gui.panes.NotEnoughVideosPane;
import gui.panes.RideStarterPane;
import gui.panes.VideoPane;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import menus.MenuFeature;
import video.Video;
import control.Controller;

public class DrivingScene extends Scene {

	private RideStarterPane myRideStarterPane;
	private VideoPane myVideoPane;
	private MenuFeature myMenuFeature;
	private Controller myControl;
	private GUIController myGUIController;
	private BorderPane myPane;

	public DrivingScene(BorderPane root, Controller control, GUIController guiControl) {
		super(root);
		myControl = control;
		myGUIController = guiControl;
		myPane = root;
		myMenuFeature = new MenuFeature(control);
		myPane.setTop(myMenuFeature);
		this.setUpPane();
		this.setOnKeyPressed(event->handleKeyPress(event));
	}

	private void handleKeyPress(KeyEvent event) {
		if(myPane.getCenter().equals(myVideoPane)){
			KeyCode code = event.getCode();
			if (code.equals(KeyCode.SPACE) && myVideoPane.isPlaying() || 
					code.equals(KeyCode.ESCAPE)) {
				myVideoPane.pause();
				showMenu();
			}
			else if(code.equals(KeyCode.SPACE)){
				myGUIController.makeFullScreen();
				myVideoPane.play();
				hideMenu();
			}
		}
	}

	private void setUpPane() {
		if(myControl.canPlayVideos()){
			myRideStarterPane = new RideStarterPane(myControl);
			myVideoPane = new VideoPane(myControl);
			showStartRideScreen();
		}
		else {
			showNotEnoughVideos();
		}
	}


	public void showVideo(Video video) {
		myPane.getChildren().remove(myPane.getCenter());
		myVideoPane.setUpVideo(video);
		myMenuFeature.configureEndRideMenu();
		myPane.setCenter(myVideoPane);
		hideMenu();
	}

	public void showNotEnoughVideos() {
		myPane.getChildren().remove(myPane.getCenter());
		myMenuFeature.configureFinishDrivingMenu();
		myPane.setCenter(new NotEnoughVideosPane());
	}

	public void showStartRideScreen() {
		myPane.getChildren().remove(myPane.getCenter());
		myMenuFeature.configureFinishDrivingMenu();
		myPane.setCenter(myRideStarterPane);
	}

	public void hideMenu() {
		myPane.getChildren().remove(myMenuFeature);
	}

	public void showMenu() {
		if(!myPane.getChildren().contains(myMenuFeature))
			myPane.setTop(myMenuFeature);
	}
}