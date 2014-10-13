package video;

import java.util.Random;

import control.Controller;

public class VideoSelector {
	
	private Random myRandomGenerator;
	private Controller myController;
	
	public VideoSelector(Controller control){
		myRandomGenerator = new Random();
		myController = control;
	}
	
	public Video selectVideo(){
		Object[] playableVideos = myController.playableVideos();
		int numPlayableVideos = playableVideos.length;
		if(numPlayableVideos > 0){
			return (Video) playableVideos[myRandomGenerator.nextInt(numPlayableVideos)];
		}		
		return null;		
	}	
}
