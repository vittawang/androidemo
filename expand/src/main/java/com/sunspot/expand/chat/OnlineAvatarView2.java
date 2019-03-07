package com.sunspot.expand.chat;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
public class OnlineAvatarView2 extends ViewGroup {

    /**
     * 压住的宽度
     */
    private int mPressedWidth;
    /**
     * 数据集
     */
    private List<DataLogin> mList;

    /**
     * 每条数据的长度
     */
    private int mItemLength;

    public OnlineAvatarView2(Context context) {
        this(context, null);
    }

    public OnlineAvatarView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OnlineAvatarView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(R.styleable.OnlineAvatarView2);
        mItemLength = typedArray.getDimensionPixelOffset(R.styleable.OnlineAvatarView2_item_view_length, CommonUtils.dip2px(context, 32));
        mPressedWidth = typedArray.getDimensionPixelOffset(R.styleable.OnlineAvatarView2_item_view_pressed_length, CommonUtils.dip2px(context, 6));
        typedArray.recycle();
    }

    /**
     * 设置数据（在线列表用）
     *
     * @param list 头像列表
     */
    public void setData(List<DataLogin> list) {
        if (list == null || list.isEmpty()) {
            setVisibility(GONE);
            return;
        }
        setVisibility(VISIBLE);
        mList = list;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //计算最多可以显示下几条数据
        int maxDisplayCount = (w - mItemLength) / (mItemLength - mPressedWidth) + 1;
        int size = mList.size();
        removeAllViews();
        if (size > maxDisplayCount) {
            //超出最大显示个数 在最后添加一个view
            int endShowIndex = maxDisplayCount - 1;
            for (int i = 0; i < endShowIndex; i++) {
                addView(createNormalView(mList.get(i)));
            }
            addView(createLastView());
            //11条数据，最多显示8条/那么showArray就是7位，hideArray就是4位/最后一条是空置位
            //showArray 0,1,2,3,4,5,6
            //hideArray 7,8,9,10
        } else {
            //没超出最大显示个数 正常显示
            for (int i = 0; i < size; i++) {
                addView(createNormalView(mList.get(i)));
            }
        }
        requestLayout();
    }

    private View createNormalView(DataLogin data) {
        TextView textView = new TextView(getContext());
        textView.setBackgroundResource(R.drawable.circle_white);
        textView.setLayoutParams(new LayoutParams(mItemLength, mItemLength));
        textView.setText(String.valueOf(data.getCount()));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLACK);
        return textView;
    }

    private View createLastView() {
        ImageView imageView = new ImageView(getContext());
        imageView.setLayoutParams(new LayoutParams(mItemLength, mItemLength));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageResource(R.drawable.online_item_last);
        return imageView;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        if (measuredWidth <= 0 || measuredHeight <= 0) {
            return;
        }
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            int left = i * (mItemLength - mPressedWidth);
            int right = left + mItemLength;
            int top = 0;
            int bottom = mItemLength;
            getChildAt(i).layout(left, top, right, bottom);
        }
    }

}
