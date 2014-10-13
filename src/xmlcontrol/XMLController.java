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
import org.xml.sax.SAXException;

import video.Video;

public class XMLController {
	
	protected static final String MASTER_PATH = "./src/xml/master_info.xml";
	private XMLWriter myWriter;
	
	public XMLController(ArrayList<Video> videoList) throws ParserConfigurationException, FileNotFoundException, SAXException, IOException, TransformerException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new FileInputStream(new File(MASTER_PATH)));
		XMLParser xmlParser = new XMLParser(document);
		xmlParser.buildVideos(videoList);
		myWriter = new XMLWriter(document, xmlParser.getVideoNodeMap());	
		myWriter.initializeMasterFile(videoList);
	}

	public void updateXML(Video videoCompleted, int numPassengers) throws TransformerException {
		myWriter.editDrivingStats(videoCompleted, numPassengers);
		
	}
}
