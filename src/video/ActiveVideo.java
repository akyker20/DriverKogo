package video;

public class ActiveVideo extends Video {

	public static final double CENTS_PER_SECOND = 0.01;

	private int myMaxPlays;
	private boolean alreadyPlayedThisRide;

	/**
	 * If the master file was already initialized then each video will have some
	 * number of plays. This constructor sets myPlays to the input
	 * playsCompleted.
	 * 
	 * @param playsCompleted
	 *            - the number of times the video has already been played.
	 * @param maxPlays
	 *            - the maximum number of times the video can be played.
	 */
	public ActiveVideo(String company, String name, int length,
			int playsPurchased, int plays, int maxPlays) {
		super(company, name, length, playsPurchased, plays);
		myMaxPlays = maxPlays;
		alreadyPlayedThisRide = false;
	}

	public int getMyMaxPlays() {
		return myMaxPlays;
	}

	public boolean canPlay() {
		return !alreadyPlayedThisRide && hasPlaysRemaining();
	}

	public boolean hasPlaysRemaining() {
		return myPlays < myMaxPlays;
	}

	public void addViews(int numPassengers) {
		myPlays += numPassengers;
		alreadyPlayedThisRide = true;
	}

	public void prepareForNewRide() {
		alreadyPlayedThisRide = false;
	}

	public void incrementCompletedViews(int playsCompleted) {
		myPlays += playsCompleted;
	}

	@Override
	public void addStudentViews(int numViews) {
		this.myPlays += numViews;
	}
}