package gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import utilities.ErrorPopup;
import video.DriverSessionData;

import com.google.gson.GsonBuilder;

import control.ProfileInfo;

public class GSONFileReader {

	private static final String READ_ERROR_MSG = "Could not read from the videos json file."
			+ "Contact Austin at 317-979-7549";
	private static final String PROFILE_ERROR = "Error establishing profile. Contact Austin" +
			" at 317-979-7549";
	protected static final String PROFILE_PATH = "./src/json/driver_profile.json";
	private static final GsonBuilder GSON_BUILDER = new GsonBuilder();

	public DriverSessionData readDriverSessionData(File jsonVideoFile) {
		DriverSessionData data = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(jsonVideoFile));
			data = GSON_BUILDER.create().fromJson(br, DriverSessionData.class);
		} catch (IOException e) {
			new ErrorPopup(READ_ERROR_MSG);
		}
		return data;
	}
	
	public boolean isDeliverableTerminated(File jsonVideoFile) {
		return readDriverSessionData(jsonVideoFile).isTerminated();
	}

	public ProfileInfo getProfileInfo() {
		ProfileInfo info = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(PROFILE_PATH));
			info = GSON_BUILDER.create().fromJson(br, ProfileInfo.class);
		} catch (IOException e) {
			new ErrorPopup(PROFILE_ERROR);
		}
		return info;
	}
}