package com.orokalimpyo.okapp.firebase_crud;


import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class firebase_functions {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    List<String> types = new ArrayList<String>();
    List<String> barangay = new ArrayList<String>();


    public List<String> populateUserType(){

        DatabaseReference typeOfUserReference = database.getReference("TypeOfUser");
        typeOfUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String userType = dataSnapshot.child("type").getValue(String.class);
                    types.add(userType);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return types;
    }
    public List<String> populateBarangay(){

        DatabaseReference typeOfUserReference = database.getReference("BarangayList");
        typeOfUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String userType = dataSnapshot.child("name").getValue(String.class);
                    barangay.add(userType);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return barangay;
    }

    public void logIn(){

    }

    public void signUp(){

    }


}
