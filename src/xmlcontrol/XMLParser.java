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
	public static final String MAX_PLAYS = "maxPlays";
	public static final String COMPANY = "company";
	public static final String TITLE = "title";
	public static final String VIDEO = "video";
	public static final String PLAYS = "plays";
	public static final String STATUS = "status";
	public static final String INITIALIZED = "initialized";


	private Map<Video, Node> myVideoNodeMap;
	private Document myDocument;

	public XMLParser(Document document, XMLController control) 
			throws ParserConfigurationException, SAXException, IOException{
		myDocument = document;
		myVideoNodeMap = new HashMap<Video, Node>();
	}

	public void buildVideos(ArrayList<Video> videoList, boolean fileAlreadyInitialized) {
		Element root = myDocument.getDocumentElement();
		NodeList videoNodes = root.getElementsByTagName(VIDEO);
		for(int i = 0; i < videoNodes.getLength(); i++){
			Node videoNode = videoNodes.item(i);
			if (videoNode instanceof Element && videoNode.getNodeName().equalsIgnoreCase("video")) {
				NamedNodeMap attributes = videoNode.getAttributes();
				int length = Integer.parseInt(getAttributeValue(attributes, LENGTH));
				int maxPlays = Integer.parseInt(getAttributeValue(attributes, MAX_PLAYS));
				String name = getAttributeValue(attributes, TITLE);
				String company = getAttributeValue(attributes, COMPANY);
				Video video;
				if(fileAlreadyInitialized){
					int playsCompleted = Integer.parseInt(getAttributeValue(attributes, PLAYS));
					video = new Video(company, name, playsCompleted, maxPlays, length);
				}
				else {
					video = new Video(company, name, maxPlays, length);
				}
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
