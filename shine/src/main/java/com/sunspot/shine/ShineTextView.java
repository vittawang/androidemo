package com.sunspot.shine;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * -------------------------------------
 * 作者：王文婷@<WVitta@126.com>
 * -------------------------------------
 * 时间：2018/9/15 上午9:47
 * -------------------------------------
 * 描述：闪光图片
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class ShineTextView extends TextView {

    private Rect mRect;
    private Paint mPaint;
    private ValueAnimator mValueAnimator;
    private int mViewWidth;
    private int mViewHeight;
    private LinearGradient mLinearGradient;
    private Matrix mGradientMatrix;
    private BitmapShader mBitmapShader;
    private ComposeShader mComposeShader;
    private float mTranslateX;
    private float mTranslateY;
    /**
     * 默认裁剪图片就是一张方形图片
     */
    private int mClipImgRes = R.mipmap.icon_video_bling_two;

    public ShineTextView(Context context) {
        this(context, null);
    }

    public ShineTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShineTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShineImageView);
//        mClipImgRes = typedArray.getResourceId(R.styleable.ShineImageView_clip_shine_img,mClipImgRes);
//        typedArray.recycle();
        init();
    }

    private void init() {
        mRect = new Rect();
        mPaint = new Paint();
        initGradientAnimator();
    }

    private void initGradientAnimator() {
        mValueAnimator = ValueAnimator.ofFloat(0, 1);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float) animation.getAnimatedValue();
                //❶ 改变每次动画的平移x、y值，范围是[-2mViewWidth, 2mViewWidth]
                mTranslateX = 4 * mViewWidth * v - mViewWidth * 2;
                mTranslateY = mViewHeight * v;
                //❷ 平移matrix, 设置平移量
                if (mGradientMatrix != null) {
                    mGradientMatrix.setTranslate(mTranslateX, mTranslateY);
                }
                //❸ 设置线性变化的matrix
                if (mLinearGradient != null) {
                    mLinearGradient.setLocalMatrix(mGradientMatrix);
                    mComposeShader = new ComposeShader(mBitmapShader, mLinearGradient, PorterDuff.Mode.SRC_IN);
                    mPaint.setShader(mComposeShader);
                }

                //❹ 重绘
                invalidate();
            }
        });
        mValueAnimator.setDuration(1500);
        mValueAnimator.setRepeatCount(-1);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.start();
    }

    public void startShineAnimation(int duration) {
        if (mValueAnimator != null) {
            if (mValueAnimator.isRunning()) {
                mValueAnimator.cancel();
            }
            mValueAnimator.setDuration(duration);
            mValueAnimator.start();
        }
    }

    public void stopAnimation() {
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mRect.set(0, 0, getWidth(), getHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0) {
            mViewWidth = getWidth();
            mViewHeight = getHeight();
            if (mViewWidth > 0) {
                int white = 0x99FFFFFF;
                int transparent = Color.TRANSPARENT;
                mLinearGradient = new LinearGradient(0, 0, mViewWidth * 3 / 2, mViewHeight,
                        new int[]{transparent, white, white, transparent, transparent, white, white, transparent, transparent},
                        new float[]{0.2f, 0.2f, 0.27f, 0.27f, 0.4f, 0.4f, 0.5f, 0.5f, 1f}, Shader.TileMode.CLAMP);

                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),mClipImgRes);
                float scale = calculateBitmapScale(bitmap);
                //2、为BitmapShader定义一个变换矩阵Matrix，通过Matrix对Bitmap进行缩放
                Matrix mMatrix = new Matrix();
                mMatrix.setScale(scale, scale);
                //3、通过Matrix将缩放后的Bitmap移动到View的中心位置
                float dx = getMeasuredWidth() - bitmap.getWidth() * scale;
                float dy = getMeasuredHeight() - bitmap.getHeight() * scale;
                //注意只能用一个set方法，其他的要用post或pre方法
                mMatrix.postTranslate(dx / 2, dy / 2);
                mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                mBitmapShader.setLocalMatrix(mMatrix);
                mComposeShader = new ComposeShader(mBitmapShader, mLinearGradient, PorterDuff.Mode.SRC_IN);
                mPaint.setShader(mComposeShader);
                mGradientMatrix = new Matrix();
                mGradientMatrix.setTranslate(-2 * mViewWidth,mViewHeight);
                mLinearGradient.setLocalMatrix(mGradientMatrix);
                mRect.set(0,0,w,h);
            }
        }
    }

    /**
     * 计算Bitmap需要缩放的比例
     */
    private float calculateBitmapScale(Bitmap bitmap) {
        float scale = 1.0f;
        if (bitmap.getWidth() != getWidth() || bitmap.getHeight() != getHeight()) {
            float scaleWidth = getWidth() * 1.0f / bitmap.getWidth();
            float scaleHeight = getHeight() * 1.0f / bitmap.getHeight();
            scale = Math.max(scaleWidth, scaleHeight);
        }
        return scale;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mValueAnimator != null && mValueAnimator.isRunning() && mGradientMatrix != null){
            canvas.drawRect(mRect,mPaint);
        }
    }

//    mLinearGradient = new LinearGradient(0, 0, mViewWidth / 2, mViewHeight,
//                        new int[]{0x00ffffff, 0x73ffffff, 0x00ffffff, 0x99ffffff, 0x00ffffff},
//            new float[]{0.2f, 0.35f, 0.5f, 0.7f, 1f}, Shader.TileMode.CLAMP);
}
