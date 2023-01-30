package com.orokalimpyo.okapp.record;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.orokalimpyo.okapp.R;
import com.orokalimpyo.okapp.firebase_crud.firebase_functions;

public class edit_contribution extends AppCompatActivity implements View.OnClickListener {

    MaterialSpinner sPlastic,sBrand;
    EditText etKilo;
    Button btnUpdate,btnUpload;

    String _id,contribution_id,barangay;

    firebase_functions ff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contribution);


        sPlastic = findViewById(R.id.sPlastic);
        sBrand = findViewById(R.id.sBrand);
        etKilo = findViewById(R.id.etKilo);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpload = findViewById(R.id.btnUpload);

        btnUpdate.setOnClickListener(this);
        btnUpload.setOnClickListener(this);

        ff = new firebase_functions();

        _id = getIntent().getStringExtra("id");
        contribution_id = getIntent().getStringExtra("contribution_id");
        barangay = getIntent().getStringExtra("barangay");


        ff.retrieveForEditting(_id,contribution_id,sPlastic,sBrand,etKilo);


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
                break;
        }
    }
}