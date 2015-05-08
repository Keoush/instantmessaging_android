package com.example.kebrit.instantmessagingikiu;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.kebrit.instantmessagingikiu.imhttpclientfile.Interaction;
import com.example.kebrit.instantmessagingikiu.parser.Message;

import java.util.ArrayList;

public class MsgReceiverIntentService extends IntentService {

    private LocalBroadcastManager broadcaster;

    public static void startReceivingMsg(Context context) {
        Intent intent = new Intent(context, MsgReceiverIntentService.class);
        context.startService(intent);
    }


    public MsgReceiverIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        broadcaster = LocalBroadcastManager.getInstance(this);

        int count = 0;

        while (true) {
//            Log.d("Kebrit:msg", "thread started.");

            ArrayList<Message> messages = new Interaction().getMsg("2");

            if(messages.size() > count){

                Log.d("Kebrit:msg", "new msg found in list.");

                Message msg = messages.get(messages.size() - 1);

                Intent sentIntent = new Intent("MSG_SIGNAL");

                sentIntent.putExtra("NAME", msg.senderId);
                sentIntent.putExtra("CONTENT", msg.content);
                sentIntent.putExtra("DATE", msg.time);

                broadcaster.sendBroadcast(sentIntent);
                count = messages.size();
            }
        }

    }

}
