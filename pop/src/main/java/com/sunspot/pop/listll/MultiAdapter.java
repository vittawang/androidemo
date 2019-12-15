package com.sunspot.pop.listll;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * -------------------------------------
 * 作者：vitta
 * -------------------------------------
 * 时间：2019-11-26 14:21
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class MultiAdapter {

    private List<Object> mData = new ArrayList<>();
    private PresenterPriorityManager priorityManager = new PresenterPriorityManager();
    public void addData(List<?> list) {
        if (list != null && list.size() > 0) {
            int oldSize = mData.size();
            mData.addAll(list);
//            notifyItemRangeInserted(oldSize, mData.size());
        }
    }

    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public void setNewData(List<?> list) {
        if (list != null && list.size() > 0) {
            mData.clear();
            mData.addAll(list);
//            notifyItemRangeInserted(0, mData.size());
        }
    }

    public void insertData(int positionStart, int itemCount, List<?> list) {
        if (itemCount <= 0) {
            return;
        }
        if (list != null && list.size() > 0) {
            if (positionStart >= 0) {
                mData.addAll(positionStart, list);
//                notifyItemRangeInserted(positionStart, itemCount);
            }
        }
    }

    public void addDataBaseOfPriority(List<?> list) {
        if (list != null && list.size() > 0) {
            priorityManager.addData(this, list);
        }
    }

    public void addPresenter(int priority, @NonNull Class<?> itemClass) {
        priorityManager.addPriority(itemClass, priority);
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int size = mData.size();
        for (int i = 0; i < size; i++) {
            builder.append(mData.get(i).toString());
        }
        return mData.toString();
    }
}
