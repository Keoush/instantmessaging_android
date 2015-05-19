package com.example.kebrit.instantmessagingikiu.client.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kebrit.instantmessagingikiu.R;
import com.example.kebrit.instantmessagingikiu.client.activity.LogInActivity;
import com.example.kebrit.instantmessagingikiu.servercommunication.parser.Message;

import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Keoush on 4/30/2015.
 */
public class MessageListAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private ArrayList<Triplet> messages;
    private Context context;

    private DateFormat dateFormatter;

    private String senderId;

    public MessageListAdapter(Context context, String senderId) {

        layoutInflater = LayoutInflater.from(context);
        messages = new ArrayList<Triplet>();
        this.context = context;

        this.senderId = senderId;

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        dateFormatter.setLenient(false);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        boolean inCome = messages.get(position).inCome;

        if (!inCome)
            convertView = layoutInflater.inflate(R.layout.chat_sent_view, parent, false);
        else
            convertView = layoutInflater.inflate(R.layout.chat_recived_view, parent, false);


        TextView txtMessage = (TextView) convertView.findViewById(R.id.txtMessage);
        TextView txtDate = (TextView) convertView.findViewById(R.id.txtDate);
        TextView txtName = (TextView) convertView.findViewById(R.id.txtSender);

        txtName.setText(messages.get(position).userID);

        txtMessage.setText(messages.get(position).messageContent);

        txtDate.setText(messages.get(position).date);


        Animation animation = AnimationUtils.loadAnimation(context, R.anim.down_from_top);
        convertView.startAnimation(animation);

        return convertView;
    }

    public void addMessage(Message msg){

        if(msg.senderId.equals(senderId))
            messages.add(new Triplet(msg, false));
        else
            messages.add(new Triplet(msg, true));

        notifyDataSetChanged();
    }

    public void addMessage(String message, boolean income) {
//        -------------------------------------------------------------------------------------------
        messages.add(new Triplet(message, income));
        notifyDataSetChanged();
    }

    public void addMessage(String name, String message, Date date, boolean income) {
//        -------------------------------------------------------------------------------------------
        messages.add(new Triplet(name, message, date,  income));
        notifyDataSetChanged();
    }

    public void addMessage(String name, String message, String date) {
//        -------------------------------------------------------------------------------------------
        messages.add(new Triplet(message, date, name, true));
        notifyDataSetChanged();
    }

    public void addListMessages(ArrayList<Message> listMsg){

        for (Message msg : listMsg){
            if(msg.senderId.equals(senderId)) {
                messages.add(new Triplet(msg, false));
            }
            else {
                messages.add(new Triplet(msg, true));
            }
        }

        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    private class Triplet {

        public String messageContent = "unkown!";
        public String date;
        public String userID = "unKnown";
        public boolean inCome = false;

        public Triplet(Message msg, boolean bol){
            userID = msg.senderId;
            messageContent = msg.content;
            date = dateFormatter.format(msg.time);
//            date = msg.time.toString();
            inCome = bol;
        }

        public Triplet(String m, String d, String n, boolean i) {
            userID = n;
            messageContent = m;
            date = d;
            inCome = i;
        }

        public Triplet(String n, String m, Date d, boolean i) {
            userID = n;
            messageContent = m;
            date = dateFormatter.format(d);
            inCome = i;
        }

        public Triplet(String m, boolean i) {
            messageContent = m;
            date = "Unknown date !";
            inCome = i;
        }

    }
}


