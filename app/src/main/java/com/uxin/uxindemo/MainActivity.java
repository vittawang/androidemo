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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

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

//        RecyclerView recy = findViewById(R.id.recy);
//        recy.setLayoutManager(new LinearLayoutManager(this));
//        SnapAdapter adapter = new SnapAdapter();
//        recy.setAdapter(adapter);
//        List<PagerSnapData> list = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            list.add(new PagerSnapData(String.valueOf(i)));
//        }
//        adapter.addAll(list);
//        adapter.insertMoreLayout();
//        Log.e(TAG, "onCreate: " + formatNum(100));

        Log.e(TAG, "onCreate: " + Double.parseDouble("1000.05") );
        Log.e(TAG, "onCreate: " + Long.parseLong("1000.08") );
        Log.e(TAG, "onCreate: " + Long.parseLong("1000.40") );
        Log.e(TAG, "onCreate: " + Long.parseLong("1000.60") );
        Log.e(TAG, "onCreate: " + Long.parseLong("1000.00") );
        Log.e(TAG, "onCreate: " + Long.parseLong("-1000.80") );


    }




    /**
     * 格式化数字为财务数字:100,000,000.00
     * 为了适应服务器端返回金额单位为"分",将数字作为字符串处理成100,000,000.00格式
     *
     * @param num 数值
     * @return 财务数值
     */
    public static String formatNum(long num) {
        try {
            if (num == 0)
                return "0.00";
            if (num < 10 && num > 0) {
                //9 --> 0.09
                return "0.0" + num;
            } else if (num < 100) {
                //99 --> 0.99
                return "0." + num;
            } else {
                //199 --> 1.99
                String result = String.valueOf(num);
                DecimalFormat format = new DecimalFormat("###,###,###");
                String beforDot = result.substring(0, result.length() - 2);
                String behindDot = result.substring(result.length() - 2);
                return format.format(Long.valueOf(beforDot)) + "." + behindDot;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "0.00";
        }
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
