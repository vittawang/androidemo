package com.sunspot.expand;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.sunspot.expand.render.MatchView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Handler.Callback {

    private static final int ROLLING_START = 100;
    private static final long ROLLING_INTERVAL_MILLIS = 2000;
    private MatchView matchView;
    private Handler mHandler = new Handler(this);
    private TextSwitcher textSwitcher;
    private int timeDeCount;
    private List<String> str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        matchView = findViewById(R.id.match_view);
        textSwitcher = findViewById(R.id.wating_ts);
        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView tv = new TextView(MainActivity.this);
                tv.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                tv.setTextColor(Color.WHITE);
                tv.setMovementMethod(new LinkMovementMethod());
                tv.setGravity(Gravity.CENTER);
                return tv;
            }
        });
        str = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            str.add("你好呀！ + " + i);
        }
        timeDeCount = str.size();
        mHandler.sendEmptyMessage(ROLLING_START);
        matchView.startAnim();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        matchView.stopAnim();
    }

    public void onCancelClick(View view) {
        startActivity(new Intent(this,TransitionActivity.class));
        if (matchView.isRunning()) {
            matchView.stopAnim();
        } else {
            matchView.startAnim();
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == ROLLING_START){
            timeDeCount--;
            textSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.match_rolling_in_anim));
            textSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.match_rolling_out_anim));
            textSwitcher.setText(str.get(timeDeCount));
        }
        if (timeDeCount <= 0){
            timeDeCount = str.size();//这里做无限循环轮播，所以把条件还原
        }
        mHandler.sendEmptyMessageDelayed(ROLLING_START, ROLLING_INTERVAL_MILLIS);
        return true;
    }
}
