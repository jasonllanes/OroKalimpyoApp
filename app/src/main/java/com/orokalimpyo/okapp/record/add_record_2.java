package com.orokalimpyo.okapp.record;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.shapes.Shape;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.imageview.ShapeableImageView;
import com.orokalimpyo.okapp.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class add_record_2 extends AppCompatActivity implements View.OnClickListener {


    ImageView ivPlastic,ivBack;
    Button btnUpload,btnCapture,btnGenerate;
    int camera;
    int imageSize = 224;
    Bitmap image;
    String plastic,brand,kilo;
    String _id,_type,_name,_barangay,_address,_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record2);

        ivBack = findViewById(R.id.ivBack);
        ivPlastic = findViewById(R.id.ivPlastic);
        btnUpload = findViewById(R.id.btnUpload);
        btnCapture = findViewById(R.id.btnCapture);
        btnGenerate = findViewById(R.id.btnGenerate);

        retrieveRecentData();

        ivBack.setOnClickListener(this);
        btnCapture.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
        btnGenerate.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivBack:
                finish();
                break;
            case R.id.btnUpload:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 1);
                break;
            case R.id.btnCapture:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, 3);
                    } else {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                    }
                }
                break;
            case R.id.btnGenerate:

                saveData();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
                if(requestCode == 3){
                    Bitmap image = (Bitmap) data.getExtras().get("data");

//                    image = (Bitmap) data.getExtras().get("data");
//                    int dimension = Math.min(image.getWidth(), image.getHeight());
//                    image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.PNG, 100,baos);
                    Bitmap newbitmap= BitmapFactory.decodeStream(new ByteArrayInputStream(baos.toByteArray()));
//                    newbitmap = Bitmap.createScaledBitmap(newbitmap, imageSize, imageSize, false);
                    ivPlastic.setImageBitmap(newbitmap);
                    camera = 0;
                    btnGenerate.setVisibility(View.VISIBLE);
                }else{
                    Uri dat = data.getData();
                    image = null;
                    try {
                        image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dat);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                    ivPlastic.setImageBitmap(image);
                    camera = 0;
                    btnGenerate.setVisibility(View.VISIBLE);
                }
            }else{
            //error message
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void retrieveRecentData(){
        plastic = getIntent().getStringExtra("plastic");
        brand = getIntent().getStringExtra("brand");
        kilo = getIntent().getStringExtra("kilo");

    }

    public void saveData(){
        Intent i = new Intent(add_record_2.this, generated_qr.class);
        i.putExtra("plastic",plastic);
        i.putExtra("brand",brand);
        i.putExtra("kilo",kilo);
        startActivity(i);
    }
}