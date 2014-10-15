package xmlcontrol;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import video.Video;

/**
 * The purpose of this controller is to set up the XMLParser and
 * XMLWriter and seperate the functionality.
 * @author Austin Kyker
 *
 */
public class XMLController {
	
	private static final String TRUE = "true";
	private XMLWriter myWriter;
	private Document myDocument;
	
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
	public XMLController(ArrayList<Video> videoList, File masterFile) 
			throws ParserConfigurationException, FileNotFoundException, SAXException, 
			IOException, TransformerException {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		myDocument = builder.parse(new FileInputStream(masterFile));
		
		XMLParser xmlParser = new XMLParser(myDocument);	
		boolean isFileInitialized = isFileInitialized();
		xmlParser.buildVideos(videoList, isFileInitialized);
		myWriter = new XMLWriter(myDocument, xmlParser.getVideoNodeMap(), masterFile);	
		if(!isFileInitialized){
			myWriter.initializeMasterFile();
		}
	}

	/**
	 * Called by the controller when a video has been played. Calls the writer
	 * to write the changes to the master XML File.
	 * @param videoCompleted - the video that was completed.
	 * @throws TransformerException
	 */
	public void updateXML(Video videoCompleted) throws TransformerException {
		myWriter.editDrivingStats(videoCompleted);
	}
	
	/**
	 * Checks the status element to see if the initialized attribute is true or false
	 * @return true if the initialized attribute is true - false, otherwise.
	 */
	public boolean isFileInitialized(){
		Element statusTag = (Element) myDocument.getDocumentElement()
				.getElementsByTagName(XMLParser.STATUS).item(0);
		return statusTag.getAttribute(XMLParser.INITIALIZED).equalsIgnoreCase(TRUE);
	}
}