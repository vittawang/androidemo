package com.uxin.recy.adapter;

import android.support.annotation.LayoutRes;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * -------------------------------------
 * 作者：vitta
 * -------------------------------------
 * 时间：2019-10-23 12:09
 * -------------------------------------
 * 描述：多布局瀑布流基类
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public abstract class BaseMultiTypeAdapter<T extends MultiItemType, VH extends BaseViewHolder> extends BaseAdapter<T, VH> {

    /**
     * 多个布局layout的集合，key为viewType，value为layoutId值
     */
    private SparseIntArray layouts;
    private SparseArray<Class<? extends BaseViewHolder>> holders;

    private static final int TYPE_NOT_FOUND = -404;
    private static final int DEFAULT_VIEW_TYPE = -0xff;

    public BaseMultiTypeAdapter(List<T> data) {
        super(data);
    }

    private int getLayoutId(int viewType) {
        return layouts.get(viewType, TYPE_NOT_FOUND);
    }

    protected void addItemType(int type, @LayoutRes int layoutResId, Class<? extends BaseViewHolder> multiTypeHolderClass) {
        if (layouts == null) {
            layouts = new SparseIntArray();
        }
        layouts.put(type, layoutResId);
        if (holders == null) {
            holders = new SparseArray<>();
        }
        holders.put(type, multiTypeHolderClass);
    }

    protected void addItemType(int type, @LayoutRes int layoutResId) {
        addItemType(type, layoutResId, null);
    }

    @Override
    protected int getDefItemViewType(int position) {
        T item = mData.get(position);
        if (item != null) {
            return item.getMultiItemType();
        }
        return DEFAULT_VIEW_TYPE;
    }

    @Override
    protected VH onCreateDefViewHolder(ViewGroup parent, int viewType) {
        View itemView = getItemView(parent, getLayoutId(viewType));
        Class<? extends BaseViewHolder> customVHClass = holders.get(viewType);
        if (customVHClass != null) {
            return createGenericVHInstance(customVHClass, itemView);
        }
        return createBaseViewHolder(itemView);
    }
}
