package com.lee.wechat.moudle.moments;

import com.lee.wechat.bean.TweetsBean;
import com.lee.wechat.bean.UserInfoBean;

import java.util.List;

/**
 * @Author: Lee
 * @CreateDate: 2020/9/10 23:08
 * @company:
 * @email: ltms2013@outlook.com
 * @Description: 主页View
 */

interface MomentsView {
    /**
     * 用户信息数据获取回调
     * @param userInfo
     */
    void onGetUserInfo(UserInfoBean userInfo);

    /**
     * 首次获取、刷新朋友圈列表数据回调
     * @param tweets
     */
    void onRefreshTweets(List<TweetsBean> tweets);


    /**
     * 加载更多列表数据回调
     * @param tweets
     */
    void onLoadMoreTweets(List<TweetsBean> tweets);

    /**
     * 错误信息处理
     * @param error
     */
    void onFail(String error);
}
