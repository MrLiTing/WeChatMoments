package com.lee.wechat.moudle.moments;

import android.content.Context;
import android.widget.ImageView;

import com.lee.wechat.R;
import com.lee.wechat.base.BaseRecycleAdapter;
import com.lee.wechat.base.BaseViewHolder;
import com.lee.wechat.bean.ImagesBean;
import com.lee.wechat.glide.ImageLoader;

/**
 * @Author: Lee
 * @CreateDate: 2020/9/14 12:35
 * @company:
 * @email: ltms2013@outlook.com
 * @Description: 朋友圈图片列表适配器
 */

public class ImagesAdapter extends BaseRecycleAdapter<ImagesBean> {
    public ImagesAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void convert(BaseViewHolder holder, ImagesBean imagesBean, boolean isHeader, int position) {
        ImageView ivItemImage=holder.getView(R.id.iv_tweets_item_image);
        ImageLoader.getInstanse().display(mContext, imagesBean.getUrl(), ivItemImage, R.mipmap.tweets_img_default, R.mipmap.tweets_img_default,R.mipmap.tweets_img_default);
    }
}
