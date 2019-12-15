package com.sunspot.pop.listll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.sunspot.pop.R;

import java.util.ArrayList;
import java.util.List;

public class TestAdapterActivity extends AppCompatActivity {

    private static final String TAG = "TestAdapterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_adapter);
        addPriorityPresenter();
    }

    public void addPriorityPresenter() {
        MultiAdapter adapter = new MultiAdapter();
        //定义数据的优先级
        adapter.addPresenter(1, A.class);
        adapter.addPresenter(2, B.class);
        adapter.addPresenter(3, C.class);
        adapter.addPresenter(3, D.class);
        adapter.addPresenter(5, E.class);
        //设置数据
        adapter.addDataBaseOfPriority(getBList());
        adapter.addDataBaseOfPriority(getDList());
        adapter.addDataBaseOfPriority(getAList());
        adapter.addDataBaseOfPriority(getEList());
        adapter.addDataBaseOfPriority(getCList());
        adapter.addDataBaseOfPriority(getBList());
        adapter.addDataBaseOfPriority(getEList());
        adapter.addDataBaseOfPriority(getDList());


        //结果
        Log.e(TAG, "addPriorityPresenter: " + adapter.toString());
    }

    public List<A> getAList() {
        List<A> list = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            list.add(new A());
        }
        return list;
    }

    public List<B> getBList() {
        List<B> list = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            list.add(new B());
        }
        return list;
    }

    public List<C> getCList() {
        List<C> list = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            list.add(new C());
        }
        return list;
    }

    public List<D> getDList() {
        List<D> list = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            list.add(new D());
        }
        return list;
    }

    public List<E> getEList() {
        List<E> list = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            list.add(new E());
        }
        return list;
    }


}
