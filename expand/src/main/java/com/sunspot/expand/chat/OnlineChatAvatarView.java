package com.sunspot.expand.chat;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sunspot.expand.CommonUtils;
import com.sunspot.expand.R;

import java.util.List;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2019/2/20 下午12:10
 * -------------------------------------
 * 描述：在线头像列表
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class OnlineChatAvatarView extends ViewGroup {

    /**
     * 压住的宽度
     */
    private int mPressedWidth;
    /**
     * 每条数据的长度
     */
    private int mItemLength;

    public OnlineChatAvatarView(Context context) {
        this(context, null);
    }

    public OnlineChatAvatarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OnlineChatAvatarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setPressedWidth(int mPressedWidth) {
        this.mPressedWidth = mPressedWidth;
    }

    public void setItemLength(int mItemLength) {
        this.mItemLength = mItemLength;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (measuredWidth <= 0 || measuredHeight <= 0) {
            return;
        }
        int count = getChildCount();
        //反向
        for (int i = 0; i < count; i++) {
            int j = count - i - 1;
            int left = j * (mItemLength - mPressedWidth);
            int right = left + mItemLength;
            int top = 0;
            int bottom = mItemLength;
            getChildAt(i).layout(left, top, right, bottom);
        }
        //正向
//        for (int i = 0; i < count; i++) {
//            int left = i * (mItemLength - mPressedWidth);
//            int right = left + mItemLength;
//            int top = 0;
//            int bottom = mItemLength;
//            getChildAt(i).layout(left, top, right, bottom);
//        }
    }
}
