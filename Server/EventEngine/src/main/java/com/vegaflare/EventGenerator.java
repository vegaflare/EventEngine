package com.vegaflare;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;

public class EventGenerator {

    public enum Events {
        Found,
        NotFound,
        FoundWaitingChange,
        NotFoundAndWaitingChange
    }
    public static Events checkFile(String fullQualifiedFileName, Events requiredEvent){
        File f = new File(fullQualifiedFileName);
        if(f.exists() && !f.isDirectory()) {
            if(requiredEvent == Events.Found){
                return Events.Found;
            }
        }
        return Events.NotFound;
    }

    public static void createFile(String name) throws IOException {
        File f = new File(name);
        f.createNewFile();
    }

    public static void lookForEvents(XDoc xmlDoc) throws TransformerException {
        int initialLength =  xmlDoc.getRecordLength();
        for (int index =0; index < xmlDoc.getRecordLength(); ++index){
        Events evnt = xmlDoc.getEvent(xmlDoc.getRecords().item(index));
        String fileName = xmlDoc.getName(index);
        if (checkFile(fileName, evnt) == Events.Found){
            System.out.println("File found: "+ fileName);
            xmlDoc.removeFileNode(index);
            index -= 1;
        }
        }
        if(initialLength != xmlDoc.getRecordLength()) {
            xmlDoc.writeDocument();
        }
    }
}
