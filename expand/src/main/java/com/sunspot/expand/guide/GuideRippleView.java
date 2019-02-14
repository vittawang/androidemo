package com.sunspot.expand.guide;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.sunspot.expand.R;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2019/1/29 下午6:00
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class GuideRippleView extends FrameLayout {

    private static final long DURATION = 2000;
    private float fraction = 0.6f;
    private View circleIn;
    private View circleOut;
    private float minR = 0.5f;

    public GuideRippleView(@NonNull Context context) {
        this(context, null);
    }

    public GuideRippleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuideRippleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_guide_ripple_view, this, true);
        circleIn = findViewById(R.id.circle_in);
        circleOut = findViewById(R.id.circle_out);
    }

    private static final String TAG = "GuideRippleView";

    public void setCenterView(final View view) {
        ValueAnimator outX = ValueAnimator.ofFloat(1, fraction, minR);
        outX.setRepeatCount(ValueAnimator.INFINITE);
        outX.setRepeatMode(ValueAnimator.RESTART);
        outX.setDuration(DURATION);
        outX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                circleOut.setScaleX(value);
                circleOut.setScaleY(value);
                if (value > fraction) {
                    double f = (1 - (double) value) / 0.3f;//0 - 1
                    circleOut.setAlpha((float) f);
                    circleIn.setAlpha(0);
                    circleIn.setScaleX(1);
                    circleIn.setScaleY(1);
                } else {
                    double f = (fraction - value) / (fraction - minR);//0-1
                    circleOut.setAlpha((float) (1 - f));//1 - 0
                    circleIn.setScaleX((float) (1 - f * (1 - minR)));//1 - 0.33
                    circleIn.setScaleY((float) (1 - f * (1 - minR)));//1 - 0.33
                    if (f < 0.5f) {
                        circleIn.setAlpha((float) (f / 0.5));//0 - 1
                    } else {
                        circleIn.setAlpha((float) ((1 - f) / 0.5));//1 - 0
                    }
                }
            }
        });
        outX.start();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "ScaleX", 1, 1.2f, 1);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "ScaleY", 1, 1.2f, 1);
        scaleX.setDuration(DURATION);
        scaleX.setRepeatCount(ObjectAnimator.INFINITE);
        scaleX.setRepeatMode(ObjectAnimator.RESTART);
        scaleY.setDuration(DURATION);
        scaleY.setRepeatCount(ObjectAnimator.INFINITE);
        scaleY.setRepeatMode(ObjectAnimator.RESTART);
        scaleX.start();
        scaleY.start();
    }

}
