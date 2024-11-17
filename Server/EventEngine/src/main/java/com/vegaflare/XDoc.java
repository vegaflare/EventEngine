package com.vegaflare;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class XDoc {

    private File xmlFileInput;
    private File xmlFileOutput;



    private NodeList records;
    private Document XDoclet;

    public XDoc() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        String dataSource = "output.xml";
        xmlFileInput = new File(dataSource);
        XDoclet = documentBuilder.parse(xmlFileInput);
        Element element = XDoclet.getDocumentElement();
        System.out.println("Root element name is "+element.getTagName());
        records = XDoclet.getElementsByTagName("record");
        System.out.println("Length: "+ records.getLength());
//        for (int i=0; i>=records.getLength(); --i){
//            EventGenerator.createFile(getName(i));
//            registerCheck(i);
//            if (!getName(i).endsWith(".4")){
//                removeFileNode(i);}
//            else{
//                System.out.println("else branch hit");
//            }
//        }
//        writeDocument(XDoclet, xmlFileInput);
    }


    public int getRecordLength() {
        return records.getLength();
    }

    public  NodeList getRecords(){
        return this.records;
    }

    String getName(int index){
        Node node = records.item(index);

        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element el = (Element) node;
            return el.getElementsByTagName("name").item(0).getTextContent();
        }
        return null;
    }
    int getWaitTime(int index){
        Node node = records.item(index);

        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element el = (Element) node;
            return Integer.parseInt(el.getElementsByTagName("waitTime").item(0).getTextContent());
        }
        return 0;
    }
    public EventGenerator.Events getEvent(Node node){

        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element el = (Element) node;
            return el.getElementsByTagName("fileChange").item(0).getTextContent().equals("no")?
                    EventGenerator.Events.Found : EventGenerator.Events.NotFoundAndWaitingChange;
        }
        return EventGenerator.Events.NotFound;
    }

    void registerCheck(int index){
        Node node = records.item(index);

        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element el = (Element) node;
            int count  = Integer.parseInt(el.getElementsByTagName("checkCount").item(0).getTextContent()) + 1;
            el.getElementsByTagName("checkCount").item(0).setTextContent(String.valueOf(count));
        }
    }


    void removeFileNode(int index){
        Element element = XDoclet.getDocumentElement();
        Node toBeRemoved = element.getElementsByTagName("record").item(index);
        element.removeChild(toBeRemoved);
    }

    public void writeDocument() throws TransformerException {
        // Set up the transformer to save the document
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // Format the output (for pretty-printing)
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        // Write the updated XML to the file
        DOMSource source = new DOMSource(XDoclet);
        StreamResult result = new StreamResult(xmlFileInput);

        transformer.transform(source, result);

        System.out.println("XML file has been updated.");
    }

}
