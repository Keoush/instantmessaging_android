package com.example.kebrit.instantmessagingikiu.client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kebrit.instantmessagingikiu.R;
import com.example.kebrit.instantmessagingikiu.client.adapter.ContactListAdapter;
import com.example.kebrit.instantmessagingikiu.servercommunication.imhttpclientfile.Constants;
import com.firebase.client.Firebase;


public class MainActivity extends ActionBarActivity {

    private ContactListAdapter adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ListView contactList = (ListView) findViewById(R.id.contactListView);

        adapter = new ContactListAdapter(this);
        contactList.setAdapter(adapter);

        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final String name = (String) parent.getItemAtPosition(position);

                Log.d("Kebrit_Log", "contact element selected : " + name);

                Intent myIntent = new Intent(MainActivity.this, ChatActivity.class);
                myIntent.putExtra("contactName", name); //Optional parameters
                MainActivity.this.startActivity(myIntent);
            }
        });
//      ------------------------------------------------------------------------------------------------ added Test element...
        adapter.addContact("Group 1");
//      ------------------------------------------------------------------------------------------------

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
