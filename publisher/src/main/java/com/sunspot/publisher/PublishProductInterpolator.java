package com.sunspot.publisher;

import android.view.animation.Interpolator;

/**
 * Created by duanbo on 17/11/16.
 */

public class PublishProductInterpolator implements Interpolator {
    @Override
    public float getInterpolation(float input) {
        return subsectionSpeed(input);
    }

    private float subsectionSpeed(float x) {
        float factor = 0.43f;
        return (float) (Math.pow(2, -10 * x) * Math.sin((x - factor / 4) * (2 * Math.PI) / factor) + 1);
    }
}
