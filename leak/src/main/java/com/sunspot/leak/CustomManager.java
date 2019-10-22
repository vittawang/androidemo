package com.sunspot.leak;

import android.app.Activity;

/**
 * -------------------------------------
 * 作者：vitta
 * -------------------------------------
 * 时间：2019/9/9 下午3:42
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class CustomManager {

    private static Activity sActivity;

    public CustomManager(Activity activity) {
        sActivity = activity;
    }
}
