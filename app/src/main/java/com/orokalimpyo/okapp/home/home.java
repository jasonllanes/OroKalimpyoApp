package com.orokalimpyo.okapp.home;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.orokalimpyo.okapp.R;
import com.orokalimpyo.okapp.databinding.ActivityHomeBinding;
import com.orokalimpyo.okapp.profile.profile_fragment;
import com.orokalimpyo.okapp.record.record_fragment;


public class home extends AppCompatActivity {

    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new home_fragment());
        binding.btmNavBarView.setBackground(null);


        binding.btmNavBarView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    replaceFragment(new home_fragment());
                    break;
                case R.id.record:
                    replaceFragment(new record_fragment());
                    break;
                case R.id.profile:
                    replaceFragment(new profile_fragment());
                    break;
            }
            return true;
        });


    }
    public String method1(String i){
        return i;
    }
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }
}