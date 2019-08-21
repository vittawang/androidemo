package com.sunspot.motion;

import android.support.constraint.motion.MotionLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MotionLayout motionContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        motionContainer = findViewById(R.id.motion_container);
        findViewById(R.id.actor).setOnClickListener(this);
        motionContainer.setDebugMode(MotionLayout.DEBUG_SHOW_PATH);
    }

    @Override
    public void onClick(View v) {
//        motionContainer.transitionToEnd();
    }
}
