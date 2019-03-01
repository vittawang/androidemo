package com.sunspot.expand.chat;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
     * 数据集
     */
    private List<DataLogin> mList;
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
        TypedArray typedArray = context.obtainStyledAttributes(R.styleable.OnlineChatAvatarView);
        mItemLength = typedArray.getDimensionPixelOffset(R.styleable.OnlineChatAvatarView_item_length, CommonUtils.dip2px(context, 32));
        mPressedWidth = typedArray.getDimensionPixelOffset(R.styleable.OnlineChatAvatarView_item_pressed_length, CommonUtils.dip2px(context, 6));
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
        if (size >= maxDisplayCount) {
            //超出最大显示个数 在最后添加一个view
            for (int i = 0; i < maxDisplayCount - 1; i++) {
                addView(createNormalView(mList.get(i)));
            }
            addView(createLastView());
        } else {
            //没超出最大显示个数 正常显示
            for (int i = 0; i < size; i++) {
                addView(createNormalView(mList.get(i)));
            }
        }
        requestLayout();
    }

    private View createNormalView(DataLogin data) {
        ImageView imageView = new ImageView(getContext());
        int padding = CommonUtils.dip2px(getContext(), 1);
        imageView.setBackgroundResource(R.drawable.circle_white);
        imageView.setPadding(padding, padding, padding, padding);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(mItemLength, mItemLength));
        imageView.setImageResource(R.drawable.avatar);
        return imageView;
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

    public void startAnim(){

    }
}
