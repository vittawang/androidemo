package com.uxin.recy.lin;

import android.support.annotation.NonNull;
import android.widget.TextView;

import com.uxin.recy.BaseAdapter;
import com.uxin.recy.BaseViewHolder;
import com.uxin.recy.R;

import java.util.List;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2019/2/27 下午12:23
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class CommentAdapter extends BaseAdapter<DataComment,BaseViewHolder>{

    public CommentAdapter(List<DataComment> data) {
        super(R.layout.item_comment, data);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        DataComment dataComment = getData().get(position % getData().size());
        ((TextView) holder.itemView).setText(dataComment.getContent());
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }
}
