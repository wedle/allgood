package com.yiqihao.loan.mvp.delegate;


import android.support.annotation.NonNull;

import com.yiqihao.loan.mvp.presenters.MvpPresenter;
import com.yiqihao.loan.mvp.views.MvpView;


/**
 * Created by 冯浩 on 2016/6/28.
 */
public interface MvpDelegateCallback<V extends MvpView, P extends MvpPresenter<V>> {

    /**
     * 创建 Presenter
     */
    @NonNull
    public P createPresenter();

    /**
     * 返回MVP Presenter
     */
    public P getPresenter();

    /**
     * 设置 Presenter
     */
    public void setPresenter(P presenter);

    /**
     * 返回MVP 中的View
     */
    public V getMvpView();


}
