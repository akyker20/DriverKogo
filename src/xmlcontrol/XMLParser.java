package xmlcontrol;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import video.Video;

public class XMLParser extends DefaultHandler {
	
	public static final String LENGTH = "length";
	public static final String PLAYS_REMAINING = "playsRemaining";
	public static final String COMPANY = "company";
	public static final String TITLE = "title";
	

	private static final String MASTER_PATH = "./src/xml/master_info.xml";

	private ArrayList<Video> myVideoList;
	private Map<Video, Node> myVideoNodeMap;

	public XMLParser(ArrayList<Video> list) 
			throws ParserConfigurationException, SAXException, IOException{
		myVideoList = list;
		myVideoNodeMap = new HashMap<Video, Node>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new FileInputStream(new File(MASTER_PATH)));
		buildVideos(document.getDocumentElement());
	}

	private void buildVideos(Element documentElement) {
		NodeList videoNodes = documentElement.getChildNodes();
		for(int i = 0; i < videoNodes.getLength(); i++){
			Node videoNode = videoNodes.item(i);
			if (videoNode instanceof Element && videoNode.getNodeName().equalsIgnoreCase("video")) {
				NamedNodeMap attributes = videoNode.getAttributes();
				int length = Integer.parseInt(getAttributeValue(attributes, LENGTH));
				int playsRemaining = Integer.parseInt(getAttributeValue(attributes, PLAYS_REMAINING));
				String name = getAttributeValue(attributes, TITLE);
				String company = getAttributeValue(attributes, COMPANY);
				Video video = new Video(company, name, playsRemaining, length);
				myVideoList.add(video);
				myVideoNodeMap.put(video, videoNode);
			}
		}
	}

	/**
	 * Helper function to fetch an attribute value.
	 * @param attributes
	 * @param attrName
	 * @return
	 */
	private String getAttributeValue(NamedNodeMap attributes, String attrName) {
		return attributes.getNamedItem(attrName).getNodeValue();
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
