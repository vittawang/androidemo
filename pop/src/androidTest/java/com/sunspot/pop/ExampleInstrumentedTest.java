package com.sunspot.pop;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.sunspot.pop.listll.A;
import com.sunspot.pop.listll.B;
import com.sunspot.pop.listll.C;
import com.sunspot.pop.listll.D;
import com.sunspot.pop.listll.E;
import com.sunspot.pop.listll.MultiAdapter;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.sunspot.pop", appContext.getPackageName());
    }

    @Test
    public void addPriorityPresenter() {
        MultiAdapter adapter = new MultiAdapter();
        //定义数据的优先级
        adapter.addPresenter(1, A.class);
        adapter.addPresenter(2, B.class);
        adapter.addPresenter(3, C.class);
        adapter.addPresenter(4, D.class);
        adapter.addPresenter(5, E.class);
        //设置数据
        adapter.addDataBaseOfPriority(getBList());
        adapter.addDataBaseOfPriority(getAList());

        //结果
        System.out.println(adapter.toString());
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
}
