package com.uxin.recy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.uxin.recy.entity.Video;
import com.uxin.recy.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2018/10/22 下午5:00
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class StaggeredAdapter extends RecyclerView.Adapter<StaggeredAdapter.Holder> {

    private static final String TAG = "StaggeredAdapter";
    public static final int LAYOUT_GRID = R.layout.item_nine_grid;
    public static final int LAYOUT_STAGGERED = R.layout.item_staggered;
    private Context mContext;
    private List<Video> mData;
    private int layoutRes;
    private int adapterType = HORIZONTAL;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    public static final int GRID = 2;

    public void setAdapterType(int adapterType) {
        this.adapterType = adapterType;
    }

    public StaggeredAdapter(int layoutRes) {
        this(new ArrayList<Video>(),layoutRes);
    }

    public StaggeredAdapter(List<Video> data, int layoutRes) {
        this.mData = data;
        this.layoutRes = layoutRes;
        Log.e(TAG, "StaggeredAdapter: " + CommonUtils.dip2px(mContext,100) );
    }

    public Video getItem(int position){
        if (mData != null && position >= 0 && position < mData.size()) {
            return mData.get(position);
        }
        return null;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new Holder(LayoutInflater.from(mContext).inflate(layoutRes,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Log.e(TAG, "onBindViewHolder: " + position );
        Video item = getItem(position);
        ViewGroup.LayoutParams layoutParams = holder.item_container.getLayoutParams();
        //瀑布流核心代码 - 设置宽高
        switch (adapterType) {
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
        holder.item_container.setLayoutParams(layoutParams);
        Glide.with(mContext).load(item.getCover_pic()).into(holder.cover_iv);
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public void setNewData(List<Video> list){
        if (list != null && list.size() > 0){
            mData.clear();
            mData.addAll(list);
            notifyItemRangeInserted(0,mData.size());
        }
    }

    public void addData(List<Video> list){
        if (list != null && list.size() > 0){
            int oldSize = mData.size();
            mData.addAll(list);
            notifyItemRangeInserted(oldSize,mData.size());
        }
    }

    class Holder extends RecyclerView.ViewHolder{

        ImageView cover_iv;
        TextView title_tv;
        View item_container;

        public Holder(@NonNull View itemView) {
            super(itemView);
            cover_iv = itemView.findViewById(R.id.cover_iv);
            title_tv = itemView.findViewById(R.id.title_tv);
            item_container = itemView.findViewById(R.id.item_container);
        }
    }

}
