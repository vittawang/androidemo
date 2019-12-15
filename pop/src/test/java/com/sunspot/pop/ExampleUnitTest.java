package com.sunspot.pop;

import com.sunspot.pop.listll.A;
import com.sunspot.pop.listll.B;
import com.sunspot.pop.listll.C;
import com.sunspot.pop.listll.D;
import com.sunspot.pop.listll.E;
import com.sunspot.pop.listll.MultiAdapter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testSubList(){
        List<A> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new A());
        }


        int startP = 0;
        List<A> list1 = list.subList(0, startP);
        System.out.println("list1 = " + list1);
    }



}