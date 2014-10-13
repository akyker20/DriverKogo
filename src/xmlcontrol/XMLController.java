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
	
	private static final String MASTER_PATH = "./src/xml/master_info.xml";
	
	public XMLController(ArrayList<Video> videoList) throws ParserConfigurationException, FileNotFoundException, SAXException, IOException, TransformerException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new FileInputStream(new File(MASTER_PATH)));
		XMLParser xmlParser = new XMLParser(document);
		xmlParser.buildVideos(videoList);
		XMLWriter xmlWriter = new XMLWriter(document, xmlParser.getVideoNodeMap());	
		xmlWriter.initializeMasterFile(videoList);
	}
}
