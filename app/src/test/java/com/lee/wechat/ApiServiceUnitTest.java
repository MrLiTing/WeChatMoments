package com.lee.wechat;

import com.lee.wechat.api.MomentsApi;
import com.lee.wechat.bean.TweetsBean;
import com.lee.wechat.bean.UserInfoBean;
import com.lee.wechat.conast.Url;
import com.lee.wechat.moudle.moments.MomentsModel;
import com.lee.wechat.net.HttpRequestClient;
import com.lee.wechat.net.subscriber.RequestSubscriber;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author: Lee
 * @CreateDate: 2020/9/8 19:36
 * @company:
 * @email: ltms2013@outlook.com
 * @Description: 数据接口单元测试类
 */

public class ApiServiceUnitTest {

    public static final String TAG = ApiServiceUnitTest.class.getSimpleName();


    /**
     * 用户信息获取测试
     */
    @Test
    public void getUserInfo() {
        HttpRequestClient.getInstence()
                .init(Url.BASE_URL)
                .createApi(MomentsApi.class)
                .getuserInfo()
                .subscribe(new RequestSubscriber<UserInfoBean>() {
                    @Override
                    public void onFail(String errorMsg) {

                    }

                    @Override
                    public void onSuccess(UserInfoBean userInfoBean) {
                        System.out.println(userInfoBean.toString());
                    }

                    @Override
                    public void onFinish() {

                    }
                });

    }

    /**
     * 朋友圈列表信息获取
     */
    @Test
    public void getTweets() {
        HttpRequestClient.getInstence()
                .init(Url.BASE_URL)
                .createApi(MomentsApi.class)
                .getTweets()
                .subscribe(new RequestSubscriber<List<TweetsBean>>() {
                    @Override
                    public void onFail(String errorMsg) {

                    }

                    @Override
                    public void onSuccess(List<TweetsBean> tweetsBeans) {
                        for (TweetsBean tweet : tweetsBeans) {
                            System.out.println(tweet.toString());
                        }

                    }

                    @Override
                    public void onFinish() {

                    }
                });

    }





    /**
     * 数据分页测试
     */
    @Test
    public void dataPaging() {
        MomentsModel mainModel = new MomentsModel();

        ArrayList<TweetsBean> list = new ArrayList<>();
        for (int i = 0; i <= 12; i++) {
            list.add(new TweetsBean("Content=" + i));
        }
        List<TweetsBean> tweetsBeans = mainModel.dataPaging(list, 5, 5);

        if(tweetsBeans==null){
            System.out.println("当前没有更多数据了");
            return;
        }
        for (TweetsBean tweet : tweetsBeans) {
            System.out.println(tweet.toString());
        }

    }

}