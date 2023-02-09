package com.orokalimpyo.okapp.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.orokalimpyo.okapp.R;
import com.orokalimpyo.okapp.firebase_crud.firebase_functions;

import java.util.ArrayList;

public class sign_up extends AppCompatActivity implements View.OnClickListener {

    EditText etFullname,etAddress,etMobileNumber;
    TextView tvLogin;
    MaterialSpinner sType,sBarangay,sGender;
    firebase_functions ff;
    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ff = new firebase_functions();

        sType = findViewById(R.id.sType);
        etFullname = findViewById(R.id.etFullname);
        sBarangay = findViewById(R.id.sBarangay);
        etAddress = findViewById(R.id.etAddress);
        sGender = findViewById(R.id.sGender);
        etMobileNumber = findViewById(R.id.etMobileNumber);
        btnNext = findViewById(R.id.btnNext);
        tvLogin = findViewById(R.id.tvLogin);


        populateSpinners();

        btnNext.setOnClickListener(this);
        tvLogin.setOnClickListener(this);




    }

    public void populateSpinners(){
        sType = (MaterialSpinner) findViewById(R.id.sType);
        sType.setItems(ff.populateUserType());
        sType.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {


                Snackbar customSnackBar = Snackbar.make(view, "Input " + item + " name", Snackbar.LENGTH_LONG);
                customSnackBar.setTextColor(ContextCompat.getColor(sign_up.this,R.color.white));
                customSnackBar.setBackgroundTint(ContextCompat.getColor(sign_up.this,R.color.green));
                customSnackBar.show();

                etFullname.setHint(item + " name");
                etFullname.setVisibility(View.VISIBLE);
            }
        });

        sBarangay = (MaterialSpinner) findViewById(R.id.sBarangay);
        sBarangay.setItems(ff.populateBarangay());

        sBarangay.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar customSnackBar = Snackbar.make(view, item, Snackbar.LENGTH_LONG);
                customSnackBar.setTextColor(ContextCompat.getColor(sign_up.this,R.color.white));
                customSnackBar.setBackgroundTint(ContextCompat.getColor(sign_up.this,R.color.green));
                customSnackBar.show();
            }
        });

        sGender = (MaterialSpinner) findViewById(R.id.sGender);
        sGender.setItems("Male", "Female");
        sGender.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                Snackbar customSnackBar = Snackbar.make(view, item, Snackbar.LENGTH_LONG);
//                customSnackBar.setTextColor(ContextCompat.getColor(sign_up.this,R.color.white));
//                customSnackBar.setBackgroundTint(ContextCompat.getColor(sign_up.this,R.color.green));
//                customSnackBar.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnNext:
                if(sType.equals("") || sBarangay.equals("") || etFullname.getText().toString().isEmpty() ||
                    etAddress.getText().toString().isEmpty() || sGender.equals("") || etMobileNumber.getText().toString().isEmpty()){
                    Snackbar customSnackBar = Snackbar.make(v, "Please fill up all fields", Snackbar.LENGTH_LONG);
                    customSnackBar.setTextColor(ContextCompat.getColor(sign_up.this,R.color.white));
                    customSnackBar.setBackgroundTint(ContextCompat.getColor(sign_up.this,R.color.green));
                    customSnackBar.show();
                }else{
                    Snackbar customSnackBar = Snackbar.make(v, "All Goods", Snackbar.LENGTH_LONG);
                    customSnackBar.setTextColor(ContextCompat.getColor(sign_up.this,R.color.white));
                    customSnackBar.setBackgroundTint(ContextCompat.getColor(sign_up.this,R.color.green));
                    customSnackBar.show();

                    String type = sType.getText().toString();
                    String name = etFullname.getText().toString();
                    String barangay = sBarangay.getText().toString();
                    String address = etAddress.getText().toString();
                    String gender = sGender.getText().toString();
                    String number = etMobileNumber.getText().toString();

                    Intent i = new Intent(sign_up.this,sign_up_2.class);
                    i.putExtra("type", type);
                    i.putExtra("name", name);
                    i.putExtra("barangay", barangay);
                    i.putExtra("address", address);
                    i.putExtra("gender", gender);
                    i.putExtra("number", number);
                    startActivity(i);


                }
                break;
            case R.id.tvLogin:
                Intent iLogIn = new Intent(sign_up.this,log_in.class);
                startActivity(iLogIn);
                finish();
                break;
        }
    }


}