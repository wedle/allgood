package com.yiqihao.loan.mvp.views;

/**
 * Refresh-Loading
 * 下拉刷新和加载更多
 * Created by 冯浩 on 2016/4/26.
 */
public interface RLView<T> extends MvpView{

    /**
     * 显示加载中
     */
    void showLoading();

    /**
     * 隐藏加载中
     */
    void hideLoading();

    /**
     * 显示内容
     */
    void showContent(T data);

    /**
     * 显示错误
     */
    void showError(boolean isShow, String msg);

    /**
     * 显示内容空的情况
     */
    void showEmpty();

    /**
     * 是否显示footView
     */
    void showFootView(boolean b);

    /**
     * 加载更多数据
     *
     * @param data
     */
    void loadMoreData(T data);


}
