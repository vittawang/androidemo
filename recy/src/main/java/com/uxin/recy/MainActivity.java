package com.uxin.recy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.uxin.recy.adapter.BaseAdapter;
import com.uxin.recy.adapter.click.OnItemChildClickListener;
import com.uxin.recy.adapter.decor.StaggeredItemDecoration;
import com.uxin.recy.child.VideoLinearAdapter;
import com.uxin.recy.child.VideoStaggeredAdapter;
import com.uxin.recy.entity.Video;
import com.uxin.recy.lin.LinearActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private String[] images = new String[]{
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1540288341192&di=60bb1c755eaa9b1f8cd74419d7987389&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201705%2F19%2F130832ureqee4u46z74e4h.jpeg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1540288463246&di=741c15fce620af78bba6a066f06aed85&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01fd1d585c0453a801219c77c48241.jpg%401280w_1l_2o_100sh.png",
            "http://b-ssl.duitang.com/uploads/blog/201312/04/20131204184148_hhXUT.jpeg",
            "http://b-ssl.duitang.com/uploads/item/201610/20/20161020180445_LvJZh.jpeg"
    };
    private FrameLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContainer = findViewById(R.id.container);
        updateRecyclerWithHeader(VideoStaggeredAdapter.LAYOUT_STAGGERED, VideoStaggeredAdapter.VERTICAL, 2, StaggeredGridLayoutManager.VERTICAL, 10, 15, queryRandomVideoList());
    }

    private RecyclerView updateRecycler(int layout, int adapterType, int spanCount, int orientation, int horizontalSpacing, int verticalSpacing, List<Video> list) {
        RecyclerView recyclerView = new RecyclerView(this);
        if (mContainer.getChildCount() > 0) {
            mContainer.removeAllViews();
        }
        mContainer.addView(recyclerView);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(spanCount, orientation);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new StaggeredItemDecoration(this, orientation, spanCount, horizontalSpacing, verticalSpacing));
        VideoStaggeredAdapter adapter = new VideoStaggeredAdapter(layout);
        adapter.setOrientationType(adapterType);
        recyclerView.setAdapter(adapter);
        adapter.setNewData(list);
        return recyclerView;
    }

    private void updateRecyclerWithHeader(int layout, int adapterType, int spanCount, int orientation, int horizontalSpacing, int verticalSpacing, List<Video> list) {
        RecyclerView rev = updateRecycler(layout, adapterType, spanCount, orientation, horizontalSpacing, verticalSpacing, list);
        final VideoStaggeredAdapter adapter = (VideoStaggeredAdapter) rev.getAdapter();
        if (adapter == null) {
            return;
        }
        View header = LayoutInflater.from(this).inflate(R.layout.staggered_header, null, false);
        adapter.setHeaderView(header);
        adapter.setHeaderView(LayoutInflater.from(this).inflate(R.layout.staggered_header, null, false));

        View footer = LayoutInflater.from(this).inflate(R.layout.staggered_footer, null, false);
        adapter.setFooterView(footer);
        RecyclerView.ItemDecoration itemDecoration = rev.getItemDecorationAt(0);
        if (itemDecoration instanceof StaggeredItemDecoration) {
            ((StaggeredItemDecoration) itemDecoration).setListHeaderCount(adapter.getHeaderCount());
        }
//        View inflate = LayoutInflater.from(this).inflate(R.layout.staggered_empty, null, false);
//        inflate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                adapter.removeFooterView(0);
//            }
//        });
//        adapter.setEmptyView(inflate);
//        adapter.setNewData(null);
    }

    private RecyclerView updateLinearRecycler(final List<Video> list) {
        RecyclerView recyclerView = new RecyclerView(this);
        if (mContainer.getChildCount() > 0) {
            mContainer.removeAllViews();
        }
        mContainer.addView(recyclerView);
        //rev设置展示布局
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        VideoLinearAdapter adapter = new VideoLinearAdapter();
        recyclerView.setAdapter(adapter);
        //添加两个header - 测试点击的位置对不对
        adapter.setHeaderView(LayoutInflater.from(this).inflate(R.layout.staggered_header, null, false));
        adapter.setHeaderView(LayoutInflater.from(this).inflate(R.layout.staggered_header, null, false));
        //设置list数据
        adapter.setNewData(list);
        //设置item的点击监听 - 好处：从view里产生的点击事件通过adapter和holder框架封装，直接抛到外层 activity/fragment，不需要一层层传递点击事件了！
        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseAdapter adapter, View view, int position) {
                Object item = adapter.getItem(position);
                if (item instanceof Video) {
                    switch (view.getId()) {
                        case R.id.container:
                            Toast.makeText(MainActivity.this, "container - " + position + " - " + ((Video) item).getTitle(), Toast.LENGTH_SHORT).show();
                            list.clear();
                            break;
                        case R.id.tv_center:
                            Toast.makeText(MainActivity.this, "tv_center - " + position + " - " + ((Video) item).getTitle(), Toast.LENGTH_SHORT).show();
                            adapter.setNewData(queryRandomVideoList());
                            break;
                    }
                }
            }
        });
        return recyclerView;
    }

    private List<Video> queryFixVideoList(int size) {
        Random random = new Random();
        List<Video> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(new Video(1, 1, images[random.nextInt(4)], " "));
        }
        return list;
    }

    @NonNull
    private List<Video> queryRandomVideoList() {
        Random random = new Random();
        List<Video> list = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            int width = random.nextInt(4);//[0,4) 0,1,2,3
            list.add(new Video(width + 1, random.nextInt(4) + 1, images[random.nextInt(4)], "你是猴子请来的救兵吗？"));
        }
        return list;
    }

    //menu 相关 ----------------

    /**
     * 创建菜单 : 系统会在启动activity时调用此方法，创建改菜单（Android 3.0之上）
     *
     * @param menu 系统的menu对象
     * @return true 代表创建了菜单栏
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.recycler_layout, menu);
        //也可以使用 menu.add() / menu.findItem()
        return true;
    }

    /**
     * 处理菜单的点击事件
     *
     * @param item 菜单条目
     * @return true 成功处理菜单项后，系统将返回 true。
     * <p>
     * 如果未处理菜单项，则应调用 onOptionsItemSelected() 的超类实现（默认实现将返回 false）。
     * 如果 Activity 包括片段，则系统将依次为 Activity 和每个片段（按照每个片段的添加顺序）调用 onOptionsItemSelected()，
     * 直到有一个返回结果为 true 或所有片段均调用完毕为止。
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.click_test:
                updateLinearRecycler(queryRandomVideoList());
                break;
            case R.id.staggered_vertical_two:
                updateRecycler(VideoStaggeredAdapter.LAYOUT_STAGGERED, VideoStaggeredAdapter.VERTICAL, 2, StaggeredGridLayoutManager.VERTICAL, 10, 10, queryRandomVideoList());
                return true;
            case R.id.staggered_vertical_three:
                updateRecycler(VideoStaggeredAdapter.LAYOUT_STAGGERED, VideoStaggeredAdapter.VERTICAL, 3, StaggeredGridLayoutManager.VERTICAL, 10, 10, queryRandomVideoList());
                break;
            case R.id.staggered_horizontal_three:
                updateRecycler(VideoStaggeredAdapter.LAYOUT_STAGGERED, VideoStaggeredAdapter.HORIZONTAL, 3, StaggeredGridLayoutManager.HORIZONTAL, 10, 10, queryRandomVideoList());
                break;
            case R.id.staggered_horizontal_four:
                updateRecycler(VideoStaggeredAdapter.LAYOUT_STAGGERED, VideoStaggeredAdapter.HORIZONTAL, 4, StaggeredGridLayoutManager.HORIZONTAL, 10, 10, queryRandomVideoList());
                break;
            case R.id.grid_two:
                updateRecycler(VideoStaggeredAdapter.LAYOUT_GRID, VideoStaggeredAdapter.GRID, 2, StaggeredGridLayoutManager.VERTICAL, 10, 10, queryFixVideoList(4));
                break;
            case R.id.grid_three:
                updateRecycler(VideoStaggeredAdapter.LAYOUT_GRID, VideoStaggeredAdapter.GRID, 3, StaggeredGridLayoutManager.VERTICAL, 10, 10, queryFixVideoList(9));
                break;
            case R.id.grid_four:
                updateRecycler(VideoStaggeredAdapter.LAYOUT_GRID, VideoStaggeredAdapter.GRID, 4, StaggeredGridLayoutManager.VERTICAL, 10, 10, queryFixVideoList(16));
                break;
            case R.id.xixi:
                startActivity(new Intent(this, LinearActivity.class));
                break;
            case R.id.haha:
                toast(item.getTitle());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 在运行时更改菜单选项；
     *
     * @param menu 当前的菜单对象
     * @return onCreateOptionsMenu 在启动时调用，创建菜单的初始状态.此方法在运行时更改menu状态
     * 调用 invalidateOptionsMenu 请求刷新菜单
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    public void toast(CharSequence content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }
}
