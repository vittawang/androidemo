package com.sunspot.sharelement.album;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.sunspot.sharelement.R;
import com.sunspot.sharelement.adapter.BaseAdapter;
import com.sunspot.sharelement.adapter.BaseViewHolder;

import java.util.List;

/**
 * -------------------------------------
 * 作者：vitta
 * -------------------------------------
 * 时间：2019/7/3 上午11:56
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class AlbumAdapter extends BaseAdapter<PhotoModel, BaseViewHolder> {

    public AlbumAdapter(List<PhotoModel> data) {
        super(R.layout.item_photo, data);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, final int position) {
        PhotoModel item = getItem(position);
        if (item == null) {
            return;
        }
        ImageView photoIv = holder.getView(R.id.photo_iv);
        photoIv.setImageResource(item.getImgRes());
        if (getOnItemClickListener() != null) {
            photoIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getOnItemClickListener().onClick(AlbumAdapter.this, v, position);
                }
            });

        }
    }
}
