package tests;


import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import video.Video;
import xmlcontrol.parsers.VideoXMLParser;


/**
 * This is a test class to test the functionality of the FireSpreadCell.
 * @author Austin Kyker
 *
 */
public class XMLTests {

	private Document myDocument;
	private ArrayList<Video> myList;

	/**
	 * This setUp method is called before the test functions. It creates a patch factory
	 * and a 2 dimensional array of patches filled with FireSpreadCells.
	 * @throws ParserConfigurationException 
	 */
	@Before
	public void setUp () throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		myDocument = builder.newDocument();
		Element videosNode = myDocument.createElement("videos");
		myDocument.appendChild(videosNode);
		Element statusNode = myDocument.createElement("status");
		statusNode.setAttribute("initialized", "true");
		videosNode.appendChild(statusNode);
		Video video1 = new Video("Shooters", "1", 25, 0, 30);
		Video video2 = new Video("Jimmy Johns", "1", 15, 0, 60);
		Video video3 = new Video("Shooters", "1", 35, 12, 30);
		myList = new ArrayList<Video>();
		myList.add(video1);
		myList.add(video2);
		myList.add(video3);
		for(int i = 0; i < myList.size(); i++){
			Element video = myDocument.createElement("video");
			video.setAttribute("company", myList.get(i).getMyCompany());
			video.setAttribute("title", myList.get(i).getMyName());
			video.setAttribute("length", ""+myList.get(i).getMyLength());
			video.setAttribute("maxPlays", myList.get(i).getMyMaxPlays()+"");
			video.setAttribute("plays", myList.get(i).getMyPlays()+"");
			videosNode.appendChild(video);
		}
	}

	/**
	 * This test function tests whether the FireSpreadCells are initialized with
	 * the correct default state. In the setUp method above the cells were initialized
	 * with the tree state.
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	@Test
	public void testCreationOfVideos () throws ParserConfigurationException, SAXException, IOException {
		VideoXMLParser parser = new VideoXMLParser(myDocument);
		List<Video> videoList = new ArrayList<Video>();
		parser.buildVideos(videoList);
		for(int i = 0; i < videoList.size(); i++){
			assertEquals(true, equals(videoList.get(i), myList.get(i)));
		}
	}


	public boolean equals(Video video1, Video video2){
		return video1.getMyCompany().equals(video2.getMyCompany()) && 
				video1.getMyName().equals(video2.getMyName()) &&
				video1.getMyLength() == video2.getMyLength() && 
				video1.getMyMaxPlays() == video2.getMyMaxPlays() && 
				video1.getMyPlays() == video2.getMyPlays();
	}
}


