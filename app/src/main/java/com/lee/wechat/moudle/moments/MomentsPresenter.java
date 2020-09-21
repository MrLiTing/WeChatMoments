package com.lee.wechat.moudle.moments;


import com.lee.wechat.base.BasePresenter;
import com.lee.wechat.bean.TweetsBean;
import com.lee.wechat.bean.UserInfoBean;
import com.lee.wechat.net.subscriber.RequestSubscriber;

import java.util.List;

import rx.Subscription;

/**
 * @Author: Lee
 * @CreateDate: 2020/9/10 23:07
 * @company:
 * @email: ltms2013@outlook.com
 * @Description: 主页Presenter
 */

class MomentsPresenter extends BasePresenter<MomentsView, MomentsModel> {

    /**
     * 当前数据页索引位置
     */
    private int currentPageIndex = 1;
    /**
     * 数据页条目数
     */
    private int pageSize = 5;

    @Override
    protected MomentsModel initModel() {
        return new MomentsModel();
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo() {
        Subscription subscription = mModel.getUserInfo(new RequestSubscriber<UserInfoBean>() {
            @Override
            public void onFail(String errorMsg) {
                mView.onFail(errorMsg);
            }

            @Override
            public void onSuccess(UserInfoBean userInfoBean) {
                mView.onGetUserInfo(userInfoBean);
            }

            @Override
            public void onFinish() {

            }
        });
        rxManager.add(subscription);
    }

    /**
     * 刷新获取朋友圈列表
     */
    public void refreshTweets() {
        Subscription subscription = mModel.getTweets(new RequestSubscriber<List<TweetsBean>>() {
            @Override
            public void onFail(String errorMsg) {
                mView.onFail(errorMsg);
            }

            @Override
            public void onSuccess(List<TweetsBean> tweetsBeans) {
                mView.onRefreshTweets(tweetsBeans);
                //刷新完成，将当前分页索引设置为1
                currentPageIndex = 1;
            }

            @Override
            public void onFinish() {

            }
        });
        rxManager.add(subscription);
    }


    /**
     * 加载更多朋友圈列表
     */
    public void loadMoreTweets() {
        Subscription subscription = mModel.getTweetsByIndex(new RequestSubscriber<List<TweetsBean>>() {
            @Override
            public void onFail(String errorMsg) {
                mView.onFail(errorMsg);
            }

            @Override
            public void onSuccess(List<TweetsBean> tweetsBeans) {

                //判断数据列表如果为空，则直接调用onFail()提示用户没有更多数据
                if (tweetsBeans == null) {
                    mView.onFail("没有更多数据了！");
                    return;
                }
                mView.onLoadMoreTweets(tweetsBeans);
            }

            @Override
            public void onFinish() {

            }
        }, ++currentPageIndex, pageSize);

        rxManager.add(subscription);
    }

}
