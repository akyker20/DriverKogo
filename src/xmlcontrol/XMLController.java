package xmlcontrol;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import video.Video;
import xmlcontrol.parsers.ProfileXMLParser;
import xmlcontrol.parsers.VideoXMLParser;
import xmlcontrol.writers.ProfileXMLWriter;
import xmlcontrol.writers.VideoXMLWriter;

/**
 * The purpose of this controller is to set up the XMLParser and
 * XMLWriter and seperate the functionality.
 * @author Austin Kyker
 *
 */
public class XMLController {

	public static final String DRIVER_PROFILE_PATH = "./src/xml/driver_profile.xml";
	private VideoXMLWriter myVideoWriter;
	private VideoXMLParser myVideoParser;
	private ProfileXMLWriter myProfileWriter;
	private ProfileXMLParser myProfileParser;
	private Document myVideosDocument;
	private Document myProfileDocument;
	private DocumentBuilder myBuilder;
	private File myXMLFile;

	/**
	 * Initializes a document and provides it to an XMLParser instance
	 * and an XMLWriter instance. Determines if the file has been initialized.
	 * @param videoList - a reference to the list of videos.
	 * @param masterFile - the xml file the driver dragged and dropped.
	 */
	public XMLController() 
			throws ParserConfigurationException, FileNotFoundException, SAXException, 
			IOException, TransformerException {

		File profileFile = new File(DRIVER_PROFILE_PATH);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		myBuilder = factory.newDocumentBuilder();
		myProfileDocument = myBuilder.parse(new FileInputStream(profileFile));

		myProfileParser = new ProfileXMLParser(myProfileDocument);
		myProfileWriter = new ProfileXMLWriter(myProfileDocument, profileFile);

	}

	/**
	 * This method is called after the driver has dragged and dropped a valid file.
	 * It begins parsing the file and creating video instances, as well as initializes
	 * the xml video writer that will allow for changes to be made and recorded.
	 */
	public void initializeVideoXMLControl(List<Video> videoList, File masterFile) {
		try {
			myXMLFile = masterFile;
			myVideosDocument = myBuilder.parse(new FileInputStream(myXMLFile));
			myVideoParser = new VideoXMLParser(myVideosDocument);	
			myVideoParser.buildVideos(videoList);
			myVideoWriter = new VideoXMLWriter(myVideosDocument, myVideoParser.getVideoNodeMap(), myXMLFile);	
			if(!myVideoParser.isFileInitialized()){
				myVideoWriter.initializeMasterFile();
			}
		}
		catch(Throwable t){
			//todo
			t.printStackTrace();
		}
	}

	/**
	 * Called by the controller when a video has been played. Calls the writer
	 * to write the changes to the master XML File.
	 * @param videoCompleted - the video that was completed.
	 * @throws TransformerException
	 */
	public void updateXML(Video videoCompleted) {
		myVideoWriter.editDrivingStats(videoCompleted);
	}

	public boolean isDriverProfileInitialized() {
		return myProfileParser.isFileInitialized();
	}

	public void initializeProfile(String initials) {
		myProfileWriter.writeProfileXML(initials);
	}
	
	public void appendInitialsToFile() {
		File desktopFile = new File(System.getProperty("user.home") + "/Desktop/" + getNewFileEnding());
		if(desktopFile.exists()) desktopFile.setWritable(true);
		Path desktopPath = desktopFile.toPath();
		try {
			Files.copy(myXMLFile.toPath(), desktopPath, REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String getNewFileEnding() {
		String originalPath = myXMLFile.getAbsolutePath();
		String originalPathFileEnding = originalPath.substring(originalPath.indexOf("kogo_"));
		String initials = myProfileParser.getDriverInitialsFromXML();
		return originalPathFileEnding.replace("kogo_", "kogo_" + initials + "_");
	}
}