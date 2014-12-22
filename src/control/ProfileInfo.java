package control;

public class ProfileInfo {
	private boolean myInitializationStatus;
	private String myInitials;
	
	public ProfileInfo(String initials) {
		myInitializationStatus = false;
		myInitials = initials;
	}
	
	public void initialize() {
		myInitializationStatus = true;
	}
	
	public boolean isInitialized() {
		return myInitializationStatus;
	}
	
	public String getInitials() {
		return myInitials;
	}
}
