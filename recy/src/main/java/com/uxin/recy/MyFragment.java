package com.uxin.recy;

import android.support.v4.app.Fragment;
import android.view.MenuItem;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2018/10/23 下午9:00
 * -------------------------------------
 * 描述：
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public class MyFragment extends Fragment{


    /**
     * 会调用每个fragment的点击事件，知道发现有一个menu 返回了true；super 默认返回false
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
