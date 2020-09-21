package com.lee.wechat.moudle.moments;


import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lee.wechat.R;
import com.lee.wechat.base.BaseRecycleAdapter;
import com.lee.wechat.base.BaseViewHolder;
import com.lee.wechat.bean.CommentsBean;
import com.lee.wechat.bean.ImagesBean;
import com.lee.wechat.bean.TweetsBean;
import com.lee.wechat.bean.UserInfoBean;
import com.lee.wechat.glide.ImageLoader;

import java.util.List;


/**
 * @Author: Lee
 * @CreateDate: 2020/9/7 0:14
 * @company:
 * @email: ltms2013@outlook.com
 * @Description: 朋友圈列表Adapter
 */

public class TweetsAdapter extends BaseRecycleAdapter<TweetsBean> {

    private UserInfoBean userInfo;

    public TweetsAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void convert(BaseViewHolder holder, TweetsBean tweetsBeans, boolean isHeader, int position) {


        //判断View类型是HeadView还是normalView
        if (isHeader) {
            TextView tvNickName = holder.getView(R.id.tv_nickname);
            ImageView ivAvatar = holder.getView(R.id.iv_moments_avatar);
            ImageView ivMomentsBackground = holder.getView(R.id.iv_moments_background);

            //设置用户名
            tvNickName.setText(userInfo.getNick());
            //设置用户头像
            ImageLoader.getInstanse().displayRadiusCircle(mContext, userInfo.getAvatar(), Math.round(mContext.getResources().getDimension(R.dimen.dp_4)), ivAvatar, R.mipmap.avatar_default);
            //设置朋友圈主页背景
            ImageLoader.getInstanse().display(mContext, userInfo.getProfileimage(), ivMomentsBackground, R.mipmap.moments_default_bg, R.mipmap.moments_default_bg);
        } else {
            View line = holder.getView(R.id.item_line);
            TextView tvTweetsContent = holder.getView(R.id.tv_tweets_content);
            TextView tvTweetsSender = holder.getView(R.id.tv_tweets_sender);
            ImageView ivSenderAvatar = holder.getView(R.id.iv_tweets_sender_avatar);
            RecyclerView rlvComments = holder.getView(R.id.rlv_comments);
            RecyclerView rlvImages = holder.getView(R.id.rlv_images);

            //判断如果是第一条数据，隐藏分割线
            line.setVisibility((position == 1) ? View.GONE : View.VISIBLE);
            //设置朋友圈内容
            tvTweetsContent.setText(tweetsBeans.getContent());
            //设置发送者名称
            tvTweetsSender.setText(tweetsBeans.getSender().getUsername());
            //设置发送者头像
            ImageLoader.getInstanse().displayRadiusCircle(mContext, tweetsBeans.getSender().getAvatar(),
                    Math.round(mContext.getResources().getDimension(R.dimen.dp_4)), ivSenderAvatar, R.mipmap.avatar_default);
            //设置评论列表
            setComments(tweetsBeans.getComments(), rlvComments);
            //设置图片列表
            setImages(tweetsBeans.getImages(), rlvImages);
        }


}

    /**
     * 设置头部数据源
     *
     * @param userInfoBean
     */
    public void setUserInfoBean(UserInfoBean userInfoBean) {
        this.userInfo = userInfoBean;
        notifyItemChanged(0);
    }


    /**
     * 设置图片列表
     *
     * @param images
     */
    public void setImages(List<ImagesBean> images, RecyclerView recyclerView) {

        //判断图片列表是否为空，为空则隐藏图片列表布局
        if (images != null && !images.isEmpty()) {
            recyclerView.setVisibility(View.VISIBLE);
            ImagesAdapter imagesAdapter = new ImagesAdapter(mContext, R.layout.item_images);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(imagesAdapter);
            imagesAdapter.setNewData(images);
        } else {
            recyclerView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置评论列表
     *
     * @param comments
     */
    public void setComments(List<CommentsBean> comments, RecyclerView recyclerView) {

        //判断评论列表是否为空，为空则隐藏评论列表布局
        if (comments != null && !comments.isEmpty()) {
            recyclerView.setVisibility(View.VISIBLE);
            CommentsAdapter commentsAdapter = new CommentsAdapter(mContext, R.layout.item_comments);
            commentsAdapter.setNewData(comments);
            LinearLayoutManager gridLayoutManager = new LinearLayoutManager(mContext);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(commentsAdapter);
        } else {
            recyclerView.setVisibility(View.GONE);
        }
    }
}
