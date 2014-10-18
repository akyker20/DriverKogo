package xmlcontrol.parsers;

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
public class ProfileXMLParser extends XMLParser {

	private static final String INITIALIZED = "initialized";

	public ProfileXMLParser(Document profileDocument) 
			throws ParserConfigurationException, SAXException, IOException{
		super(profileDocument);
	}

	@Override
	public boolean isFileInitialized() {
		return myDocument.getDocumentElement()
				.getAttribute(INITIALIZED).equalsIgnoreCase("true");
	}
}
