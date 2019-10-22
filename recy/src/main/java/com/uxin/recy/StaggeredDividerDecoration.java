package com.uxin.recy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * -------------------------------------
 * 作者：vitta
 * -------------------------------------
 * 时间：2019-10-22 17:07
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class StaggeredDividerDecoration extends DividerItemDecoration {

    private static final String TAG = "StaggeredDividerDecorat";

    public StaggeredDividerDecoration(Context context, int orientation) {
        super(context, orientation);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        Log.e(TAG, "onDraw: " );
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        Log.e(TAG, "getItemOffsets: " );
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        Log.e(TAG, "onDrawOver: " );
    }
}
