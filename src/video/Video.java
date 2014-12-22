package video;

public abstract class Video {

	public static final double CENTS_PER_SECOND = 0.010;

	private String myCompany;
	private String myName;
	protected int myPlays;
	private int myLength;
	private double myRevenue;
	private int myTotalPlaysPurchased;

	public Video(String company, String name, int length,
			int totalPlaysPurchased, int plays) {
		myCompany = company;
		myName = name;
		myLength = length;
		myTotalPlaysPurchased = totalPlaysPurchased;
		myPlays = plays;
	}

	public int getMyPlaysPurchased() {
		return myTotalPlaysPurchased;
	}

	public int getMyPlays() {
		return myPlays;
	}

	public String getMyName() {
		return myName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	public int getMyLength() {
		return myLength;
	}

	public String getMyCompany() {
		return myCompany;
	}

	public void setMyCompany(String myCompany) {
		this.myCompany = myCompany;
	}

	public boolean sameAs(ActiveVideo other) {
		return other.getMyCompany().equals(myCompany)
				&& other.getMyName().equals(myName);
	}

	public double getMyRevenue() {
		myRevenue = myLength * CENTS_PER_SECOND * getMyPlays();
		return Math.round(myRevenue * 100.00) / 100.00;
	}

	public abstract void addStudentViews(int numViews);

	@Override
	public boolean equals(Object otherVideo) {
		if (otherVideo == null) {
			return false;
		}
		return this.getMyCompany().equalsIgnoreCase(
				((Video) otherVideo).getMyCompany())
				&& this.getMyName().equalsIgnoreCase(
						((Video) otherVideo).getMyName());
	}
}
