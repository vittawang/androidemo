package com.sunspot.sharelement;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        ViewCompat.setTransitionName(findViewById(R.id.lav_gashapon), SHARE_ELEMENT_NIUDAN_MACHINE);
        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                Log.e(TAG, "onMapSharedElements: " + sharedElements );
            }
        });
    }
}
