package com.sunspot.expand.render;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.sunspot.expand.CommonUtils;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2019/1/25 下午5:29
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class RadarView extends View {

    //为了改变角度
    private Matrix mRadarMatrix;
    private Paint mRadarPaint;
    private RectF mRadarRect;
    private int centerX;
    private int centerY;
    private int sweepStartColor = Color.parseColor("#00ffffff");
    private int sweepEndColor = Color.parseColor("#60ffffff");
    private int DURATION = 5000;
    private Paint mLinePaint;
    private int radius;
    private int lineStartColor = Color.parseColor("#CCFFFFFF");
    private int lineEndColor = Color.TRANSPARENT;
    private ValueAnimator valueAnimator;
    private int mLineStartDistance = CommonUtils.dip2px(getContext(),60);

    public RadarView(Context context) {
        this(context, null);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        valueAnimator = ValueAnimator.ofInt(0, 360);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(DURATION);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                mRadarMatrix = new Matrix();
                mRadarMatrix.setRotate(value, centerX, centerY);
                invalidate();
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        radius = Math.min(w, h) / 2;
        centerX = centerY = radius;
        mRadarRect = new RectF(0, 0, radius * 2, radius * 2);//这个是view相对于自己的 相对位置
//      centerX = getLeft() + radius;//错的
//      mRadarRect = new RectF(getLeft(),getTop(),getRight(),getBottom()); //错的
        //扫描渐变
        SweepGradient sweepGradient = new SweepGradient(centerX, centerY, sweepStartColor, sweepEndColor);
        mRadarPaint = new Paint();
        mRadarPaint.setAntiAlias(true);
        mRadarPaint.setShader(sweepGradient);
        //线的paint
        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setShader(new LinearGradient(centerX + mLineStartDistance, centerY, centerX + radius, centerY, lineStartColor, lineEndColor, Shader.TileMode.REPEAT));//渐变的线
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mRadarMatrix == null || mRadarRect == null) {
            return;
        }
        canvas.concat(mRadarMatrix);
        canvas.drawOval(mRadarRect, mRadarPaint);
        canvas.drawRect(centerX, centerY - 2, centerX + radius, centerY + 2, mLinePaint);
    }

    public void startAnim() {
        valueAnimator.start();
    }

    public void stopAnim() {
        valueAnimator.end();
    }

}
