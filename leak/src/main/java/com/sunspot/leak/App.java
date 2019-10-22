package com.sunspot.leak;

import android.app.Application;

/**
 * -------------------------------------
 * 作者：vitta
 * -------------------------------------
 * 时间：2019/9/9 下午2:32
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanaryManager.getInstance().init(this);
    }
}
