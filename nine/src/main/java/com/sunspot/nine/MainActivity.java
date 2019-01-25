package com.sunspot.nine;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rev = findViewById(R.id.rev);
        rev.setLayoutManager(new LinearLayoutManager(this));
        rev.setAdapter(new GridImageAdapter(getData()));
        rev.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e(TAG, "onScrollStateChanged: " + newState );
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }



    private List<List<ImgInfo>> getData() {
        List<List<ImgInfo>> data = new ArrayList<>();


        //一张图
        data.add(getOneImage(500,500));
        data.add(getFourImage());
        data.add(getImages(2));
        data.add(getImages(3));
        data.add(getImages(4));
        data.add(getImages(5));
        data.add(getImages(6));
        data.add(getImages(7));
        data.add(getImages(8));
        data.add(getImages(9));
        data.add(getImages(10));
        data.add(getImages(11));
        data.add(getImages(12));
        data.add(getOneImage(400,1200));
        data.add(getOneImage(1200,400));
        data.add(getOneImage(600,1200));
        data.add(getOneImage(1200,600));
        data.add(getOneImage(100,1200));
        data.add(getOneImage(1200,1000));

        return data;
    }

    private List<ImgInfo> getFourImage() {
        ImgInfo imgInfo = new ImgInfo();
        List<ImgInfo> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            imgInfo.setWidth(600);
            imgInfo.setHeight(600);
            list.add(imgInfo);
        }
        return list;
    }

    private List<ImgInfo> getImages(int size) {
        ImgInfo imgInfo = new ImgInfo();
        List<ImgInfo> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            imgInfo.setWidth(600);
            imgInfo.setHeight(600);
            list.add(imgInfo);
        }
        return list;
    }


    @NonNull
    private List<ImgInfo> getOneImage(int w, int h) {
        ImgInfo imgInfo = new ImgInfo();
        List<ImgInfo> list = new ArrayList<>();
        imgInfo.setWidth(w);
        imgInfo.setHeight(h);
        list.add(imgInfo);
        return list;
    }
}
