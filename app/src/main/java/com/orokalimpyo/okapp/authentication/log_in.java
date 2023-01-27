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
import com.orokalimpyo.okapp.home.home;

public class log_in extends AppCompatActivity implements View.OnClickListener {
    TextView tvSignUp;
    EditText etEmail,etPassword;
    Button btnLogin;

    firebase_functions ff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        tvSignUp = findViewById(R.id.tvSignUp);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        ff = new firebase_functions();

        btnLogin.setOnClickListener(this);

        tvSignUp.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                if(etEmail.getText().toString().isEmpty()){
                    etEmail.setError("Please enter your email");
                }else if(etPassword.getText().toString().isEmpty()){
                    etPassword.setError("Empty Password");
                }else{
                    etEmail.setText("");
                    etPassword.setText("");
                    ff.logIn(this, getApplicationContext(),email,password);

                }
                break;
            case R.id.tvSignUp:
                etEmail.setText("");
                etPassword.setText("");
                Intent i = new Intent(log_in.this,sign_up.class);
                startActivity(i);
                break;
        }
    }
}