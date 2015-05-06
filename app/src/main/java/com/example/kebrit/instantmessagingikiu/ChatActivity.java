package com.example.kebrit.instantmessagingikiu;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kebrit.instantmessagingikiu.adapter.MessageListAdapter;
import com.example.kebrit.instantmessagingikiu.imhttpclientfile.Interaction;

import java.util.Date;


public class ChatActivity extends ActionBarActivity {


    private MessageListAdapter adapter;
    private static Interaction interaction;
    private static String SENDER_ID = "1";
    private static String RECEIVER_ID = "2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        interaction = new Interaction();

        ListView chatList = (ListView) findViewById(R.id.listMessages);

        adapter = new MessageListAdapter(this);
        chatList.setAdapter(adapter);

//      ------------------------------------------------------------------------------------------------ added Test element...
        adapter.addMessage("first for test only..", false);
        adapter.addMessage("first received for test only..", true);
//      ------------------------------------------------------------------------------------------------

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
    }


    private class SendMsgOperation extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... msg) {
            Interaction interaction = new Interaction();
            try {
                    Log.d("kebrit:msgSending", "Tring to send msg. -> " + msg[0]);
                    interaction.sendMsg(msg[0], "1", "2");
                    Log.d("kebrit:msgSending", "sent suc.");
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
