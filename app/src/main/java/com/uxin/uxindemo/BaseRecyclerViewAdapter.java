package com.uxin.uxindemo;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView基类，可设置外部点击事件监听
 * Created by Sylvester on 17/3/22.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> mDatas = new ArrayList<>();
    protected boolean isScrolling = false;

    public BaseRecyclerViewAdapter() {
        mDatas = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public T getItem(int position) {
        if (position < 0 || position >= getItemCount())
            return null;
        return mDatas == null ? null : mDatas.get(position);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
    }

    // 加载数据时调用
    public void addAll(List<T> mList) {
        if (mList != null) {
            mDatas.clear();
            mDatas.addAll(mList);
            notifyDataSetChanged();
        }
    }

    public void addItem(T t) {
        if (t == null) {
            return;
        }
        mDatas.add(t);
        notifyDataSetChanged();
    }

    public void addItem(T t,int position) {
        if (t == null) {
            return;
        }
        mDatas.add(position,t);
        notifyDataSetChanged();
    }

    public List<T> getmDatas(){
        return mDatas;
    }

    public void removeItemByPosition(int position) {
        if (position > mDatas.size() - 1 || position < 0) {
            return;
        }
        mDatas.remove(position);
        notifyDataSetChanged();
    }

    // 清空列表
    public void clearAll() {
        addAll(new ArrayList<T>());
    }

    // 移除单个item，带原生动效
    public void removeItemWithAnim(int position) {
        if (mDatas != null && mDatas.size() > position && position >= 0) {
            mDatas.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void setScrolling(boolean scrolling) {
        isScrolling = scrolling;
    }

    // 防文本复用
    protected void setCommonText(TextView textView, String str) {
        setCommonText(textView, str, "");
    }

    protected void setCommonText(TextView textView, String str, String defaultStr) {
        if (!TextUtils.isEmpty(str)) {
            textView.setText(str);
        } else {
            textView.setText(defaultStr);
        }
    }
}
