package com.example.kebrit.instantmessagingikiu.servercommunication.parser;
import java.util.ArrayList;
import java.util.Date;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class MyHandler extends DefaultHandler {

    public final ArrayList<Message> messages = new ArrayList<Message>();
    
    private String senderId;
    private boolean senderIdFlag = false;
    
    private String receiverId;
    private boolean receiverIdFlag = false;
    
    private String content;
    private boolean contentFlag = false;
    
    private Date time;
    private boolean timeFlag = false;

    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {

        if (qName.equalsIgnoreCase("senderId")) {
            senderIdFlag = true;
        }
        
        if (qName.equalsIgnoreCase("receiverId")) {
            receiverIdFlag = true;
        }
        
        if (qName.equalsIgnoreCase("content")) {
            contentFlag = true;
        }
        
        if (qName.equalsIgnoreCase("time")) {
            timeFlag = true;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (senderIdFlag) {
            senderId = new String(ch, start, length);
            senderIdFlag = false;
        }
        
        if (receiverIdFlag) {
            receiverId = new String(ch, start, length);
            receiverIdFlag = false;
        }
        
        if (contentFlag) {
            content = new String(ch, start, length);
            contentFlag = false;
        }
        
        if (timeFlag) {
            time = new Date();
            timeFlag = false;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("message")) {
            Message message = new Message(senderId, receiverId, content, time);
            messages.add(message);
        }
    }
    
}
