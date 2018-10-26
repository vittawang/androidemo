package com.uxin.recy;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.uxin.recy.entity.Video;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private String[] images = new String[]{
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1540288341192&di=60bb1c755eaa9b1f8cd74419d7987389&imgtype=0&src=http%3A%2F%2Fattach.bbs.miui.com%2Fforum%2F201705%2F19%2F130832ureqee4u46z74e4h.jpeg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1540288402903&di=0d3322b2ba4166639ec2b918ded4b926&imgtype=0&src=http%3A%2F%2Fimg.igeek.com.cn%2Fuploads%2F2018%2F01%2F17%2Fcf8cc8dc748c78cb28495f7088fc50b4.jpeg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1540288424745&di=e9a545c8650b1656be649b7aef5c3c9d&imgtype=0&src=http%3A%2F%2Fp0.so.qhmsg.com%2Ft0143fd18308a3c8e75.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1540288463246&di=741c15fce620af78bba6a066f06aed85&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01fd1d585c0453a801219c77c48241.jpg%401280w_1l_2o_100sh.png",
    };
    private FrameLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContainer = findViewById(R.id.container);
        updateRecycler(StaggeredAdapter.LAYOUT_STAGGERED,StaggeredAdapter.VERTICAL,2,StaggeredGridLayoutManager.VERTICAL,10,15,queryRandomVideoList());
    }

    private void updateRecycler(int layout,int adapterType,int spanCount,int orientation,int horizontalSpacing,int verticalSpacing,List<Video> list) {
        RecyclerView recyclerView = new RecyclerView(this);
        if (mContainer.getChildCount() > 0) {
            mContainer.removeAllViews();
        }
        mContainer.addView(recyclerView);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(spanCount, orientation);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new StaggeredItemDecoration(this,orientation,spanCount,horizontalSpacing,verticalSpacing));
        StaggeredAdapter adapter = new StaggeredAdapter(layout);
        adapter.setAdapterType(adapterType);
        recyclerView.setAdapter(adapter);
        adapter.setNewData(list);
    }

    private List<Video> queryFixVideoList(int size) {
        Random random = new Random();
        List<Video> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(new Video(1,1,images[random.nextInt(4)]," "));
        }
        return list;
    }

    @NonNull
    private List<Video> queryRandomVideoList() {
        Random random = new Random();
        List<Video> list = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            int width = random.nextInt(4);//[0,4) 0,1,2,3
            list.add(new Video(width + 1,random.nextInt(4) + 1,images[random.nextInt(4)],"你是猴子请来的救兵吗？"));
        }
        return list;
    }

    //menu 相关 ----------------

    /**
     * 创建菜单 : 系统会在启动activity时调用此方法，创建改菜单（Android 3.0之上）
     * @param menu 系统的menu对象
     * @return true 代表创建了菜单栏
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.recycler_layout,menu);
        //也可以使用 menu.add() / menu.findItem()
        return true;
    }

    /**
     * 处理菜单的点击事件
     * @param item 菜单条目
     * @return true 成功处理菜单项后，系统将返回 true。
     *
     * 如果未处理菜单项，则应调用 onOptionsItemSelected() 的超类实现（默认实现将返回 false）。
     * 如果 Activity 包括片段，则系统将依次为 Activity 和每个片段（按照每个片段的添加顺序）调用 onOptionsItemSelected()，
     * 直到有一个返回结果为 true 或所有片段均调用完毕为止。
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.staggered_vertical_two:
                updateRecycler(StaggeredAdapter.LAYOUT_STAGGERED,StaggeredAdapter.VERTICAL,2,StaggeredGridLayoutManager.VERTICAL,10,10,queryRandomVideoList());
                return true;
            case R.id.staggered_vertical_three:
                updateRecycler(StaggeredAdapter.LAYOUT_STAGGERED,StaggeredAdapter.VERTICAL,3,StaggeredGridLayoutManager.VERTICAL,10,10,queryRandomVideoList());
                break;
            case R.id.staggered_horizontal_three:
                updateRecycler(StaggeredAdapter.LAYOUT_STAGGERED,StaggeredAdapter.HORIZONTAL,3,StaggeredGridLayoutManager.HORIZONTAL,10,10,queryRandomVideoList());
                break;
            case R.id.staggered_horizontal_four:
                updateRecycler(StaggeredAdapter.LAYOUT_STAGGERED,StaggeredAdapter.HORIZONTAL,4,StaggeredGridLayoutManager.HORIZONTAL,10,10,queryRandomVideoList());
                break;
            case R.id.grid_two:
                updateRecycler(StaggeredAdapter.LAYOUT_GRID,StaggeredAdapter.GRID,2,StaggeredGridLayoutManager.VERTICAL,10,10,queryFixVideoList(4));
                break;
            case R.id.grid_three:
                updateRecycler(StaggeredAdapter.LAYOUT_GRID,StaggeredAdapter.GRID,3,StaggeredGridLayoutManager.VERTICAL,10,10,queryFixVideoList(9));
                break;
            case R.id.grid_four:
                updateRecycler(StaggeredAdapter.LAYOUT_GRID,StaggeredAdapter.GRID,4,StaggeredGridLayoutManager.VERTICAL,10,10,queryFixVideoList(16));
                break;
            case R.id.xixi:
                toast(item.getTitle());
                break;
            case R.id.haha:
                toast(item.getTitle());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 在运行时更改菜单选项；
     * @param menu 当前的菜单对象
     * @return
     *
     * onCreateOptionsMenu 在启动时调用，创建菜单的初始状态.此方法在运行时更改menu状态
     * 调用 invalidateOptionsMenu 请求刷新菜单
     *
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    public void toast(CharSequence content){
        Toast.makeText(this,content,Toast.LENGTH_SHORT).show();
    }
}
