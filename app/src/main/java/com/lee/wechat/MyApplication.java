package com.lee.wechat;

import android.app.Application;

import com.lee.wechat.conast.Url;
import com.lee.wechat.net.HttpRequestClient;


/**
 * @Author: Lee
 * @CreateDate: 2020/9/12 23:43
 * @company:
 * @email: ltms2013@outlook.com
 * @Description: 自定义Application
 */

class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化网络请求客户端
        HttpRequestClient.getInstence().init(Url.BASE_URL);
    }
}
