package com.sunspot.sharelement;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * -------------------------------------
 * 作者：vitta
 * -------------------------------------
 * 时间：2019/5/29 下午8:57
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class MainFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "MainFragment";

    private View mNiudanMachine;
    public boolean isBack;
    private ImageView mNiudanIv;
    private RecyclerView mRev;
    private MainAdapter mAdapter;
    private View mNiudanLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.guide_niudan, container, false);
        mNiudanMachine = view.findViewById(R.id.niudan_machine);
        mNiudanLayout = view.findViewById(R.id.guide_niudan_layout);
        mNiudanMachine.setOnClickListener(this);
        mNiudanIv = view.findViewById(R.id.niudan_iv);
        mRev = view.findViewById(R.id.rev);
        mRev.setLayoutManager(new LinearLayoutManager(getActivity()));
        //exit reenter
        getActivity().setExitSharedElementCallback(new SharedElementCallback() {

            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                Log.e(TAG, "setExitSharedElementCallback: " + sharedElements + " / " + names );
                if (isBack){
                    sharedElements.put(NiudanActivity.SHARE_ELEMENT_NIUDAN_MACHINE,mNiudanIv);
                    isBack = false;
                    refreshData();
                    mNiudanLayout.setVisibility(View.GONE);
                }
            }
        });
        return view;
    }



    private List<TextModel> getData() {
        List<TextModel> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(new TextModel("承蒙你的出现让我傻了好多年"));
        }
        return list;
    }

    @Override
    public void onClick(View v) {
        NiudanActivity.start(this, mNiudanMachine);
    }

    public void refreshData() {
        Log.e(TAG, "refreshData: "  );
        mAdapter = new MainAdapter(getData());
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_bottom_in);
        mRev.setLayoutAnimation(controller);
        mRev.setAdapter(mAdapter);
        mRev.scheduleLayoutAnimation();
    }

}
