package com.example.kebrit.instantmessagingikiu.client.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;

import com.example.kebrit.instantmessagingikiu.R;

import java.util.logging.Handler;

/**
 * Created by Keoush on 5/19/2015.
 */
public class SplashActivity extends Activity {

    Intent nextAcitvity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        SharedPreferences preferences = getSharedPreferences("PREFERENCES", 0);

        if(preferences.contains("USERNAME")){
            Log.d("Kebrit", "userName exist . skip log_in activity.");
            nextAcitvity = new Intent(SplashActivity.this, MainActivity.class);
            nextAcitvity.putExtra("USERNAME", preferences.getString("USERNAME", ""));
            return;
        }
        else{
            nextAcitvity = new Intent(SplashActivity.this, LogInActivity.class);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        View v = findViewById(R.id.splashImageView);

        AnimationSet set = new AnimationSet(true);

        Animation fadeIn = FadeIn(800);
        fadeIn.setStartOffset(200);
        set.addAnimation(fadeIn);

        Animation fadeOut = FadeOut(800);
        fadeOut.setStartOffset(1800);
        set.addAnimation(fadeOut);

        v.startAnimation(set);

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(2500);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    startActivity(nextAcitvity);
                    finish();
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private Animation FadeIn(int t)
    {
        Animation fade;
        fade = new AlphaAnimation(0.0f,1.0f);
        fade.setDuration(t);
        fade.setInterpolator(new AccelerateInterpolator());
        return fade;
    }
    private Animation FadeOut(int t)
    {
        Animation fade;
        fade = new AlphaAnimation(1.0f,0.0f);
        fade.setDuration(t);
        fade.setInterpolator(new AccelerateInterpolator());
        return fade;
    }

}
