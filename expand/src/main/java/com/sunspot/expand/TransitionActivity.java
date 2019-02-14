package com.sunspot.expand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sunspot.expand.guide.GuideRippleView;
import com.sunspot.expand.render.RippleView;

public class TransitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);
        ((RippleView) findViewById(R.id.ripple)).startAnim();
//        ((GuideRippleView) findViewById(R.id.guide)).setCenterView(findViewById(R.id.red_bean));
    }
}
