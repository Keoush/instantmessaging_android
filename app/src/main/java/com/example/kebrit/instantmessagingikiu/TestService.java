package com.example.kebrit.instantmessagingikiu;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.example.kebrit.instantmessagingikiu.imhttpclientfile.Interaction;

/**
 * Created by Keoush on 5/6/2015.
 */
public class TestService extends Service {

    private static Interaction interaction;
    private static String SENDER_ID = "1";
    private static String RECEIVER_ID = "2";

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        interaction = new Interaction();
        try {
            interaction.sendMsg("Kebrit", SENDER_ID, RECEIVER_ID);
        } catch (Exception e) {
            Toast.makeText(this, "Msg not sent.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }
}
