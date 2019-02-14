package com.sunspot.expand.render;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.sunspot.expand.R;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2019/1/25 下午7:59
 * -------------------------------------
 * 描述：匹配动画
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class MatchView extends FrameLayout {

    private ValueAnimator bgColorAnimator;
    private int bgColor;
    private ImageView bgView;
    private View musicOne, musicTwo, musicThree;
    private View avatarContainer;
    private ValueAnimator mAvatarAnim;
    private ValueAnimator mMusicAnim;
    private RippleView ripple;
    private RadarView radar;

    public MatchView(@NonNull Context context) {
        this(context, null);
    }

    public MatchView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MatchView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_match_view, this, true);
        bgView = (ImageView) findViewById(R.id.bg_view);
        musicOne = findViewById(R.id.music_one);
        musicTwo = findViewById(R.id.music_two);
        musicThree = findViewById(R.id.music_three);
        avatarContainer = findViewById(R.id.avatar_container);
        ripple = (RippleView) findViewById(R.id.ripple);
        radar = (RadarView) findViewById(R.id.radar);
        initAvatarAnim();
        initMusicAnim();
        initBgColorAnim();
    }

    private void initBgColorAnim() {
        //ARGB 背景颜色渐变（这里）
        bgColorAnimator = ValueAnimator.ofInt(0xFFBEA2EA, 0xFF8FACED, 0xFFF893B4, 0xFF50CFBD, 0xFFBEA2EA);
        bgColorAnimator.setEvaluator(new ArgbEvaluator());
        bgColorAnimator.setRepeatCount(ValueAnimator.INFINITE);
        bgColorAnimator.setRepeatMode(ValueAnimator.RESTART);
        bgColorAnimator.setInterpolator(new LinearInterpolator());
        bgColorAnimator.setDuration(4000);
        bgColorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                bgColor = (int) animation.getAnimatedValue();
                bgView.setBackgroundColor(bgColor);
            }
        });
    }

    private void initAvatarAnim() {
        mAvatarAnim = ValueAnimator.ofFloat(1, 0.9f, 1);
        mAvatarAnim.setRepeatMode(ValueAnimator.RESTART);
        mAvatarAnim.setRepeatCount(ValueAnimator.INFINITE);
        mAvatarAnim.setDuration(1800);
        mAvatarAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = (float) animation.getAnimatedValue();
                avatarContainer.setScaleX(fraction);
                avatarContainer.setScaleY(fraction);
            }
        });
    }

    private void initMusicAnim() {
        mMusicAnim = ValueAnimator.ofInt(0, -45, 0, -30, 0, 0);
        mMusicAnim.setDuration(1000);
        mMusicAnim.setRepeatCount(ValueAnimator.INFINITE);
        mMusicAnim.setRepeatMode(ValueAnimator.RESTART);
        mMusicAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        mMusicAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                musicOne.setRotation((int) animation.getAnimatedValue());
                musicTwo.setRotation((int) animation.getAnimatedValue());
                musicThree.setRotation((int) animation.getAnimatedValue());
            }
        });
    }

    public void startAnim() {
        bgColorAnimator.start();
        mAvatarAnim.start();
        mMusicAnim.start();
        radar.startAnim();
        ripple.startAnim();
    }

    public void stopAnim() {
        bgColorAnimator.end();
        mAvatarAnim.end();
        mMusicAnim.end();
        radar.stopAnim();
        ripple.stopAnim();
    }

    public boolean isRunning() {
        return mAvatarAnim.isRunning();
    }
}
