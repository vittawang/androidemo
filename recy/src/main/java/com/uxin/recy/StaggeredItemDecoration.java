package com.uxin.recy;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.View;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2018/10/22 下午3:30
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class StaggeredItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * 行数
     */
    private int spanCount;
    /**
     * 横向间距，纵向间距
     */
    private int horizontalSpacing,verticalSpacing;

    public StaggeredItemDecoration(Context context,int spanCount, int horizontalSpacing, int verticalSpacing) {
        this.spanCount = spanCount;
        this.horizontalSpacing = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,horizontalSpacing,context.getResources().getDisplayMetrics());
        this.verticalSpacing = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,verticalSpacing,context.getResources().getDisplayMetrics());
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        int spanIndex = params.getSpanIndex();
        //从字面意思理解：分割的列的角标；(不能用position判断，要用spanIndex判断)
        if (spanIndex % spanCount == 0){
            //left
            outRect.left = horizontalSpacing;
        }else {
            //else
            outRect.left = 0;
        }
        outRect.right = horizontalSpacing;
        outRect.top = verticalSpacing;
    }
}
