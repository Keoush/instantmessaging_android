/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.kebrit.instantmessagingikiu.imhttpclientfile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.xml.sax.SAXException;
import com.example.kebrit.instantmessagingikiu.parser.*;

/**
 *
 * @author sadegh
 */
public class Interaction {

    private int fileIndex;

    public void sendMsg(String message, String sender_id, String receiver_id) throws Exception {

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(Constants.URL_SEND);

        // add header
        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("sen_id", sender_id));
        urlParameters.add(new BasicNameValuePair("rec_id", receiver_id));
        urlParameters.add(new BasicNameValuePair("message", message));
        urlParameters.add(new BasicNameValuePair("time", System.currentTimeMillis() + ""));
        // new Timestamp(new java.util.Date().getTime()).toString()
        //System.currentTimeMillis()
        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);
//        System.out.println("Response Code : "
//                + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result.toString());

    }

    public ArrayList<Message> getMsg(String receiverId) {
        ArrayList<Message> resultSet = new ArrayList<>();
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(Constants.URL_PHP_READ_FILE);
        StringBuilder sb = new StringBuilder();
        try {

            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            int i = 0;
            String line = "";
            while ((line = rd.readLine()) != null) {
                ++i;
                if (i > fileIndex) {
                    sb.append(line + "\n");
                }

            }
            fileIndex = i;
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!sb.toString().trim().isEmpty()) {
            try {
                String temp = "<root>" + sb + "</root>";
//                            System.err.println(temp);
                MessageParser mp = new MessageParser();
                ArrayList<Message> arrayStr = mp.getMessagesList(temp);
                for (Message message : arrayStr) {//important
                    if (message.receiverId.equals(receiverId)) {
//                        System.out.println(message);
                        resultSet.add(message);
                        
                    }
                }
            } catch (ParserConfigurationException ex) {
//                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
//                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
//                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return resultSet;
    }

    public int getFileIndex() {
        return fileIndex;
    }

    public void setFileIndex(int fileIndex) {
        this.fileIndex = fileIndex;
    }

}
