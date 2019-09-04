package com.sunspot.pk;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TiltProgressView clip = findViewById(R.id.clip);
        clip.setAngle(45);
        //这样就从右边开始绘制了 哈哈哈！！！
        clip.setRotation(180);
        clip.setFraction(0.3f);
//        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
//        valueAnimator.setDuration(3 * 1000);
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float value = (float) animation.getAnimatedValue();
//                clip.setFraction(value);
//            }
//        });
//        valueAnimator.start();

        final PKBottomView pk = findViewById(R.id.pk);
        pk.setData();
        pk.setVoteListener(new PKBottomView.VoteListener() {
            @Override
            public void onSupportRedClick(View view) {
                pk.doVoteAnim(25,75);
            }

            @Override
            public void onSupportBlueClick(View view) {
                pk.doVoteAnim(50,50);
            }
        });
    }
}
