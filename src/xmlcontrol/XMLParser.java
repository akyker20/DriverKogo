package xmlcontrol;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import video.Video;

public class XMLParser extends DefaultHandler {

	private static final String MASTER_PATH = "./src/xml/master_info.xml";

	private ArrayList<Video> myVideoList;

	public XMLParser(ArrayList<Video> list) 
			throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(getClass().getResourceAsStream(MASTER_PATH));
		buildVideos(document.getDocumentElement());
	}

	private void buildVideos(Element documentElement) {
		NodeList videoNodes = documentElement.getChildNodes();
		for(int i = 0; i < videoNodes.getLength(); i++){
			 Node n = videoNodes.item(i);
	            if (n instanceof Element) {
	                String value = n.getTextContent();
	               
	            }
		}
		
	}

	@Override
	public void startElement (String uri,
			String localName,
			String elementName,
			Attributes attributes) throws SAXException {
		if (elementName.equalsIgnoreCase("video")) {
			String title = attributes.getValue("title");
			String company = attributes.getValue("company");
			int length = Integer.parseInt(attributes.getValue("length"));
			int numPlaysRemaining = Integer.parseInt(attributes.getValue("playsRemaining"));
			myVideoList.add(new Video(company, title, numPlaysRemaining, length));
		}
	}
}
