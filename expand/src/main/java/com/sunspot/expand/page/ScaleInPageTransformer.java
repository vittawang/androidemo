package com.sunspot.expand.page;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.sunspot.expand.R;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2019/1/24 下午5:16
 * -------------------------------------
 * 描述：ViewPager Page切换过程中的动画效果
 * -------------------------------------
 * 备注：可自定义，这里实现的是切换缩放效果
 * -------------------------------------
 */
public class ScaleInPageTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.8f;
    private static final String TAG = "ScaleInPageTransformer";

    @Override
    public void transformPage(@NonNull View page, float position) {
        View maskView = page.findViewById(R.id.mask);
        Object tag = page.getTag();
        Log.e(TAG, tag + " / " + position);
        if (position < -1) {//在左边 (~,-1]
            page.setScaleY(MIN_SCALE);
            maskView.setAlpha(1);
        } else if (position <= 0) {//中间位置，左边去到中间 (-1,0]
            float scaleY = MIN_SCALE + (position + 1) * (1 - MIN_SCALE);
            page.setScaleY(scaleY);
            maskView.setAlpha(-position);
        } else if (position <= 1) {//中间去到右边 (0,1]
            float scaleY = MIN_SCALE + (1 - position) * (1 - MIN_SCALE);
            page.setScaleY(scaleY);
            maskView.setAlpha(position);
        } else {//右边位置 (1,~]
            page.setScaleY(MIN_SCALE);
            maskView.setAlpha(1);
        }
    }
}
