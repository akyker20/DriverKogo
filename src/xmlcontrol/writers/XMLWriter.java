package xmlcontrol.writers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public abstract class XMLWriter {

	protected File myFile;
	protected Document myDocument;
	private Transformer myTransformer;

	public XMLWriter(Document document, File file) 
			throws FileNotFoundException, SAXException, IOException, 
			ParserConfigurationException, TransformerConfigurationException{
		
		myFile = file;
		myDocument = document;
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		myTransformer = transformerFactory.newTransformer();
		myTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
		
	}
	
	/**
	 * Writes the updates to the file, ensuring the file ends up read-only.
	 * @param document
	 * @param xmlFile
	 * @throws TransformerException
	 */
	protected void writeFile(Document document, File xmlFile) {
		xmlFile.setWritable(true);
		StreamResult result = new StreamResult(xmlFile);
		try {
			myTransformer.transform(new DOMSource(document), result);
			xmlFile.setReadOnly();
			System.out.println("File saved!");
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
