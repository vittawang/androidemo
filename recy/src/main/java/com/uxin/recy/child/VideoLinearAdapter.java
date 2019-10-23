package com.uxin.recy.child;

import android.graphics.Color;

import com.uxin.recy.R;
import com.uxin.recy.adapter.BaseAdapter;
import com.uxin.recy.adapter.BaseViewHolder;
import com.uxin.recy.entity.Video;

/**
 * -------------------------------------
 * 作者：vitta
 * -------------------------------------
 * 时间：2019-10-23 11:22
 * -------------------------------------
 * 描述：单布局 线性布局 使用
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class VideoLinearAdapter extends BaseAdapter<Video, BaseViewHolder> {

    public VideoLinearAdapter() {
        super(R.layout.item_linear_video);
    }

    @Override
    public void convert(BaseViewHolder holder, Video item, int position) {
        holder.setText(R.id.tv_center, item.getTitle() + "豆子兵" + position)
                .addOnClickListener(R.id.tv_center)
                .addOnClickListener(R.id.container);
    }
}
