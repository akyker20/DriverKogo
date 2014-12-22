package video;

import java.util.List;

public class TransferVideoData {
	
	//Termination status is set to true after the driver 
	//clicks finished riding.
	private boolean myTerminationStatus;
	private List<ActiveVideo> myVideos;
	
	public TransferVideoData(List<ActiveVideo> videos) {
		myTerminationStatus = false;
		myVideos = videos;
	}
	
	public boolean isTerminated() {
		return myTerminationStatus;
	}
	
	public void terminate() {
		myTerminationStatus = true;
	}
	
	public List<ActiveVideo> getVideos() {
		return myVideos;
	}
}