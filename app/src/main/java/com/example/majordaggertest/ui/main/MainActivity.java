package com.example.majordaggertest.ui.main;

import android.os.Bundle;

import com.example.majordaggertest.R;
import com.example.majordaggertest.base.BaseActivity;
import com.example.majordaggertest.ui.list.ListFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected int layoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().add(R.id.screenContainer, new ListFragment()).commit();
    }
}
