package com.sunspot.sharelement;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "MainActivity";
    private ImageView mNiudanIv;
    public boolean isBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNiudanIv = findViewById(R.id.niudan_iv);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = supportFragmentManager.beginTransaction();
        ft.add(R.id.container, new MainFragment());
        ft.commit();
        setExitSharedElementCallback(new SharedElementCallback() {

            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                super.onMapSharedElements(names, sharedElements);
                Log.e(TAG, "onMapSharedElements: " + sharedElements + " / " + names );
                if (isBack){
                    sharedElements.put(NiudanActivity.SHARE_ELEMENT_NIUDAN_MACHINE,mNiudanIv);
                    isBack = false;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
