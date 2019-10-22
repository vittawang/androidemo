package com.uxin.recy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * -------------------------------------
 * 作者：王文婷@<vitta.wang@uxin.com>
 * -------------------------------------
 * 时间：2018/10/22 下午5:00
 * -------------------------------------
 * 描述：瀑布流适配器基类
 * -------------------------------------
 * 备注：功能简述 - header/footer/empty/orientation/addData/setNewData/baseHolder
 * -------------------------------------
 */
public abstract class StaggeredAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    private static final String TAG = "StaggeredAdapter";
    private static final int HEADER_TYPE = 100;
    private static final int FOOTER_TYPE = 200;
    private static final int EMPTY_TYPE = 300;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    protected Context mContext;
    private List<T> mData;
    private int layoutRes;
    private int orientationType = VERTICAL;

    /**
     * 头，脚
     */
    private LinearLayout mHeaderLayout, mFooterLayout;
    /**
     * 空页面和data互斥
     */
    private FrameLayout mEmptyLayout;
    private boolean mIsUseEmpty;

    public StaggeredAdapter(int layoutRes) {
        this(new ArrayList<T>(), layoutRes);
    }

    public StaggeredAdapter(List<T> data, int layoutRes) {
        this.mData = data;
        this.layoutRes = layoutRes;
    }

    public void setOrientationType(int orientationType) {
        this.orientationType = orientationType;
    }

    public int getOrientationType() {
        return orientationType;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        //header和footer需要合并单元格
        if (viewType == HEADER_TYPE && mHeaderLayout != null) {
            if (mHeaderLayout.getParent() instanceof ViewGroup) {
                ((ViewGroup) mHeaderLayout.getParent()).removeView(mHeaderLayout);
            }
            mHeaderLayout.setLayoutParams(generateFullSpanLPWithOrientation());
            return new BaseViewHolder(mHeaderLayout);
        } else if (viewType == FOOTER_TYPE && mFooterLayout != null) {
            if (mFooterLayout.getParent() instanceof ViewGroup) {
                ((ViewGroup) mFooterLayout.getParent()).removeView(mFooterLayout);
            }
            mFooterLayout.setLayoutParams(generateFullSpanLPWithOrientation());
            return new BaseViewHolder(mFooterLayout);
        } else if (viewType == EMPTY_TYPE && mEmptyLayout != null) {
            if (mEmptyLayout.getParent() instanceof ViewGroup) {
                ((ViewGroup) mEmptyLayout.getParent()).removeView(mEmptyLayout);
            }
            StaggeredGridLayoutManager.LayoutParams lp = new StaggeredGridLayoutManager.LayoutParams(StaggeredGridLayoutManager.LayoutParams.MATCH_PARENT, StaggeredGridLayoutManager.LayoutParams.MATCH_PARENT);
            lp.setFullSpan(true);
            mEmptyLayout.setLayoutParams(lp);
            return new BaseViewHolder(mEmptyLayout);
        }
        return new BaseViewHolder(LayoutInflater.from(mContext).inflate(layoutRes, parent, false));
    }

    @NonNull
    private StaggeredGridLayoutManager.LayoutParams generateFullSpanLPWithOrientation() {
        StaggeredGridLayoutManager.LayoutParams lp;
        if (orientationType == HORIZONTAL) {
            lp = new StaggeredGridLayoutManager.LayoutParams(StaggeredGridLayoutManager.LayoutParams.WRAP_CONTENT, StaggeredGridLayoutManager.LayoutParams.MATCH_PARENT);
        } else {
            lp = new StaggeredGridLayoutManager.LayoutParams(StaggeredGridLayoutManager.LayoutParams.MATCH_PARENT, StaggeredGridLayoutManager.LayoutParams.WRAP_CONTENT);
        }
        //设置layoutParams 合并单元格
        lp.setFullSpan(true);
        return lp;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        Log.e(TAG, "onBindViewHolder: " + position);
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case HEADER_TYPE:
            case FOOTER_TYPE:
            case EMPTY_TYPE:
                //to do nothing,just show view
                break;
            default:
                //这里需要设置item的宽高
                convert(holder, getItem(position), position);
                break;
        }
    }

    public abstract void convert(BaseViewHolder holder, T item, int position);

    @Override
    public int getItemCount() {
        return getDataCount() + getHeaderCount() + getFooterCount() + getEmptyViewCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getHeaderViewPosition()) {
            return HEADER_TYPE;
        } else if (position == getFooterViewPosition()) {
            return FOOTER_TYPE;
        } else if (position == getEmptyViewPosition()) {
            return EMPTY_TYPE;
        }
        return super.getItemViewType(position);
    }

    public int getDataCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    public int getHeaderCount() {
        if (mHeaderLayout != null && mHeaderLayout.getChildCount() > 0) {
            return 1;
        }
        return 0;
    }

    public int getFooterCount() {
        if (mFooterLayout != null && mFooterLayout.getChildCount() > 0) {
            return 1;
        }
        return 0;
    }

    /**
     * 空页面个数
     * 1 ：页面内容数据为空 && 空布局不为空，满足两个条件认为有一个空item；
     * 0 ：其余情况认为没有空item；
     */
    private int getEmptyViewCount() {
        if (getDataCount() <= 0 && mIsUseEmpty && mEmptyLayout != null && mEmptyLayout.getChildCount() > 0) {
            return 1;
        }
        return 0;
    }

    private int getHeaderViewPosition() {
        if (getHeaderCount() > 0) {
            return 0;
        }
        return -1;
    }

    private int getFooterViewPosition() {
        if (getFooterCount() > 0) {
            return getItemCount() - getFooterCount();
        }
        return -1;
    }

    private int getEmptyViewPosition() {
        if (getEmptyViewCount() > 0) {
            return getHeaderViewPosition() + 1;
        }
        return -1;
    }

    public T getItem(int position) {
        int dataCount = getDataCount();
        if (dataCount > 0) {
            int index = position - getHeaderCount();
            if (index >= 0 && index < getDataCount()) {
                return mData.get(index);
            }
        }
        return null;
    }

    public void setNewData(List<T> list) {
        if (list != null && list.size() > 0) {
            mData.clear();
            mData.addAll(list);
            notifyItemRangeInserted(0, mData.size());
        } else if (mIsUseEmpty) {
            //展示空布局
            mData.clear();
            notifyItemChanged(getEmptyViewPosition());
        }
    }

    public void addData(List<T> list) {
        if (list != null && list.size() > 0) {
            int oldSize = mData.size();
            mData.addAll(list);
            notifyItemRangeInserted(oldSize, mData.size());
        } else if (mIsUseEmpty && getDataCount() <= 0) {
            //判断空布局是否需要展示
            notifyItemChanged(getEmptyViewPosition());
        }
    }

    public int setHeaderView(View header) {
        return setHeaderView(-1, header, LinearLayout.VERTICAL);
    }

    public int setHeaderView(int index, View header, int orientation) {
        if (mHeaderLayout == null) {
            mHeaderLayout = new LinearLayout(header.getContext());
            if (orientation == LinearLayout.VERTICAL) {
                mHeaderLayout.setOrientation(LinearLayout.VERTICAL);
                mHeaderLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            } else {
                mHeaderLayout.setOrientation(LinearLayout.HORIZONTAL);
                mHeaderLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
        }
        int childCount = mHeaderLayout.getChildCount();
        if (index < 0 || index > childCount) {
            index = childCount;
        }
        mHeaderLayout.addView(header, index);
        if (mHeaderLayout.getChildCount() == 1) {
            int position = getHeaderViewPosition();
            if (position != -1) {
                notifyItemInserted(position);
            }
        }
        return index;
    }

    public int setFooterView(int index, View footer, int orientation) {
        //初始化footerLayout
        if (mFooterLayout == null) {
            mFooterLayout = new LinearLayout(footer.getContext());
            if (orientation == LinearLayout.VERTICAL) {
                mFooterLayout.setOrientation(LinearLayout.VERTICAL);
                mFooterLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            } else if (orientation == LinearLayout.HORIZONTAL) {
                mFooterLayout.setOrientation(LinearLayout.HORIZONTAL);
                mFooterLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        }
        //addView 根据index
        int childCount = mFooterLayout.getChildCount();
        if (index < 0 || index > childCount) {
            index = childCount;
        }
        mFooterLayout.addView(footer, index);
        //notifyViewInsert
        if (mFooterLayout.getChildCount() == 1) {
            int footerViewPosition = getFooterViewPosition();
            if (footerViewPosition != -1) {
                notifyItemInserted(footerViewPosition);
            }
        }
        return index;
    }

    public int setFooterView(View view) {
        return setFooterView(-1, view, LinearLayout.VERTICAL);
    }

    public void removeHeaderView(View view) {
        if (getHeaderCount() > 0) {
            mHeaderLayout.removeView(view);
            int headerViewPosition = getHeaderViewPosition();
            if (mHeaderLayout.getChildCount() == 0 && headerViewPosition >= 0) {
                notifyItemRemoved(headerViewPosition);
            }
        }
    }

    public void removeHeaderView(int index) {
        if (getHeaderCount() > 0) {
            int childCount = mHeaderLayout.getChildCount();
            if (index >= 0 && index < childCount) {
                int headerViewPosition = getHeaderViewPosition();
                mHeaderLayout.removeViewAt(index);
                if (mHeaderLayout.getChildCount() == 0 && headerViewPosition >= 0) {
                    notifyItemRemoved(headerViewPosition);
                }
            }
        }
    }

    public void removeFooterView(View view) {
        if (getHeaderCount() > 0) {
            mFooterLayout.removeView(view);
            int footerPosition = getFooterViewPosition();
            if (mFooterLayout.getChildCount() == 0 && footerPosition >= 0) {
                notifyItemRemoved(footerPosition);
            }
        }
    }

    public void removeFooterView(int index) {
        if (getHeaderCount() > 0) {
            int childCount = mFooterLayout.getChildCount();
            if (index >= 0 && index < childCount) {
                int footerPosition = getFooterViewPosition();
                mFooterLayout.removeViewAt(index);
                if (mFooterLayout.getChildCount() == 0 && footerPosition >= 0) {
                    notifyItemRemoved(footerPosition);
                }
            }
        }
    }

    public void setEmptyView(View emptyView) {
        if (emptyView == null) {
            return;
        }
        boolean insert = false;
        //init empty layout
        if (mEmptyLayout == null) {
            mEmptyLayout = new FrameLayout(emptyView.getContext());
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mEmptyLayout.setLayoutParams(layoutParams);
            ViewGroup.LayoutParams lp = emptyView.getLayoutParams();
            if (lp != null) {
                layoutParams.width = lp.width;
                layoutParams.height = lp.height;
            }
            mEmptyLayout.setLayoutParams(layoutParams);
            insert = true;
        }
        //add view
        mEmptyLayout.removeAllViews();
        mEmptyLayout.addView(emptyView);
        mIsUseEmpty = true;
        //notify item insert
        if (insert) {
            if (getEmptyViewCount() == 1) {
                int emptyViewPosition = getEmptyViewPosition();
                if (emptyViewPosition != -1) {
                    notifyItemInserted(emptyViewPosition);
                }
            }
        }
    }

}
