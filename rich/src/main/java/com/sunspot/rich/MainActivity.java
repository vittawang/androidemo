package com.sunspot.rich;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private int isAutoShare = 1;
    private Integer isAuto = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View groupLL = findViewById(R.id.group_ll);
        View roundLL = findViewById(R.id.round_ll);
        Drawable groupBg = groupLL.getBackground();
        GradientDrawable roundBg = (GradientDrawable) roundLL.getBackground();
        groupBg.setColorFilter(Color.parseColor("#FEF1CC"), PorterDuff.Mode.SRC_ATOP);
        groupLL.setBackgroundDrawable(groupBg);

        roundBg.setColor(Color.parseColor("#FEF1CC"));
        roundBg.setAlpha(255 * 3 / 10);
        roundLL.setBackgroundDrawable(roundBg);
//        roundBg.setColorFilter(Color.parseColor("#FEF1CC"), PorterDuff.Mode.SRC_ATOP);
//        roundLL.setBackgroundDrawable(roundBg);
//        setIsAuto(isAuto);
//        setIsAutoShare(isAutoShare);
//        Log.e(TAG, "onCreate: " + isAuto + " / " + isAutoShare );

//        LayoutInflater inflater = LayoutInflater.from(this);
//        LinearLayout shareContainer = findViewById(R.id.share_container);
//        List<KilaSharePlatform> platformList = getPlatformList();
//        if (platformList != null && platformList.size() > 0) {
//            shareContainer.removeAllViews();
//            int size = platformList.size();
//            for (int i = 0; i < size; i++) {
//                KilaSharePlatform platform = platformList.get(i);
//                View platformView = inflater.inflate(R.layout.share_platform_wh33_item, shareContainer, false);
//                ImageView img = (ImageView) platformView.findViewById(R.id.platform_iv);
//                img.setImageResource(platform.getDisplayImg());
//                ((TextView) platformView.findViewById(R.id.platform_tv)).setText(platform.getTitle());
//                final int shareScene = platform.getPlatform();
//                shareContainer.addView(platformView);
//            }
//        }

//        String[] stringArray = getResources().getStringArray(R.array.share_platform_text);
//        for (int i = 0; i < stringArray.length; i++) {
//            Log.e(TAG, "text: " + stringArray[i]);
//        }
//
//        int[] intArray = getResources().getIntArray(R.array.share_platform_img);
//        int length = intArray.length;
//        for (int i = 0; i < length; i++) {
//            Log.e(TAG, "img: " + intArray[i]);
//        }
//
//        TypedArray typedArray = getResources().obtainTypedArray(R.array.share_platform_img);
//        for (int i = 0; i < length; i++) {
//            int resourceId = typedArray.getResourceId(i, 0);
//            Log.e(TAG, "onCreate: " + resourceId);
//        }
//        typedArray.recycle();

    }

//    private void setIsAutoShare(int isAutoShare) {
//        isAutoShare = 4;
//    }
//
//    private void setIsAuto(Integer isAuto) {
//        isAuto = 3;
//    }
//
//
//    private List<KilaSharePlatform> getPlatformList() {
//
//        List<KilaSharePlatform> list = new ArrayList<>();
//        list.add(new KilaSharePlatform(1, R.string.social_share_weibo, R.drawable.selector_share_weibo));
//        list.add(new KilaSharePlatform(2, R.string.social_share_wechat, R.drawable.selector_share_weixin));
//        list.add(new KilaSharePlatform(3, R.string.social_share_wechat_timeline, R.drawable.selector_share_friends));
//        list.add(new KilaSharePlatform(4,R.string.social_share_qq,R.drawable.selector_share_qq));
//        list.add(new KilaSharePlatform(5,R.string.social_share_qq_zone,R.drawable.selector_share_space));
//        return list;
//    }

    public static int getGroupDynamicCardBgColorRes(Context context, long groupId) {
        TypedArray typedArray = context.getResources().obtainTypedArray(R.array.group_dynamic_card_bg);
        int colorId = typedArray.getColor((int) (groupId % 6),Color.YELLOW);
        typedArray.recycle();
        return colorId;
    }
}
