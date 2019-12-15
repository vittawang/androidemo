package com.sunspot.pop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RoomStatusIconsLayout viewById = findViewById(R.id.icon_layout);
        viewById.setData(getData());
    }

    private List<DataRoomCoverIcon> getData() {
        List<DataRoomCoverIcon> list = new ArrayList<>();
        list.add(new DataRoomCoverIcon("1",R.mipmap.icon_home_envelope));
        list.add(new DataRoomCoverIcon("1",R.mipmap.icon_home_attachment));
        list.add(new DataRoomCoverIcon("1",R.mipmap.icon_home_pk));
        list.add(new DataRoomCoverIcon("1",R.mipmap.icon_home_questions));
        list.add(new DataRoomCoverIcon("1",R.mipmap.icon_home_video));
        list.add(new DataRoomCoverIcon("1",R.mipmap.icon_home_envelope));
        for (int i = 0; i < 14; i++) {
            list.add(new DataRoomCoverIcon(String.valueOf(i),R.mipmap.icon_home_pk));
        }
        return list;
    }
}
