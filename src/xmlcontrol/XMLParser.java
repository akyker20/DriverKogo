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


	private Map<Video, Node> myVideoNodeMap;
	private Document myDocument;

	public XMLParser(Document document) 
			throws ParserConfigurationException, SAXException, IOException{
		myVideoNodeMap = new HashMap<Video, Node>();
		myDocument = document;
	}

	public void buildVideos(ArrayList<Video> videoList) {
		NodeList videoNodes = myDocument.getDocumentElement().getChildNodes();
		for(int i = 0; i < videoNodes.getLength(); i++){
			Node videoNode = videoNodes.item(i);
			if (videoNode instanceof Element && videoNode.getNodeName().equalsIgnoreCase("video")) {
				NamedNodeMap attributes = videoNode.getAttributes();
				int length = Integer.parseInt(getAttributeValue(attributes, LENGTH));
				int playsRemaining = Integer.parseInt(getAttributeValue(attributes, PLAYS_REMAINING));
				String name = getAttributeValue(attributes, TITLE);
				String company = getAttributeValue(attributes, COMPANY);
				Video video = new Video(company, name, playsRemaining, length);
				videoList.add(video);
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

	public Map<Video, Node> getVideoNodeMap() {
		return myVideoNodeMap;
	}
}
