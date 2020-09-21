package com.lee.wechat.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lee.wechat.R;

import butterknife.ButterKnife;

/**
 * @Author: Lee
 * @CreateDate: 2020/9/7 0:12
 * @company: LeeStudio
 * @email: ltms2013@outlook.com
 * @Description: Acticity通用基类
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutID());
        initStatusBar();
        ButterKnife.bind(this);
        initPresenter();
        initView();
        initData();
    }


    /**
     * 设置布局参数
     *
     * @return
     */
    @LayoutRes
    public abstract int setLayoutID();

    /**
     * 界面初始化
     */
    public abstract void initView();

    /**
     * 数据初始化
     */
    public abstract void initData();

    /**
     * 创建Activity对应发的Presenter
     * @return T由具体子类的实际Presenter传入
     */
    public abstract T createPresenter();

    /**
     * presenter与Activity view关联
     */
    protected void initPresenter() {
        mPresenter = createPresenter();
        mPresenter.bindView(this);
    }


    /**
     * Destory生命周期，结束Presenter关联
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
    }

    /**
     * 无参数传递开启Activity
     *
     * @param pClass
     */
    public void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    /**
     * 带传递参数开启Activity
     *
     * @param pClass
     * @param bundle
     */
    public void openActivity(Class<?> pClass, Bundle bundle) {
        Intent intent = new Intent(this, pClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    /**
     * 初始化沉浸式状态栏
     */
    private void initStatusBar() {
        //判断SDK的版本是否>=21
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            //允许页面可以拉伸到顶部状态栏并且定义顶部状态栏透名
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //设置全屏显示
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏为透明
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 切换状态栏文字颜色
     * @param isDark
     */
    public void setStatusBarTextColor(boolean isDark) {
        View decor = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (isDark) {
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                getWindow().setStatusBarColor(Color.TRANSPARENT);

            } else {
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                getWindow().setStatusBarColor(getResources().getColor(R.color.gray_ed));
            }
        }
    }
}



