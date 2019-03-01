package com.uxin.recy.lin;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

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
public class OnlineChatScrollRecyclerView extends RecyclerView implements Handler.Callback, LifecycleObserver {

    private static final String TAG = "OnlineChatScrollRecycle";
    private static final int START_INDEX = 2;

    private int index = START_INDEX;
    private static final int START_SCROLL = -10;
    private Handler mHandler = new Handler(this);
    private long mDelayMillis = 2000;

    public OnlineChatScrollRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public OnlineChatScrollRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OnlineChatScrollRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
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
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendEmptyMessageDelayed(START_SCROLL, mDelayMillis);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.e(TAG, "onDetachedFromWindow: " + getTag());
        scrollToPosition(0);
        index = START_INDEX;
        mHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 拦截滚动各种事件
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return true;
    }

    /**
     * 监听activity的生命周期 onStart
     * 官方文档 https://developer.android.com/reference/androidx/lifecycle/Lifecycle.html?hl=en
     *
     * @param activity 来源页面
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void start(LifecycleOwner activity) {
        // connect
        //handler开始发送消息
        mHandler.sendEmptyMessageDelayed(START_SCROLL, mDelayMillis);
    }

    /**
     * 生命周期 onStop
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void stop(LifecycleOwner activity) {
        // disconnect if connected
        //停止handler发送消息
        mHandler.removeCallbacksAndMessages(null);
    }
}
