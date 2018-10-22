package com.uxin.uxindemo;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        RecyclerView recy = findViewById(R.id.recy);
//        recy.setLayoutManager(new LinearLayoutManager(this));
//        SnapAdapter adapter = new SnapAdapter();
//        recy.setAdapter(adapter);
//        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
//        pagerSnapHelper.attachToRecyclerView(recy);
//        List<PagerSnapData> list = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            list.add(new PagerSnapData(String.valueOf(i)));
//        }
//        adapter.addAll(list);
//        recy.setItemViewCacheSize(0);


//        //打开下载管理器
//        Uri uri = Uri.parse("http://gdown.baidu.com/data/wisegame/93f953251b6d8f65/yingyongbao_7272130.apk");
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        startActivity(intent);



//        View view = findViewById(R.id.diamond);

//        //这样写两个动画是同时开始的
//        ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "TranslationX", 0f,500f);
//        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "ScaleX", 0, 1);
//        translationX.setDuration(3000);
//        scaleX.setDuration(3000);
//        translationX.start();
//        scaleX.start();

        RecyclerView recy = findViewById(R.id.recy);
        recy.setLayoutManager(new LinearLayoutManager(this));
        SnapAdapter adapter = new SnapAdapter();
        recy.setAdapter(adapter);
        List<PagerSnapData> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new PagerSnapData(String.valueOf(i)));
        }
        adapter.addAll(list);
        adapter.insertMoreLayout();
    }

//    private Dialog mRuleDialog;
//    public void onShowDialog(View view) {
//        if (mRuleDialog == null){
//            mRuleDialog = new Dialog(this,R.style.CustomDialogStyle);
//            mRuleDialog = new Dialog(this);
//            mRuleDialog.setContentView(LayoutInflater.from(this).inflate(R.layout.dialog_novel_auto_pay_rule,null));
//            mRuleDialog.setCancelable(true);
//            mRuleDialog.setCanceledOnTouchOutside(true);
//            Window window = mRuleDialog.getWindow();
//            if (window != null){
//                window.setGravity(Gravity.BOTTOM);
////                window.setBackgroundDrawable(new ColorDrawable(0));
////                WindowManager.LayoutParams params = new WindowManager.LayoutParams();
////                params.alpha = 0;
////                params.dimAmount = 0;
////                window.setAttributes(params);
//            }
//        }
//        mRuleDialog.show();
//
//    }
}
