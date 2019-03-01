package com.sunspot.pop.frag;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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
public class OneFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "OneFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_one, container, false);
        inflate.findViewById(R.id.btn).setOnClickListener(this);
        return inflate;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                if (getActivity() instanceof PopActivity) {
                    PopActivity activity = (PopActivity) getActivity();
                    FragmentManager fm = activity.getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Log.e(TAG, "onClick: " + fm + " / " + ft);
                    ft.replace(R.id.container,new TwoFragment());
                    ft.addToBackStack(null);
                    ft.commit();
                }
                //从f1 跳转到 f2，f2销毁回到f2； - 添加fragment 回退栈
//                FragmentManager fm = getChildFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                Log.e(TAG, "onClick: " + fm + " / " + ft);
//                ft.replace(R.id.container,new TwoFragment());
//                ft.addToBackStack(null);
//                ft.commit();
                break;
        }
    }
}
