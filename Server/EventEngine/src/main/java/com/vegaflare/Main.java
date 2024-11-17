package com.vegaflare;


import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.*;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws XMLStreamException, IOException, ParserConfigurationException, SAXException, TransformerException, InterruptedException {
         XDoc d = new XDoc();
         while(true){
         EventGenerator.lookForEvents(d);
         TimeUnit.SECONDS.sleep(1);
         }
    }


}
