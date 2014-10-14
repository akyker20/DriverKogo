package video;
public class Video {
	
	public static final double CENTS_PER_SECOND = 0.01;

	private String myCompany;
	private String myName;
	private int myMaxPlays;
	private int myLength;
	private int myPlays;

	public Video(String company, String name, int maxPlays, int length){
		myCompany = company;
		myName = name;
		myMaxPlays = maxPlays;
		myLength = length;
		myPlays = 0;
	}
	
	public Video(String company, String name, int playsCompleted, int maxPlays, int length){
		myCompany = company;
		myName = name;
		myMaxPlays = maxPlays;
		myLength = length;
		myPlays = playsCompleted;
	}

	public String getMyName() {
		return myName;
	}
	
	public int getMyLength() {
		return myLength;
	}

	public String getMyCompany() {
		return myCompany;
	}
	
	/**
	 * @return true if the video has plays remaining.
	 */
	public boolean canPlay(){
		return myPlays < myMaxPlays;
	}
	
	public void addViews(int numPassengers){
		myPlays += numPassengers;
	}

	public String getPath() {
		return "./src/videos/" + myCompany.replaceAll(" ", "") + "_" + myName + ".mp4";
	}

	public int getMyPlays() {
		return myPlays;
	}

	public int getMyMaxPlays() {
		return myMaxPlays;
	}
}