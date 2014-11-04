package xmlcontrol.parsers;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

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
	private static final String INITIALS = "initials";

	public ProfileXMLParser(Document profileDocument) 
			throws ParserConfigurationException, SAXException, IOException{
		super(profileDocument);
	}

	@Override
	public boolean isFileInitialized() {
		return myDocument.getDocumentElement()
				.getAttribute(INITIALIZED).equalsIgnoreCase("true");
	}

	public String getDriverInitialsFromXML() {
		Element initialsNode = (Element) myDocument.getDocumentElement()
				.getElementsByTagName(INITIALS).item(0);
		return initialsNode.getTextContent();
	}
}
