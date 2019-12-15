package com.sunspot.pop.listll.listpriority;

import java.util.ArrayList;
import java.util.List;

/**
 * -------------------------------------
 * 作者：vitta
 * -------------------------------------
 * 时间：2019-12-14 18:18
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class BBAdapter {

    /**
     * 数据源
     */
    private List<Object> mData;

    private ArrayList<? extends String> presenters;

    private List<Integer> multiDataSize;

    public BBAdapter(ArrayList<? extends String> typeList) {
        if (typeList != null && typeList.size() > 0) {
            presenters = typeList;
            multiDataSize = new ArrayList<>(typeList.size());
        }
        mData = new ArrayList<>();
    }

    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    /**
     * 算法 - 跟5个if/else 执行效率是一样的
     */
    public int getItemType(int position) {
        if (multiDataSize != null && multiDataSize.size() > 0) {
            int size = multiDataSize.size();
            int sumSize = 0;
            for (int i = 0; i < size; i++) {
                if (position <= sumSize) {
                    return i;
                }
                sumSize += multiDataSize.get(i);
            }
        }
        return -1;
    }

    public Object getItem(int position) {
        if (mData != null && position >= 0 && position < mData.size()) {
            return mData.get(position);
        }
        return null;
    }

    /**
     * = null 不做处理
     * size = 0 清空数据，清空展示
     * size > 0 正常展示
     *
     * @param list 具体的数据模型
     */
    public void setNewList(String presenterType, List<?> list) {
        int index = getIndex(presenterType);
        if (list != null) {
            multiDataSize.set(index, list.size());
            mData.clear();
            if (list.size() > 0) {
                mData.addAll(list);
            }
            notifyDataSetChanged();
        }
    }

    public void clearData(String presenterType) {
        int index = getIndex(presenterType);
        int startPosition = 0;
        int endPosition = 0;
        int size = multiDataSize.size();
        for (int i = 0; i < size; i++) {
            startPosition = endPosition;
            if (i <= index) {
                break;
            }
            endPosition += multiDataSize.get(i);
        }
        //notifyRemove
    }

    public void clearAll(){
        mData.clear();
        notifyDataSetChanged();
    }

    private int getIndex(String presenterType) {
        int index = presenters.indexOf(presenterType);
        if (index < 0) {
            throw new IllegalArgumentException("unknown presenter type");
        }
        return index;
    }

    /**
     * == null 不做处理
     */
    public void addList(String presenterType, List<?> list) {
        int index = getIndex(presenterType);
        if (list != null && list.size() > 0) {

        }
    }

    public void setNewItem(String dataType, Object item) {

    }

    public void addItem(String dataType, Object item) {

    }

    public void notifyDataSetChanged() {

    }

    /**
     * -1 处理
     */
}
