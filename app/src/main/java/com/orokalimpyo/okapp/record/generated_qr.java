package com.orokalimpyo.okapp.record;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.orokalimpyo.okapp.R;
import com.orokalimpyo.okapp.firebase_crud.firebase_functions;
import com.orokalimpyo.okapp.home.home;
import com.orokalimpyo.okapp.profile.profile_fragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;



public class generated_qr extends AppCompatActivity implements View.OnClickListener {
    private String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
    Button btnViewRecord;
    ImageView ivQR,ivBack;
    Bitmap bitmap;
    Uri uri;


    firebase_functions ff;
    FirebaseAuth mAuth;

    String plastic,brand,kilo;
    String _id,_type,_name,_barangay,_address,_number;

    SimpleDateFormat month,day,year,week,date,hours,minutes,seconds,time;
    String currentMonth,currentDay,currentYear,currentWeek,currentDate,currentHour,currentMinute,currentSeconds,currentTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generated_qr);

        ff = new firebase_functions();
        mAuth = FirebaseAuth.getInstance();

        ivBack = findViewById(R.id.ivBack);
        btnViewRecord = findViewById(R.id.btnViewRecord);
        ivQR = findViewById(R.id.ivQR);



        retrieveDate();
        retrieveRecentData();
        generateQR(ivQR);

        ivBack.setOnClickListener(this);
        btnViewRecord.setOnClickListener(this);



//        ff.retrieveUserDetails(_id,_type,_name,_barangay,_address,_number);
//        _name = pf.tvFullname.getText().toString();



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnViewRecord:
                Intent records = new Intent(generated_qr.this, home.class);
                records.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(records);
                generated_qr.this.finish();
                break;
            case R.id.ivBack:
                Intent back = new Intent(generated_qr.this, home.class);
                back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(back);
                generated_qr.this.finish();
        }
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


    public void generateQR(ImageView imageView){
        QRGEncoder qrgEncoder = new QRGEncoder(_id + "\n" + _type+ "\n" +_name+ "\n" +_barangay+ "\n" +_address+ "\n" +_number+ "\n" + plastic
                + "\n" + brand + "\n" + kilo + "\n" + "gs://orokalimpyo.appspot.com/TBC_Contributions/"+_barangay+"Contribution_QRCodes/"+ _id+".png" , null, QRGContents.Type.TEXT, 800);
//        QRGEncoder qrgEncoder = new QRGEncoder("Jason Kyut" , null, QRGContents.Type.TEXT, 800);

        qrgEncoder.setColorBlack(Color.rgb(10,147,81));
        qrgEncoder.setColorWhite(Color.rgb(255,255,255));
        // Getting QR-Code as Bitmap
        bitmap = qrgEncoder.getBitmap();
        // Setting Bitmap to ImageView
        imageView.setImageBitmap(bitmap);
        ff.saveTBCContributions(this,getApplicationContext(),mAuth.getUid(),_id,_name,_type,_barangay,_address,_number,plastic,brand,kilo,currentMonth,currentDay,currentYear,currentDate,currentTime,"gs://orokalimpyo.appspot.com/TBC_Contributions/"+_barangay+"Contribution_QRCodes/"+ _id+".png");
        saveQR();
    }

    public void saveQR(){
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,currentDate+ "| qr_code_image" , "A QR Code that is generated base on the data given");
        } else {
            ActivityCompat.requestPermissions(generated_qr.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte [] byteArray = stream.toByteArray();

        ff.saveQRStorage(byteArray,getApplicationContext(),_barangay,_id);

    }

    public void retrieveRecentData(){
        _id = getIntent().getStringExtra("id");
        _name = getIntent().getStringExtra("name");
        _type = getIntent().getStringExtra("type");
        _barangay = getIntent().getStringExtra("barangay");
        _address = getIntent().getStringExtra("address");
        _number = getIntent().getStringExtra("number");
        plastic = getIntent().getStringExtra("plastic");
        brand = getIntent().getStringExtra("brand");
        kilo = getIntent().getStringExtra("kilo");
    }


    public static final String insertImage(ContentResolver cr, Bitmap source, String title, String description) {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, title);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, title);
        values.put(MediaStore.Images.Media.DESCRIPTION, description);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        // Add the date meta data to ensure the image is added at the front of the gallery
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());

        Uri url = null;
        String stringUrl = null;    /* value to be returned */

        try {
            url = cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            if (source != null) {
                OutputStream imageOut = cr.openOutputStream(url);
                try {
                    source.compress(Bitmap.CompressFormat.JPEG, 50, imageOut);
                } finally {
                    imageOut.close();
                }

                long id = ContentUris.parseId(url);
                // Wait until MINI_KIND thumbnail is generated.
                Bitmap miniThumb = MediaStore.Images.Thumbnails.getThumbnail(cr, id, MediaStore.Images.Thumbnails.MINI_KIND, null);
                // This is for backward compatibility.
                storeThumbnail(cr, miniThumb, id, 50F, 50F, MediaStore.Images.Thumbnails.MICRO_KIND);
            } else {
                cr.delete(url, null, null);
                url = null;
            }
        } catch (Exception e) {
            if (url != null) {
                cr.delete(url, null, null);
                url = null;
            }
        }

        if (url != null) {
            stringUrl = url.toString();
        }

        return stringUrl;
    }

    private static final Bitmap storeThumbnail(ContentResolver cr, Bitmap source, long id, float width, float height, int kind) {

        // create the matrix to scale it
        Matrix matrix = new Matrix();

        float scaleX = width / source.getWidth();
        float scaleY = height / source.getHeight();

        matrix.setScale(scaleX, scaleY);

        Bitmap thumb = Bitmap.createBitmap(source, 0, 0,
                source.getWidth(),
                source.getHeight(), matrix,
                true
        );

        ContentValues values = new ContentValues(4);
        values.put(MediaStore.Images.Thumbnails.KIND, kind);
        values.put(MediaStore.Images.Thumbnails.IMAGE_ID, (int) id);
        values.put(MediaStore.Images.Thumbnails.HEIGHT, thumb.getHeight());
        values.put(MediaStore.Images.Thumbnails.WIDTH, thumb.getWidth());

        Uri url = cr.insert(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, values);

        try {
            OutputStream thumbOut = cr.openOutputStream(url);
            thumb.compress(Bitmap.CompressFormat.JPEG, 100, thumbOut);
            thumbOut.close();
            return thumb;
        } catch (FileNotFoundException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }
    }
}