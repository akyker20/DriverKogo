package xmlcontrol.writers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import video.Video;
import xmlcontrol.parsers.VideoXMLParser;

/**
 * The purpose of this class is to write changes to the driver XML File.
 * This is facilitated by the presence of the video to node map that 
 * was provided conveniently by the XMLParser. The changes to the XML file
 * will be a result of videos being played. Each video element in the file
 * will have a plays attribute that reflects the number of times the file
 * has been played.
 * @author Austin Kyker
 *
 */
public class VideoXMLWriter extends XMLWriter {
	
	private Map<Video, Node> myVideoNodeMap;

	public VideoXMLWriter(Document videoDocument, Map<Video, Node> videoNodeMap, File file) 
			throws FileNotFoundException, SAXException, IOException, 
			ParserConfigurationException, TransformerConfigurationException{
		super(videoDocument, file);
		myVideoNodeMap = videoNodeMap;
	}
	
	/**
	 * Adds play attributes to the master file. This method is only called if
	 * the master file had not already been initialized. The status element's
	 * "initialized" attribute is set to true to signify that the file has now
	 * been initialized. All videos are given a "plays" attribute that is set to 0.
	 * This plays attribute represents the number of times the video has been played.
	 * This number will never be more than 1 or 2 higher than the maxPlays attribute.
	 * The logic for this is contained in the controller.
	 * @param lists
	 * @throws TransformerException 
	 */
	public void initializeMasterFile() throws TransformerException{
		Element statusTag = (Element) myDocument.getDocumentElement()
				.getElementsByTagName(VideoXMLParser.STATUS).item(0);
		statusTag.setAttribute(VideoXMLParser.INITIALIZED, "true");
		for(Node videoNode:myVideoNodeMap.values()){
			((Element) videoNode).setAttribute(VideoXMLParser.PLAYS, "0");
		}
		writeFile(myDocument, myFile);
	}
	
	/**
	 * When a video is played, the controller calls this method to update the 
	 * driver XML File. The video that was played is mapped to its video node
	 * and its "plays" attribute is updated to the video's myPlays value. This
	 * works because the controller updates the video's fields before calling this
	 * method. The file is then rewritten.
	 * @param video
	 * @throws TransformerException
	 */
	public void editDrivingStats(Video video) throws TransformerException{
		Element videoElement = (Element) myVideoNodeMap.get(video);
		videoElement.setAttribute(VideoXMLParser.PLAYS, "" + video.getMyPlays());
		super.writeFile(myDocument, myFile);
	}	
}
