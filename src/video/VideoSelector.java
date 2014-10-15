package video;

import java.util.Random;

import control.Controller;

/**
 * This class is used to select the next video to be played.
 * @author Austin Kyker
 *
 */
public class VideoSelector {
	
	private Random myRandomGenerator;
	
	public VideoSelector(Controller control){
		myRandomGenerator = new Random();
	}
	
	/**
	 * This method receives the playable videos from the controller
	 * and then uses a random number generator to choose one.
	 * @return the selected video to be played next
	 */
	public Video selectVideo(Object[] playableVideos){
		int numPlayableVideos = playableVideos.length;
		if(numPlayableVideos > 0){
			return (Video) playableVideos[myRandomGenerator.nextInt(numPlayableVideos)];
		}		
		return null;		
	}	
}