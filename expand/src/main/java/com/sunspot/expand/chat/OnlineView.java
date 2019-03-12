package com.sunspot.expand.chat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
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
 * 时间：2019/3/6 下午7:36
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class OnlineView extends FrameLayout implements Handler.Callback, ValueAnimator.AnimatorUpdateListener {

    private static final String TAG = "OnlineView";

    private static final int TIME = 3000;
    private static final int ANIM = 1000;
    private int mItemLength;
    private int mPressedWidth;
    private OnlineChatAvatarView mAvatarView;
    private List<DataLogin> mList;
    private TextView mLastTv;
    private ImageView mFirstV, mLastV;
    private View mLastIndexView;
    private List<View> mHideList = new ArrayList<>();
    private int maxDisplayCount;

    public OnlineView(Context context) {
        this(context, null);
    }

    public OnlineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OnlineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.OnlineView);
        mItemLength = typedArray.getDimensionPixelOffset(R.styleable.OnlineView_item_length, CommonUtils.dip2px(context, 42));
        mPressedWidth = typedArray.getDimensionPixelOffset(R.styleable.OnlineView_item_pressed_length, CommonUtils.dip2px(context, 8));
        typedArray.recycle();
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
        addChildView();
    }

    //lastV firstV avatarList 在同一层级，做动画互不影响
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //计算最多可以显示下几条数据
        maxDisplayCount = (w - mItemLength) / (mItemLength - mPressedWidth) + 1;
        addChildView();
    }

    private void addChildView() {
        if (mList == null || mList.size() == 0 || maxDisplayCount == 0) {
            return;
        }
        int size = mList.size();
        int showCount;
        if (size > maxDisplayCount) {
            showCount = maxDisplayCount - 1;
            addNeedAnimView(size, showCount);
        } else {
            showCount = size;
            addNoNeedAnimView(showCount);
        }
    }

    /**
     * 添加需要动画的view
     *
     * @param showCount 可展示的view数量去掉最后一位
     */
    private void addNoNeedAnimView(int showCount) {
        //不需要动画、不需要lastIv，不需要lastTv，不需要firstIv
        mAvatarView.removeAllViews();
        for (int i = 0; i < showCount; i++) {
            mAvatarView.addView(createNormalView(mList.get(i)));
        }
    }

    /**
     * 添加不需要动画的view
     */
    private void addNeedAnimView(int size, int showCount) {
        //需要动画、需要lastIv，需要lastTv，需要firstIv
        LayoutParams params = new LayoutParams(mItemLength, mItemLength);
        params.leftMargin = (mItemLength - mPressedWidth) * showCount;
        mLastTv.setLayoutParams(params);
        LayoutParams params1 = new LayoutParams(mItemLength, mItemLength);
        params1.leftMargin = (mItemLength - mPressedWidth) * (showCount - 1);
        mLastV.setLayoutParams(params1);
        //show add view
        mAvatarView.removeAllViews();
        for (int i = 1; i < showCount; i++) {
            mAvatarView.addView(createNormalView(mList.get(i)));
        }
        //hide add view
        for (int i = showCount; i < size; i++) {
            mHideList.add(createNormalView(mList.get(i)));
        }
        mLastIndexView = createNormalView(mList.get(0));
//        log();
        //first 做动画的
        mFirstV.setImageResource(mList.get(showCount).getDrawableRes());
        //last 真实显示和做动画的
        mLastV.setImageResource(mList.get(0).getDrawableRes());
    }

    private Handler mHandler = new Handler(this);

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
        mLastV.setTranslationX(value);
    }

    @Override
    public boolean handleMessage(final Message msg) {
        if (msg.what == ANIM) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mItemLength - mPressedWidth);
            valueAnimator.setDuration(1000);
            valueAnimator.setInterpolator(new AccelerateInterpolator());
            valueAnimator.addUpdateListener(this);
            valueAnimator.addListener(mAnimationListener);
            valueAnimator.start();
            mHandler.sendEmptyMessageDelayed(ANIM, TIME);
        }
        return true;
    }

    private AnimatorListenerAdapter mAnimationListener = new AnimatorListenerAdapter() {

        @Override
        public void onAnimationEnd(Animator animation) {
            //重制各种view的状态
            //show add 0 / remove lastV(先remove 再add)
            View foot = mAvatarView.getChildAt(0);
            mAvatarView.removeView(foot);
            View head = mHideList.get(0);
            if (head.getParent() != null) {
                ((ViewGroup) head.getParent()).removeView(head);
            }
            mAvatarView.addView(head);
            mAvatarView.setTranslationX(0);

            //hide add last / remove 0
            if (mLastIndexView.getParent() != null) {
                ((ViewGroup) mLastIndexView.getParent()).removeView(mLastIndexView);
            }
            mHideList.add(mLastIndexView);
            mHideList.remove(0);
            mLastIndexView = foot;

            //firstV
            mFirstV.setImageResource(mList.get((Integer) mHideList.get(0).getTag()).getDrawableRes());
            mFirstV.setScaleX(0);
            mFirstV.setScaleY(0);
            mFirstV.setAlpha(0f);

            //lastV
            mLastV.setImageResource(mList.get((Integer) mLastIndexView.getTag()).getDrawableRes());
            mLastV.setScaleX(1);
            mLastV.setScaleY(1);
            mLastV.setAlpha(1f);
            mLastV.setTranslationX(0);
        }
    };

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == VISIBLE) {
            //handler开始发送消息
            mHandler.removeCallbacksAndMessages(null);
            mHandler.sendEmptyMessageDelayed(ANIM, TIME);
        } else {
            //停止handler发送消息
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    public View createNormalView(DataLogin data) {
        ImageView imageView = new ImageView(getContext());
        int padding = CommonUtils.dip2px(getContext(), 1);
        imageView.setBackgroundResource(R.drawable.circle_white);
        imageView.setPadding(padding, padding, padding, padding);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(mItemLength, mItemLength));
        imageView.setImageResource(data.getDrawableRes());
        imageView.setTag(data.getCount());
        return imageView;
    }

//    private void log() {
//        StringBuilder builder = new StringBuilder();
//        int hideSize = mHideList.size();
//        for (int i = 0; i < hideSize; i++) {
//            View view = mHideList.get(i);
//            builder.append(view.getTag()).append(" / ")
//                    .append(view.getScaleX()).append(" / ")
//                    .append(view.getScaleY()).append(" / ")
//                    .append(view.getAlpha()).append("\n");
//        }
//        Log.e(TAG, "log:hideList " + builder.toString());
//        StringBuilder showBuilder = new StringBuilder();
//        int childCount = mAvatarView.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View childAt = mAvatarView.getChildAt(i);
//            showBuilder.append(childAt.getTag()).append(" / ")
//                    .append(childAt.getScaleX()).append(" / ")
//                    .append(childAt.getScaleY()).append(" / ")
//                    .append(childAt.getAlpha()).append("\n");
//        }
//        Log.e(TAG, "log:showList " + showBuilder.toString());
//    }
}
