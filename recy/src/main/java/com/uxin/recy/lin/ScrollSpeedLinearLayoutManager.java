package com.uxin.recy.lin;

import android.content.Context;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2019/2/27 下午3:39
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class ScrollSpeedLinearLayoutManager extends LinearLayoutManager {

    public ScrollSpeedLinearLayoutManager(Context context) {
        super(context);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        LinearSmoothScroller linearSmoothScroller =
                new LinearSmoothScroller(recyclerView.getContext()) {

                    /**
                     * 计算滚动的速度
                     * @param displayMetrics 屏幕分辨率
                     * @return 1px滑过的时间(ms)
                     */
                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        //densityDpi 360dpi/480dpi...
                        return 1400F / (float) displayMetrics.densityDpi;
                    }

                };
        linearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(linearSmoothScroller);
    }

}
