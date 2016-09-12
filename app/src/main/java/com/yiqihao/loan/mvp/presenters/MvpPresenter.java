package com.yiqihao.loan.mvp.presenters;


import com.yiqihao.loan.mvp.views.MvpView;

/**
 * Description:MVP 中Presenter的基本接口
 * Created by 冯浩 on 2016/6/28.
 */
public interface MvpPresenter<V extends MvpView> {

    /**
     * 绑定View到Presenter
     */
    void attachView(V view);

    /**
     * 解绑View
     */
    void detachView();

}
