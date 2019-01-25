package com.sunspot.nine;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2018/12/5 下午5:59
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class GridImageAdapter extends BaseQuickAdapter<List<ImgInfo>,BaseViewHolder> {

    public GridImageAdapter(@Nullable List<List<ImgInfo>> data) {
        super(R.layout.layout_nine_grid,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, List<ImgInfo> item) {
        ((NineGridLayout) helper.getView(R.id.nine_grid)).setImageList(item);
    }
}
