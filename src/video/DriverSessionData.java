package video;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class DriverSessionData {
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM-dd-yyyy");
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:SS Z");
	
	private boolean myTerminationStatus;
	private List<ActiveVideo> myVideos;
	private LocalDate myDate;
	private String myFileName;
	private Date myFinishTime;
	private int myDriverNumRides;
	private int myDriverSeconds;
	private Date myStartTime;
	
	public DriverSessionData(List<ActiveVideo> videos) {
		this(videos, LocalDate.now());
	}
	
	public DriverSessionData(List<ActiveVideo> videos, LocalDate date) {
		myTerminationStatus = false;
		myVideos = videos;
		myDate = date;
		myDriverNumRides = 0;
		myDriverSeconds = 0;
	}
	
	public int getDriverSeconds() {
		return myDriverSeconds;
	}
	
	public int getDriverNumRides() {
		return myDriverNumRides;
	}
	
	public boolean isTerminated() {
		return myTerminationStatus;
	}
	
	public void terminate() {
		myTerminationStatus = true;
		myFinishTime = new Date();
	}
	
	public void setStartTime() {
		if(myStartTime == null)
			myStartTime = new Date();
	}
	
	public String getStartTime() {
		return DATE_FORMAT.format(myStartTime);
	}
	
	public String getFinishTime() {
       return DATE_FORMAT.format(myFinishTime);
	}
	
	public List<ActiveVideo> getVideos() {
		return myVideos;
	}
	
	public String getDate() {
		return myDate.format(FORMATTER);
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

	public void completedVideo(ActiveVideo videoCompleted) {
		myDriverNumRides++;
		myDriverSeconds += videoCompleted.getMyLength();		
	}
}