package com.orokalimpyo.okapp.record;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.orokalimpyo.okapp.R;
import com.orokalimpyo.okapp.firebase_crud.firebase_functions;

import java.text.SimpleDateFormat;
import java.util.Date;

public class contribution_summary extends AppCompatActivity implements View.OnClickListener {

    TextView tvID, tvName,tvType,tvAddress,tvBarangay,tvNumber,
            tvPlastic,tvBrand,tvKilo;

    ImageView ivBack;

    Button btnGenerate;

    String plastic,brand,kilo;
    String id;

    SimpleDateFormat month,day,year,week,date,hours,minutes,seconds,time;
    String currentMonth,currentDay,currentYear,currentWeek,currentDate,currentHour,currentMinute,currentSeconds,currentTime;

    firebase_functions ff;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribution_summary);



        ff = new firebase_functions();
        mAuth = FirebaseAuth.getInstance();
        tvID = findViewById(R.id.tvID);
        tvName = findViewById(R.id.tvName);
        tvType = findViewById(R.id.tvType);
        tvAddress = findViewById(R.id.tvAddress);
        tvBarangay = findViewById(R.id.tvBarangay);
        tvNumber = findViewById(R.id.tvNumber);

        tvPlastic = findViewById(R.id.tvPlastic);
        tvBrand = findViewById(R.id.tvBrand);
        tvKilo = findViewById(R.id.tvKilo);

        ivBack = findViewById(R.id.ivBack);

        btnGenerate = findViewById(R.id.btnGenerate);






        ivBack.setOnClickListener(this);
        btnGenerate.setOnClickListener(this);

        retrieveDate();
        retrieveRecentData();





    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivBack:
                finish();
                break;
            case R.id.btnGenerate:

                saveData();
                break;
        }
    }

    public void retrieveRecentData(){
        ff.retrieveProfile(mAuth.getUid(),tvType,tvName,tvBarangay,tvAddress,tvNumber);
//        id = mAuth.getUid().substring(0,9) + currentMonth + currentDay+currentYear+currentHour+currentMinute+currentSeconds;
        id = getIntent().getStringExtra("contribution_id");
        tvID.setText(id);
        plastic = getIntent().getStringExtra("plastic");
        brand = getIntent().getStringExtra("brand");
        kilo = getIntent().getStringExtra("kilo");
        tvPlastic.setText(plastic);
        tvBrand.setText(brand);
        tvKilo.setText(kilo);

    }
    public void saveData(){

        Intent i = new Intent(contribution_summary.this, generated_qr.class);
        i.putExtra("contribution_id", tvID.getText().toString());
        i.putExtra("name",tvName.getText().toString());
        i.putExtra("type",tvType.getText().toString());
        i.putExtra("address",tvAddress.getText().toString());
        i.putExtra("barangay",tvBarangay.getText().toString());
        i.putExtra("number",tvNumber.getText().toString());
        i.putExtra("plastic",plastic);
        i.putExtra("brand",brand);
        i.putExtra("kilo",kilo);
        startActivity(i);
    }

    public void retrieveDate(){
        month = new SimpleDateFormat("MM");
        day = new SimpleDateFormat("dd");
        year = new SimpleDateFormat("yy");

        week = new SimpleDateFormat("W");

        date = new SimpleDateFormat("MM/dd/yy");

        hours = new SimpleDateFormat("hh");
        minutes = new SimpleDateFormat("mm");
        seconds = new SimpleDateFormat("ss");

        time = new SimpleDateFormat("hh:mm:ss a");

        currentMonth = month.format(new Date());
        currentDay = day.format(new Date());
        currentYear = year.format(new Date());

        currentWeek = week.format(new Date());

        currentDate = date.format(new Date());

        currentHour = hours.format(new Date());
        currentMinute = minutes.format(new Date());
        currentSeconds = seconds.format(new Date());

        currentTime = time.format(new Date());

    }
}