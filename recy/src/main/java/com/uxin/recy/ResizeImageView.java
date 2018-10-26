package com.uxin.recy;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2018/10/23 下午4:09
 * -------------------------------------
 * 描述：自定义宽高比的图片控件
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class ResizeImageView extends ImageView {

    private float mAspectRatio;

    public ResizeImageView(Context context) {
        this(context, null);
    }

    public ResizeImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ResizeImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ResizeImageView);
        mAspectRatio = typedArray.getFloat(R.styleable.ResizeImageView_aspect_ratio, -1);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mAspectRatio > 0) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            setMeasuredDimension(width, (int) (width / mAspectRatio));
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
