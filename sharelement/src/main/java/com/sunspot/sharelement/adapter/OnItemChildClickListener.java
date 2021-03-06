package com.sunspot.sharelement.adapter;

import android.view.View;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
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
