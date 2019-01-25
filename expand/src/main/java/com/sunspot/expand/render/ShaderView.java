package com.sunspot.expand.render;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

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
public class ShaderView extends View {

    private LinearGradient linearGradient;
    private SweepGradient sweepGradient;
    //为了改变角度
    private Matrix matrix;

    public ShaderView(Context context) {
        this(context, null);
    }

    public ShaderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        /**
         *  X0:    渐变起初点坐标x位置
         *  y0:    渐变起初点坐标y位置
         *  x1:    渐变终点坐标x位置
         *  y1:    渐变终点坐标y位置
         *  colors:  渐变颜色数组
         *  positions:这个也是一个数组用来指定颜色数组的相对位置 如果为null 就沿坡度线均匀分布
         *  tile：渲染模式(平铺方式)
         */
        //线性渐变
        linearGradient = new LinearGradient(0, 10, 400, 10, new int[]{Color.RED,Color.GREEN,Color.BLUE},
                new float[]{0.33f,0.66f,1f}, Shader.TileMode.REPEAT);
        /**
         * new LinearGradient(0, 0, mViewWidth * 3 / 2, mViewHeight,
           new int[]{transparent, white, white, transparent, transparent, white, white, transparent, transparent},
           new float[]{0.2f, 0.2f, 0.27f, 0.27f, 0.4f, 0.4f, 0.5f, 0.5f, 1f}, Shader.TileMode.CLAMP);
         */

        //环形渐变
        //扫描渐变
        sweepGradient = new SweepGradient(300,300,Color.parseColor("#00ffffff"),Color.parseColor("#60ffffff"));
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 360);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                matrix = new Matrix();
                matrix.setRotate(value,300,300);
                invalidate();
            }
        });
        valueAnimator.start();


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (matrix == null){
            return;
        }
        Paint paint = new Paint();
//        paint.setShader(linearGradient);
//        canvas.drawRect(100,400,400,1000,paint);
        canvas.drawColor(Color.BLUE);
        paint.setShader(sweepGradient);
        canvas.concat(matrix);
        canvas.drawOval(0,0,600,600,paint);
    }

}
