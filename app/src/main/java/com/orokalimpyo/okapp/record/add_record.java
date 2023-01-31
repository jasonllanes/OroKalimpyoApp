package com.orokalimpyo.okapp.record;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.orokalimpyo.okapp.R;
import com.orokalimpyo.okapp.firebase_crud.firebase_functions;
import com.orokalimpyo.okapp.ml.BrandModelUnquant;
import com.orokalimpyo.okapp.ml.ModelUnquant;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class add_record extends AppCompatActivity implements View.OnClickListener {
    int imageSize = 224;
    ImageView ivBack;
    Button btnScanPlastic,btnScanBrand,btnConfirm,btnNext;
    EditText etKilo;
    ShapeableImageView ivResult;
    TextView tvResult,tvDescription;
    MaterialSpinner sPlastic,sBrand;
    Bitmap image;
    firebase_functions ff;
    int camera;

    LinearLayout layout;

    FirebaseAuth mAuth;
    String id;

    SimpleDateFormat month,day,year,week,date,hours,minutes,seconds,time;
    String currentMonth,currentDay,currentYear,currentWeek,currentDate,currentHour,currentMinute,currentSeconds,currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        ff = new firebase_functions();
        mAuth = FirebaseAuth.getInstance();
        ivBack = findViewById(R.id.ivBack);
        sPlastic = findViewById(R.id.sPlastic);
        sBrand = findViewById(R.id.sBrand);
        etKilo = findViewById(R.id.etKilo);
        btnScanPlastic = findViewById(R.id.btnScanPlastic);
        btnScanBrand = findViewById(R.id.btnScanBrand);
        btnNext = findViewById(R.id.btnNext);

        layout = findViewById(R.id.layout);

        ivBack.setOnClickListener(this);
        btnScanPlastic.setOnClickListener(this);
        btnScanBrand.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        populateSpinners();
        retrieveDate();
        retrieveRecentData();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivBack:
                finish();
                break;
            case R.id.btnScanPlastic:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        camera = 1;
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, 3);
                    } else {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                    }
                }
                break;
            case R.id.btnScanBrand:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        camera= 2;
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, 4);
                    } else {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                    }
                }
                break;
            case R.id.btnNext:
                sendData();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
            if(camera == 1){
                if(requestCode == 3){

                    image = (Bitmap) data.getExtras().get("data");
                    int dimension = Math.min(image.getWidth(), image.getHeight());
                    image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);

                    image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                    classifyPlastic(image);
                    camera = 0;
                }else{
                    Uri dat = data.getData();
                    image = null;
                    try {
                        image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dat);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                    classifyPlastic(image);
                    camera = 0;
                }
            }else if(camera == 2) {
                if(requestCode == 4) {

                    image = (Bitmap) data.getExtras().get("data");
                    int dimension = Math.min(image.getWidth(), image.getHeight());
                    image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);

                    image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                    classifyBrand(image);
                    camera = 0;
                } else {
                    Uri dat = data.getData();
                    image = null;
                    try {
                        image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dat);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                    classifyBrand(image);
                    camera = 0;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void retrieveDate(){
        month = new SimpleDateFormat("MM");
        day = new SimpleDateFormat("dd");
        year = new SimpleDateFormat("yy");

        week = new SimpleDateFormat("W");

        date = new SimpleDateFormat("MM/dd/yy");

        hours = new SimpleDateFormat("hh");
        minutes = new SimpleDateFormat("mm");
        seconds = new SimpleDateFormat("ss");

        time = new SimpleDateFormat("hh:mm:ss a");

        currentMonth = month.format(new Date());
        currentDay = day.format(new Date());
        currentYear = year.format(new Date());

        currentWeek = week.format(new Date());

        currentDate = date.format(new Date());

        currentHour = hours.format(new Date());
        currentMinute = minutes.format(new Date());
        currentSeconds = seconds.format(new Date());

        currentTime = time.format(new Date());

    }

    public void retrieveRecentData(){
        id = mAuth.getUid().substring(0,9) + currentMonth + currentDay+currentYear+currentHour+currentMinute+currentSeconds;
    }

    public void sendData(){
        if(sPlastic.getText().toString().isEmpty() || sBrand.getText().toString().isEmpty() || etKilo.getText().toString().isEmpty()){
            Snackbar customSnackBar = Snackbar.make(layout, "Please fill up all fields.", Snackbar.LENGTH_LONG);
            customSnackBar.setTextColor(ContextCompat.getColor(add_record.this,R.color.white));
            customSnackBar.setBackgroundTint(ContextCompat.getColor(add_record.this,R.color.green));
            customSnackBar.show();
        }else{
            Intent i = new Intent(add_record.this,add_record_2.class);
            i.putExtra("contribution_id",id);
            i.putExtra("plastic",sPlastic.getText().toString());
            i.putExtra("brand",sBrand.getText().toString());
            i.putExtra("kilo",etKilo.getText().toString());
            startActivity(i);
        }
    }
    public void classifyPlastic(Bitmap image){
        try {
            ModelUnquant model = ModelUnquant.newInstance(getApplicationContext());
            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int[] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
            int pixel = 0;
            //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
            for(int i = 0; i < imageSize; i ++){
                for(int j = 0; j < imageSize; j++){
                    int val = intValues[pixel++]; // RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                }
            }

            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            ModelUnquant.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();
            // find the index of the class with the biggest confidence.
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidences.length; i++) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }
            String[] classes = {"Plastic Bottle","Carton","Shampoo Bottle"};
            Dialog builder = new Dialog(add_record.this);
            builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
            builder.setContentView(R.layout.scanned_plastic_pop);
            builder.setCancelable(true);
            builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            tvResult = builder.findViewById(R.id.tvResult);
            tvDescription = builder.findViewById(R.id.tvDescription);
            ivResult = builder.findViewById(R.id.ivResult);
            btnConfirm = builder.findViewById(R.id.btnConfirm);
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sPlastic.setText(tvResult.getText().toString());
                    builder.dismiss();
                }
            });
            ivResult.setImageBitmap(image);
            tvResult.setText(classes[maxPos]);
            String s = "";
            for(int i = 0; i < classes.length; i++){
                s += String.format("%s: %.1f%%\n", classes[i], confidences[i] * 100);
            }

            tvDescription.setText("It is a " + tvResult.getText().toString() + "." + "\n\nConfidence Level:\n" + s);



            builder.show();
