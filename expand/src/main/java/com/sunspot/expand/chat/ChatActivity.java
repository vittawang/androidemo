package com.sunspot.expand.chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sunspot.expand.R;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        OnlineAvatarView view = findViewById(R.id.online_list);
        List<DataLogin> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new DataLogin(i));
        }
        view.setData(list);
    }
}
