package com.sunspot.pop.listll.listpriority;

/**
 * -------------------------------------
 * 作者：vitta
 * -------------------------------------
 * 时间：2019-12-14 17:57
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public abstract class BasePresenter {

    private Class<?> itemClass;

    public BasePresenter(Class<?> itemClass) {
        this.itemClass = itemClass;
    }

    public Class<?> getItemClass() {
        return itemClass;
    }
}
