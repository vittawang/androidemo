package com.uxin.uxindemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2018/9/15 下午2:17
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class ComposeShaderView extends View{

    Bitmap mBitmap = null;
    int bitwidth = 0;
    int bitheight = 0;
    Paint mPaint = null;
    // bitmap渲染
    Shader mBitmapShader = null;
    // 线性渐变渲染
    Shader mLinearGradient = null;
    // 混合渲染
    Shader mComposeShader = null;

    ShapeDrawable mShapeDrawable = null;

    public ComposeShaderView(Context context) {
        super(context);
        // 装载资源
        mBitmap = ((BitmapDrawable) getResources().getDrawable(R.mipmap.icon_video_bling_three))
                .getBitmap();
        // 得到宽高
        bitwidth = mBitmap.getWidth();
        bitheight = mBitmap.getHeight();
        // 创建BitmapShader对象
        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.MIRROR,
                Shader.TileMode.MIRROR);
        // 创建LinearGradient并设置渐变颜色数组,平铺效果为镜像
        mLinearGradient = new LinearGradient(0, 0, 0, 100, new int[] {
                Color.WHITE, Color.LTGRAY, Color.TRANSPARENT, Color.GREEN }, null,
                Shader.TileMode.MIRROR);

        // 混合渲染 将两个效果叠加,使用PorterDuff叠加模式
        mComposeShader = new ComposeShader(mBitmapShader, mLinearGradient,  PorterDuff.Mode.MULTIPLY);
        mPaint = new Paint();
    }

    public ComposeShaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制混合渲染效果
        mPaint.setShader(mComposeShader);
        canvas.drawCircle(240, 360, 200, mPaint);

    }

}
