package video;

import java.time.LocalDate;
import java.util.List;

public class TransferVideoData {
	private boolean myTerminationStatus;
	private List<ActiveVideo> myVideos;
	private LocalDate myDate;
	
	public TransferVideoData(List<ActiveVideo> videos) {
		this(videos, LocalDate.now());
	}
	
	public TransferVideoData(List<ActiveVideo> videos, LocalDate date) {
		myTerminationStatus = false;
		myVideos = videos;
		myDate = date;
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
	
	public LocalDate getDate() {
		return myDate;
	}
}