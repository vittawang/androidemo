package com.sunspot.pop;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2019/1/14 下午4:45
 * -------------------------------------
 * 描述：直播间封面状态标识view
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class RoomStatusIconsLayout extends LinearLayout {

    int verticalPadding = 9;
    int horizontalPadding = 16;
    private int needRemovePosition;

    public RoomStatusIconsLayout(Context context) {
        this(context, null);
    }

    public RoomStatusIconsLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoomStatusIconsLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setData(List<DataRoomCoverIcon> iconList) {
        setBackgroundResource(R.drawable.bg_oval_translucent_black);
        setPadding(horizontalPadding, verticalPadding, 0, verticalPadding);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        if (iconList == null || iconList.size() <= 0) {
            setVisibility(GONE);
            return;
        }
        setVisibility(VISIBLE);
        for (DataRoomCoverIcon coverIcon : iconList) {
            LayoutParams params = generateDefaultLayoutParams();
            params.rightMargin = horizontalPadding;
            addView(getView(coverIcon), params);
        }
    }

    private View getView(DataRoomCoverIcon coverIcon) {
        ImageView imageView = new ImageView(getContext());
        imageView.setTag(coverIcon.getName());
        imageView.setImageResource(coverIcon.getIconUrl());
        return imageView;
    }

    private static final String TAG = "RoomStatusIconsLayout";

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int count = getChildCount();
        int childWidthAccumulate = horizontalPadding;
        needRemovePosition = -1;
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                View childView = getChildAt(i);
                childWidthAccumulate += childView.getMeasuredWidth();
                childWidthAccumulate += horizontalPadding;
                if (childWidthAccumulate >= measureWidth) {
                    //说明子view已经超出了父控件，将这一条数据和之后的所有数据都删掉 不展示后面的
                    needRemovePosition = i;
                    childWidthAccumulate -= childView.getMeasuredWidth();
                    childWidthAccumulate -= horizontalPadding;
                    break;
                }
            }
        }
        setMeasuredDimension(childWidthAccumulate,measuredHeight);
        Log.e(TAG, "onMeasure: " + getMeasuredWidth() + " / " + getMeasuredHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e(TAG, "onSizeChanged: " + "w = [" + w + "], h = [" + h + "], oldw = [" + oldw + "], oldh = [" + oldh + "]");
        int count = getChildCount();
        if (needRemovePosition > 0){
            removeViewsInLayout(needRemovePosition,count - needRemovePosition);
        }
        Log.e(TAG, "onSizeChanged: " + getChildCount());
        for (int i = 0; i < getChildCount(); i++) {
            Log.e(TAG, "onSizeChanged: " + getChildAt(i).getTag() );
        }
    }
}
