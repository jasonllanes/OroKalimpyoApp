package com.orokalimpyo.okapp.record;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.orokalimpyo.okapp.R;
import com.orokalimpyo.okapp.data.ContributionListData;
import com.orokalimpyo.okapp.firebase_crud.firebase_functions;

public class view_contributions extends AppCompatActivity {

    FirebaseListAdapter listAdapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    TextView tvID,tvDate;
    ListView lvContributions;

    firebase_functions ff;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contributions);

        lvContributions = findViewById(R.id.lvContributions);
        mAuth = FirebaseAuth.getInstance();
        ff = new firebase_functions();




        retrieveContributions(this,mAuth.getUid(),lvContributions);



    }
    public void retrieveContributions(Activity activity, String id, ListView contributionList){
        Query query = FirebaseDatabase.getInstance().getReference().child("TBC_Contributions/"+mAuth.getUid());
        FirebaseListOptions<ContributionListData> o = new FirebaseListOptions.Builder<ContributionListData>()
                .setLayout(R.layout.item_list)
                .setQuery(query,ContributionListData.class)
                .build();
        listAdapter = new FirebaseListAdapter(o) {
            @Override
            protected void populateView(View v, Object model, int position) {
                String id,barangay;
                TextView contribution_id = v.findViewById(R.id.tvID);
                TextView currentDateTime = v.findViewById(R.id.tvDateTime);
                TextView plastic = v.findViewById(R.id.tvPlastic);
                ImageView qr = v.findViewById(R.id.ivQR);

                ContributionListData contributionListData = (ContributionListData) model;
                id = ((ContributionListData) model).getId();
                contribution_id.setText(((ContributionListData) model).getContribution_id());
                barangay = ((ContributionListData) model).getBarangay();
                currentDateTime.setText(((ContributionListData) model).getDate() + " " + ((ContributionListData) model).getTime());
                plastic.setText(((ContributionListData) model).getPlastic());


                //set the proper link when saving already the contribution for this to work
                Glide.with(activity).load("https://firebasestorage.googleapis.com/v0/b/orokalimpyo.appspot.com/o/TBC_Contributions%2FNazarethContribution_QRCodes%2F5B7uY3Ioe013023034102.png?alt=media&token=a6f2bc95-baf9-4f13-b00f-610f3f0d9601").into(qr);

                contributionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        TextView tvID = view.findViewById(R.id.tvID);
                        String contribution_id = tvID.getText().toString();
                        Intent intent = new Intent(activity, edit_contribution.class);
                        intent.putExtra("id",id);
                        intent.putExtra("contribution_id",contribution_id);
                        intent.putExtra("barangay",barangay);
                        activity.startActivity(intent);

                    }
                });

            }
        };
        contributionList.setAdapter(listAdapter);

    }
    @Override
    protected void onStart() {
        super.onStart();
        listAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        listAdapter.stopListening();
    }

}