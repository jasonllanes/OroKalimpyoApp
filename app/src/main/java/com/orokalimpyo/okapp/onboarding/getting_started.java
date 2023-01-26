package com.orokalimpyo.okapp.onboarding;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.orokalimpyo.okapp.R;
import com.orokalimpyo.okapp.SplashActivity;
import com.orokalimpyo.okapp.home.home;

public class getting_started extends AppCompatActivity {

    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_started);

        btnStart = findViewById(R.id.startButton);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getting_started.this, navigation_activity.class);
                startActivity(intent);
                finish();
            }
        });



    }
}