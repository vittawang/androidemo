package com.sunspot.sharelement.album;

import android.app.Activity;
import android.content.Context;
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
 * 时间：2019/7/3 下午2:32
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class PhotoAdapter extends BaseAdapter<PhotoModel, BaseViewHolder> {

    public PhotoAdapter(List<PhotoModel> data) {
        super(R.layout.item_photo_detail, data);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        PhotoModel item = getItem(position);
        if (item == null) {
            return;
        }
        ImageView iv = holder.getView(R.id.iv);
        iv.setImageResource(item.getImgRes());
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                if (context instanceof Activity) {
                    //做动画!!!!!!
                    ((Activity) context).finishAfterTransition();
                }
            }
        });
    }
}
