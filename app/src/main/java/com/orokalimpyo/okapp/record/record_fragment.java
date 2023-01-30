package com.orokalimpyo.okapp.record;

import static android.app.Activity.RESULT_OK;
import static androidx.core.content.ContextCompat.checkSelfPermission;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.orokalimpyo.okapp.R;
import com.orokalimpyo.okapp.authentication.sign_up;
import com.orokalimpyo.okapp.firebase_crud.firebase_functions;
import com.orokalimpyo.okapp.home.home;
import com.orokalimpyo.okapp.ml.ModelUnquant;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class record_fragment extends Fragment implements View.OnClickListener  {


    Button btnAddRecord,btnShowRecord;

    firebase_functions ff;

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_record_fragment, container, false);

        ff = new firebase_functions();

        btnAddRecord = view.findViewById(R.id.btnAddRecord);
        btnShowRecord = view.findViewById(R.id.btnShowRecord);


        btnAddRecord.setOnClickListener(this);
        btnShowRecord.setOnClickListener(this);





        return view;




    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAddRecord:
                Intent addRecord = new Intent(getContext(),add_record.class);
                startActivity(addRecord);
                break;
            case R.id.btnShowRecord:
                Intent i = new Intent(getContext(),view_contributions.class);
                startActivity(i);
                break;
        }
    }
}