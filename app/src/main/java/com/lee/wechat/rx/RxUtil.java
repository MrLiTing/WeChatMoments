package com.lee.wechat.rx;

import com.lee.wechat.bean.TweetsBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscription;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * @Author: Lee
 * @CreateDate: 2020/9/9 20:30
 * @company:LeeStudio
 * @email: ltms2013@outlook.com
 * @Description: Rx订阅事件管理器
 */
public class RxUtil {


    /**
     * 管理订阅者
     */
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();


    /**
     * 添加订阅事件
     * @param subscription 订阅事件
     */
    public void add(Subscription subscription) {
        mCompositeSubscription.add(subscription);

    }

    /**
     * 清除所有订阅事件
     */
    public void clear() {
        // 取消订阅
        mCompositeSubscription.unsubscribe();

    }

    /**
     * 处理rx线程切换 调用compose操作符传入此方法
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<T, T> io_main() {

        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {

                return tObservable
                        // 生产线程
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        // 消费线程
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


}
