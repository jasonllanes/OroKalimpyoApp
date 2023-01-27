package com.orokalimpyo.okapp.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.orokalimpyo.okapp.R;
import com.orokalimpyo.okapp.firebase_crud.firebase_functions;

public class sign_up_2 extends AppCompatActivity implements View.OnClickListener {

    firebase_functions ff;
    Button btnSignIn;
    TextView tvLogin;
    EditText etEmail,etPassword,etConfirmPassword;
    String type,name,barangay,address,gender,number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        ff = new firebase_functions();
        tvLogin = findViewById(R.id.tvLogin);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSignIn = findViewById(R.id.btnSignIn);

        retrieveRecentData();


        btnSignIn.setOnClickListener(this);




    }
    public void retrieveRecentData(){
        type = getIntent().getStringExtra("type");
        name = getIntent().getStringExtra("name");
        barangay = getIntent().getStringExtra("barangay");
        address = getIntent().getStringExtra("address");
        gender = getIntent().getStringExtra("gender");
        number = getIntent().getStringExtra("number");
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignIn:
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String confirm = etConfirmPassword.getText().toString();
                if(etEmail.getText().toString().isEmpty()){
                    etEmail.setError("Please enter your email");
                }else if(!password.equalsIgnoreCase(confirm)){
                    etPassword.setError("Not Matched");
                    etConfirmPassword.setError("Not Matched");
                }else if(etPassword.getText().toString().isEmpty()){
                    etPassword.setError("Empty Password");
                }else{
                    etEmail.setText("");
                    etPassword.setText("");
                    etConfirmPassword.setText("");
                    ff.signUp(this, getApplicationContext(),type,name,barangay,address,gender,number,email,password);
                }
                break;
            case R.id.tvLogin:
                Intent i = new Intent(sign_up_2.this, log_in.class);
                startActivity(i);
                finish();
        }
    }
}