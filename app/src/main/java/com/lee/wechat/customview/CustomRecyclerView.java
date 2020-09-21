package com.lee.wechat.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author: Lee
 * @CreateDate: 2020/9/14 23:01
 * @company:
 * @email: ltms2013@outlook.com
 * @Description: 自定义RecyclerView，支持下滑手势监听
 */

public class CustomRecyclerView extends RecyclerView {


    private OnPullRefreshListener onPullRefreshListener;

    public CustomRecyclerView(@NonNull Context context) {
        super(context);
    }

    public CustomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 按下时Y轴位置
     */
    private float downY;
    /**
     * 抬起时Y轴的位置
     */
    private float upY;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        //判断滑动到顶部时，处理下拉手势
        if (!canScrollVertically(-1)) {

            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downY = e.getRawY();
                    break;
                case MotionEvent.ACTION_UP:
                    upY = e.getRawY();
                    //按下位置小于抬起位置时，判断为向下滑动手势，调用刷新接口
                    if ((upY - downY) > 0) {
                        onPullRefreshListener.OnPullRefreshListener();
                    }
                    break;
            }
        }
        return super.onTouchEvent(e);
    }


    public void setOnPullRefreshListener(OnPullRefreshListener onPullRefreshListener) {
        this.onPullRefreshListener = onPullRefreshListener;
    }

    /**
     * 下拉刷新接口
     */
    public interface OnPullRefreshListener {
        void OnPullRefreshListener();
    }
}
