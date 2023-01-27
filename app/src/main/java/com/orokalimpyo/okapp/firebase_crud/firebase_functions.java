package com.orokalimpyo.okapp.firebase_crud;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orokalimpyo.okapp.authentication.log_in;
import com.orokalimpyo.okapp.authentication.sign_up;
import com.orokalimpyo.okapp.data.UserDetails;
import com.orokalimpyo.okapp.home.home;

import java.util.ArrayList;
import java.util.List;

public class firebase_functions {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth mAuth;
    List<String> types = new ArrayList<String>();
    List<String> barangay = new ArrayList<String>();
    List<String> plastic = new ArrayList<String>();
    List<String> brand = new ArrayList<String>();

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
    public List<String> populatePlastic(){

        DatabaseReference typeOfUserReference = database.getReference("TypeOfPlastic");
        typeOfUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String userType = dataSnapshot.child("type").getValue(String.class);
                    plastic.add(userType);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return plastic;
    }
    public List<String> populateBrand(){

            DatabaseReference typeOfUserReference = database.getReference("TypeOfBrand");
        typeOfUserReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String userType = dataSnapshot.child("name").getValue(String.class);
                    brand.add(userType);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return brand;
    }



    public void check(Context context, Activity activity){
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            activity.startActivityForResult(cameraIntent, 3);
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, 100);
        }
    }

    public void retrieveProfile(String id,TextView type, TextView name, TextView barangay, TextView address, TextView number){
        DatabaseReference profileReference = database.getReference("Users/" + id);
        profileReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                type.setText(snapshot.child("user_type").getValue(String.class));
                name.setText(snapshot.child("name").getValue(String.class));
                barangay.setText(snapshot.child("barangay").getValue(String.class));
                address.setText(snapshot.child("address").getValue(String.class));
                number.setText(snapshot.child("number").getValue(String.class));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void logIn(Activity activity,Context context, String email, String password){
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(context, home.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); ;
                    context.startActivity(i);
                    activity.finish();
                }else{
                    Toast.makeText(context, task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void signUp(Activity activity, Context context,String user_type,String name, String barangay, String address,
                       String gender, String number,String email,String password){
        DatabaseReference accountRefGeneral = database.getReference("Users");
        DatabaseReference accountRefBarangay = database.getReference(barangay+"Users");
        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    UserDetails userDetails = new UserDetails(mAuth.getUid(),user_type, name, barangay, address, gender, number, email,password);
                    accountRefGeneral.child(mAuth.getUid()).setValue(userDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            accountRefBarangay.child(mAuth.getUid()).setValue(userDetails);
                            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(context, home.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); ;
                            context.startActivity(i);
                            activity.finish();
                        }
                    });
                }else{
                    Toast.makeText(context, task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void logOut(Activity activity, Context context){
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(context, log_in.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); ;
        context.startActivity(i);
        activity.finish();
    }


}
