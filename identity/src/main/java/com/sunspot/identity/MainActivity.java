package com.sunspot.identity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((AvatarImageView) findViewById(R.id.avatar_cv)).setData();

//        ((AvatarImageView) findViewById(R.id.a2)).setData();
//        ((AvatarImageView) findViewById(R.id.pendant)).setData();
        ((AvatarImageView) findViewById(R.id.a4)).setData();
        ((AvatarImageView) findViewById(R.id.a6)).setData();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Intent intent = new Intent(this,ShowActivity.class);
        startActivity(intent);
        return true;
    }
}
