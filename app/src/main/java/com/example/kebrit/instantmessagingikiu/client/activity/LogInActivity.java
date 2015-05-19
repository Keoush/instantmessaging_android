package com.example.kebrit.instantmessagingikiu.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kebrit.instantmessagingikiu.R;
import com.example.kebrit.instantmessagingikiu.servercommunication.imhttpclientfile.Constants;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class LogInActivity extends Activity{

    private Button logInButton;
    private EditText nameText;
    private EditText passwordText;

    private SharedPreferences preferences;
    private boolean isFirebase = true;

    private Firebase myFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Firebase.setAndroidContext(this);
        myFirebase = new Firebase(Constants.URL_FIREBASE);

        preferences = getSharedPreferences("PREFERENCES", 0);

        if(preferences.contains("USERNAME")){
            Log.d("Kebrit", "userName exist . skip log_in activity.");
            goToNextActivity(preferences.getString("USERNAME", ""));
            return;
        }

        setContentView(R.layout.activity_log_in);

        logInButton = (Button) findViewById(R.id.logInButton);
        nameText = (EditText) findViewById(R.id.nameField);
        passwordText = (EditText) findViewById(R.id.passwordField);

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = nameText.getText().toString();

                if(isFirebase) {
                    checkIfUserExists(name, name);
                }
                else{
                    preferences.edit().putString("USERID", name).commit();
                    preferences.edit().putString("USERNAME", name).commit();
                }

                goToNextActivity(name);
            }
        });
    }

    private void checkIfUserExists(final String uID, final String uName) {
        myFirebase.child("users").child(uID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() == null) {
                    Map map = new HashMap();
                    map.put("userId", uID);
                    map.put("username", uName);

                    myFirebase.child("users").child(uID).setValue(map);

                    preferences.edit().putString("USERNAME", uName).commit();
                    preferences.edit().putString("USERID", uID).commit();

                } else {
                    Toast.makeText(LogInActivity.this, "usersname already exists\nenter new name.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
    }

    private void goToNextActivity(String name) {

        Intent myIntent = new Intent(LogInActivity.this, MainActivity.class);
        myIntent.putExtra("USERNAME", name);
        LogInActivity.this.startActivity(myIntent);

        finish();
        return;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_log_in, menu);
//        return true;
//    }
//
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
