package com.uxin.recy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

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
 * 时间：2018/10/22 下午3:13
 * -------------------------------------
 * 描述：Adapter 基类
 * -------------------------------------
 * 备注：
 * -------------------------------------
 */
public abstract class BaseAdapter<T, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {

    private List<T> mData;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private int layoutRes;
    //onClick
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private OnItemChildClickListener onItemChildClickListener;
    private OnItemChildLongClickListener onItemChildLongClickListener;
    //loading empty header footer main
    private LinearLayout mHeaderLayout;
    private LinearLayout mFooterLayout;
    private FrameLayout mEmptyLayout;
    private boolean mHeadAndEmptyEnable;
    private boolean mIsUseEmpty;
    private boolean mFooterAndEmptyEnable;

    public BaseAdapter(int layoutRes, List<T> data) {
        this.layoutRes = layoutRes;
        if (data == null){
            data = new ArrayList<>();
        }
        this.mData = data;
    }

    public BaseAdapter(List<T> data){
        if (data == null){
            data = new ArrayList<>();
        }
        this.mData = data;
    }

    public List<T> getData() {
        return mData;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        mContext = parent.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        View view = mLayoutInflater.inflate(layoutRes, parent, false);
        return createBaseViewHolder(view);
    }

    /**
     * 返回值控制onBindViewHolder调用的个数
     */
    @Override
    public int getItemCount() {
        int count = 0;
        if (getEmptyViewCount() == 1) {
            if (mHeadAndEmptyEnable && getHeaderLayoutCount() > 0) {
                count++;
            }
            if (mFooterAndEmptyEnable && getFooterLayoutCount() > 0) {
                count++;
            }
        } else {
            count += (mData != null ? mData.size() : 0) + getHeaderLayoutCount() + getFooterLayoutCount() + getLoadMoreViewCount();
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public T getItem(int position) {
        if (position >= 0 && mData != null && position < mData.size() ) {
            return mData.get(position);
        }
        return null;
    }

    public void setNewData(List<T> data) {
        if (data != null && data.size() > 0) {
            mData.clear();
            mData.addAll(data);
            notifyItemRangeInserted(0, mData.size());
        }
    }

    public void addData(List<T> data) {
        if (data != null && data.size() > 0) {
            int oldSize = mData.size();
            mData.addAll(data);
            notifyItemRangeInserted(oldSize, mData.size());
        }
    }


    /**
     * 拿到泛型上的VH类型（不管继承几层 - getSuperClass做的），然后new一个VH对象出来
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
    public abstract void onBindViewHolder(@NonNull VH holder, int position);

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
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

    public int addHeaderView(View header) {
        return addHeaderView(-1, header);
    }

    public int addHeaderView(int index, View header) {
        return addHeaderView(index, header, LinearLayout.VERTICAL);
    }

    public int addHeaderView(int index, View header, int orientation) {
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

    /**
     * 返回headerView要notify的位置
     */
    private int getHeaderViewPosition() {
        if (getEmptyViewCount() == 1) {
            if (mHeadAndEmptyEnable) {
                return 0;
            }
        } else {
            return 0;
        }
        return -1;
    }

    private int getEmptyViewCount() {
        if (mEmptyLayout == null || mEmptyLayout.getChildCount() == 0) {
            return 0;
        }
        if (!mIsUseEmpty) {
            return 0;
        }
        if (mData.size() != 0) {
            return 0;
        }
        return 1;
    }

    public int addFooterView(int index, View footer, int orientation) {
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

    private int getFooterViewPosition() {
        if (getEmptyViewCount() == 1) {
            int position = 1;
            if (mHeadAndEmptyEnable && getHeaderLayoutCount() != 0) {
                //有header
                position++;
            }
            if (mFooterAndEmptyEnable) {
                return position;
            }
        } else {
            //empty 和 data 是互斥存在的！
            return (mData != null ? mData.size() : 0) + getHeaderLayoutCount();
        }
        return -1;
    }

    public int getHeaderLayoutCount() {
        if (mHeaderLayout == null || mHeaderLayout.getChildCount() == 0) {
            return 0;
        }
        return 1;
    }

    public int getFooterLayoutCount() {
        if (mFooterLayout == null || mFooterLayout.getChildCount() == 0) {
            return 0;
        }
        return 1;
    }

    public int getLoadMoreViewCount() {
//        ff
        return 10;
    }


    public void addFooterView(int index, View footer) {
        addFooterView(index, footer, LinearLayout.VERTICAL);
    }

    public void addFooterView(View footer) {
        addFooterView(-1, footer);
    }

    public void setEmptyView(int layoutResId){

    }

    public void setEmptyView(View emptyView) {
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
                int position = 0;
                if (mHeadAndEmptyEnable && getHeaderLayoutCount() > 0) {
                    position++;
                }
                notifyItemInserted(position);
            }
        }
    }

    private void checkNotNull() {
//        if (getRecyclerView() == null) {
//            throw new RuntimeException("please bind recyclerView first!");
//        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }
}
