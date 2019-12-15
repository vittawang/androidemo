package com.sunspot.pop.listll.eve;

import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseIntArray;

import com.sunspot.pop.listll.A;
import com.sunspot.pop.listll.B;
import com.sunspot.pop.listll.C;
import com.sunspot.pop.listll.listpriority.BasePresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * -------------------------------------
 * 作者：vitta
 * -------------------------------------
 * 时间：2019-12-14 15:50
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class TestAAA {

    private static final String TAG = "TestBBB";


    /**
     * 一对多
     */
    public Map<String, List<? extends HolderPresenter>> setData() {

        //定义数据类型和对应的控制器
        ArrayList<HolderPresenter> aP = new ArrayList<HolderPresenter>(3) {{
            add(new APresenter());
            add(new A2Presenter());
            add(new A3Presenter());
        }};
        List<BPresenter> bPresenters = Collections.singletonList(new BPresenter());
        Map<String, List<? extends HolderPresenter>> map = new HashMap<>();
        map.put(A.class.getName(), aP);
        map.put(B.class.getName(), bPresenters);
        map.put(C.class.getName(), Collections.singletonList(new CPresenter()));
        Log.e(TAG, "setData: 5 " + map);
        return map;
    }


    /**
     * 一对多
     */
    public List<List<? extends HolderPresenter>> setListData() {

        //定义数据类型和对应的控制器
        ArrayList<HolderPresenter> aP = new ArrayList<HolderPresenter>(3) {{
            add(new APresenter());//A.class
            add(new A2Presenter());//A.class
            add(new A3Presenter());//A.class
        }};
        List<BPresenter> bPresenters = Collections.singletonList(new BPresenter());
        List<List<? extends HolderPresenter>> list = new ArrayList<>(3);
        list.add(aP);
        list.add(bPresenters);
        list.add(Collections.singletonList(new CPresenter()));
        Log.e(TAG, "setData: 5 " + list);
        return list;
    }

    private Map<String, List<? extends HolderPresenter>> map;



    private SparseIntArray sizeArray;
    private List<? extends HolderPresenter> presenters;


    public void init(Map<String, List<? extends HolderPresenter>> map, int presenterCount) {
        this.map = map;

    }


//    public Object getItem(int position){
//
//    }

//    public int getItemCount(){
//
//    }


}
