package com.sunspot.sharelement.adapter;

import android.view.View;

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

    void onClick(BaseAdapter adapter, View view,int position);

}
