package com.sunspot.pop.listll.listpriority;

import android.util.Log;
import android.util.SparseIntArray;

import com.sunspot.pop.listll.A;
import com.sunspot.pop.listll.B;
import com.sunspot.pop.listll.C;
import com.sunspot.pop.listll.eve.A2Presenter;
import com.sunspot.pop.listll.eve.A3Presenter;
import com.sunspot.pop.listll.eve.APresenter;
import com.sunspot.pop.listll.eve.BPresenter;
import com.sunspot.pop.listll.eve.CPresenter;
import com.sunspot.pop.listll.eve.HolderPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * -------------------------------------
 * 作者：vitta
 * -------------------------------------
 * 时间：2019-12-14 18:05
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class TestBBB {

    private static final String TAG = "TestBBB";


    /**
     * 有优先级的
     */
    public List<? extends BasePresenter> setList() {

        //定义数据类型和对应的控制器
        ArrayList<BasePresenter> list = new ArrayList<BasePresenter>(5) {{
            add(new ABPresenter(A.class));//A.class
            add(new A2BBPresenter(A.class));//A.class
            add(new A3BBPresenter(A.class));//A.class
            add(new BBPresenter(B.class));//B.class
            add(new CBPresenter(C.class));//C.class
        }};
        Log.e(TAG, "setData: 5 " + list);
        return list;
    }

//
//
//    public int getItemType(int position) {
//
//    }

}
