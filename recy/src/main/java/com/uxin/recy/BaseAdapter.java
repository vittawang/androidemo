package com.uxin.recy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2018/10/22 下午3:13
 * -------------------------------------
 * 描述：Adapter 基类
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class BaseAdapter<T,VH extends BaseViewHolder> extends RecyclerView.Adapter<VH>{

    protected List<T> mData;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private int layoutRes;

    public BaseAdapter() {
        this.mData = new ArrayList<>();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        this.mLayoutInflater = LayoutInflater.from(mContext);
//        VH holder = ;
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public void setNewData(){

    }

    public void addData(){

    }

    public void setLoadMoreListener(){

    }

    public void addHeaderView(){

    }

    public void addFooterView(){

    }

    public void setEmptyView(){

    }

    public void setLoadingView(){

    }
}
