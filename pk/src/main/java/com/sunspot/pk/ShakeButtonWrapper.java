package com.sunspot.pk;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;


/**
 * 按钮加缩小抖动动画包装视图
 * 用法：xml里包裹需要动画的视图就行了，代码里直接给按钮本身设置点击事件即可，不需要手动操作动画
 * Create by zhangkun on 2018/9/4.
 */
public class ShakeButtonWrapper extends FrameLayout {

    public ShakeButtonWrapper(@NonNull Context context) {
        super(context, null, 0);
    }

    public ShakeButtonWrapper(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShakeButtonWrapper(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initShake();
    }

    private void initShake() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getChildCount() != 1) {
                    return;
                }
                View shake = getChildAt(0);
                if (shake != null && shake.hasOnClickListeners()) {
                    shake.callOnClick();
                }
                UxAnimationUtils.scaleAnimationSet(shake);
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }
}
