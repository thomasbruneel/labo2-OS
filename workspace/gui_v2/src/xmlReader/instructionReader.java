package xmlReader;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import model.Instructie;

public class instructionReader {
		
		
		
		
		public Queue<Instructie> readIn(String fileName) {
			Queue<Instructie> instructies = new LinkedList<>();
				try{
					File xmlFile = new File(fileName);
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(xmlFile);

					NodeList processlist = doc.getElementsByTagName("instruction");

					for (int temp = 0; temp < processlist.getLength(); temp++) {

						Node nNode = processlist.item(temp);

						if (nNode.getNodeType() == Node.ELEMENT_NODE) {

							Element eElement = (Element) nNode;
							int procesId=Integer.parseInt(eElement.getElementsByTagName("processID").item(0).getTextContent());
							String operation=eElement.getElementsByTagName("operation").item(0).getTextContent();
							long adres= Long.parseLong(eElement.getElementsByTagName("address").item(0).getTextContent());
							Instructie i =new Instructie(procesId,operation,adres);
							instructies.add(i);

						}
					}
				    } catch (Exception e) {
					e.printStackTrace();
				    }
				return instructies;
			}
}
	

