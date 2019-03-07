package com.sunspot.expand.chat;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sunspot.expand.R;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private static final int DURATION = 3000;
    private LayoutTransition mTransitioner;
    private int[] drawableRes = new int[]{
            R.mipmap.a_0,
            R.mipmap.a_1,
            R.mipmap.a_2,
            R.mipmap.a_3,
            R.mipmap.a_4,
            R.mipmap.a_5,
            R.mipmap.a_6,
            R.mipmap.a_7,
            R.mipmap.a_8,
            R.mipmap.a_9,
            R.mipmap.a_10,
            R.mipmap.a_11,
            R.mipmap.a_12,
            R.mipmap.a_13,
            R.mipmap.a_14,
            R.mipmap.a_15,
            R.mipmap.a_16,
            R.mipmap.a_17,
            R.mipmap.a_18,
            R.mipmap.a_19
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
//        OnlineAvatarView view = findViewById(R.id.online_list);
//        OnlineChatAvatarView view = findViewById(R.id.online_list);
        OnlineView view = findViewById(R.id.online_list);
        List<DataLogin> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new DataLogin(i,drawableRes[i]));
        }
        view.setData(list);


//        mTransitioner = new LayoutTransition();
//        view.setLayoutTransition(mTransitioner);

        //进入动画
//        long inDuration = transition.getDuration(LayoutTransition.APPEARING);
//        ObjectAnimator inScaleX = ObjectAnimator.ofFloat(null, "ScaleX", 0, 1)
//                .setDuration(inDuration);
//        ObjectAnimator inScaleY = ObjectAnimator.ofFloat(null, "ScaleY", 0, 1)
//                .setDuration(inDuration);
//        ObjectAnimator inAlpha = ObjectAnimator.ofFloat(null, "Alpha", 0, 1)
//                .setDuration(inDuration);
//        AnimatorSet set = new AnimatorSet();
//        set.playTogether(inScaleX,inScaleY,inAlpha);
//        transition.setAnimator(LayoutTransition.APPEARING,set);

        //退出动画
//        long outDuration = transition.getDuration(LayoutTransition.DISAPPEARING);
//        ObjectAnimator outScaleX = ObjectAnimator.ofFloat(null, "ScaleX", 1, 0)
//                .setDuration(outDuration);
//        ObjectAnimator outScaleY = ObjectAnimator.ofFloat(null, "ScaleY", 1, 0)
//                .setDuration(outDuration);
//        ObjectAnimator outAlpha = ObjectAnimator.ofFloat(null, "Alpha", 1, 0)
//                .setDuration(outDuration);
//        AnimatorSet outSet = new AnimatorSet();
//        outSet.playTogether(outScaleX,outScaleY,outAlpha);
//        transition.setAnimator(LayoutTransition.DISAPPEARING,outSet);

        //使用滑动动画代替默认布局改变的动画

//        ObjectAnimator x = ObjectAnimator.ofFloat(null, "TranslationX", -1, 0, 1);
//        x.setDuration(DURATION);
//        transition.setAnimator(LayoutTransition.CHANGE_APPEARING,
//                x);
//        transition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING,
//                x);

    }

    private void setTransition() {
        /**
         * 添加View时过渡动画效果
         */
//        ObjectAnimator addAnimator = ObjectAnimator.ofFloat(null, "rotationY", 0, 90,0).
        ObjectAnimator addAnimator = ObjectAnimator.ofFloat(null, "Alpha", 0, 1).
                setDuration(mTransitioner.getDuration(LayoutTransition.APPEARING));
        mTransitioner.setAnimator(LayoutTransition.APPEARING, addAnimator);

        /**
         * 移除View时过渡动画效果
         */
//        ObjectAnimator removeAnimator = ObjectAnimator.ofFloat(null, "rotationX", 0, -90, 0).
        ObjectAnimator removeAnimator = ObjectAnimator.ofFloat(null, "Alpha", 1,0).
                setDuration(mTransitioner.getDuration(LayoutTransition.DISAPPEARING));
        mTransitioner.setAnimator(LayoutTransition.DISAPPEARING, removeAnimator);

        /**
         * view 动画改变时，布局中的每个子view动画的时间间隔
         */
        mTransitioner.setStagger(LayoutTransition.CHANGE_APPEARING, 30);
        mTransitioner.setStagger(LayoutTransition.CHANGE_DISAPPEARING, 30);


        /**
         *LayoutTransition.CHANGE_APPEARING和LayoutTransition.CHANGE_DISAPPEARING的过渡动画效果
         * 必须使用PropertyValuesHolder所构造的动画才会有效果，不然无效！使用ObjectAnimator是行不通的,
         * 发现这点时真特么恶心,但没想到更恶心的在后面,在测试效果时发现在构造动画时，”left”、”top”、”bottom”、”right”属性的
         * 变动是必须设置的,至少设置两个,不然动画无效,问题是我们即使这些属性不想变动!!!也得设置!!!
         * 我就问您恶不恶心!,因为这里不想变动,所以设置为(0,0)
         *
         */
        PropertyValuesHolder pvhLeft =
                PropertyValuesHolder.ofInt("left", 0, 0);
        PropertyValuesHolder pvhTop =
                PropertyValuesHolder.ofInt("top", 0, 0);
        PropertyValuesHolder pvhRight =
                PropertyValuesHolder.ofInt("right", 0, 0);
        PropertyValuesHolder pvhBottom =
                PropertyValuesHolder.ofInt("bottom", 0, 0);


        /**
         * view被添加时,其他子View的过渡动画效果
         */
//        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX", 1, 1.5f, 1);
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("TranslationX", 1,0,1);
        final ObjectAnimator changeIn = ObjectAnimator.ofPropertyValuesHolder(
                this, pvhLeft,  pvhBottom, animator).
                setDuration(mTransitioner.getDuration(LayoutTransition.CHANGE_APPEARING));

        mTransitioner.setAnimator(LayoutTransition.CHANGE_APPEARING, changeIn);


        /**
         * view移除时，其他子View的过渡动画
         */
        PropertyValuesHolder pvhRotation =
//                PropertyValuesHolder.ofFloat("scaleX", 1, 1.5f, 1);
                PropertyValuesHolder.ofFloat("TranslationX", 1,0,1);
        final ObjectAnimator changeOut = ObjectAnimator.ofPropertyValuesHolder(
                this, pvhLeft, pvhBottom, pvhRotation).
                setDuration(mTransitioner.getDuration(LayoutTransition.CHANGE_DISAPPEARING));

        mTransitioner.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, changeOut);
    }
}
