package xmlcontrol.writers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import video.Video;
import xmlcontrol.XMLController;
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
public class ProfileXMLWriter extends XMLWriter {

	
	public ProfileXMLWriter(Document document, File file)
			throws FileNotFoundException, SAXException, IOException,
			ParserConfigurationException, TransformerConfigurationException {
		super(document, file);
	}

	/**
	 * Initializes the driver profile xml file and writes the driver's initials so that
	 * when the driver saves their session file it will have their initials in the
	 * file path.
	 * @param initials
	 * @param document
	 * @throws TransformerException
	 */
	public void writeProfileXML(String initials) throws TransformerException{
		Element profileNode = myDocument.getDocumentElement();
		profileNode.setAttribute(VideoXMLParser.INITIALIZED, "true");
		Element initialsNode = myDocument.createElement("initials");
		initialsNode.setTextContent(initials);
		profileNode.appendChild(initialsNode);
		writeFile(myDocument, new File(XMLController.DRIVER_PROFILE_PATH));
	}
}
