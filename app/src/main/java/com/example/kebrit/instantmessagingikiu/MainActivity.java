package com.example.kebrit.instantmessagingikiu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {

    private ContactListAdapter adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Intent myIntent = new Intent(MainActivity.this, LogInActivity.class);
//        myIntent.putExtra("name", "test"); //Optional parameters
//        MainActivity.this.startActivity(myIntent);
//        finish();
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
                myIntent.putExtra("name", name); //Optional parameters
                MainActivity.this.startActivity(myIntent);
            }
        });
//      ------------------------------------------------------------------------------------------------ added Test element...
        adapter.addContact("kebrit bala");
 //      ------------------------------------------------------------------------------------------------

    }

    private void testRestart(){
//        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
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
