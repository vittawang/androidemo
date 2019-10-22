package com.uxin.recy.adapter.decor;

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
     * 横向间距，纵向间距(单位：dp)
     */
    private int horizontalSpace, verticalSpace;
    /**
     * 方向
     */
    private int orientation;

    /**
     * recyclerView 的header数量，对于获取到的position有影响，需要减去
     */
    private int mListHeaderCount;

    /**
     * @param context         context
     * @param orientation     {@link #HORIZONTAL}{@link #VERTICAL}}
     * @param spanCount       {@link StaggeredGridLayoutManager#getSpanCount()}
     * @param horizontalSpace 卡片之间横向间距，单位：dp
     * @param verticalSpace   卡片之间纵向间距，单位：dp
     */
    public StaggeredItemDecoration(Context context, int orientation, int spanCount, int horizontalSpace, int verticalSpace) {
        this.spanCount = spanCount;
        this.horizontalSpace = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, horizontalSpace, context.getResources().getDisplayMetrics());
        this.verticalSpace = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, verticalSpace, context.getResources().getDisplayMetrics());
        this.orientation = orientation;
    }

    public void setListHeaderCount(int listHeaderCount) {
        this.mListHeaderCount = listHeaderCount;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        if (params.isFullSpan()) {
            //兼容有header/footer的情况，position会=-1；header/footer的间距不处理；
            super.getItemOffsets(outRect, view, parent, state);
            return;
        }
        int staggeredItemIndex = parent.getChildAdapterPosition(view) - mListHeaderCount;
        //从字面意思理解：item的角标，理解成标识item在哪一列；(不能用position判断，要用spanIndex判断，因为瀑布流的排布方式是按照剩余空间排列的，并不是按照左右左右... 排列的)
        int spanIndex = params.getSpanIndex();
        if (orientation == HORIZONTAL) {
            outRect.top = (spanCount == 2 && spanIndex % 2 == 0) ? verticalSpace : verticalSpace / 2;
            outRect.bottom = (spanCount == 2 && (spanIndex + 1) % 2 == 0) ? verticalSpace : verticalSpace / 2;
            outRect.right = horizontalSpace;
            outRect.left = staggeredItemIndex < spanCount ? horizontalSpace : 0;
        } else if (orientation == VERTICAL) {
            outRect.left = (spanCount == 2 && spanIndex % 2 == 0) ? horizontalSpace : horizontalSpace / 2;
            outRect.right = (spanCount == 2 && (spanIndex + 1) % 2 == 0) ? horizontalSpace : horizontalSpace / 2;
            outRect.bottom = verticalSpace;
            outRect.top = staggeredItemIndex < spanCount ? verticalSpace : 0;
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
