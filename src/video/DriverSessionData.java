package video;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class DriverSessionData {
	private boolean myTerminationStatus;
	private List<ActiveVideo> myVideos;
	private LocalDate myDate;
	private String myFileName;
	private Date myFinishedDate;
	
	public DriverSessionData(List<ActiveVideo> videos) {
		this(videos, LocalDate.now());
	}
	
	public DriverSessionData(List<ActiveVideo> videos, LocalDate date) {
		myTerminationStatus = false;
		myVideos = videos;
		myDate = date;
	}
	
	public boolean isTerminated() {
		return myTerminationStatus;
	}
	
	public void terminate() {
		myTerminationStatus = true;
		myFinishedDate = new Date();
	}
	
	public Date getFinishedDate() {
		return this.myFinishedDate;
	}
	
	public List<ActiveVideo> getVideos() {
		return myVideos;
	}
	
	public LocalDate getDate() {
		return myDate;
	}
	
	public String getFileName() {
		return myFileName;
	}
	
	public void setFileName(String name) {
		this.myFileName = name;
	}
	
	public double getTotalRevenue() {
		return myVideos.stream().map(vid->vid.getMyRevenue()).reduce(0.0, (a, b) -> a+b); 
	}
	
	public int getTotalImpactSeconds() {
		return myVideos.stream().map(vid->vid.getMyPlays()*vid.getMyLength()).reduce(0, (a, b) -> a+b);
	}
}