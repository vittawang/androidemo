package com.sunspot.expand.render;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.sunspot.expand.CommonUtils;
import com.sunspot.expand.ViewAnim;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2019/1/25 下午7:59
 * -------------------------------------
 * 描述：匹配动画
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class MatchView extends View implements Handler.Callback, ViewAnim {

    private static final int START_ANIM = 100;
    private static final int RADA = 101;
    private Handler mHandler = new Handler(this);
    //页面中心点坐标
    private int centerX, centerY;
    private Paint mScanPaint;
    private Context mContext;
    private Paint mScanLinePaint;
    private Matrix mRadaMatrix;
    private int degree;

    public MatchView(Context context) {
        this(context, null);
    }

    public MatchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MatchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        centerX = CommonUtils.getScreenWidth(context) / 2;
        centerY = CommonUtils.getScreenHeight(context) / 2;
        initScanGradient();
    }

    private void initScanGradient() {
        mScanPaint = new Paint();
        SweepGradient sweepGradient = new SweepGradient(centerX, centerY, Color.TRANSPARENT, Color.parseColor("#80FFFFFF"));//50% 透明度
        mScanPaint.setShader(sweepGradient);
        mScanLinePaint = new Paint();
        mScanLinePaint.setColor(Color.WHITE);//雷达的一根线颜色
        mScanLinePaint.setAntiAlias(true);
        mScanLinePaint.setStrokeWidth(5);//雷达线的宽度
        mRadaMatrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRada(canvas);

    }

    private void drawRada(Canvas canvas) {
        //雷达宽高 336dp
        int r = CommonUtils.dip2px(mContext, 336) / 2;
        int left = centerX - r;
        int top = centerY - r;
        canvas.setMatrix(mRadaMatrix);
        RectF oval = new RectF(left, top, left + r * 2, top + r * 2);
        canvas.drawOval(oval, mScanPaint);
        //雷达线
        canvas.drawLine(centerX, centerY, centerX + r, centerY, mScanLinePaint);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case RADA:
                mRadaMatrix.setRotate(degree,centerX,centerY);
                invalidate();
                degree+=1;
                if (degree > 360) {
                    degree = 2;
                }
                mHandler.sendEmptyMessageDelayed(RADA, 1);//雷达刷新速率
                return true;
            case START_ANIM:

                return true;
        }
        return false;
    }

    @Override
    public void startAnim() {
        mHandler.sendEmptyMessage(RADA);
    }

    @Override
    public void stopAnim() {
        mHandler.removeCallbacksAndMessages(null);
    }
}
