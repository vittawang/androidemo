package com.uxin.recy.child;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.uxin.recy.R;
import com.uxin.recy.adapter.BaseAdapter;
import com.uxin.recy.adapter.BaseViewHolder;
import com.uxin.recy.entity.Video;

/**
 * -------------------------------------
 * 作者：vitta
 * -------------------------------------
 * 时间：2019-10-22 12:14
 * -------------------------------------
 * 描述：单布局瀑布流
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class VideoStaggeredAdapter extends BaseAdapter<Video, BaseViewHolder> {

    public static final int LAYOUT_GRID = R.layout.item_nine_grid;
    public static final int LAYOUT_STAGGERED = R.layout.item_staggered;
    public static final int GRID = 2;

    public VideoStaggeredAdapter(int layoutRes) {
        super(layoutRes);
    }

    @Override
    public void convert(BaseViewHolder holder, Video item, int position) {
        ViewGroup.LayoutParams layoutParams = holder.getView(R.id.item_container).getLayoutParams();
        //瀑布流核心代码 - 设置宽高
        switch (getOrientationType()) {
            case HORIZONTAL:
                layoutParams.width = item.getWidth() * 300;
                layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                break;
            case VERTICAL:
                layoutParams.height = item.getWidth() * 300;
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                break;
            case GRID:
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                break;
        }
        holder.getView(R.id.item_container).setLayoutParams(layoutParams);
        Glide.with(mContext).load(item.getCover_pic()).into((ImageView) holder.getView(R.id.cover_iv));
    }
}
