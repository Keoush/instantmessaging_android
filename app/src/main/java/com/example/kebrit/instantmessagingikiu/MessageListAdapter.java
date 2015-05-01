package com.example.kebrit.instantmessagingikiu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Keoush on 4/30/2015.
 */
public class MessageListAdapter extends BaseAdapter {


    private LayoutInflater layoutInflater;
    private ArrayList<Triplet> messages;
    private Context context;


    public MessageListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        messages = new ArrayList<Triplet>();
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        boolean inCome = messages.get(position).inCome;

        if (!inCome)
            convertView = layoutInflater.inflate(R.layout.chat_sent_view, parent, false);
        else
            convertView = layoutInflater.inflate(R.layout.chat_recived_view, parent, false);


        TextView txtMessage = (TextView) convertView.findViewById(R.id.txtMessage);
        txtMessage.setText(messages.get(position).messageContent);


//        Animtion animation = AnimationUtils.loadAnimation(context, (position > -1) ? R.anim.up_from_bottom : R.anim.down_from_top);
//        convertView.startAnimation(animation);

        return convertView;
    }

    public void addMessage(String message, boolean income) {
//        -------------------------------------------------------------------------------------------
        messages.add(new Triplet(message, income));
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
        public Date date;
        public boolean inCome = false;

        public Triplet(String m, Date d, boolean i) {
            messageContent = m;
            date = d;
            inCome = i;
        }

        public Triplet(String m, boolean i) {
            messageContent = m;
            inCome = i;
        }

    }
}


