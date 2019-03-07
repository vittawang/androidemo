package com.uxin.recy.lin;

import android.arch.lifecycle.LifecycleObserver;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2019/2/27 下午3:04
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class AutoScrollRecyclerView extends RecyclerView implements Handler.Callback, LifecycleObserver {

    private static final String TAG = "OnlineChatScrollRecycle";
    private static final int START_INDEX = 2;

    private int index = START_INDEX;
    private static final int START_SCROLL = -10;
    private Handler mHandler = new Handler(this);
    private long mDelayMillis = 2000;

    public AutoScrollRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public AutoScrollRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoScrollRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == START_SCROLL) {
            Log.e(TAG, "handleMessage: " + getTag() + " / " + index);
            smoothScrollToPosition(++index);
            mHandler.sendEmptyMessageDelayed(START_SCROLL, mDelayMillis);
        }
        return true;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e(TAG, "onAttachedToWindow: " + getTag());
        index = START_INDEX;
        scrollToPosition(0);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.e(TAG, "onDetachedFromWindow: " + getTag());
        scrollToPosition(0);
        index = START_INDEX;
    }

    /**
     * 拦截滚动各种事件
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return false;
    }

    /**
     * 监听view的可见状态
     */
    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == VISIBLE){
            //handler开始发送消息
            mHandler.removeCallbacksAndMessages(null);
            mHandler.sendEmptyMessageDelayed(START_SCROLL, mDelayMillis);
        }else{
            //停止handler发送消息
            mHandler.removeCallbacksAndMessages(null);
        }
//        Log.e(TAG, "onWindowVisibilityChanged: " + visibility );
    }

}
