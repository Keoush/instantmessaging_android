package com.example.kebrit.instantmessagingikiu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kebrit.instantmessagingikiu.R;

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

    public MessageListAdapter(Context context) {

        layoutInflater = LayoutInflater.from(context);
        messages = new ArrayList<Triplet>();
        this.context = context;

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

        txtMessage.setText(messages.get(position).messageContent);

        txtDate.setText(messages.get(position).date);


        Animation animation = AnimationUtils.loadAnimation(context, R.anim.down_from_top);
        convertView.startAnimation(animation);

        return convertView;
    }

    public void addMessage(String message, boolean income) {
//        -------------------------------------------------------------------------------------------
        messages.add(new Triplet(message, income));
        notifyDataSetChanged();
    }

    public void addMessage(String message, Date date, boolean income) {
//        -------------------------------------------------------------------------------------------
        messages.add(new Triplet(message, date,  income));
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
        public boolean inCome = false;

        public Triplet(String m, Date d, boolean i) {
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


