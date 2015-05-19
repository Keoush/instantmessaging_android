package com.example.kebrit.instantmessagingikiu.servercommunication.parser;
import java.util.Date;

public class Message {

    public final String senderId;
    public final String receiverId;

    public final String content;

    public final Date time;

    public Message(String senderId, String receiverId, String content, Date time) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.time = time;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("SenderId: ").append(senderId).append(", ReceiverId: ").append(receiverId).append(", Time: ").append(time).append("\n   ").append(content);
        return str.toString();
    }
}
