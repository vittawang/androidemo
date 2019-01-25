package com.sunspot.expand;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sunspot.expand.page.ImgInfo;
import com.sunspot.expand.page.MainPublishAdapter;
import com.sunspot.expand.page.ScaleInPageTransformer;
import com.sunspot.expand.page.ZoomOutPageTransformer;
import com.sunspot.expand.render.MatchView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private MatchView match;

//    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        match = findViewById(R.id.match);
//        match.startAnim();
//        mViewPager = findViewById(R.id.view_pager);
//        //页面间距
////        mViewPager.setPageMargin(CommonUtils.dip2px(this, 12));
//        //页面切换缩放动画
//        mViewPager.setPageTransformer(false, new ScaleInPageTransformer());
////        mViewPager.setPageTransformer(false,new ZoomOutPageTransformer());
//
//        MainPublishAdapter mPagerAdapter = new MainPublishAdapter();
//        mViewPager.setAdapter(mPagerAdapter);
//        mViewPager.addOnPageChangeListener(this);
//
//
//        ArrayList<ImgInfo> objects = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            ImgInfo e = new ImgInfo();
//            e.setUrl("https://img.hongrenshuo.com.cn/21430327828871540121267351.png");
//            e.setHeight(i);
//            objects.add(e);
//        }
//        mViewPager.setOffscreenPageLimit(objects.size());
//        mPagerAdapter.setData(objects);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        match.stopAnim();
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
