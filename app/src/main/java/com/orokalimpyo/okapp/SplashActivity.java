package com.orokalimpyo.okapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.orokalimpyo.okapp.authentication.log_in;
import com.orokalimpyo.okapp.home.home;
import com.orokalimpyo.okapp.onboarding.getting_started;
import com.orokalimpyo.okapp.onboarding.navigation_activity;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(SplashActivity.this, home.class);
                    startActivity(intent);
                    finish();
                }else{
                    SharedPreferences sharedPreferences = getSharedPreferences("getting_started",MODE_PRIVATE);
                    String value = sharedPreferences.getString("was_launched", "");
                    Toast.makeText(SplashActivity.this, "", Toast.LENGTH_SHORT).show();

                    if(value.equalsIgnoreCase("true")){
                        Intent intent = new Intent(SplashActivity.this, log_in.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Intent intent = new Intent(SplashActivity.this, getting_started.class);
                        startActivity(intent);
                        finish();
                    }
                }



            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}