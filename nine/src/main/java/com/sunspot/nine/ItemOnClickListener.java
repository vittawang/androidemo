package com.sunspot.nine;

import android.view.View;

import java.util.List;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2019/5/13 上午11:51
 * -------------------------------------
 * 描述：九宫格点击事件
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public interface ItemOnClickListener {

    void onImageItemClick(View view, int position, List<ImgInfo> list);

}
