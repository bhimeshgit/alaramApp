package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.user.UserRegistrationActivity;
import com.example.myapplication.user.UserWelcomeActivity;
import com.example.myapplication.utils.AppSettingSharePref;
//	bmi_range	morning_drink	morning_break	lunch	evening_break	dinner	exercise
public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if (!AppSettingSharePref.getInstance(SplashScreenActivity.this).getUserName().trim().equals("")) {
                    Intent i = new Intent(SplashScreenActivity.this, UserWelcomeActivity.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(SplashScreenActivity.this, UserRegistrationActivity.class);
                    startActivity(i);
                }

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);


    }
}