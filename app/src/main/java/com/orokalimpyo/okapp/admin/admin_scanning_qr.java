package com.orokalimpyo.okapp.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.zxing.Result;
import com.orokalimpyo.okapp.R;

public class admin_scanning_qr extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    StorageReference storageRef;
    final long ONE_MEGABYTE = 1024 * 1024 *5;

    private CodeScanner mCodeScanner;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    TextView tv_time,tv_timee,tv_details,tv_name;
    ImageView ivQR;
    Button btnUpdate;

    String year,department,course,uid,fullname,currentTime = " ";
    String contribution_id,type,name,barangay,address,number,plastic,brand,kilo,imageLink;
    LinearLayout linearLayout;

    TextClock tc_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_scanning_qr);

        Dialog dialog = new Dialog(admin_scanning_qr.this);
        dialog.setContentView(R.layout.scanned_result);
        dialog.setCancelable(true);

        linearLayout = (LinearLayout) findViewById(R.id.mainLayout);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
            }
        }

        String time = getIntent().getStringExtra("Time");


        tv_time = findViewById(R.id.tv_time);
//        tc_time = findViewById(R.id.digitalClock);

        btnUpdate = dialog.findViewById(R.id.btnUpdate);
        tv_details = dialog.findViewById(R.id.tv_details);
        ivQR = dialog.findViewById(R.id.ivQR);
        tv_name = dialog.findViewById(R.id.tv_name);
//        tv_time.setText(time);




        DatabaseReference myRef = database.getReference(tv_time.getText().toString());






//        btn_submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                currentTime = tc_time.getText().toString();
//
//                try{
//                    String details = tv_details.getText().toString();
//                    String[] details_split = details.split("\n");
//                    for (int i=0; i < details_split.length; i++){
//                        fullname = details_split[0];
//                        course = details_split[1];
//                        year = details_split[2];
//
//                    }
//
//                    Accounts acc = new Accounts(fullname,course,year,currentTime);
//                    myRef.child(fullname).setValue(acc).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            @SuppressLint("ResourceAsColor") Snackbar snackbar = Snackbar
//                                    .make(linearLayout, "Nice ka one " + fullname.toUpperCase() + "!", Snackbar.LENGTH_LONG).setTextColor(getResources().getColor(R.color.black)).setBackgroundTint(getResources().getColor(R.color.green));
//                            snackbar.show();
//                            dialog.cancel();
//                        }
//                    });
//                }catch (ArrayIndexOutOfBoundsException e){
//                    Snackbar snackbar = Snackbar
//                            .make(linearLayout, "Dili mana mao na QR Code chuy", Snackbar.LENGTH_LONG).setTextColor(getResources().getColor(R.color.white)).setBackgroundTint(getResources().getColor(R.color.red));
//                    snackbar.show();
//                }
//
//
//
//            }
//        });

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try{
                            String details = result.getText();
                            String[] details_split = details.split("\n");
                            for (int i=0; i < details_split.length; i++){
                                contribution_id = details_split[0];
                                type = details_split[1];
                                name = details_split[2];
                                barangay = details_split[3];
                                address = details_split[4];
                                number = details_split[5];
                                plastic = details_split[6];
                                brand = details_split[7];
                                kilo = details_split[8];
                                imageLink = details_split[9];
                            }
                            tv_name.setText("It's you " + name + "♥");
                            tv_details.setText(contribution_id+"\n"+type+"\n"+name+"\n"+barangay+"\n"+address+"\n"+number+"\n"+plastic+"\n"+brand+"\n"+kilo+"\n"+imageLink);
                        } catch (ArrayIndexOutOfBoundsException e){
                            Snackbar snackbar = Snackbar
                                    .make(linearLayout, "Invalid QR-Code", Snackbar.LENGTH_LONG).setTextColor(getResources().getColor(R.color.white)).setBackgroundTint(getResources().getColor(R.color.green));
                            snackbar.show();
                        }
                        System.out.println(uid);
                        storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://orokalimpyo.appspot.com/TBC_Contributions/"+barangay+"Contribution_QRCodes/"+contribution_id.trim()+".png");
                        storageRef.getBytes(ONE_MEGABYTE)
                                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                    @Override
                                    public void onSuccess(byte[] bytes) {
                                        Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                        DisplayMetrics dm = new DisplayMetrics();
                                        getWindowManager().getDefaultDisplay().getMetrics(dm);

                                        ivQR.setMinimumHeight(dm.heightPixels);
                                        ivQR.setMinimumWidth(dm.widthPixels);
                                        ivQR.setImageBitmap(bm);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        System.out.println(e);
                                        Toast.makeText(admin_scanning_qr.this, "Something went wrong!", Toast.LENGTH_LONG).show();

                                    }
                                });
//                        Toast.makeText(admin_scanning_qr.this, fullname+"\n"+course+"\n"+year, Toast.LENGTH_SHORT).show();

                        dialog.show();
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }




    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}