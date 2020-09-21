package com.lee.wechat.api;

import com.lee.wechat.bean.TweetsBean;
import com.lee.wechat.bean.UserInfoBean;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * @Author: Lee
 * @CreateDate: 2020/9/10 22:37
 * @company: LeeStudio
 * @email: ltms2013@outlook.com
 * @Description: 朋友圈接口定义
 */

public interface MomentsApi {


    /**
     * 获取个人信息
     * @return 个人信息Observeable
     */
    @GET("user/jsmith")
    Observable<UserInfoBean> getuserInfo();

    /**
     * 获取朋友圈数据列表
     * @return 朋友圈数据列表Observeable
     */
    @GET("user/jsmith/tweets")
    Observable<List<TweetsBean>> getTweets();


}
