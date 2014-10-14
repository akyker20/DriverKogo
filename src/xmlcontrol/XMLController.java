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

public class XMLController {
	
	protected static final String MASTER_PATH = "./src/xml/master_info.xml";
	private static final String TRUE = "true";
	private XMLWriter myWriter;
	private Document myDocument;
	
	public XMLController(ArrayList<Video> videoList) throws ParserConfigurationException, FileNotFoundException, SAXException, IOException, TransformerException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		myDocument = builder.parse(new FileInputStream(new File(MASTER_PATH)));
		XMLParser xmlParser = new XMLParser(myDocument, this);
		
		boolean isFileInitialized = isFileInitialized();
		xmlParser.buildVideos(videoList, isFileInitialized);
		myWriter = new XMLWriter(myDocument, xmlParser.getVideoNodeMap());	
		if(!isFileInitialized){
			myWriter.initializeMasterFile(videoList);
		}
	}

	public void updateXML(Video videoCompleted, int numPassengers) throws TransformerException {
		myWriter.editDrivingStats(videoCompleted, numPassengers);
	}
	
	public boolean isFileInitialized(){
		Element statusTag = (Element) myDocument.getDocumentElement().getElementsByTagName(XMLParser.STATUS).item(0);
		return statusTag.getAttribute(XMLParser.INITIALIZED).equalsIgnoreCase(TRUE);
	}
}
