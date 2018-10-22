package com.uxin.uxindemo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2018/9/17 下午3:57
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class AnimationUtils {

    /**
     * 呼吸动画
     */
    public static void breathAnim(View view) {
        breathAnim(view, 1.2f, 3000);
    }

    /**
     * 呼吸动画
     *
     * @param view        做呼吸的view
     * @param scaleValues 缩放的大小
     * @param duration    动画时长(呼吸时长 = 停顿时长 = duration / 2)
     */
    public static void breathAnim(View view, float scaleValues, long duration) {
        if (view == null) {
            return;
        }
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "ScaleX", 1f, scaleValues, 1f, scaleValues, 1f, 1, 1, 1, 1, 1);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "ScaleY", 1f, scaleValues, 1f, scaleValues, 1f, 1, 1, 1, 1, 1);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(scaleX, scaleY);
        set.setDuration(duration);
        set.start();
    }

    /**
     * 抛物线
     */
    public static void throwLine(View start, View end, int controlPoint) {
        if (start == null || end == null) {

        }
    }

}
