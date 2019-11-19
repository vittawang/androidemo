package com.uxin.recy.adapter;

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

import com.uxin.recy.adapter.click.OnItemChildClickListener;
import com.uxin.recy.adapter.click.OnItemChildLongClickListener;
import com.uxin.recy.adapter.click.OnItemClickListener;
import com.uxin.recy.adapter.click.OnItemLongClickListener;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
public abstract class BaseAdapter<T, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {

    private static final String TAG = "BaseAdapter";

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
    /**
     * 此适配器attach的RecyclerView
     */
    private RecyclerView mRecyclerView;

    /**
     * 仅itemView的点击监听
     */
    private OnItemClickListener onItemClickListener;
    /**
     * 仅itemView的长按监听
     */
    private OnItemLongClickListener onItemLongClickListener;
    /**
     * item子view的点击监听
     */
    private OnItemChildClickListener onItemChildClickListener;
    /**
     * item子view的长按监听
     */
    private OnItemChildLongClickListener onItemChildLongClickListener;

    public BaseAdapter(int layoutRes) {
        this(layoutRes, new ArrayList<T>());
    }

    public BaseAdapter(int layoutRes, List<T> data) {
        this.layoutRes = layoutRes;
        this.mData = data;
        if (mData == null) {
            mData = new ArrayList<>();
        }
    }

    public void setOrientationType(int orientationType) {
        this.orientationType = orientationType;
    }

    public int getOrientationType() {
        return orientationType;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        VH holder;
        if (viewType == HEADER_TYPE && mHeaderLayout != null) {
            if (mHeaderLayout.getParent() instanceof ViewGroup) {
                ((ViewGroup) mHeaderLayout.getParent()).removeView(mHeaderLayout);
            }
            mHeaderLayout.setLayoutParams(generateFullSpanParamsWithOrientation());
            holder = createBaseViewHolder(mHeaderLayout);
        } else if (viewType == FOOTER_TYPE && mFooterLayout != null) {
            if (mFooterLayout.getParent() instanceof ViewGroup) {
                ((ViewGroup) mFooterLayout.getParent()).removeView(mFooterLayout);
            }
            mFooterLayout.setLayoutParams(generateFullSpanParamsWithOrientation());
            holder = createBaseViewHolder(mFooterLayout);
        } else if (viewType == EMPTY_TYPE && mEmptyLayout != null) {
            if (mEmptyLayout.getParent() instanceof ViewGroup) {
                ((ViewGroup) mEmptyLayout.getParent()).removeView(mEmptyLayout);
            }
            mEmptyLayout.setLayoutParams(generateFullSpanParams());
            holder = createBaseViewHolder(mEmptyLayout);
        } else {
            holder = createBaseViewHolder(LayoutInflater.from(mContext).inflate(layoutRes, parent, false));
            bindItemViewClickListener(holder);
        }
        holder.setAdapter(this);
        return holder;
    }

