package video;
public class Video {
	
	public static final double CENTS_PER_SECOND = 0.01;

	private String myCompany;
	private String myName;
	private int myPlaysRemaining;
	private int myLength;

	/**
	 * Video constructor
	 * @param name
	 * @param playsRemaining
	 * @param length
	 * @param company
	 */
	public Video(String company, String name, int playsRemaining, int length){
		myCompany = company;
		myName = name;
		myPlaysRemaining = playsRemaining;
		myLength = length;
	}

	public String getMyName() {
		return myName;
	}

	public int getMyPlaysRemaining() {
		return myPlaysRemaining;
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
		return myPlaysRemaining > 0;
	}
	
	public void subtractViews(int numPassengers){
		System.out.print("Num views remaining reduced from " + myPlaysRemaining);
		myPlaysRemaining -= numPassengers;
		System.out.println(" to " + myPlaysRemaining);
	}

	public String getPath() {
		return "./src/videos/" + myCompany.replaceAll(" ", "") + "_" + myName + ".mp4";
	}
}