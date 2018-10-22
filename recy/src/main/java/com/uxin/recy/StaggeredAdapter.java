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
    public static final int LAYOUT = R.layout.item_staggered;
    private Context mContext;
    private List<Video> mData;
    private int layoutRes;

    public StaggeredAdapter(int layoutRes) {
        this(new ArrayList<Video>(),layoutRes);
    }

    public StaggeredAdapter(List<Video> data, int layoutRes) {
        this.mData = data;
        this.layoutRes = layoutRes;
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
        layoutParams.height = item.getHeight() * 300;
        holder.item_container.setLayoutParams(layoutParams);
        Glide.with(mContext).load(item.getCover_pic()).into(holder.cover_iv);
        holder.title_tv.setText(item.getTitle());
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
