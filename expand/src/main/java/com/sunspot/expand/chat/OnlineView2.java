package com.sunspot.expand.chat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunspot.expand.CommonUtils;
import com.sunspot.expand.R;

import java.util.ArrayList;
import java.util.List;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2019/3/7 下午4:27
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class OnlineView2 extends FrameLayout implements Handler.Callback {

    private static final int TIME = 3000;
    private static final int ANIM = 1000;
    private int mItemLength;
    private int mPressedWidth;
    private OnlineChatAvatarView mAvatarView;
    private List<DataLogin> mList;
    private TextView mLastTv;
    private ImageView mFirstV,mLastV;
    private List<View> mHideList = new ArrayList<>();
    private int showCount;

    public OnlineView2(Context context) {
        this(context, null);
    }

    public OnlineView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OnlineView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        TypedArray typedArray = context.obtainStyledAttributes(R.styleable.OnlineView);
//        mItemLength = typedArray.getDimensionPixelOffset(R.styleable.OnlineView_item_length, CommonUtils.dip2px(context, 42));
//        mPressedWidth = typedArray.getDimensionPixelOffset(R.styleable.OnlineView_item_pressed_length, CommonUtils.dip2px(context, 8));
//        typedArray.recycle();
        LayoutInflater.from(context).inflate(R.layout.layout_online, this, true);
        mAvatarView = findViewById(R.id.online_view);
        mAvatarView.setItemLength(mItemLength);
        mAvatarView.setPressedWidth(mPressedWidth);
        mLastTv = findViewById(R.id.last_tv);
        mFirstV = findViewById(R.id.first_iv);
        mLastV = findViewById(R.id.last_iv);
    }

    public void setData(List<DataLogin> list) {
        mList = list;
        mAvatarView.setData(list);
        Log.e(TAG, "setData: ");
    }

    //lastV firstV avatarList 在同一层级，做动画互不影响
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e(TAG, "onSizeChanged: "  );
        //计算最多可以显示下几条数据
        int maxDisplayCount = (w - mItemLength) / (mItemLength - mPressedWidth) + 1;
        int size = mList.size();
        if (size > maxDisplayCount) {
            showCount = maxDisplayCount - 1;
            //需要动画、需要lastIv，需要lastTv，需要firstIv
            FrameLayout.LayoutParams params = new LayoutParams(mItemLength, mItemLength);
            params.leftMargin = (mItemLength - mPressedWidth) * showCount;
            mLastTv.setLayoutParams(params);
            LayoutParams params1 = new LayoutParams(mItemLength, mItemLength);
            params1.leftMargin = (mItemLength - mPressedWidth) * (showCount - 1);
            mLastV.setLayoutParams(params1);
//            mAvatarView.addView(showCount - 1);
            for (int i = showCount; i < size; i++) {
                mHideList.add(mAvatarView.createNormalView(mList.get(i)));
            }
            //first 做动画的
            mFirstV.setImageResource(mList.get(showCount).getDrawableRes());
            //last 真实显示和做动画的
            mLastV.setImageResource(mList.get(showCount - 1).getDrawableRes());

            //开始动画
            mHandler.sendEmptyMessageDelayed(ANIM,TIME);
        } else {
            showCount = size;
            //不需要动画、不需要lastIv，不需要lastTv，不需要firstIv
//            mAvatarView.addView(showCount);
        }
    }

    private Handler mHandler = new Handler(this);

    private static final String TAG = "OnlineView";
    @Override
    public boolean handleMessage(final Message msg) {
        Log.e(TAG, "handleMessage: "  );
        if (msg.what == ANIM) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mItemLength - mPressedWidth);
            valueAnimator.setDuration(1000);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    mAvatarView.setTranslationX(value);
                    float fraction = animation.getAnimatedFraction();
                    mFirstV.setScaleX(fraction);
                    mFirstV.setScaleY(fraction);
                    mFirstV.setAlpha(fraction);
                    mLastV.setScaleX(1 - fraction);
                    mLastV.setScaleY(1 - fraction);
                    mLastV.setAlpha(1 - fraction);
//                    Log.e(TAG, "onAnimationUpdate: " + fraction );
                }
            });
            valueAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    Log.e(TAG, "onAnimationStart: " );
//                    foot = mAvatarView.getChildAt(showCount - 1);
//                    head = mHideList.get(0);
//                    mAvatarView.removeView(foot);
                }

                @Override
                public void onAnimationEnd(Animator animation) {

                    //last mAvatarView.getChildAt(showCount - 2)
                    mLastV.setScaleY(1f);
                    mLastV.setScaleX(1f);
                    mLastV.setAlpha(1f);
                    mLastV.setImageResource(mList.get((Integer) mAvatarView.getChildAt(showCount - 2).getTag()).getDrawableRes());

                    //show add 0 / remove last
                    View head = mHideList.get(0);
                    if (head.getParent() != null) {
                        ((ViewGroup) head.getParent()).removeView(head);
                    }
                    mAvatarView.addView(head, 0);
                    mAvatarView.removeViewAt(showCount - 2);
                    mAvatarView.setTranslationX(0);

                    //hide remove 0 / add last
                    mHideList.remove(0);
                    if (mLastV.getParent() != null) {
                        ((ViewGroup) mLastV.getParent()).removeView(mLastV);
                    }
                    mHideList.add(mLastV);

                    //first mHideList.get(0)
                    mFirstV.setImageResource(mList.get((Integer) mHideList.get(0).getTag()).getDrawableRes());
                    log();
                    Log.e(TAG, "onAnimationEnd: " + mHideList.size() + " / " + mAvatarView.getChildCount() );
                }
            });
            valueAnimator.start();
            mHandler.sendEmptyMessageDelayed(ANIM, TIME);
        }
        return true;
    }

    private void log() {
        StringBuilder builder = new StringBuilder();
        int hideSize = mHideList.size();
        for (int i = 0; i < hideSize; i++) {
            View view = mHideList.get(i);
            builder.append(view.getTag()).append(" / ")
                    .append(view.getScaleX()).append(" / ")
                    .append(view.getScaleY()).append(" / ")
                    .append(view.getAlpha()).append("\n");
        }
        Log.e(TAG, "log:hideList " + builder.toString() );
        StringBuilder showBuilder = new StringBuilder();
        int childCount = mAvatarView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = mAvatarView.getChildAt(i);
            showBuilder.append(childAt.getTag()).append(" / ")
                    .append(childAt.getScaleX()).append(" / ")
                    .append(childAt.getScaleY()).append(" / ")
                    .append(childAt.getAlpha()).append("\n");
        }
        Log.e(TAG, "log:showList " + showBuilder.toString() );
    }
}
