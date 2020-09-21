package com.lee.wechat.moudle.moments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.lee.wechat.R;
import com.lee.wechat.base.BaseActivity;
import com.lee.wechat.base.BaseRecycleAdapter;
import com.lee.wechat.bean.TweetsBean;
import com.lee.wechat.bean.UserInfoBean;
import com.lee.wechat.customview.CustomRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * @Author: Lee
 * @CreateDate: 2020/9/7 0:12
 * @company:LeeStudio
 * @email: ltms2013@outlook.com
 * @Description: 朋友圈主页Activity
 */
public class MomentsActivity extends BaseActivity<MomentsPresenter> implements MomentsView
        , BaseRecycleAdapter.OnLoadMoreListener, BaseRecycleAdapter.OnScrolledTopListener, CustomRecyclerView.OnPullRefreshListener {


    @BindView(R.id.rlv_moments)
    CustomRecyclerView rlvMoments;
    @BindView(R.id.rv_title_bar)
    RelativeLayout rvTitleBar;
    @BindView(R.id.tv_moments_title)
    TextView tvMomentsTitle;
    @BindView(R.id.rb_moments_back)
    RadioButton rbMomentsBack;
    @BindView(R.id.rb_camera)
    RadioButton rbCamera;
    @BindView(R.id.iv_refresh)
    ImageView ivRefresh;

    /**
     * 朋友圈列表适配器
     */
    private TweetsAdapter tweetsAdapter;

    @Override
    public int setLayoutID() {
        return R.layout.activity_moments;
    }

    /**
     * 初始化布局
     */
    @Override
    public void initView() {
        tweetsAdapter = new TweetsAdapter(this, R.layout.item_tweets);
        //设置RecycleView 可以下拉加载更多
        tweetsAdapter.setCanLoad(true);
        tweetsAdapter.setOnLoadMoreListener(this);
        tweetsAdapter.setOnScrolledTopListener(this);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.scrollToPosition(0);
        rlvMoments.setLayoutManager(linearLayoutManager);
        rlvMoments.setAdapter(tweetsAdapter);
        rlvMoments.setOnPullRefreshListener(this);

    }

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        mPresenter.getUserInfo();
        mPresenter.refreshTweets();
        startLoadingAnim();
    }

    @OnClick(R.id.rb_moments_back)
    public void onViewClicked() {
        finish();
    }


    /**
     * 构建MomentsPresenter
     *
     * @return
     */
    @Override
    public MomentsPresenter createPresenter() {
        return new MomentsPresenter();
    }


    /**
     * 用户信息获取完成回调
     *
     * @param userInfo
     */
    @Override
    public void onGetUserInfo(UserInfoBean userInfo) {
        RelativeLayout headView = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.layout_moments_head, null);
        tweetsAdapter.addHeader(headView);
        tweetsAdapter.setUserInfoBean(userInfo);
    }

    /**
     * 刷新完成回调
     *
     * @param tweets
     */
    @Override
    public void onRefreshTweets(List<TweetsBean> tweets) {
        tweetsAdapter.setNewData(tweets);
        //刷新完成
        tweetsAdapter.setCanLoad(true);
        endLoadingAnim();
    }

    /**
     * 加载更多完成回调
     *
     * @param tweets
     */
    @Override
    public void onLoadMoreTweets(List<TweetsBean> tweets) {
        tweetsAdapter.addAll(tweets);
        tweetsAdapter.loadComplete();
    }

    /**
     * 错误回调，可以展示错误页面或者Toast提示
     * @param error 错误信息
     */
    @Override
    public void onFail(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    /**
     * 下拉加载更多朋友圈列表
     */
    @Override
    public void loadMore() {
        mPresenter.loadMoreTweets();
    }


    /**
     * 列表滑动到顶部处理，改变布局显示状态
     *
     * @param isTop
     */
    @Override
    public void onScrolledTop(boolean isTop) {
        rvTitleBar.setBackgroundColor(isTop ? getResources().getColor(R.color.transparent) : getResources().getColor(R.color.gray_ed));
        setStatusBarTextColor(isTop);
        tvMomentsTitle.setVisibility(isTop ? View.GONE : View.VISIBLE);
        rbMomentsBack.setChecked(isTop);
        rbCamera.setChecked(isTop);

    }

    /**
     * 下拉刷新回调
     */
    @Override
    public void OnPullRefreshListener() {
        mPresenter.refreshTweets();
        startLoadingAnim();
    }

    /**
     * 开启刷新动画
     */
    public void startLoadingAnim() {
        ivRefresh.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.refresh_rotate_anim);
        ivRefresh.setAnimation(animation);
    }

    /**
     * 结束刷新动画
     */
    public void endLoadingAnim() {
        ivRefresh.setVisibility(View.GONE);
        ivRefresh.clearAnimation();
    }


}