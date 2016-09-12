package com.yiqihao.loan.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.yiqihao.loan.mvp.delegate.ActivityMvpDelegate;
import com.yiqihao.loan.mvp.delegate.ActivityMvpDelegateImpl;
import com.yiqihao.loan.mvp.delegate.MvpDelegateCallback;
import com.yiqihao.loan.mvp.presenters.MvpPresenter;
import com.yiqihao.loan.mvp.views.MvpView;


/**
 * Description:mvp activity基类
 * Created by 冯浩 on 2016/8/17.
 */
public abstract class MvpActivity<V extends MvpView, P extends MvpPresenter<V>>
        extends BaseActivity implements MvpDelegateCallback<V, P> {

    protected ActivityMvpDelegate mvpDelegate;
    protected P presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMvpDelegate().onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getMvpDelegate().onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        getMvpDelegate().onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMvpDelegate().onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getMvpDelegate().onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getMvpDelegate().onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getMvpDelegate().onRestart();
    }


    @NonNull
    public abstract P createPresenter();


    @NonNull
    protected ActivityMvpDelegate<V, P> getMvpDelegate() {
        if (mvpDelegate == null) {
            mvpDelegate = new ActivityMvpDelegateImpl(this);
        }

        return mvpDelegate;
    }

    @NonNull
    @Override
    public P getPresenter() {
        return presenter;
    }

    @Override
    public void setPresenter(@NonNull P presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public V getMvpView() {
        return (V) this;
    }

}
