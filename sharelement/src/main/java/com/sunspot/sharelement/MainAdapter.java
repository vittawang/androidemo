package com.sunspot.sharelement;

import android.support.annotation.NonNull;
import android.util.Log;

import com.sunspot.sharelement.adapter.BaseAdapter;
import com.sunspot.sharelement.adapter.BaseViewHolder;

import java.util.List;

/**
 * -------------------------------------
 * 作者：vitta
 * -------------------------------------
 * 时间：2019/5/31 下午3:30
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class MainAdapter extends BaseAdapter<TextModel, BaseViewHolder> {

    private static final String TAG = "MainAdapter";

    public MainAdapter(List<TextModel> data) {
        super(R.layout.item_main,data);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        Log.e(TAG, "onBindViewHolder: " + position );
        if (getItem(position) == null){
            return;
        }
        holder.setText(R.id.tv,getItem(position).getMessage());
    }
}
