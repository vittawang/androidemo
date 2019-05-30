package com.sunspot.sharelement;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    private View mNiudanMachine;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.guide_niudan, container, false);
        mNiudanMachine = view.findViewById(R.id.niudan_machine);
        mNiudanMachine.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        NiudanActivity.start(((MainActivity) getActivity()), mNiudanMachine);
    }
}
