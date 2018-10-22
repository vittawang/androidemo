package com.uxin.uxindemo;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2018/10/11 上午10:41
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class SnapAdapter extends BaseRecyclerViewAdapter<PagerSnapData> {

    private static final String TAG = "SnapAdapter";
    public static final int TYPE_MORE = R.layout.load_end;
    public static final int TYPE_NORMAL = R.layout.snap_item;
    private boolean hasMoreLayout = false;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {
            return new SnapHolder(LayoutInflater.from(parent.getContext()).inflate(TYPE_NORMAL, parent, false));
        } else if (viewType == TYPE_MORE) {
            return new LoadEndHolder(LayoutInflater.from(parent.getContext()).inflate(TYPE_MORE, parent, false));
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public int getItemViewType(int position) {
        if (hasMoreLayout && position == getItemCount() - 1) {
            return TYPE_MORE;
        }
        return TYPE_NORMAL;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.e(TAG, "onBindViewHolder: " + position);
        if (holder instanceof SnapHolder) {
            ((SnapHolder) holder).tv.setText(String.valueOf(position));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        Log.e(TAG, "payloads: " + position + " / " + payloads );
        super.onBindViewHolder(holder, position, payloads);
    }

    /**
     * 将更多插入到最后一条
     */
    public void insertMoreLayout() {
        hasMoreLayout = true;
        int itemCount = getItemCount();
        notifyItemInserted(itemCount - 1);
//        notifyItemInserted(itemCount);
    }


    class SnapHolder extends RecyclerView.ViewHolder{

        TextView tv;

        public SnapHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }

    }

    class LoadEndHolder extends RecyclerView.ViewHolder{

        public LoadEndHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemCount() {
        if (hasMoreLayout){
            return super.getItemCount() + 1;
        }
        return super.getItemCount();
    }
}
