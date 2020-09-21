package com.lee.wechat.moudle.moments;

import android.text.TextUtils;

import com.lee.wechat.api.MomentsApi;
import com.lee.wechat.bean.TweetsBean;
import com.lee.wechat.bean.UserInfoBean;
import com.lee.wechat.conast.Url;
import com.lee.wechat.net.HttpRequestClient;
import com.lee.wechat.net.subscriber.RequestSubscriber;
import com.lee.wechat.rx.RxUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;

/**
 * @Author: Lee
 * @CreateDate: 2020/9/10 23:08
 * @company:
 * @email: ltms2013@outlook.com
 * @Description: 主页Model
 */

public class MomentsModel implements Serializable {

    /**
     * 朋友圈数据缓存集合
     */
    private List<TweetsBean> tweetsList;

    /**
     * 获取用户信息
     *
     * @param subscriber 请求结果订阅者，处理结果数据，由调用presenter传入
     * @return
     */
    public Subscription getUserInfo(RequestSubscriber<UserInfoBean> subscriber) {
        return HttpRequestClient.getInstence()
                .init(Url.BASE_URL)
                .createApi(MomentsApi.class)
                .getuserInfo()
                .compose(RxUtil.io_main())
                .subscribe(subscriber);

    }


    /**
     * 获取朋友圈列表
     *
     * @param subscriber 请求结果订阅者，处理结果数据，由调用presenter传入
     * @return
     */
    public Subscription getTweets(RequestSubscriber<List<TweetsBean>> subscriber) {
        return HttpRequestClient.getInstence()
                .init(Url.BASE_URL)
                .createApi(MomentsApi.class)
                .getTweets()
                .map(new Func1<List<TweetsBean>, List<TweetsBean>>() {
                    @Override
                    public List<TweetsBean> call(List<TweetsBean> tweetsBeans) {
                        if (tweetsList == null) {
                            tweetsList = new ArrayList<>();
                        }else{
                            tweetsList.clear();
                        }
                        //将朋友圈列表数据缓存到内存中,并过滤错误数据
                        for (TweetsBean tweetsBean : tweetsBeans) {
                            if (TextUtils.isEmpty(tweetsBean.getError()) && TextUtils.isEmpty(tweetsBean.getUnknownError())) {
                                tweetsList.add(tweetsBean);
                            }
                        }
                        //第一次默认返回五条数据
                        return tweetsList.subList(0, 5);
                    }
                })
                .compose(RxUtil.io_main())
                .subscribe(subscriber);

    }


    /**
     * 分页获取朋友圈数据列表
     *
     * @param subscriber 请求结果订阅者，处理结果数据，由调用presenter传入
     * @param pageIndex  页面索引，起始index为1
     * @param pageSize   每页数据条数
     * @return
     */
    public Subscription getTweetsByIndex(Subscriber<List<TweetsBean>> subscriber, int pageIndex, int pageSize) {

        return Observable.just(tweetsList)
                .map(new Func1<List<TweetsBean>, List<TweetsBean>>() {
                    @Override
                    public List<TweetsBean> call(List<TweetsBean> tweetsBeans) {
                        return dataPaging(tweetsBeans, pageIndex, pageSize);
                    }
                })
                .subscribe(subscriber);


    }


    /**
     * 数据分页算法封装
     *
     * @param pageIndex 页面索引，起始index为1
     * @param pageSize  每页数据条数
     * @return
     */
    public List<TweetsBean> dataPaging(List<TweetsBean> tweetsList, int pageIndex, int pageSize) {

        //数据总数
        int totalRows = tweetsList.size();
        //根据pageSize计算出分页总数
        int pageCount = (totalRows / pageSize) + ((totalRows % pageSize == 0) ? 0 : 1);
        //如果分页索引大于分页总数，直接返回null
        if (pageIndex > pageCount) {
            return null;
        }
        //计算数据截取的起始索引
        int startIndex = (pageIndex - 1) * pageSize;
        //计算数据截取的截止索引
        int endIndex = (pageIndex * pageSize) >= totalRows ? totalRows : (pageIndex * pageSize);
        //返回截取数据
        return tweetsList.subList(startIndex, endIndex);

    }
}
