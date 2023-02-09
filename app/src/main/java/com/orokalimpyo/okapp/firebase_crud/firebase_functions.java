package com.orokalimpyo.okapp.firebase_crud;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.orokalimpyo.okapp.R;
import com.orokalimpyo.okapp.authentication.log_in;
import com.orokalimpyo.okapp.authentication.sign_up;
import com.orokalimpyo.okapp.data.ContributionDetails;
import com.orokalimpyo.okapp.data.ContributionListData;
import com.orokalimpyo.okapp.data.UpdateDetails;
import com.orokalimpyo.okapp.data.UserDetails;
import com.orokalimpyo.okapp.home.home;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.orokalimpyo.okapp.record.edit_contribution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class firebase_functions {
    FirebaseListAdapter listAdapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth mAuth;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    private Uri filePath;

    String _id,_type,_name,_barangay,_address,_number;
    String contribution_id;

    List<String> types = new ArrayList<String>();
    List<String> barangay = new ArrayList<String>();
    List<String> plastic = new ArrayList<String>();
    List<String> brand = new ArrayList<String>();

    List<String> data = new ArrayList<String>();

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



    public void retrieveForEditting(String id,String contribution_id, MaterialSpinner plastic, MaterialSpinner brand, EditText kilo){
        DatabaseReference profileReference = database.getReference("TBC_Contributions/" + id+"/"+contribution_id);
        profileReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                plastic.setText(snapshot.child("plastic").getValue(String.class));
                brand.setText(snapshot.child("brand").getValue(String.class));
                kilo.setText(snapshot.child("kilo").getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void retrieveAllSpecificID(String id,String contribution_id,String name){
//        String type, String barangay, String address, String number, String plastic, String brand, String kilo, String month, String day, String year, String date, String time,String imageLink

        DatabaseReference profileReference = database.getReference("TBC_Contributions/" +id+"/"+contribution_id);
        profileReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                snapshot.child("name").getValue(String.class);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }



    public List<String> retrieveUserDetails(){
        DatabaseReference profileReference = database.getReference("Users/" + mAuth.getUid());
        profileReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                _id = mAuth.getUid();
                _type = snapshot.child("user_type").getValue(String.class);
                _name = snapshot.child("name").getValue(String.class);
                _barangay = snapshot.child("barangay").getValue(String.class);
                _address = snapshot.child("address").getValue(String.class);
                _number = snapshot.child("number").getValue(String.class);

                data.add(_id);
                data.add(_type);
                data.add(_name);
                data.add(_barangay);
                data.add(_address);
                data.add(_number);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return data;

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

    public void saveQRStorage(byte[] bb,Context context, String barangay,String id){
        StorageReference ref = storageRef.child("TBC_Contributions/" + barangay+"Contribution_QRCodes/" + id + ".png");

        ref.putBytes(bb).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveProofStorage(byte[] bb,Context context, String barangay,String id){
        StorageReference generalProof = storageRef.child("Proof_Contributions/" + id + ".png");
//        StorageReference specificProof = storageRef.child(barangay+"Proof_Contributions/" + id + ".png");
        generalProof.putBytes(bb).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                specificProof.putBytes(bb);;

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void updateContribution(Context context,String id,String contribution_id,String barangay,String plastic,String brand,String kilo){
        DatabaseReference contributionsRefSpecific = database.getReference(barangay+"_TBC_Contributions");
        DatabaseReference contributionsRefGeneral = database.getReference("TBC_Contributions");
        UpdateDetails updateDetails = new UpdateDetails(plastic,brand,kilo);

        Map<String, Object> updates = new HashMap<>();
        updates.put("plastic", plastic);
        updates.put("brand", brand);
        updates.put("kilo", kilo);
        contributionsRefGeneral.child(id).child(contribution_id).updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                contributionsRefSpecific.child(id).setValue(updateDetails);
                Toast.makeText(context, "Successfully Edited", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveTBCContributions(Activity activity,Context context, String id,String contribution_id,String name,String type, String barangay, String address,
                                     String number, String plastic,String brand,String kilo,String month,String day,String year,String date,String time,String imageLink){
        DatabaseReference contributionsRefGeneral = database.getReference("TBC_Contributions");
        DatabaseReference contributionsDashboardData = database.getReference("Contributions");
        DatabaseReference contributionsRefSpecific = database.getReference(barangay+"_TBC_Contributions");
        ContributionDetails contributionDetails = new
                ContributionDetails(id,contribution_id,name, type, barangay, address, number, plastic, brand, kilo, month, day, year, date, time, imageLink);

        contributionsRefGeneral.child(id).child(contribution_id).setValue(contributionDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                contributionsDashboardData.child(contribution_id).setValue(contributionDetails);
                contributionsRefSpecific.child(id).setValue(contributionDetails);
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    public void saveTBCContributions(Activity activity,Context context, String id,String contribution_id,String name,String type, String barangay, String address,
//                                     String number, String plastic,String brand,String kilo,String month,String day,String year,String date,String time,String imageLink){
//        DatabaseReference contributionsRefGeneral = database.getReference("TBC_Contributions");
//        DatabaseReference contributionsRefSpecific = database.getReference(barangay+"_TBC_Contributions");
//        ContributionDetails contributionDetails = new
//                ContributionDetails(id,contribution_id,name, type, barangay, address, number, plastic, brand, kilo, month, day, year, date, time, imageLink);
//
//        contributionsRefGeneral.child(id).child(contribution_id).setValue(contributionDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                contributionsRefSpecific.child(id).child(contribution_id).setValue(contributionDetails);
//                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

}
