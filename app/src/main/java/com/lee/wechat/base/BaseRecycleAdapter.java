package com.lee.wechat.base;

import android.annotation.SuppressLint;
import android.content.Context;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Lee
 * @CreateDate: 2020/9/7 0:12
 * @company: LeeStudio
 * @email: ltms2013@outlook.com
 * @Description: 带头的RecycleViewAdapter
 */
public abstract class BaseRecycleAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    protected Context mContext;
    /**
     * 默认列表Item布局ID
     */
    protected int layoutId;
    /**
     * Adapter数据集合
     */
    protected List<T> mData = new ArrayList<>();
    /**
     * 默认View类型，列表条目布局
     */
    protected static final int NORMAL_TYPE = 0x0001;
    /**
     * 头部布局类型
     */
    protected static final int HEADER_TYPE = 0x0002;

    /**
     * 头部View缓存集合，方便添加多条
     */
    private List<View> mHeaderViews = new ArrayList<>();

    /**
     * 是否可以上拉加载状态，默认不开启
     */
    private boolean canLoad = false;

    /**
     * 加载更多数据监听
     */
    private OnLoadMoreListener mOnLoadMoreListener;

    /**
     * RecycleView滚动到顶部监听
     */
    private OnScrolledTopListener onScrolledTopListener;

    public BaseRecycleAdapter(Context context, int layoutId) {
        this(context, layoutId, null);
    }

    public BaseRecycleAdapter(Context context, int layoutId, List<T> data) {
        this.mContext = context;
        this.layoutId = layoutId;
        if (data != null) {
            this.mData.addAll(data);
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }

    public void setOnScrolledTopListener(OnScrolledTopListener onScrolledTopListener) {
        this.onScrolledTopListener = onScrolledTopListener;
    }

    /**
     * 获取Adapter中的列表数据
     * @return
     */
    public List<T> getListData() {
        return mData;
    }

    /**
     * 获取HeadView
     * @return
     */
    public List<View> getHeaderViews() {
        return mHeaderViews;
    }

    /**
     * 添加Headview
     * @param header
     */
    public void addHeader(View header) {
        if (header == null) {
            throw new RuntimeException("header is null");
        }
        if (mHeaderViews.size() == 0) {
            mHeaderViews.add(header);
        }
        notifyItemInserted(0);
    }



    /**
     * 移除头部布局,默认移除第一条
     */
    public void removeHeader() {
        if (mHeaderViews.size() > 0) {
            mHeaderViews.remove(0);
            if (getItemCount() > 0) {
                notifyItemRemoved(0);
            } else {
                notifyDataSetChanged();
            }
        }
    }


    /**
     * 获取头部View数量
     * @return
     */
    public int getHeaderCount() {
        return mHeaderViews.size();
    }


    /**
     * 根据头尾类型创建Holder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER_TYPE) {
            return BaseViewHolder.getViewHolder(parent, mHeaderViews.get(0));
        }  else {
            return BaseViewHolder.getViewHolder(parent, layoutId);
        }
    }



    @Override
    public void onBindViewHolder(BaseViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        int index;
        int type = getItemViewType(position);
        if (type == NORMAL_TYPE) {
            index = position - getHeaderCount();
            convert(holder, mData.get(index), false, position);
        }
        //如果View类型等于头部View，isHeader设置为true，交由子类实现具体逻辑
        if (type == HEADER_TYPE) {
            convert(holder, null, true, position);
        }
    }

    public abstract void convert(BaseViewHolder holder, T t, boolean isHeader, int position);

    @Override
    public int getItemCount() {
        return mData.size() + getHeaderCount();
    }

    /**
     * 获取默认数据条数
     * @return
     */
    public int getDataNum() {
        return mData.size();
    }

    /**
     * 清除默认数据
     */
    public void clearData() {
        mData.clear();
        notifyDataSetChanged();
    }


    /**
     * 增加默认数据
     * @param data
     */
    public void addAll(List<T> data) {
        int index = getDataNum() + getHeaderCount();
        this.mData.addAll(data);
        notifyItemRangeInserted(index, data.size());
    }

    /**
     * 添加默认新数据
     * @param data
     */
    public void setNewData(List<T> data) {
        mData.clear();
        this.mData.addAll(data);
        notifyDataSetChanged();
    }


    /**
     * 获取列表数据长度
     *
     * @return
     */
    public int getSize() {
        return mData.size();
    }


    /**
     * 根据位置获取ItemView的类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (getHeaderCount() > 0 && position >= 0 && position < getHeaderCount()) {
            return HEADER_TYPE;
        }else{
            return NORMAL_TYPE;
        }

    }


    /**
     * 设置是否可以上拉加载
     */
    public void setCanLoad(boolean canLoad) {
        this.canLoad = canLoad;
    }


    /**
     * 当Adapter依附到RecyclerView上时，添加滚动监听
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        //设置RecyclerView滚动监听
        if (mOnScrollListener != null) {
            recyclerView.addOnScrollListener(mOnScrollListener);
        }
    }

    /**
     * RecyclerView滚动监听，用于下拉加载更多
     */
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE && canScroll(recyclerView)) {
                if (canLoad && mOnLoadMoreListener != null) {
                    canLoad = false;
                    mOnLoadMoreListener.loadMore();
                }
            }

        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (onScrolledTopListener != null) {
                //判断当前是否滑倒了顶部
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                boolean isTop = layoutManager.findFirstVisibleItemPosition() == 0 ? true : false;
                onScrolledTopListener.onScrolledTop(isTop);
            }
        }
    };


    /**
     * 当RecycleView与Adapter解除关联，移除RecyclerView滚动监听
     * @param recyclerView
     */
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        recyclerView.removeOnScrollListener(mOnScrollListener);
    }

    /**
     * 判断RecyclerView当前能否滚动
     * @param recyclerView
     * @return
     */
    private boolean canScroll(RecyclerView recyclerView) {
        boolean canScroll = recyclerView.canScrollVertically(1);
        return !canScroll;
    }


    /**
     * 上拉加载更多监听接口
     */
    public interface OnLoadMoreListener {
         public void loadMore();
    }


    /**
     * RecyclerView滑动到顶部回调接口
     */
    public interface OnScrolledTopListener {
         public void onScrolledTop(boolean isTop);
    }


    /**
     * 加载完成回调
     */
    public void loadComplete() {
        canLoad = true;
        notifyDataSetChanged();
    }
}
