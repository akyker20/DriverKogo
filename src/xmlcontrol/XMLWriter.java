package xmlcontrol;

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

public class XMLWriter {
	
	private Transformer myTransformer;
	private Document myDocument;
	private Map<Video, Node> myVideoNodeMap;
	private File myFile;

	public XMLWriter(Document document, Map<Video, Node> videoNodeMap, File file) 
			throws FileNotFoundException, SAXException, IOException, 
			ParserConfigurationException, TransformerConfigurationException{
		myFile = file;
		myVideoNodeMap = videoNodeMap;
		myDocument = document;
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		myTransformer = transformerFactory.newTransformer();
		myTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
	}
	
	/**
	 * Adds play attributes to the master file
	 * @param lists
	 * @throws TransformerException 
	 */
	public void initializeMasterFile(ArrayList<Video> lists) throws TransformerException{
		Element statusTag = (Element) myDocument.getDocumentElement().getElementsByTagName(XMLParser.STATUS).item(0);
		statusTag.setAttribute(XMLParser.INITIALIZED, "true");
		for(Node videoNode:myVideoNodeMap.values()){
			((Element) videoNode).setAttribute(XMLParser.PLAYS, "0");
		}
		writeFile(myDocument, myFile);
	}
	
	public void editDrivingStats(Video video, int numPassengers) throws TransformerException{
		Element videoElement = (Element) myVideoNodeMap.get(video);
		videoElement.setAttribute(XMLParser.PLAYS, ""+video.getMyPlays());
		writeFile(myDocument, myFile);
	}	

	private void writeFile(Document document, File xmlFile) throws TransformerException {
		xmlFile.setWritable(true);
		StreamResult result = new StreamResult(xmlFile);
		myTransformer.transform(new DOMSource(document), result);
		xmlFile.setReadOnly();
		System.out.println("File saved!");
	}
}
