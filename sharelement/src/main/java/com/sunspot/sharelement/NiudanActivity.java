package com.sunspot.sharelement;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.Window;

public class NiudanActivity extends AppCompatActivity {

    public static final String SHARE_ELEMENT_NIUDAN_MACHINE = "niudan_machine";

    public static void start(MainActivity context, View mNiudanMachine) {
        Intent intent = new Intent(context, NiudanActivity.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(context, new Pair<View, String>(mNiudanMachine, NiudanActivity.SHARE_ELEMENT_NIUDAN_MACHINE));
        context.startActivity(intent, options.toBundle());
        context.isBack = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_niudan);
        ViewCompat.setTransitionName(findViewById(R.id.lav_gashapon), SHARE_ELEMENT_NIUDAN_MACHINE);
    }
}
