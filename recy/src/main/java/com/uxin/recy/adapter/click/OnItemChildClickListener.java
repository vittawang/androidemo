package com.uxin.recy.adapter.click;

import android.view.View;

import com.uxin.recy.adapter.BaseAdapter;


/**
 * -------------------------------------
 * 作者：vitta
 * -------------------------------------
 * 时间：2018/11/5 上午11:51
 * -------------------------------------
 * 描述：item子view点击事件
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public interface OnItemChildClickListener {

    void onItemChildClick(BaseAdapter adapter, View view, int position);

}
