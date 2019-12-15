package com.sunspot.pop.listll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;

import com.sunspot.pop.R;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

public class TestClassActivity extends AppCompatActivity {

    private static final String TAG = "TestClassActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_class);




//        SparseArray<Class<?>> array = new SparseArray<>();
//        array.put(1,A.class);
//        //测试排序
//        array.put(3,B.class);
//        array.put(2,A.class);
//        //测试值覆盖
//        array.put(1,B.class);
//        Log.e(TAG, "onCreate: " + array.toString() );

        List<A> listA = new ArrayList<>();
        List<B> listB = new ArrayList<>();
        Log.e(TAG, "onCreate: " + listA.toString() + " / " + listB.toString() );

//        Class<? extends List> aClass = listA.getClass();
//        TypeVariable<? extends Class<? extends List>>[] typeParameters = aClass.getTypeParameters();
//        Type genericSuperclass = aClass.getGenericSuperclass();
//
//        int length = typeParameters.length;
//        for (int i = 0; i < length; i++) {
//            TypeVariable<? extends Class<? extends List>> typeParameter = typeParameters[i];
//            Log.e(TAG, "onCreate: " + typeParameter.getName());
//        }
//        if (genericSuperclass instanceof ParameterizedType){
//            Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
//            int length1 = actualTypeArguments.length;
//            for (int i = 0; i < length1; i++) {
//                Type actualTypeArgument = actualTypeArguments[i];
//                Log.e(TAG, "onCreate: " + actualTypeArgument.getClass());
//            }
//        }
//
//        Log.e(TAG, "onCreate: " +  genericSuperclass);
//
//        Log.e(TAG, "onCreate: " + listA.getClass() + " / " + listB.getClass() );

    }
}
