package gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import utilities.ErrorPopup;
import video.ActiveVideo;
import video.DriverSessionData;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import control.ProfileInfo;

public class GSONFileWriter {

	private static final GsonBuilder GSON_BUILDER = new GsonBuilder();


	public void writeMasterFile(List<ActiveVideo> videos) {
		writeToFile(
				"./src/json/videos.json",
				GSON_BUILDER.create().toJson(videos,
						new TypeToken<List<ActiveVideo>>() {
				}.getType()));
	}

	public void writeToFile(String fileName, String json) {
		try {
			File file = new File(fileName);
			file.setWritable(true);
			FileWriter writer = new FileWriter(file);
			writer.write(json);
			writer.close();
			file.setReadOnly();
		} catch (IOException e) {
			new ErrorPopup("Could not write to " + fileName);
		}
	}

	public void updateVideoJson(File videoJsonFile, List<ActiveVideo> videos) {
		DriverSessionData data = new DriverSessionData(videos);
		writeToFile(videoJsonFile.getPath(), 
				GSON_BUILDER.create().toJson(data, DriverSessionData.class));	
	}

	public void initializeDriverProfile(String initials) {
		ProfileInfo info = new ProfileInfo(initials);
		info.initialize();
		writeToFile(GSONFileReader.PROFILE_PATH,
				GSON_BUILDER.create().toJson(info, ProfileInfo.class));
	}

	/**
	 * @param videoJsonFile
	 * @param data
	 * @param finalFileName - the name of the file that the driver session
	 * will be saved to. This file will appear on the driver's desktop after
	 * they finish driving.
	 */
	public void terminateDeliverable(File videoJsonFile, DriverSessionData data, String finalFileName) {
		data.terminate();
		data.setFileName(finalFileName);
		writeToFile(videoJsonFile.getPath(), 
				GSON_BUILDER.create().toJson(data, DriverSessionData.class));
	}
}