package com.sunspot.colordemo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Log.e(TAG, "onCreate: " + 0xFF * 0.33 );

//        int blue = Color.rgb(219, 230, 255);
//        int rgb2 = Color.rgb(243, 247, 255);
//        Log.e(TAG, "1111111: " + blue + " / " + rgb2  );
//        int rgb = Color.rgb(254, 241, 204);
//        int rgb3 = Color.rgb(255, 251, 240);
//        Log.e(TAG, "2222222: " + rgb + " / " + rgb3 );
//        int rgb1 = Color.rgb(236, 224, 255);
//        int rgb4 = Color.rgb(250, 246, 255);
//        Log.e(TAG, "3333333: " + rgb1 + " / " + rgb4 );

//        String color = "DBE6FF";
        int r = 0xDB;
        int g = 0xE6;
        int b = 0xFF;
        findViewById(R.id.tv).setBackgroundColor(Color.rgb(r, g, b));
        int r3 = getToWhiteAlphaColor(r, 0.33f);
        int g3 = getToWhiteAlphaColor(g, 0.33f);
        int b3 = getToWhiteAlphaColor(b, 0.33f);
        findViewById(R.id.ffff).setBackgroundColor(Color.rgb(r3, g3, b3));
        Log.e(TAG, "rgb: " + r + " / " + g + " /  " + b);
        Log.e(TAG, "rgb33333: " + r3 + " / " + g3 + " /  " + b3);


        String str = "#DBE6FF";
        int rs = Integer.parseInt(str.substring(1, 3), 16);
        int gs = Integer.parseInt(str.substring(3, 5), 16);
        int bs = Integer.parseInt(str.substring(5, 7), 16);
        Log.e(TAG, "strrgbparse: " + rs + " / " + gs + " / " + bs);


        Log.e(TAG, "onCreate: --------------------------");

        findViewById(R.id.ffff).setBackgroundColor(getAlphaColor("#FF0000", 0.33));


    }

    /**
     * 计算渐变到white的单个RGB的值
     *
     * @param signalRGB    RGB单个的int值
     * @param alphaPercent 0 - 1
     * @return 渐变完的R/G/B 值
     */
    private int getToWhiteAlphaColor(int signalRGB, double alphaPercent) {
        return (int) ((255 - signalRGB) * (1 - alphaPercent) + signalRGB);
    }

    /**
     * 通过一个颜色计算他的渐变色值,参照点是（255，255，255）纯白色
     *
     * @param color        原始颜色 exp.（#FF0000 纯红色）
     * @param alphaPercent 渐变值 exp.(30%)
     * @return 输出颜色 exp. (#FFA8A8 )
     */
    public int getAlphaColor(String color, double alphaPercent) {
        int defaultColor = Color.BLACK;
        if (TextUtils.isEmpty(color)) {
            return defaultColor;
        }
        if (!color.startsWith("#") || color.length() != 7) {
            return defaultColor;
        }
        try {
            int rs = Integer.parseInt(color.substring(1, 3), 16);
            int gs = Integer.parseInt(color.substring(3, 5), 16);
            int bs = Integer.parseInt(color.substring(5, 7), 16);
            int ri = (int) ((255 - rs) * (1 - alphaPercent) + rs);
            int gi = (int) ((255 - gs) * (1 - alphaPercent) + gs);
            int bi = (int) ((255 - bs) * (1 - alphaPercent) + bs);
            return Color.rgb(ri, gi, bi);
        } catch (Exception e) {
            Log.e(TAG, "getAlphaColor: " + e.getMessage());
            return defaultColor;
        }
    }
}
