package com.sunspot.pop.frag;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.sunspot.pop.R;

public class PopActivity extends AppCompatActivity {

    private static final String TAG = "PopActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Log.e(TAG, "onClick: " + fm + " / " + ft);
        ft.add(R.id.container, new OneFragment());
        ft.commit();
    }
}