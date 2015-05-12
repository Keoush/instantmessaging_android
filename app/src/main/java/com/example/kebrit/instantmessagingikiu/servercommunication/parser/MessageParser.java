package com.example.kebrit.instantmessagingikiu.servercommunication.parser;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MessageParser {

    private ArrayList<Message> messages;

    public ArrayList<Message> getMessagesList(String xmlMessage)
            throws ParserConfigurationException, SAXException, IOException {

        InputStream input = new ByteArrayInputStream(xmlMessage.getBytes());
        messages = new ArrayList<Message>();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        MyHandler mHandler = new MyHandler();
        saxParser.parse(input, mHandler);
        return messages;
    }

    private class MyHandler extends DefaultHandler {
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

            if (qName.equalsIgnoreCase("sen_Id")) {
                senderIdFlag = true;
            }

            if (qName.equalsIgnoreCase("rec_Id")) {
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
                String timeString = new String(ch, start, length);
//                System.out.println("shit:" + timeString);
                time = new Date(Long.parseLong(timeString));
                
                timeFlag = false;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) 
                throws SAXException {
            
            if (qName.equalsIgnoreCase("message")) {
                Message message = new Message(senderId, receiverId, content, time);
                messages.add(message);
            }
        }
    }
}
