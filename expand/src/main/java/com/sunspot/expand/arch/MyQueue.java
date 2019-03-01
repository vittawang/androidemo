package com.sunspot.expand.arch;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2019/3/1 上午11:36
 * -------------------------------------
 * 描述：一个队列的数据结构
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class MyQueue {

    private int head = 0;
    private int tail = 0;
    private Object[] queue;

    /**
     * 构造
     * @param capacity 容量
     */
    public MyQueue(int capacity) {
        queue = new Object[capacity];
    }

    public boolean put(Object item){
        queue[tail] = item;
//        tail =
        return true;
    }



}
