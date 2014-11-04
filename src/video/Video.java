package video;

public class Video {

	public static final double CENTS_PER_SECOND = 0.01;

	private String myCompany;
	private String myName;
	private int myMaxPlays;
	private int myLength;
	private int myPlays;
	private boolean alreadyPlayedThisRide;

	/**
	 * If the master XML File is not initialized then none of the videos have
	 * been played. Therefore there is no completedPlays input and myPlays is
	 * set to 0.
	 */
	public Video(String company, String name, int maxPlays, int length){
		this(company, name, 0, maxPlays, length);
	}

	/**
	 * If the master file was already initialized then each video will have some number
	 * of plays. This constructor sets myPlays to the input playsCompleted.
	 * @param playsCompleted - the number of times the video has already been played.
	 * @param maxPlays - the maximum number of times the video can be played.
	 */
	public Video(String company, String name, int playsCompleted, int maxPlays, int length){
		myCompany = company;
		myName = name;
		myMaxPlays = maxPlays;
		myLength = length;
		myPlays = playsCompleted;
		alreadyPlayedThisRide = false;
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
	
	public int getMyPlays() {
		return myPlays;
	}

	public int getMyMaxPlays() {
		return myMaxPlays;
	}

	public boolean canPlay(){
		return hasPlaysRemaining() && !alreadyPlayedThisRide;
	}

	public boolean hasPlaysRemaining(){
		return myPlays < myMaxPlays;
	}

	public void addViews(int numPassengers) {
		myPlays += numPassengers;
		alreadyPlayedThisRide = true;
	}

	public void prepareForNewRide(){
		alreadyPlayedThisRide = false;
	}
}