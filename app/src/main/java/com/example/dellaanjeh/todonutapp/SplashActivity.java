package com.example.dellaanjeh.todonutapp;

import android.support.v7.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    TextView tvTitle, tvDonut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tvDonut = (TextView) findViewById(R.id.tvDonut);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        Typeface sifonn = Typeface.createFromAsset(getAssets(), "fonts/SIFONN_BASIC.otf");
        Typeface sifonnOutline = Typeface.createFromAsset(getAssets(), "fonts/SIFONN_BASIC_OUTLINE.otf");
        tvDonut.setTypeface(sifonnOutline);
        tvTitle.setTypeface(sifonn);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
