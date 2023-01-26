package com.orokalimpyo.okapp.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.orokalimpyo.okapp.R;
import com.orokalimpyo.okapp.firebase_crud.firebase_functions;

import java.util.ArrayList;

public class sign_up extends AppCompatActivity {

    EditText etFullname;
    firebase_functions ff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ff = new firebase_functions();
        etFullname = findViewById(R.id.etFullname);


//        ArrayList<String> types = new ArrayList<String>();
//        types.add("House");
//        ff.populateUserType(types);
        MaterialSpinner type = (MaterialSpinner) findViewById(R.id.sType);
        type.setItems(ff.populateUserType());
        type.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {


                Snackbar customSnackBar = Snackbar.make(view, "Input " + item + " name", Snackbar.LENGTH_LONG);
                customSnackBar.setTextColor(ContextCompat.getColor(sign_up.this,R.color.white));
                customSnackBar.setBackgroundTint(ContextCompat.getColor(sign_up.this,R.color.green));
                customSnackBar.show();

                etFullname.setHint(item + " name");
                etFullname.setVisibility(View.VISIBLE);
            }
        });

        MaterialSpinner barangay = (MaterialSpinner) findViewById(R.id.sBarangay);
        barangay.setItems(ff.populateBarangay());
        barangay.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar customSnackBar = Snackbar.make(view, item, Snackbar.LENGTH_LONG);
                customSnackBar.setTextColor(ContextCompat.getColor(sign_up.this,R.color.white));
                customSnackBar.setBackgroundTint(ContextCompat.getColor(sign_up.this,R.color.green));
                customSnackBar.show();
            }
        });

        MaterialSpinner gender = (MaterialSpinner) findViewById(R.id.sGender);
        gender.setItems("Male", "Female");
        gender.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                Snackbar customSnackBar = Snackbar.make(view, item, Snackbar.LENGTH_LONG);
//                customSnackBar.setTextColor(ContextCompat.getColor(sign_up.this,R.color.white));
//                customSnackBar.setBackgroundTint(ContextCompat.getColor(sign_up.this,R.color.green));
//                customSnackBar.show();
            }
        });



    }
}