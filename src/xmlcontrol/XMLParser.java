package xmlcontrol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import video.Video;

/**
 * The purpose of this class is to parse the driver XML File. Two things
 * come of this. First, a list of video instances is created with this information.
 * Secondly, a map is created that maps each video to its node in the document.
 * The reason for this map is that when a change needs to be made (a video has been
 * watched and its play count changes), the node where the change needs to occur
 * in the document can be obtained simply from the video that was played.
 * @author Austin Kyker
 *
 */
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

	public XMLParser(Document document) 
			throws ParserConfigurationException, SAXException, IOException{
		myDocument = document;
		myVideoNodeMap = new HashMap<Video, Node>();
	}

	/**
	 * Looks at all the video nodes and builds video instances. Each video instance
	 * is added to the input ArrayList which will hold all the videos and be maintained
	 * in the controller. The map is also updated to map each video instance to its node.
	 * @param videoList - the list that will hold all videos
	 * @param fileAlreadyInitialized - whether or not the file has already been initialized
	 */
	public void buildVideos(ArrayList<Video> videoList, boolean fileAlreadyInitialized) {
		Element root = myDocument.getDocumentElement();
		NodeList videoNodes = root.getElementsByTagName(VIDEO);
		for(int i = 0; i < videoNodes.getLength(); i++){
			Node videoNode = videoNodes.item(i);
			if (videoNode instanceof Element && videoNode.getNodeName().equalsIgnoreCase(VIDEO)) {
				Video video = buildVideoFromNode(videoNode, fileAlreadyInitialized);
				videoList.add(video);
				myVideoNodeMap.put(video, videoNode);
			}
		}
	}

	/**
	 * Creates a video instance from the videoNode.
	 * @param videoNode
	 * @param fileAlreadyInitialized
	 * @return
	 */
	private Video buildVideoFromNode(Node videoNode, boolean fileAlreadyInitialized) {
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
		return video;
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

	/**
	 * Allows the XMLController to provide the XMLWriter with the node map so
	 * that editing the driver XML File is simple.
	 * @return the VideoNode map
	 */
	public Map<Video, Node> getVideoNodeMap() {
		return myVideoNodeMap;
	}
}
