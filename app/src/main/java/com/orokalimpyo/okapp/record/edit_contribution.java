package com.orokalimpyo.okapp.record;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.orokalimpyo.okapp.R;
import com.orokalimpyo.okapp.firebase_crud.firebase_functions;

public class edit_contribution extends AppCompatActivity implements View.OnClickListener {

    MaterialSpinner sPlastic,sBrand;
    EditText etKilo;
    Button btnUpdate,btnUpload;

    String _id,contribution_id,barangay;
    String name;
    ImageView ivQR;
    firebase_functions ff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contribution);


        sPlastic = findViewById(R.id.sPlastic);
        sBrand = findViewById(R.id.sBrand);
        etKilo = findViewById(R.id.etKilo);

        ivQR = findViewById(R.id.ivQR);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpload = findViewById(R.id.btnUpload);

        btnUpdate.setOnClickListener(this);
        btnUpload.setOnClickListener(this);

        ff = new firebase_functions();

        _id = getIntent().getStringExtra("id");
        contribution_id = getIntent().getStringExtra("contribution_id");
        barangay = getIntent().getStringExtra("barangay");


        Glide.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/orokalimpyo.appspot.com/o/TBC_Contributions%2FNazarethContribution_QRCodes%2F" + contribution_id + ".png?alt=media&token=a6f2bc95-baf9-4f13-b00f-610f3f0d9601").into(ivQR);
        ff.retrieveForEditting(_id,contribution_id,sPlastic,sBrand,etKilo);
//        ff.retrieveAllSpecificID(_id,contribution_id);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnUpdate:
                String plastic = sPlastic.getText().toString();
                String brand = sBrand.getText().toString();
                String kilo = etKilo.getText().toString();
                ff.updateContribution(getApplicationContext(),_id,contribution_id,barangay,plastic,brand,kilo);
                break;
            case R.id.btnUpload:

//                Toast.makeText(this, getName(), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}