    @NonNull
    private RecyclerView.LayoutParams generateFullSpanParams() {
        if (mRecyclerView != null && mRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager.LayoutParams lp = new StaggeredGridLayoutManager.LayoutParams(StaggeredGridLayoutManager.LayoutParams.MATCH_PARENT, StaggeredGridLayoutManager.LayoutParams.MATCH_PARENT);
            lp.setFullSpan(true);
            return lp;
        } else {
            return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT);
        }
    }

    @NonNull
    private RecyclerView.LayoutParams generateFullSpanParamsWithOrientation() {
        if (mRecyclerView != null && mRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            //header和footer需要合并单元格
            StaggeredGridLayoutManager.LayoutParams lp;
            if (orientationType == HORIZONTAL) {
                lp = new StaggeredGridLayoutManager.LayoutParams(StaggeredGridLayoutManager.LayoutParams.WRAP_CONTENT, StaggeredGridLayoutManager.LayoutParams.MATCH_PARENT);
            } else {
                lp = new StaggeredGridLayoutManager.LayoutParams(StaggeredGridLayoutManager.LayoutParams.MATCH_PARENT, StaggeredGridLayoutManager.LayoutParams.WRAP_CONTENT);
            }
            lp.setFullSpan(true);
            return lp;
        } else {
            if (orientationType == HORIZONTAL) {
                return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.MATCH_PARENT);
            } else {
                return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Log.e(TAG, "onBindViewHolder: " + position );
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

    public abstract void convert(VH holder, T item, int position);

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
        return RecyclerView.NO_POSITION;
    }

    private int getFooterViewPosition() {
        if (getFooterCount() > 0) {
            return getItemCount() - getFooterCount();
        }
        return RecyclerView.NO_POSITION;
    }

    private int getEmptyViewPosition() {
        if (getEmptyViewCount() > 0) {
            return getHeaderViewPosition() + 1;
        }
        return RecyclerView.NO_POSITION;
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
            if (position != RecyclerView.NO_POSITION) {
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
            if (footerViewPosition != RecyclerView.NO_POSITION) {
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
                if (emptyViewPosition != RecyclerView.NO_POSITION) {
                    notifyItemInserted(emptyViewPosition);
                }
            }
        }
    }

    /**
     * 拿到泛型上的VH类型（不管继承几层 - 通过getSuperClass实现），然后new一个VH对象出来
     *
     * @param view
     * @return
     */
    @SuppressWarnings("unchecked")
    protected VH createBaseViewHolder(View view) {
        Class temp = getClass();
        Class z = null;
        while (z == null && temp != null) {
            //不管继承几层
            z = getGenericVHClass(temp);
            temp = temp.getSuperclass();
        }
        VH vh;
        //泛型擦出会导致z是null
        if (z == null) {
            vh = (VH) new BaseViewHolder(view);
        } else {
            vh = createGenericVHInstance(z, view);
        }
        return vh;
    }

    /**
     * 获取类上的泛型类VH
     *
     * @param z 要获取泛型的类(RecyclerView.Adapter)
     * @return 类上的泛型的Class对象(Class ..VH)
     */
    private Class getGenericVHClass(Class z) {
        //z : temp - RecyclerView.Adapter<VH>
        Type genericSuperclass = z.getGenericSuperclass();
        //Type 有四种子类 ： Class（类类型） ParameterizedType（泛型类型List<T>、Map<K,V>） TypeVariable（类型变量，泛型中的T、K、V） GenericArrayType（泛型数组类型List<T>[] 、T[]）
        if (genericSuperclass instanceof ParameterizedType) {
            //actualTypeArguments - VH
            Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
            for (Type typeArg : actualTypeArguments) {
                if (typeArg instanceof Class) {
                    //VH
                    Class typeClass = (Class) typeArg;
                    if (BaseViewHolder.class.isAssignableFrom(typeClass)) {
                        //typeClass 是 BaseViewHolder类型吗
                        return typeClass;
                    }
                } else if (typeArg instanceof ParameterizedType) {
                    //VH<T>
                    Type rawType = ((ParameterizedType) typeArg).getRawType();
                    if (rawType instanceof Class && BaseViewHolder.class.isAssignableFrom((Class<?>) rawType)) {
                        return (Class<?>) rawType;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 根据类new一个对象出来
     *
     * @param z    VH
     * @param view VH 构造里的参数
     * @return VH对象
     */
    @SuppressWarnings("unchecked")
    private VH createGenericVHInstance(Class z, View view) {
        Constructor constructor;
        try {
            if (z.isMemberClass() && !Modifier.isStatic(z.getModifiers())) {
                //VH 是成员内部类，外部类对象 adapter 在构造方法里（两个参数）
                constructor = z.getDeclaredConstructor(getClass(), View.class);
                constructor.setAccessible(true);
                return (VH) constructor.newInstance(this, view);
            } else {
                //非成员内部类，一个参数的构造
                constructor = z.getDeclaredConstructor(View.class);
                constructor.setAccessible(true);
                return (VH) constructor.newInstance(view);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mRecyclerView = null;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public OnItemChildClickListener getOnItemChildClickListener() {
        return onItemChildClickListener;
    }

    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        this.onItemChildClickListener = onItemChildClickListener;
    }

    public OnItemChildLongClickListener getOnItemChildLongClickListener() {
        return onItemChildLongClickListener;
    }

    public void setOnItemChildLongClickListener(OnItemChildLongClickListener onItemChildLongClickListener) {
        this.onItemChildLongClickListener = onItemChildLongClickListener;
    }

    /**
     * 如果有必要，在创建holder时就去创建ItemView的点击事件
     */
    private void bindItemViewClickListener(VH holder) {
        if (getOnItemClickListener() != null) {
            getOnItemClickListener().onItemClick(this, holder.itemView, holder.getAdapterPosition());
        }
    }
}
