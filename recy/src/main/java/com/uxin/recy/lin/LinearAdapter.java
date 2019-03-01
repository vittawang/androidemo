package com.uxin.recy.lin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.uxin.recy.BaseAdapter;
import com.uxin.recy.BaseViewHolder;
import com.uxin.recy.R;

import java.util.List;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2019/2/20 下午4:29
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class LinearAdapter extends BaseAdapter<DataLinear, BaseViewHolder> {

    private static final String TAG = "LinearAdapter";

    public LinearAdapter(List<DataLinear> data) {
        super(R.layout.item_linear, data);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        BaseViewHolder baseViewHolder = super.onCreateViewHolder(parent, position);
        View view = baseViewHolder.getView(R.id.rev_inner);
        if (view instanceof AutoScrollRecyclerView) {
            Context context = parent.getContext();
            ScrollSpeedLinearLayoutManager layoutManager = new ScrollSpeedLinearLayoutManager(context);
            layoutManager.setSmoothScrollbarEnabled(true);
            ((RecyclerView) view).setLayoutManager(layoutManager);
            ((RecyclerView) view).setAdapter(new CommentAdapter(null));
            ((RecyclerView) view).setHasFixedSize(true);
            if (context instanceof AppCompatActivity) {
                ((AppCompatActivity) context).getLifecycle().addObserver(((AutoScrollRecyclerView) view));
            }
        }
        return baseViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        View view = holder.getView(R.id.rev_inner);
        if (view instanceof RecyclerView) {
            view.setTag(position);
            RecyclerView.Adapter adapter = ((RecyclerView) view).getAdapter();
            if (adapter instanceof CommentAdapter) {
                DataLinear item = getItem(position);
                Log.e(TAG, "onBindViewHolder: " + item + " / " + position);
                if (item != null) {
                    ((CommentAdapter) adapter).setNewData(item.getCommentList());
                }
            }
        }
    }

}
