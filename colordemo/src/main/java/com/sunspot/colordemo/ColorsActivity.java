package com.sunspot.colordemo;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class ColorsActivity extends AppCompatActivity {

    //    int[] RAINBOW_COLORS = new int[]{0xFFFF8383, 0xFFFF8B78, 0xFFFFB553, 0xFF39D5BF, 0xFF759DF2, 0xFFA27AE0, 0xFFFF8383};
    int[] RAINBOW_COLORS = new int[]{0xFFFF8383, 0xFFFF8B78};

    private static final String TAG = "ColorsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);
        final View mBgColorView = findViewById(R.id.bg);
        ValueAnimator slowBgAnimator = ValueAnimator.ofInt(RAINBOW_COLORS);
        slowBgAnimator.setEvaluator(new ArgbEvaluator());
//        slowBgAnimator.setInterpolator(new LinearInterpolator());//线性差值器
        slowBgAnimator.setDuration(6 * 1000);//6s 切换一个颜色
//        slowBgAnimator.setRepeatMode(ValueAnimator.RESTART);//播完重新开始
        slowBgAnimator.setRepeatCount(1);//无限循环
        slowBgAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.e(TAG, "onAnimationUpdate: " + animation.getCurrentPlayTime() + " / " + animation.getAnimatedValue() + " / " + animation.getAnimatedFraction());
                mBgColorView.setBackgroundColor((Integer) animation.getAnimatedValue());
            }
        });
        slowBgAnimator.start();
        //-31360
        //-30341

    }
}
