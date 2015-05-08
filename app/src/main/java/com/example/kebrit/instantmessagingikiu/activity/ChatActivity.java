package com.example.kebrit.instantmessagingikiu.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import com.example.kebrit.instantmessagingikiu.service.MsgReceiverIntentService;
import com.example.kebrit.instantmessagingikiu.R;
import com.example.kebrit.instantmessagingikiu.adapter.MessageListAdapter;
import com.example.kebrit.instantmessagingikiu.imhttpclientfile.Interaction;
import com.example.kebrit.instantmessagingikiu.parser.Message;

import java.util.ArrayList;
import java.util.Date;


public class ChatActivity extends ActionBarActivity {

    private MessageListAdapter adapter;

    private BroadcastReceiver receiver;
    private MsgReceiverIntentService msgReceiver;

    private static Interaction interaction;

    private static String SENDER_ID = "1";
    private static String RECEIVER_ID = "2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        interaction = new Interaction();
        adapter = new MessageListAdapter(this);

        ListView chatList = (ListView) findViewById(R.id.listMessages);

        chatList.setAdapter(adapter);

        final EditText inputText = (EditText) findViewById(R.id.messageBodyField);

        Button sendButton = (Button) findViewById(R.id.sendButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inputText.getText().toString().equals("")) {

                    final String msg = inputText.getText().toString();
                    adapter.addMessage(msg, new Date(), false);

                    new SendMsgOperation().execute(msg);

                    inputText.setText("");

                } else {
                    Toast.makeText(getApplicationContext(), "Please enter some text...",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String name = intent.getStringExtra("NAME");
                String content = intent.getStringExtra("CONTENT");
                String date = intent.getStringExtra("DATE");

                Log.d("Kebrit:msg", "new msg received from braodcast.");

                adapter.addMessage(content, date, name);
            }
        };

        new FirstMsgListOperation().execute();

        msgReceiver.startReceivingMsg(this);

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
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
//        msgReceiver.stopSelf();
        super.onStop();
    }

    private class FirstMsgListOperation extends AsyncTask<Void, Void, ArrayList<Message>> {

        @Override
        protected ArrayList<Message> doInBackground(Void... voids) {
            interaction = new Interaction();

            return interaction.getMsg(RECEIVER_ID);
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
