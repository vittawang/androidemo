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
 * 描述：瀑布流间距
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class StaggeredItemDecoration extends RecyclerView.ItemDecoration {

    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;

    /**
     * 行数
     */
    private int spanCount;
    /**
     * 横向间距，纵向间距
     */
    private int horizontalSpacing, verticalSpacing;
    /**
     * 方向
     */
    private int orientation;

    /**
     * recyclerView 的header数量，对于获取到的position有影响，需要减去
     */
    private int mListHeaderCount;

    public StaggeredItemDecoration(Context context, int orientation, int spanCount, int horizontalSpacing, int verticalSpacing) {
        this.spanCount = spanCount;
        this.horizontalSpacing = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, horizontalSpacing, context.getResources().getDisplayMetrics());
        this.verticalSpacing = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, verticalSpacing, context.getResources().getDisplayMetrics());
        this.orientation = orientation;
    }

    public void setListHeaderCount(int listHeaderCount) {
        this.mListHeaderCount = listHeaderCount;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view) - mListHeaderCount;
        StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        //从字面意思理解：分割的列的角标；(不能用position判断，要用spanIndex判断)
        int spanIndex = params.getSpanIndex();
        if (orientation == HORIZONTAL) {
            outRect.top = (spanCount == 2 && spanIndex % 2 == 0) ? verticalSpacing : verticalSpacing / 2;
            outRect.bottom = (spanCount == 2 && (spanIndex + 1) % 2 == 0) ? verticalSpacing : verticalSpacing / 2;
            outRect.right = horizontalSpacing;
            outRect.left = position < spanCount ? horizontalSpacing : 0;
        } else if (orientation == VERTICAL) {
            outRect.left = (spanCount == 2 && spanIndex % 2 == 0) ? horizontalSpacing : horizontalSpacing / 2;
            outRect.right = (spanCount == 2 && (spanIndex + 1) % 2 == 0) ? horizontalSpacing : horizontalSpacing / 2;
            outRect.bottom = verticalSpacing;
            outRect.top = position < spanCount ? verticalSpacing : 0;
        } else {
            super.getItemOffsets(outRect, view, parent, state);
        }
        /**
         * 以纵向为例，
         * //2行才可以做左右边距不相同，但是item的宽度是相同的。
         * //多行不能写左右边距不相同，会导致item的宽度不相同。
         */
    }
}
