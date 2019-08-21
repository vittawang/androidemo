package com.sunspot.pk;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * -------------------------------------
 * 作者：vitta
 * -------------------------------------
 * 时间：2019/8/20 下午12:30
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：（上下两层去绘制的）
 * -------------------------------------
 */
public class TiltProgressView extends View {

    private static final String TAG = "ClipView";

    /**
     * 两支画笔
     */
    private Paint mUpPaint, mDownPaint;
    /**
     * 当前进度比例[0,1]
     */
    private double fraction;

    /**
     * 倾斜角度[0-90]
     */
    private int angle;
    /**
     * 存储三角形路径
     */
    private Path path;
    /**
     * 进度条的颜色
     */
    private int upPaintColor, downPaintColor;

    public TiltProgressView(Context context) {
        this(context, null);
    }

    public TiltProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TiltProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDownPaint = new Paint();
        if (downPaintColor <= 0) {
            downPaintColor = Color.parseColor("#9EBBFB");
        }
        mDownPaint.setColor(downPaintColor);
        mDownPaint.setAntiAlias(true);
        mDownPaint.setStyle(Paint.Style.FILL);
        mUpPaint = new Paint();
        if (upPaintColor <= 0) {
            upPaintColor = Color.parseColor("#FF8383");
        }
        mUpPaint.setColor(upPaintColor);
        mUpPaint.setAntiAlias(true);
        mUpPaint.setStyle(Paint.Style.FILL);
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        //三角函数(注意：这里要传弧度单位)
        double tan = Math.tan(angle * Math.PI / 180f);
//        Log.e(TAG, "onDraw: " + tan + " / " + measuredWidth + " / " + measuredHeight);
        if (measuredWidth > 0 && measuredHeight > 0) {
            //绘制底下的蓝色条
            canvas.drawRect(0, 0, measuredWidth, measuredHeight, mDownPaint);
            //绘制上层的红色条
            float triangleHalfX = (float) (measuredHeight / (2f * tan));
            float centerPoint = (float) ((measuredWidth + triangleHalfX) * fraction);
            float xBottomOffset = (centerPoint - triangleHalfX);
            if (xBottomOffset > 0) {
                //矩形
                canvas.drawRect(0, 0, xBottomOffset, measuredHeight, mUpPaint);
                //三角形
                path.moveTo(xBottomOffset, 0);
                path.lineTo((xBottomOffset + triangleHalfX * 2), 0);
                path.lineTo(xBottomOffset, measuredHeight);
                path.close();
                canvas.drawPath(path, mUpPaint);
            } else {
                //小的三角形
                path.moveTo(0, 0);
                float triangleHeight = (centerPoint / triangleHalfX) * measuredHeight;
                float triangleWidth = (float) (triangleHeight / tan);
                path.lineTo(triangleWidth, 0);
                path.lineTo(0, triangleHeight);
                path.close();
                canvas.drawPath(path, mUpPaint);
            }
        }
    }

    /**
     * 设置当前进度
     *
     * @param fraction 进度比例[0-1]
     */
    public void setFraction(double fraction) {
        this.fraction = fraction;
        invalidate();
    }

    /**
     * 设置倾斜角度
     *
     * @param angle [0-90]
     */
    public void setAngle(int angle) {
        this.angle = angle;
    }

    /**
     * 设置当前进度
     *
     * @param progress 进度值[0-100]
     */
    public void setProgress(int progress) {
        setFraction(progress / 100f);
    }

    /**
     * 设置底部颜色
     */
    public void setDownColor(int downPaintColor) {
        this.downPaintColor = downPaintColor;
    }

    /**
     * 设置顶部颜色
     */
    public void setUpColor(int upPaintColor) {
        this.upPaintColor = upPaintColor;
    }

    /**
     * 1、上下两层，改变上层的绘制区域，控制进度
     * 2、绘制颜色需要可配置
     * 3、支持反向绘制 setRotation 哈哈哈！！！😂
     */
}
