package com.sunspot.expand.page;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.sunspot.expand.R;

import java.util.ArrayList;
import java.util.List;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2019/1/24 下午5:36
 * -------------------------------------
 * 描述：发布器C位adapter
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class MainPublishAdapter extends PagerAdapter {

    private List<ImgInfo> list = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public void setData(List<ImgInfo> list) {
        if (list != null) {
            this.list = list;
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (object instanceof View) {
            container.removeView((View) object);
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Context context = container.getContext();
        ImgInfo imgInfo = list.get(position);
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(context);
        }
        View view = layoutInflater.inflate(R.layout.layout_publish_project_c, container, false);
        view.setTag(position);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });
//        view.findViewById(R.id.carry_tv).setVisibility();
        ((TextView) view.findViewById(R.id.tv_position)).setText(String.valueOf(imgInfo.getHeight()));
        container.addView(view);
        return view;
    }
}
