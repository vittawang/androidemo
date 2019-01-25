package com.sunspot.identity;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2018/11/26 上午10:48
 * -------------------------------------
 * 描述：身份头像
 * -------------------------------------
 * 备注：1、宽高设置的是图片的宽高
 * 2、动态设置K的大小
 * 3、设置描边宽高和颜色
 * 4、设置挂件
 * 5、一直是一个正方形
 * -------------------------------------
 */
public class AvatarImageView extends ViewGroup {

    /**
     * 1、addView 进去
     * 2、onMeasure 测量总大小
     * 3、onDraw 绘制描边
     * 4、onLayout 摆放位置
     * 5、设置图片和数据
     */

    public static final int DEFAULT_ANGLE = 45;
    private int DEFAULT_AVATAR_LENGTH = 200;
    private int DEFAULT_K_LENGTH = 50;

    private ImageView mAvatarIv;
    private ImageView mIdentityIv;
    private View mAvatarCover;
    private Context mContext;
    private int avatarLength, kLength;
    private Paint mBorderPaint;
    private int mBorderColor;
    private int mBorderWidth;
    private int mInnerBorderWidth;
    private int mInnerBorderColor;
    private View mPendentView;
    private double mAngle = DEFAULT_ANGLE;//角度 大圆和小圆的

    private Paint mInnerBorderPaint;
    /**
     * 是否有头像蒙层
     */
    private boolean hasAvatarCover;
    /**
     * 是否有头像挂件
     */
    private boolean hasPendant;
    /**
     * 挂件占view的高度
     */
    private int mPendantHeight;
    /**
     * 控件总宽度
     */
    private int totalWidth;

    public AvatarImageView(Context context) {
        this(context, null);
    }

