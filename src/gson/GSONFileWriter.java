package gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import utilities.ErrorPopup;
import video.ActiveVideo;
import video.TransferVideoData;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import control.ProfileInfo;

public class GSONFileWriter {

	private static final String ERROR_MSG = "File to store videos could not be found.";
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
			FileWriter writer = new FileWriter(file);
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			new ErrorPopup(ERROR_MSG);
		}
	}

	public void updateVideoJson(File videoJsonFile, List<ActiveVideo> videos) {
		TransferVideoData data = new TransferVideoData(videos);
		writeToFile(videoJsonFile.getPath(), 
				GSON_BUILDER.create().toJson(data, TransferVideoData.class));	
	}

	public void initializeDriverProfile(String initials) {
		ProfileInfo info = new ProfileInfo(initials);
		info.initialize();
		writeToFile(GSONFileReader.PROFILE_PATH,
				GSON_BUILDER.create().toJson(info, ProfileInfo.class));
	}

	public void terminateDeliverable(File videoJsonFile, TransferVideoData data) {
		data.terminate();
		writeToFile(videoJsonFile.getPath(), 
				GSON_BUILDER.create().toJson(data, TransferVideoData.class));		
	}
}