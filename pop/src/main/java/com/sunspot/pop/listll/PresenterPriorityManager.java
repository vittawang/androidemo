package com.sunspot.pop.listll;

import android.support.annotation.NonNull;
import android.util.SparseIntArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * -------------------------------------
 * 作者：vitta
 * -------------------------------------
 * 时间：2019-11-25 00:56
 * -------------------------------------
 * 描述：holderPresenter展示优先级管理池
 * -------------------------------------
 * 备注：目前要求所有使用优先级的必须在addPresenter定义自己的优先级
 * -------------------------------------
 */
public class PresenterPriorityManager {

    private Map<Class<?>, Integer> priorityMap;
    //priority -> position
    private SparseIntArray priorityArray;

    public PresenterPriorityManager(int dataCount) {
        priorityMap = new HashMap<>(dataCount);
        priorityArray = new SparseIntArray(dataCount);
    }

    public PresenterPriorityManager() {
        priorityMap = new HashMap<>();
        priorityArray = new SparseIntArray();
    }

    public void addPriority(Class<?> aClass, int priority) {
        priorityMap.put(aClass, priority);
    }

    public void addData(MultiAdapter multiAdapter, List<?> list) {
        Class<?> aClass = getListDataClass(list);
        if (aClass != null) {
            Integer newPri = priorityMap.get(aClass);
            if (newPri == null || newPri < 0) {
                //找不到数据对应的优先级，直接添加在最后
                multiAdapter.addData(list);
                return;
            }
            //数据有对应的优先级为 newPri
            int itemCount = multiAdapter.getItemCount();
            if (itemCount == 0) {
                //空的直接add
                multiAdapter.setNewData(list);
                priorityArray.put(newPri, 0);
            } else {
                //挨个判断优先级插入到合适的位置
                int size = priorityArray.size();
                if (size == 0) {
                    //说明之前设置的数据没有优先级
                    priorityArray.put(newPri, 0);
                    multiAdapter.insertData(0, list.size(), list);
                    return;
                }
                //说明之前设置的数据有优先级
                //在最后面add的判断
                int lastPri = priorityArray.keyAt(size - 1);
                if (newPri > lastPri) {
                    priorityArray.put(newPri, itemCount);
                    multiAdapter.addData(list);
                    return;
                }
                //在中间和最前面插入的判断
                for (int i = 0; i < size; i++) {
                    int pri = priorityArray.keyAt(i);
                    if (newPri < pri) {
                        int adapterPosition = priorityArray.get(pri);
                        //在adapterPosition位置插入
                        multiAdapter.insertData(adapterPosition, list.size(), list);
                        //更新所有后面老的位置
                        for (int j = i; j < size; j++) {
                            int changePri = priorityArray.keyAt(j);
                            int changePosition = priorityArray.get(changePri);
                            priorityArray.put(changePri, changePosition + list.size());
                        }
                        //更新新的位置
                        priorityArray.put(newPri, adapterPosition);
                        return;
                    }
                }
            }
        }
    }

    private Class<?> getListDataClass(@NonNull List<?> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Object o = list.get(i);
            if (o != null) {
                return o.getClass();
            }
        }
        return null;
    }
}