    public AvatarImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AvatarImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initAttrs(attrs);
        setWillNotDraw(false);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.AvatarImageView);
        mAngle = typedArray.getInteger(R.styleable.AvatarImageView_k_angle, DEFAULT_ANGLE);
        kLength = typedArray.getDimensionPixelOffset(R.styleable.AvatarImageView_k_length, DEFAULT_K_LENGTH);
        mBorderColor = typedArray.getColor(R.styleable.AvatarImageView_border_color, Color.WHITE);
        mBorderWidth = typedArray.getDimensionPixelOffset(R.styleable.AvatarImageView_border_width, 0);
        mInnerBorderWidth = typedArray.getDimensionPixelOffset(R.styleable.AvatarImageView_inner_border_width, 0);
        mInnerBorderColor = typedArray.getColor(R.styleable.AvatarImageView_inner_border_color, Color.WHITE);
        hasAvatarCover = typedArray.getBoolean(R.styleable.AvatarImageView_has_avatar_cover, false);
        hasPendant = typedArray.getBoolean(R.styleable.AvatarImageView_has_pendant, false);
        mPendantHeight = typedArray.getDimensionPixelOffset(R.styleable.AvatarImageView_pendant_height, 0);
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (hasPendant && hasAvatarCover && childCount == 2) {
            //0位置是挂件 1位置是蒙层
            mPendentView = getChildAt(0);
            mAvatarCover = getChildAt(1);
            mAvatarCover.setTag(1);
        } else if (hasPendant && !hasAvatarCover && childCount == 1) {
            //0位置是挂件
            mPendentView = getChildAt(0);
        } else if (hasAvatarCover && !hasPendant && childCount == 1) {
            //0位置是蒙层
            mAvatarCover = getChildAt(0);
            mAvatarCover.setTag(0);
        }
        //头像
        mAvatarIv = new ImageView(mContext);
        mAvatarIv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (hasAvatarCover) {
            addView(mAvatarIv, (Integer) mAvatarCover.getTag());
        } else {
            addView(mAvatarIv);
        }
        //K标
        mIdentityIv = new ImageView(mContext);
        mIdentityIv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        addView(mIdentityIv);
        mIdentityIv.bringToFront();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        switch (widthMode) {
            case MeasureSpec.EXACTLY://match_parent 或者 固定值
                avatarLength = Math.min(measureWidth, measureHeight);
                break;
            default:
                avatarLength = DEFAULT_AVATAR_LENGTH;
                break;
        }
        totalWidth = getTotalWidth();
        int totalHeight = totalWidth;
        //设置每个子view的大小
        //头像
        int avatarSpec = MeasureSpec.makeMeasureSpec(avatarLength, MeasureSpec.EXACTLY);
        mAvatarIv.measure(avatarSpec, avatarSpec);
        if (hasAvatarCover && mAvatarCover != null) {
            mAvatarCover.measure(avatarSpec, avatarSpec);
        }
        //K标
        int kSpec = MeasureSpec.makeMeasureSpec(kLength, MeasureSpec.EXACTLY);
        mIdentityIv.measure(kSpec, kSpec);
        //挂件
        if (hasPendant && mPendentView != null) {
            measureChild(mPendentView, widthMeasureSpec, heightMeasureSpec);
            totalHeight += mPendantHeight;
        }
        //设置总view的大小
        setMeasuredDimension(totalWidth, totalHeight);
    }

    private int getTotalWidth() {
        double cos = Math.abs(Math.cos(mAngle * Math.PI / 180));
        double sin = Math.abs(Math.sin(mAngle * Math.PI / 180));
        int avatarBorderRadius = avatarLength + mBorderWidth * 2 + mInnerBorderWidth * 2;
        double addWidth = avatarBorderRadius * Math.max(cos, sin) + kLength;
        return (int) Math.ceil(Math.max(addWidth,avatarBorderRadius));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mInnerBorderWidth > 0) {
            //头像的描边
            if (mInnerBorderPaint == null) {
                mInnerBorderPaint = new Paint();
                mInnerBorderPaint.setStyle(Paint.Style.STROKE);
                mInnerBorderPaint.setAntiAlias(true);
            }
            mInnerBorderPaint.setColor(mInnerBorderColor);
            mInnerBorderPaint.setStrokeWidth(mInnerBorderWidth);
            int center = totalWidth / 2;
            canvas.drawCircle(center, center + mPendantHeight, avatarLength / 2 + mInnerBorderWidth / 2, mInnerBorderPaint);
        }
        if (mBorderWidth > 0) {
            //增加头像的描边
            if (mBorderPaint == null) {
                mBorderPaint = new Paint();
                mBorderPaint.setStyle(Paint.Style.STROKE);
                mBorderPaint.setAntiAlias(true);
            }
            mBorderPaint.setColor(mBorderColor);
            //setStrokeWidth方法，并不是往圆内侧增加圆环（圆弧）宽度的，而是往外侧增加一半，往内侧增加一半
            mBorderPaint.setStrokeWidth(mBorderWidth);
            int center = totalWidth / 2;
            canvas.drawCircle(center, center + mPendantHeight, avatarLength / 2 + mInnerBorderWidth + mBorderWidth / 2, mBorderPaint);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //摆放挂件
        int center = totalWidth / 2;
        if (hasPendant && mPendentView != null) {
            int pendantWidth = mPendentView.getMeasuredWidth();
            int pendantHeight = mPendentView.getMeasuredHeight();
            int pendantLeft = center - pendantWidth / 2;
            int pendantTop = 0;
            mPendentView.layout(pendantLeft, pendantTop, pendantLeft + pendantWidth, pendantHeight);
        }
        //摆放K标
        double cos = Math.cos(mAngle * Math.PI / 180);//邻边
        double sin = Math.sin(mAngle * Math.PI / 180);//对边
        int radius = avatarLength / 2 + mInnerBorderWidth + mBorderWidth;
        int kRadius = kLength / 2;
        double left = center + (radius * cos - kRadius);
        double top = center + (radius * sin - kRadius) + mPendantHeight;
        double right = left + kLength;
        double bottom = top + kLength;
        mIdentityIv.layout((int) left, (int) top, (int) right, (int) bottom);
        //摆放头像
        int avatarLeft = (totalWidth - avatarLength) / 2;
        int avatarTop = (totalWidth - avatarLength) / 2 + mPendantHeight;
        mAvatarIv.layout(avatarLeft, avatarTop, avatarLeft + avatarLength, avatarTop + avatarLength);
        //摆放头像蒙层
        if (hasAvatarCover && mAvatarCover != null) {
            mAvatarCover.layout(avatarLeft, avatarTop, avatarLeft + avatarLength, avatarTop + avatarLength);
        }
    }

    public void setData() {
        mAvatarIv.setImageResource(R.mipmap.pic_me_avatar);
        mIdentityIv.setImageResource(R.mipmap.vipk80_video);
//        UxinImageLoader.loadHeaderBitmap(data.getHeadPortraitUrl(), mAvatarIv, mDefaultAvatarRes);
//        if (data.getIsVip() == GlobalContant.TYPE_IS_AUTH_VIP) {//V
//            mIdentityIv.stopAnimation();
//            mIdentityIv.setImageResource(DEFAULT_V_ICON);
//        } else if (data.getUserType() > 0) {//K
//            mIdentityIv.setVipIcon(data);
//        }
    }

}

