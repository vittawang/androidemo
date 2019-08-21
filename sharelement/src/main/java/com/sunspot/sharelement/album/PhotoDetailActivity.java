package com.sunspot.sharelement.album;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sunspot.sharelement.R;

import java.util.ArrayList;

public class PhotoDetailActivity extends AppCompatActivity {

    public static final String KEY_PHOTO_LIST = "PHOTO_LIST";
    public static final String KEY_POSITION = "POSITION";

    public static final String TRANSITION_IMAGE = "TRANSITION_IMAGE";

    public static void start(Context context, ArrayList<PhotoModel> list, int position) {
        Intent starter = new Intent(context, PhotoDetailActivity.class);
        starter.putParcelableArrayListExtra(KEY_PHOTO_LIST, list);
        starter.putExtra(KEY_POSITION, position);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        RecyclerView rev = findViewById(R.id.rev);
        ViewCompat.setTransitionName(rev,TRANSITION_IMAGE);
        Intent intent = getIntent();
        int position = intent.getIntExtra(KEY_POSITION, 0);
        PhotoAdapter adapter = new PhotoAdapter(intent.<PhotoModel>getParcelableArrayListExtra(KEY_PHOTO_LIST));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rev.setLayoutManager(layoutManager);
        rev.setAdapter(adapter);
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(rev);
        rev.scrollToPosition(position);
    }
}
