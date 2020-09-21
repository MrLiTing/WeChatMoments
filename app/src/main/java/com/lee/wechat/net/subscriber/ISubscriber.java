package com.lee.wechat.net.subscriber;

/**
 * @Author: Lee
 * @CreateDate: 2020/9/9 20:01
 * @company:LeeStudio
 * @email: ltms2013@outlook.com
 * @Description: 请求结果回调抽象
 */

public interface ISubscriber<T> {

    /**
     * 请求失败回调，由具体实现类实现处理
     * @param errorMsg 错误消息
     */
    void onFail(String errorMsg);

    /**
     * 请求成功回调，有具体实现类实现处理
     * @param t 请求成功数据结果<T>
     */
    void onSuccess(T t);

    /**
     * 请求完成回调
     */
    void onFinish();
}
