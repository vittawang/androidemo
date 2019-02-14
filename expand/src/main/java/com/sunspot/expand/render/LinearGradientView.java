package com.sunspot.expand.render;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2019/1/28 下午4:07
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class LinearGradientView extends View {

    private Paint mPaint;

    public LinearGradientView(Context context) {
        this(context, null);
    }

    public LinearGradientView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        LinearGradient shader = new LinearGradient(0,0,1000,4, Color.parseColor("#CCFFFFFF"),Color.TRANSPARENT, Shader.TileMode.REPEAT);
        mPaint.setShader(shader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0,0,1000,10,mPaint);
    }
}
