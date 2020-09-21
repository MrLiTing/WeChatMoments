package com.lee.wechat.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author: Lee
 * @CreateDate: 2020/9/7 0:14
 * @company: LeeStudio
 * @email: ltms2013@outlook.com
 * @Description: 基础ViewHolder
 */

public class BaseViewHolder extends RecyclerView.ViewHolder{
    /**
     * item控件缓存集合
     */
    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;
    protected BaseViewHolder(Context context, View itemView) {
        super(itemView);
        this.mContext=context;
        this.mConvertView = itemView;
        this.mViews = new SparseArray<>();
    }

    public static BaseViewHolder getViewHolder(ViewGroup parent, int layoutId) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new BaseViewHolder(parent.getContext(), view);
    }
    public static BaseViewHolder getViewHolder(ViewGroup parent, View view) {
        return new BaseViewHolder(parent.getContext(), view);
    }

    /**
     * 根据ID获取缓存的View
     * @param viewId 布局ID
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T)view;
    }

}
