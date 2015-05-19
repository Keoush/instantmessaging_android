package com.example.kebrit.instantmessagingikiu.client.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kebrit.instantmessagingikiu.client.service.MsgReceiverIntentService;
import com.example.kebrit.instantmessagingikiu.R;
import com.example.kebrit.instantmessagingikiu.client.adapter.MessageListAdapter;
import com.example.kebrit.instantmessagingikiu.servercommunication.imhttpclientfile.Constants;
import com.example.kebrit.instantmessagingikiu.servercommunication.imhttpclientfile.Interaction;
import com.example.kebrit.instantmessagingikiu.servercommunication.parser.Message;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class ChatActivity extends ActionBarActivity {

    private boolean isFirebase = true;

    private MessageListAdapter adapter;

    private BroadcastReceiver receiver;
    private MsgReceiverIntentService msgReceiver;

    private static Interaction interaction;

    private Firebase myFirebase;
    private boolean firstTime = true;

    private static String SENDER_ID = "201";
    private static String RECEIVER_ID = "GROUP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        SharedPreferences settings = getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE);
        SENDER_ID = settings.getString("USERID", "default");

        adapter = new MessageListAdapter(this, SENDER_ID);

        ListView chatList = (ListView) findViewById(R.id.listMessages);

        chatList.setAdapter(adapter);

        final EditText inputText = (EditText) findViewById(R.id.messageBodyField);

        Button sendButton = (Button) findViewById(R.id.sendButton);

        if(isFirebase){
            Firebase.setAndroidContext(this);
            myFirebase = new Firebase(Constants.URL_FIREBASE);
        }
        else
            interaction = new Interaction();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inputText.getText().toString().equals("")) {

                    final String msg = inputText.getText().toString();
                    adapter.addMessage(SENDER_ID, msg, new Date(), false);

                    sendMessage(msg);

                    inputText.setText("");

                } else {
                    Toast.makeText(getApplicationContext(), "Please enter some text...",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        fillMsgList();

        setMsgReceiver();

    }

    private void setMsgReceiver() {

        if (isFirebase){
            firstTime = true;

            myFirebase.child("messages").addChildEventListener(new ChildEventListener() {

                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    if(firstTime) {
                        firstTime = false;
                        return;
                    }

                    Map messageMap = (Map)dataSnapshot.getValue();
                    Log.d("Kebrit:msg", "New msg added to DB: " + messageMap.get("senderId") + "->" + messageMap.get("content") );

                    if(!messageMap.get("senderId").toString().equals(SENDER_ID) ) {
                        adapter.addMessage(messageMap.get("senderId").toString(), messageMap.get("content").toString(),
                                new Date(), true); //(Date) messageMap.get("time")
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
        else {
            msgReceiver.startReceivingMsg(this, SENDER_ID);

            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    String name = intent.getStringExtra("NAME");
                    String content = intent.getStringExtra("CONTENT");
                    String date = intent.getStringExtra("DATE");

                    Log.d("Kebrit:msg", "new msg received from braodcast.");

                    adapter.addMessage(name, content, date);
                }
            };
        }
    }

    private void fillMsgList() {

        if (isFirebase){
            myFirebase.child("messages").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {

                    ArrayList<Message> list = new ArrayList<Message>();

                    for (DataSnapshot child : snapshot.getChildren()) {
                        Map messageMap = (Map) child.getValue();

                        list.add(new Message(messageMap.get("senderId").toString(),
                                messageMap.get("receiverId").toString(), messageMap.get("content").toString(), new Date())); //(Date) messageMap.get("time")
                    }
                    adapter.addListMessages(list);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
        else {
            new FirstMsgListOperation().execute();
        }
    }

    private void sendMessage(String msg) {
        if (isFirebase){
            myFirebase.child("messages").push().
                    setValue(new Message(SENDER_ID, RECEIVER_ID, msg, new Date()));
        }
        else {
            new SendMsgOperation().execute(msg);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter("MSG_SIGNAL")
        );
    }

    @Override
    protected void onStop() {
//        msgReceiver.stopSelf();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);

        super.onStop();
    }

    private class FirstMsgListOperation extends AsyncTask<Void, Void, ArrayList<Message>> {

        @Override
        protected ArrayList<Message> doInBackground(Void... voids) {
            interaction = new Interaction();
            return interaction.getMsg(SENDER_ID);
        }

        @Override
        protected void onPostExecute(ArrayList<Message> result) {
            Log.d("kebrit:msg", "list filled with DB saved Msg's.");
            adapter.addListMessages(result);
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}

    }

    private class SendMsgOperation extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... msg) {

            try {
                Log.d("kebrit:msg", "User tries to send msg : " + msg[0]);
                interaction.sendMsg(msg[0], SENDER_ID, RECEIVER_ID);
                Log.d("kebrit:msg", "Last msg sent suc.");
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

    /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
