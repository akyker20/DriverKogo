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
	 * @param company
	 * @param name
	 * @param maxPlays
	 * @param length
	 */
	public Video(String company, String name, int maxPlays, int length){
		this(company, name, 0, maxPlays, length);
	}

	/**
	 * If the master file was already initialized then each video will have some number
	 * of plays. This constructor sets myPlays to the input playsCompleted.
	 * @param company
	 * @param name
	 * @param playsCompleted - the number of times the video has already been played.
	 * @param maxPlays - the maximum number of times the video can be played.
	 * @param length
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

	/**
	 * @return true if the video has plays remaining and has not already
	 * been played that ride.
	 */
	public boolean canPlay(){
		return hasPlaysRemaining() && !alreadyPlayedThisRide;
	}

	/**
	 * @return true if the video has plays remaining.
	 */
	public boolean hasPlaysRemaining(){
		return myPlays < myMaxPlays;
	}

	/**
	 * Method called after a video is finished playing. Adds a number of views
	 * to the video equal to the number of passengers that were watching. Sets
	 * the alreadyPlayedThisRide flag so the video will not be played again in 
	 * the current ride if there are other playable videos that have not been played.
	 * @param numPassengers
	 */
	public void addViews(int numPassengers){
		myPlays += numPassengers;
		alreadyPlayedThisRide = true;
	}

	/**
	 * Prepares the video for a new ride by setting alreadyPlayedThisRide back to false
	 */
	public void prepareForNewRide(){
		alreadyPlayedThisRide = false;
	}
}