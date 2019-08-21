package com.sunspot.sharelement.album;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * -------------------------------------
 * 作者：vitta
 * -------------------------------------
 * 时间：2019/7/3 下午12:02
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class GridItemDecoration extends RecyclerView.ItemDecoration {

    int margin = 5;

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//        int childLayoutPosition = parent.getChildLayoutPosition(view);
//        if (childLayoutPosition % spanCount == 0) {//left
//
//        } else if (childLayoutPosition % (spanCount - 1) == 0) {//right
//
//        } else {//middle
//
//        }
        outRect.set(margin, margin, margin, margin);
    }
}
