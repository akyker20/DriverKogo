package xmlcontrol;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

	/**
	 * Initializes a document and provides it to an XMLParser instance
	 * and an XMLWriter instance. Determines if the file has been initialized.
	 * @param videoList - a reference to the list of videos.
	 * @param masterFile - the xml file the driver dragged and dropped.
	 * @throws ParserConfigurationException
	 * @throws FileNotFoundException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
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
	 * @param videoList
	 * @param masterFile
	 * @throws FileNotFoundException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	public void initializeVideoXMLControl(List<Video> videoList, File masterFile) throws FileNotFoundException, 
	SAXException, IOException, ParserConfigurationException, TransformerException {
		myVideosDocument = myBuilder.parse(new FileInputStream(masterFile));
		myVideoParser = new VideoXMLParser(myVideosDocument);	
		myVideoParser.buildVideos(videoList);
		myVideoWriter = new VideoXMLWriter(myVideosDocument, myVideoParser.getVideoNodeMap(), masterFile);	
		if(!myVideoParser.isFileInitialized()){
			myVideoWriter.initializeMasterFile();
		}
	}

	/**
	 * Called by the controller when a video has been played. Calls the writer
	 * to write the changes to the master XML File.
	 * @param videoCompleted - the video that was completed.
	 * @throws TransformerException
	 */
	public void updateXML(Video videoCompleted) throws TransformerException {
		myVideoWriter.editDrivingStats(videoCompleted);
	}

	public boolean isProfileInitialized() {
		return myProfileParser.isFileInitialized();
	}

	public void initializeProfile(String initials) throws TransformerException {
		myProfileWriter.writeProfileXML(initials);
	}

	public String getInitials() {
		return myProfileParser.getInitials();
	}
}