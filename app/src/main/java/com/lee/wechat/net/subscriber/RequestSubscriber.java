package com.lee.wechat.net.subscriber;

import android.util.Log;


import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rx.Subscriber;

/**
 * @Author: Lee
 * @CreateDate: 2020/9/9 20:15
 * @company:LeeStudio
 * @email: ltms2013@outlook.com
 * @Description: 请求事件订阅者
 */
public abstract class RequestSubscriber<T> extends Subscriber<T> implements ISubscriber<T> {
    private static final String TAG = "BaseSubscriber";
    private static final String errorMsg_SocketTimeoutException = "服务器响应超时";
    private static final String errorMsg_ConnectException = "服务器请求超时";
    private static final String errorMsg_UnknownHostException = "解析错误";

    /**
     * 异常信息处理
     *
     * @param e 异常信息
     */
    @Override
    public void onError(Throwable e) {

        if (e instanceof SocketTimeoutException) {
            onFail(errorMsg_SocketTimeoutException);
        } else if (e instanceof ConnectException) {
            onFail(errorMsg_ConnectException);
        } else if (e instanceof UnknownHostException) {
            onFail(errorMsg_UnknownHostException);
        } else {
            e.printStackTrace();
            onFail(e.getMessage());
        }
        onFinish();
    }

    /**
     * 处理返回结果信息
     * @param t 响应结果
     */
    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onCompleted() {
        onFinish();
    }
}
