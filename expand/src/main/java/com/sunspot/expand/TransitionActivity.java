package com.sunspot.expand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sunspot.expand.guide.GuideRippleView;

public class TransitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        ((GuideRippleView) findViewById(R.id.guide)).setCenterView(findViewById(R.id.red_bean));
    }
}
