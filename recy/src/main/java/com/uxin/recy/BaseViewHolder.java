package com.uxin.recy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2018/10/22 下午3:14
 * -------------------------------------
 * 描述：ViewHolder 基类
 * -------------------------------------
 * 备注：1. ViewHolder里不能显示的声明具体的view，只能声明view的集合，具体使用SparseArray缓存一个view集合，不要每次获取view都去findViewById！
 *      2. 封装view常见的设置方法，可以链式调用
 *      3. 封装view的点击事件，回调给adapter的onClickListener
 *      4. NoDoubleClickListener 的衔接问题
 * -------------------------------------
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> views;
    private BaseAdapter adapter;
    private View.OnClickListener mOnClickListener;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        this.views = new SparseArray<>();
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(@IdRes int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public BaseViewHolder setText(@IdRes int viewId, CharSequence value) {
        TextView view = getView(viewId);
        view.setText(value);
        return this;
    }

    public BaseViewHolder setText(@IdRes int viewId, @StringRes int stringRes) {
        TextView view = getView(viewId);
        view.setText(stringRes);
        return this;
    }

    public BaseViewHolder setImageResource(@IdRes int viewId, @DrawableRes int drawableRes) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableRes);
        return this;
    }

    public BaseViewHolder setBackGroundColor(@IdRes int viewId, @ColorInt int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public BaseViewHolder setBackgroundRes(@IdRes int viewId, @DrawableRes int drawableRes) {
        View view = getView(viewId);
        view.setBackgroundResource(drawableRes);
        return this;
    }

    public BaseViewHolder setTextColor(@IdRes int viewId, @ColorInt int color) {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public BaseViewHolder setImageDrawable(@IdRes int viewId, Drawable drawable) {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public BaseViewHolder setImageBitmap(@IdRes int viewId, Bitmap bitmap) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public BaseViewHolder setAlpha(@IdRes int viewId, float alpha) {
        getView(viewId).setAlpha(alpha);
        return this;
    }

    public BaseViewHolder setGone(@IdRes int viewId) {
        getView(viewId).setVisibility(View.GONE);
        return this;
    }

    public BaseViewHolder setVisible(@IdRes int viewId) {
        getView(viewId).setVisibility(View.VISIBLE);
        return this;
    }

    public BaseViewHolder setProgress(@IdRes int viewId, int progress) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    public BaseViewHolder setProgress(@IdRes int viewId, int progress, int max) {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        view.setMax(max);
        return this;
    }

    public BaseViewHolder setMax(@IdRes int viewId, int max) {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }

    public BaseViewHolder setOnClickListener(@IdRes int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public BaseViewHolder addOnClickListener(@IdRes final int viewId) {
        View view = getView(viewId);
        if (view != null) {
            if (!view.isClickable()) {
                view.setClickable(true);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (adapter.getOnItemChildClickListener() != null) {
                        adapter.getOnItemChildClickListener().onItemChildClick(adapter, v, getClickPosition());
                    }
                }
            });
        }
        return this;
    }

    public int getClickPosition() {
        if (getLayoutPosition() >= adapter.getHeaderLayoutCount()) {
            return getLayoutPosition() - adapter.getHeaderLayoutCount();
        }
        return 0;
    }

    public BaseViewHolder addItemChildListeners(@IdRes int ... idRes){
        if (idRes != null && idRes.length > 0) {
            int length = idRes.length;
            for (int i = 0; i < length; i++) {
                addOnClickListener(idRes[i]);
            }
        }
        return this;
    }

    public void setAdapter (BaseAdapter adapter){
        this.adapter = adapter;
    }

}
