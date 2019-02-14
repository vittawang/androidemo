package com.sunspot.expand.render;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.sunspot.expand.R;

import java.util.ArrayList;
import java.util.List;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2019/1/28 上午10:52
 * -------------------------------------
 * 描述：水波纹
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class RippleView extends View {

    private static final String TAG = "RippleView";

    private int DEFAULT_COLOR = Color.parseColor("#80FFFFFF");
    private int count;//波纹个数
    private int minR;//波纹最小半径
    private int maxR;//波纹最大半径
    private int speed;//扩散速度（作为每次半径增加的值）
    private Paint mRipplePaint;
    private int rippleColor;
    private int centerX;
    private int centerY;
    private List<Integer> rList = new ArrayList<>();
    private boolean isRunning;//动画是否播放中
    private Paint mStrokePaint;
    private int strokeColor;//圆的描边颜色
    private float strokeWidth;//圆的描边宽度
    private ValueAnimator mValueAnimator;

    public RippleView(Context context) {
        this(context, null);
    }

    public RippleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
        initPaint();
        initValueAnimator();
    }

    private void initValueAnimator() {
        mValueAnimator = ValueAnimator.ofFloat(1,10);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.setDuration(100);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                invalidate();
            }
        });
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RippleView);
        minR = typedArray.getDimensionPixelOffset(R.styleable.RippleView_min_circle_radius, 0);
        count = typedArray.getInteger(R.styleable.RippleView_circle_count, 2);
        speed = typedArray.getInteger(R.styleable.RippleView_ripple_speed, 5);
        rippleColor = typedArray.getColor(R.styleable.RippleView_ripple_color, DEFAULT_COLOR);
        strokeColor = typedArray.getColor(R.styleable.RippleView_stroke_color, 0);
        strokeWidth = typedArray.getDimensionPixelOffset(R.styleable.RippleView_stroke_width, 0);
        typedArray.recycle();
    }


    private void initPaint() {
        mRipplePaint = new Paint();
        mRipplePaint.setColor(rippleColor);
        mRipplePaint.setAntiAlias(true);
        mRipplePaint.setStyle(Paint.Style.FILL);//画笔属性是实心圆
        if (strokeColor != 0 && strokeWidth != 0) {
            //描边的线
            mStrokePaint = new Paint();
            mStrokePaint.setAntiAlias(true);
            mStrokePaint.setStyle(Paint.Style.STROKE);//画笔属性是空心圆
            mStrokePaint.setStrokeWidth(strokeWidth);
            mStrokePaint.setColor(strokeColor);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        maxR = Math.min(w, h) / 2;
        centerX = centerY = maxR;
        rList.clear();
        rList.add(minR);
//        Log.e(TAG, "onSizeChanged: w = " + w + " / h = " + h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isRunning) {
            return;
        }
        int currentCount = rList.size();
        for (int i = 0; i < currentCount; i++) {
            Integer radius = rList.get(i);
            int alpha = (int) (255 * (1 - Double.valueOf(radius) / maxR));
            if (alpha < 0) {
                //规避掉一些特殊尺寸， alpha = -1
                continue;
            }
            mRipplePaint.setAlpha(alpha);
//            Log.e(TAG, "onDraw: " + radius + " / " + alpha);
            canvas.drawCircle(centerX, centerY, radius, mRipplePaint);
            if (mStrokePaint != null) {
                canvas.drawCircle(centerX, centerY, radius, mStrokePaint);
            }
            rList.set(i, radius + speed);
        }
        int circleDistance = (maxR - minR) / count;//圆与圆之间的距离（根据count等分的）
        if (rList.get(rList.size() - 1) >= (circleDistance + minR)) {
            //最内层的圆到达一个间距，内层应该再画一个圆;在最后的位置，添加一条数据；因为这条要最后绘制；
            rList.add(minR);
        }
        if (rList.size() > count) {
            //在最内层添加了一条数据，size就会变大；要把最外层的圆移除掉（0位置）
            rList.remove(0);
        }
//        postInvalidateDelayed(10);
    }

    public void startAnim() {
        isRunning = true;
        mValueAnimator.start();
        //post 与handler冲突，会有问题
//        postInvalidate();
    }

    public void stopAnim() {
        if (isRunning) {
            isRunning = false;
            mValueAnimator.end();
        }
    }

    /**
     * 1、定格出每一帧的画面 考虑怎么绘制一帧的画面
     * 2、提取出 随时间改变的参数和不随时间变的参数（固定的参数，set/attr设置进来）
     * 3、根据时间绘制出 参数时间轴
     * 4、提供start stop 方法等
     */
    /**
     * 1、N 个圆，从内向外；R 变大，A 变小
     *
     * 1、initAttr
     * 2、initPaint
     * 3、onDraw
     * 4、handler / ValueAnimator / postInvalidate
     */
}
