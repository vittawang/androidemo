package com.sunspot.pk;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


/**
 * Created by summer on 2017/8/3.
 * 动画操作工具类
 */

public class UxAnimationUtils {
    /**
     * View缩小放大动效
     * 不需要直接使用这个类，请使用直接使用{@link com.uxin.base.view.ShakeButtonWrapper} or {@link com.uxin.base.view.ShakeImageViewButton}
     * @param view View
     */
    public static void scaleAnimationSet(final View view) {
        scaleAnimation(view, 1.0f, 0.8f, 80, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                scaleAnimation(view, 0.8f, 1.10f, 145, new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if (view != null) {
                            view.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    scaleAnimation(view, 1f, 1.0f, 80, new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                        }
                                    });
                                }
                            }, 10);

                        }
                    }
                });
            }
        });
    }

    private static void scaleAnimation(final View view, float start, float end, long duration, final AnimatorListenerAdapter listenerAdapter) {
        ValueAnimator animator = ValueAnimator.ofFloat(start, end);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                view.setScaleX(value);
                view.setScaleY(value);
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (listenerAdapter != null) {
                    listenerAdapter.onAnimationEnd(animation);
                }
            }
        });
        animator.start();
    }

}
