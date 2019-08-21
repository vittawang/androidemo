package com.sunspot.sharelement;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.Transition;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.Window;

import java.util.List;
import java.util.Map;

public class NiudanActivity extends AppCompatActivity {

    public static final String SHARE_ELEMENT_NIUDAN_MACHINE = "niudan_machine";

    private static final String TAG = "NiudanActivity";

    public static void start(MainFragment context, View mNiudanMachine) {
        Intent intent = new Intent(context.getActivity(), NiudanActivity.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(context.getActivity(), Pair.create(mNiudanMachine, NiudanActivity.SHARE_ELEMENT_NIUDAN_MACHINE));
        context.startActivity(intent, options.toBundle());
        context.isBack = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niudan);
        //called activity
        getWindow().setEnterTransition();
        getWindow().setReturnTransition();
        //calling activity
        getWindow().setExitTransition();
        getWindow().setReenterTransition();
        //slide explode fade

        Slide transition = new Slide();
        //动画时间
        transition.setDuration();
        //差值器
        transition.setInterpolator();
        //延迟开始的时间
        transition.setStartDelay();
        //运行的路径
        transition.setPathMotion();
        //出现/消失的模式。Visibility.MODE_IN 进入，Visibility.MODE_OUT 退出
        transition.setMode();
        //动画监听
        transition.addListener();
        ViewCompat.setTransitionName(findViewById(R.id.lav_gashapon), SHARE_ELEMENT_NIUDAN_MACHINE);
        //enter return
        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                Log.e(TAG, "onMapSharedElements: " + sharedElements );
            }
        });
    }
}
