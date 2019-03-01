package com.sunspot.pop.frag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sunspot.pop.R;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2019/2/21 下午5:27
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class TwoFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_two, container, false);
        inflate.findViewById(R.id.btn).setOnClickListener(this);
        return inflate;
    }

    @Override
    public void onClick(View v) {
        if (getActivity() instanceof PopActivity) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.popBackStack();
        }
    }
}
