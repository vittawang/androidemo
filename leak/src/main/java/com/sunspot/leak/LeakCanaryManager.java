package com.sunspot.leak;


import android.app.Application;
import android.os.Process;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by duanbo on 16/12/27.
 */

public class LeakCanaryManager {

    private static final String TAG = "LeakCanaryManager";
    private static LeakCanaryManager sInstance;
    private RefWatcher mRefWatcher;

    private LeakCanaryManager() {

    }

    public static LeakCanaryManager getInstance() {
        if (sInstance == null) {
            synchronized (LeakCanaryManager.class) {
                if (sInstance == null) {
                    sInstance = new LeakCanaryManager();
                }
            }
        }
        return sInstance;
    }

    public void init(Application context) {
        boolean inAnalyzerProcess = LeakCanary.isInAnalyzerProcess(context);
        Log.e(TAG, "init: " + Process.myPid() + " / " + inAnalyzerProcess);
        if (inAnalyzerProcess) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        mRefWatcher = LeakCanary.install(context);
    }

    public void watch(Object watchedReference) {
        mRefWatcher.watch(watchedReference);
    }

    public void watch(Object watchedReference, String referenceName) {
        mRefWatcher.watch(watchedReference, referenceName);
    }
}
