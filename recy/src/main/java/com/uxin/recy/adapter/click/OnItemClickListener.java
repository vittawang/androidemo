package com.uxin.recy.adapter.click;

import android.view.View;

import com.uxin.recy.adapter.BaseAdapter;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2018/11/5 下午7:27
 * -------------------------------------
 * 描述：item 点击事件
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public interface OnItemClickListener {

    void onItemClick(BaseAdapter adapter, View view, int position);

}
