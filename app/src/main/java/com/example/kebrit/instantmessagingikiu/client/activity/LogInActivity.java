package com.example.kebrit.instantmessagingikiu.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kebrit.instantmessagingikiu.R;


public class LogInActivity extends Activity{

    private Button logInButton;
    private EditText nameText;
    private EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final SharedPreferences logHistory = getSharedPreferences("USERHISTORY", 0);

        if(logHistory.contains("USERNAME")){
            Log.d("Kebrit", "userName exist . skip log_in activity.");
            Intent myIntent = new Intent(LogInActivity.this, MainActivity.class);
            myIntent.putExtra("USERNAME", logHistory.getString("USERNAME", ""));
            LogInActivity.this.startActivity(myIntent);

            finish();
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

                logHistory.edit().putString("USERNAME", name).commit();
                Log.d("Kebrit", "new userName Entered : " + name);

                Intent myIntent = new Intent(LogInActivity.this, MainActivity.class);
                myIntent.putExtra("name", name);
                LogInActivity.this.startActivity(myIntent);

                finish();
            }
        });
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
