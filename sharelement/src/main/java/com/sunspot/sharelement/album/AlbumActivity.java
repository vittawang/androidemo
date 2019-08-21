package com.sunspot.sharelement.album;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sunspot.sharelement.R;
import com.sunspot.sharelement.adapter.BaseAdapter;
import com.sunspot.sharelement.adapter.OnItemChildClickListener;
import com.sunspot.sharelement.adapter.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        RecyclerView rev = findViewById(R.id.rev);
        rev.setLayoutManager(new GridLayoutManager(this, 3));
        final ArrayList<PhotoModel> data = getData();
        AlbumAdapter adapter = new AlbumAdapter(data);
        rev.setAdapter(adapter);
        rev.addItemDecoration(new GridItemDecoration());


        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(BaseAdapter adapter, View view, int position) {
                //设置共享的view和名字
                ViewCompat.setTransitionName(view, PhotoDetailActivity.TRANSITION_IMAGE);

                Intent starter = new Intent(AlbumActivity.this, PhotoDetailActivity.class);
                starter.putParcelableArrayListExtra(PhotoDetailActivity.KEY_PHOTO_LIST, data);
                starter.putExtra(PhotoDetailActivity.KEY_POSITION, position);

                Pair<View, String> pair = new Pair<>(view, ViewCompat.getTransitionName(view));
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(AlbumActivity.this, pair);
                startActivity(starter, activityOptionsCompat.toBundle());
            }
        });
    }

    private ArrayList<PhotoModel> getData() {
        ArrayList<PhotoModel> list = new ArrayList<>();
        list.add(new PhotoModel(R.mipmap.a1));
        list.add(new PhotoModel(R.mipmap.a2));
        list.add(new PhotoModel(R.mipmap.a3));
        list.add(new PhotoModel(R.mipmap.a4));
        list.add(new PhotoModel(R.mipmap.a5));
        list.add(new PhotoModel(R.mipmap.a6));
        list.add(new PhotoModel(R.mipmap.a7));
        list.add(new PhotoModel(R.mipmap.a8));
        list.add(new PhotoModel(R.mipmap.a9));
        list.add(new PhotoModel(R.mipmap.a10));
        list.add(new PhotoModel(R.mipmap.a11));
        list.add(new PhotoModel(R.mipmap.a12));
        list.add(new PhotoModel(R.mipmap.a13));
        list.add(new PhotoModel(R.mipmap.a14));
        list.add(new PhotoModel(R.mipmap.a15));
        list.add(new PhotoModel(R.mipmap.a16));
        list.add(new PhotoModel(R.mipmap.a17));
        list.add(new PhotoModel(R.mipmap.a18));
        list.add(new PhotoModel(R.mipmap.a19));
        list.add(new PhotoModel(R.mipmap.a20));
        list.add(new PhotoModel(R.mipmap.a21));
        list.add(new PhotoModel(R.mipmap.a22));
        list.add(new PhotoModel(R.mipmap.a23));
        list.add(new PhotoModel(R.mipmap.a24));
        list.add(new PhotoModel(R.mipmap.a25));
        list.add(new PhotoModel(R.mipmap.a26));
        list.add(new PhotoModel(R.mipmap.a27));
        list.add(new PhotoModel(R.mipmap.a28));
        list.add(new PhotoModel(R.mipmap.a29));
        return list;
    }
}
