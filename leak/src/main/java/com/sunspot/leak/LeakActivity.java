package com.sunspot.leak;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LeakActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak);
        CustomManager customManager = new CustomManager(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LeakCanaryManager.getInstance().watch(this);
    }
}
