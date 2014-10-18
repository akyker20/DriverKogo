package xmlcontrol.parsers;

import org.w3c.dom.Document;

public abstract class XMLParser {
	
	protected Document myDocument;
	
	public XMLParser(Document document){
		myDocument = document;
	}
	
	public abstract boolean isFileInitialized();
}
