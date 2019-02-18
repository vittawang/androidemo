package com.sunspot.expand.guide;

import android.animation.AnimatorSet;
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
 * 描述：大红豆引导动画
 * -------------------------------------
 * 备注：start delay 也是可以循环播放的
 * -------------------------------------
 */
public class GuideRippleView extends FrameLayout {

    private static final long DURATION = 1500;
    private float fraction = 0.66f;
    private View circleIn;
    private View circleOut;
    private float minR = 0.33f;

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

    public void setCenterView(final View view) {
        ValueAnimator outScale = ValueAnimator.ofFloat(1, fraction, minR);//先开始
        outScale.setRepeatCount(ValueAnimator.INFINITE);
        outScale.setRepeatMode(ValueAnimator.RESTART);
        outScale.setDuration(1100);
        outScale.addUpdateListener(new AnimationListener(circleOut));
        ValueAnimator inScale = ValueAnimator.ofFloat(1, fraction, minR);//后开始
        inScale.setRepeatCount(ValueAnimator.INFINITE);
        inScale.setRepeatMode(ValueAnimator.RESTART);
        inScale.setDuration(1100);
        inScale.setStartDelay(400);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(1500);
        set.playTogether(inScale,outScale);
        inScale.addUpdateListener(new AnimationListener(circleIn));
        ObjectAnimator centerViewScaleX = ObjectAnimator.ofFloat(view, "ScaleX", 1, 1.13f, 1);
        ObjectAnimator centerViewScaleY = ObjectAnimator.ofFloat(view, "ScaleY", 1, 1.13f, 1);
        centerViewScaleX.setDuration(1500);
        centerViewScaleX.setRepeatCount(ObjectAnimator.INFINITE);
        centerViewScaleX.setRepeatMode(ObjectAnimator.RESTART);
        centerViewScaleY.setDuration(1500);
        centerViewScaleY.setRepeatCount(ObjectAnimator.INFINITE);
        centerViewScaleY.setRepeatMode(ObjectAnimator.RESTART);
        centerViewScaleX.start();
        centerViewScaleY.start();
        set.start();
    }

    class AnimationListener implements ValueAnimator.AnimatorUpdateListener {

        private View view;

        public AnimationListener(View view) {
            this.view = view;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            //1 - 0.66 - 0.33
            float value = (float) animation.getAnimatedValue();
            view.setScaleX(value);
            view.setScaleY(value);
            if (value > fraction) {//1 - 0.66
                double f = (1 - (double) value) / minR;//0 - 0.33 / 0 - 1
                view.setAlpha((float) f);
            } else {//0.66 - 0.33
                double f = (fraction - value) / (fraction - minR);//0 - 0.33 / 0 - 1
                view.setAlpha((float) (1 - f));//1 - 0
            }
        }
    }


}
