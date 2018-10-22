package com.uxin.recy;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.uxin.recy.entity.Video;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private String[] images = new String[]{
            "https://www.baidu.com/img/bd_logo1.png",
            "https://www.baidu.com/img/bd_logo1.png",
            "https://www.baidu.com/img/bd_logo1.png",
            "https://www.baidu.com/img/bd_logo1.png",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recy = findViewById(R.id.recy);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recy.setLayoutManager(layoutManager);
        recy.addItemDecoration(new StaggeredItemDecoration(this,2,10,10));
        StaggeredAdapter adapter = new StaggeredAdapter(StaggeredAdapter.LAYOUT);
        recy.setAdapter(adapter);
        adapter.setNewData(queryVideoList());
    }

    @NonNull
    private List<Video> queryVideoList() {
        Random random = new Random();
        List<Video> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            int width = random.nextInt(4);//[0,4) 0,1,2,3
            list.add(new Video(width + 1,random.nextInt(4) + 1,images[random.nextInt(4)],"你是猴子请来的救兵吗？"));
        }
        return list;
    }
}
