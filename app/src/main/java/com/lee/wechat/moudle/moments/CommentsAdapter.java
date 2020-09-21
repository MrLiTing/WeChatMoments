package com.lee.wechat.moudle.moments;

import android.content.Context;
import android.widget.TextView;

import com.lee.wechat.R;
import com.lee.wechat.base.BaseRecycleAdapter;
import com.lee.wechat.base.BaseViewHolder;
import com.lee.wechat.bean.CommentsBean;

/**
 * @Author: Lee
 * @CreateDate: 2020/9/14 12:31
 * @company:
 * @email: ltms2013@outlook.com
 * @Description: 评论列表Adapter
 */

class CommentsAdapter extends BaseRecycleAdapter<CommentsBean> {
    public CommentsAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void convert(BaseViewHolder holder, CommentsBean commentsBean, boolean isHeader, int position) {
        TextView tvCommentSender= holder.getView(R.id.tv_comment_sender);
        TextView tvCommentContent=   holder.getView(R.id.tv_comment_content);

        tvCommentSender.setText(commentsBean.getSender().getUsername()+":");
        tvCommentContent.setText(commentsBean.getContent());
    }
}
