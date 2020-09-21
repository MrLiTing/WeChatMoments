package com.lee.wechat.base;


import com.lee.wechat.rx.RxUtil;

/**
 * @Author: Lee
 * @CreateDate: 2020/9/11 21:03
 * @company: LeeStudio
 * @email: ltms2013@outlook.com
 * @Description: Presenter基类
 */
public abstract class BasePresenter<V, M> {
    public V mView;
    public M mModel;
    /**
     * Rx订阅事件管理器
     */
    protected RxUtil rxManager = new RxUtil();

    public BasePresenter() {
        mModel = initModel();
    }

    /**
     * 初始化Model，由子类实现
     * @return
     */
    protected abstract M initModel();

    /**
     * 绑定view，
     * @param view
     */
    public void bindView(V view) {
        mView = view;
    }

    /**
     * Rx注册事件绑定销毁生命周期，随生命周期结束而结束
     */
    public void onDestroy() {
        rxManager.clear();
        mView = null;
        mModel = null;
    }
}