//            result.setText(classes[maxPos]);

            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
    }
    public void classifyBrand(Bitmap image){
        try {
            BrandModelUnquant model = BrandModelUnquant.newInstance(getApplicationContext());
            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int[] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
            int pixel = 0;
            //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
            for(int i = 0; i < imageSize; i ++){
                for(int j = 0; j < imageSize; j++){
                    int val = intValues[pixel++]; // RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                }
            }

            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            BrandModelUnquant.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();
            // find the index of the class with the biggest confidence.
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidences.length; i++) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }
            String[] classes = {"Coca Cola","Unilever","Nestle"};
            Dialog builder = new Dialog(add_record.this);
            builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
            builder.setContentView(R.layout.scanned_plastic_pop);
            builder.setCancelable(true);
            builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            tvResult = builder.findViewById(R.id.tvResult);
            tvDescription = builder.findViewById(R.id.tvDescription);
            ivResult = builder.findViewById(R.id.ivResult);
            btnConfirm = builder.findViewById(R.id.btnConfirm);
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sBrand.setText(tvResult.getText().toString());
                    builder.dismiss();
                }
            });
            ivResult.setImageBitmap(image);
            tvResult.setText(classes[maxPos]);
            String s = "";
            for(int i = 0; i < classes.length; i++){
                s += String.format("%s: %.1f%%\n", classes[i], confidences[i] * 100);
            }
            tvDescription.setText("It is a " + tvResult.getText().toString() + "." + "\n\nConfidence Level:\n" + s);

            builder.show();
//            result.setText(classes[maxPos]);

            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }

    public void populateSpinners(){
        sPlastic = (MaterialSpinner) findViewById(R.id.sPlastic);
        sPlastic.setItems(ff.populatePlastic());
        sPlastic.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                Snackbar customSnackBar = Snackbar.make(view, "Type of Plastic: " + item, Snackbar.LENGTH_LONG);
                customSnackBar.setTextColor(ContextCompat.getColor(add_record.this,R.color.white));
                customSnackBar.setBackgroundTint(ContextCompat.getColor(add_record.this,R.color.green));
                customSnackBar.show();

            }
        });

        sBrand = (MaterialSpinner) findViewById(R.id.sBrand);
        sBrand.setItems(ff.populateBrand());
        sBrand.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                Snackbar customSnackBar = Snackbar.make(view, "Type of Brand: " + item, Snackbar.LENGTH_LONG);
                customSnackBar.setTextColor(ContextCompat.getColor(add_record.this,R.color.white));
                customSnackBar.setBackgroundTint(ContextCompat.getColor(add_record.this,R.color.green));
                customSnackBar.show();

            }
        });

    }


}