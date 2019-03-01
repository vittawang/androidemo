package com.uxin.recy.lin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.uxin.recy.R;

import java.util.ArrayList;
import java.util.List;

public class LinearActivity extends AppCompatActivity {

    private LinearAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear);

        RecyclerView rev = findViewById(R.id.rev);
        rev.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new LinearAdapter(null);
        rev.setAdapter(mAdapter);
        List<DataLinear> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            List<DataComment> comments = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                comments.add(new DataComment(i + "小组：" + "猴子兵-" + j + "-号！"));
            }
            list.add(new DataLinear(comments));
        }
        mAdapter.setNewData(list);
    }
}
