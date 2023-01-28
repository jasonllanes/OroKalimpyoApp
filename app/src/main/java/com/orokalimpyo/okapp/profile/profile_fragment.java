package com.orokalimpyo.okapp.profile;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.orokalimpyo.okapp.R;
import com.orokalimpyo.okapp.authentication.log_in;
import com.orokalimpyo.okapp.authentication.sign_up_2;
import com.orokalimpyo.okapp.firebase_crud.firebase_functions;


public class profile_fragment extends Fragment implements View.OnClickListener {

    Button btnLogout,btnYes,btnNo;
    public TextView tvType,tvFullname,tvBarangay,tvAddress,tvNumber;
    firebase_functions ff;
    FirebaseAuth mAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_fragment, container, false);

        ff = new firebase_functions();
        mAuth = FirebaseAuth.getInstance();

        tvType = view.findViewById(R.id.tvType);
        tvFullname = view.findViewById(R.id.tvFullname);
        tvBarangay = view.findViewById(R.id.tvBarangay);
        tvAddress = view.findViewById(R.id.tvAddress);
        tvNumber = view.findViewById(R.id.tvNumber);

        btnLogout = view.findViewById(R.id.btnLogout);


        ff.retrieveProfile(mAuth.getUid(),tvType,tvFullname,tvBarangay,tvAddress,tvNumber);


        btnLogout.setOnClickListener(this);



        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogout:
                Dialog builder = new Dialog(getContext());
                builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                builder.setContentView(R.layout.log_out_pop);
                builder.setCancelable(true);
                builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                btnYes = builder.findViewById(R.id.btnYes);
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ff.logOut(getActivity(), getContext());
                        Toast.makeText(getActivity(), "Log out successfully", Toast.LENGTH_SHORT).show();



                    }
                });

                btnNo = builder.findViewById(R.id.btnNo);
                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), "No", Toast.LENGTH_SHORT).show();
                        builder.dismiss();
                    }
                });
                builder.show();
                break;


        }
    }
}