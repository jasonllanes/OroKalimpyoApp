package com.orokalimpyo.okapp.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.orokalimpyo.okapp.R;
import com.orokalimpyo.okapp.databinding.ActivityAdminHomeBinding;
import com.orokalimpyo.okapp.databinding.ActivityHomeBinding;
import com.orokalimpyo.okapp.home.home_fragment;
import com.orokalimpyo.okapp.profile.profile_fragment;
import com.orokalimpyo.okapp.record.record_fragment;

public class admin_home extends AppCompatActivity {

    ActivityAdminHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new scan_fragment());
        binding.btmNavBarView.setBackground(null);


        binding.btmNavBarView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.record:
                    replaceFragment(new scan_fragment());
                    break;
                case R.id.profile:
                    replaceFragment(new admin_profile());
                    break;
            }
            return true;
        });

    }
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }
}