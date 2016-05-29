package model;

import model.service.FlashCardService;
import model.service.UserModelService;
import view.MainWindow;

import java.io.IOException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Box {
    private MainWindow window;
    private FlashcardFactory flashcardFactory;
    private FlashCardService flashCardService;
    private UserModelService userService;

    public Box(String dbPath) {
        this.flashcardFactory = FlashcardFactory.createOnStart();
        this.flashCardService = new FlashCardService(dbPath);
        this.userService = new UserModelService(dbPath);
        
//        User user = new User();
//        user.setId(1);
//        user.setName("My trusty Test");
//        if (!this.userService.createtUser(user)) {
//        	System.out.println("not happensing");
//        }
//        
//        user = this.userService.getUserByUserName("My trusty Test");
//        System.out.println(user.getName());
//        return;
        
        window = new MainWindow(flashcardFactory, this, dbPath);
    }

    public void refresh() {

    }


    public static void main(String[] args)
    {
    	// datenbank tag wird im XML ausgewählt
    	XPath xpath = XPathFactory.newInstance().newXPath();
    	String expression = "//config//database//attribute[@name='path']";
    	InputSource inputSource = new InputSource("config/config.xml");
    	NodeList nodes;
		try {
			nodes = (NodeList) xpath.evaluate(expression, inputSource, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
			return;
		}
        
    	if (nodes.getLength() > 1) {
    		throw new IllegalArgumentException("Der XML tag attribute im database-tag ist doppelt vorhanden");
    	}
    	
    	Node node = nodes.item(0);
    	String dbPath = node.getAttributes().getNamedItem("value").getNodeValue();
    
    	try {
			dbPath = new java.io.File( "." ).getCanonicalPath() + dbPath;
		} catch (IOException e) {		
			e.printStackTrace();
		}
    	
        new Box(dbPath);
    }
}
