package video;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import control.Controller;

/**
 * This class is used to select the next video to be played.
 * @author Austin Kyker
 *
 */
public class VideoManager {

	private Random myRandomGenerator;
	private List<ActiveVideo> myVideoList;
	private File myJsonVideoFile;

	public VideoManager(File videoJsonFile){
		myRandomGenerator = new Random();
		myJsonVideoFile = videoJsonFile;
		DriverSessionData data = 
				Controller.GSON_READER.readDriverSessionData(myJsonVideoFile);
		myVideoList = data.getVideos();
		resetVideosForNewRide();
	}

	public ActiveVideo selectVideo(){
		List<ActiveVideo> playableVideos = getPlayableVideos();
		return playableVideos.get(myRandomGenerator.nextInt(playableVideos.size()));	
	}

	public List<ActiveVideo> getPlayableVideos() {
		return myVideoList.stream()
				.filter(ActiveVideo::canPlay)
				.collect(Collectors.toList());
	}

	public boolean canSelectVideo() {
		return !this.getPlayableVideos().isEmpty();
	}

	public void updateVideoJson() {
		Controller.GSON_WRITER.updateVideoJson(myJsonVideoFile, myVideoList);	
	}

	public void resetVideosForNewRide() {
		for(ActiveVideo vid:myVideoList)
			vid.prepareForNewRide();	
	}

	public void terminateVideoDeliverable(String fileName) {
		Controller.GSON_WRITER.terminateDeliverable(myJsonVideoFile, 
				Controller.GSON_READER.readDriverSessionData(myJsonVideoFile),
				fileName);
	}

	public boolean videoViewsStillExist() {
		return myVideoList.stream()
				.filter(video->video.hasPlaysRemaining())
				.count() > 0;
	}
}