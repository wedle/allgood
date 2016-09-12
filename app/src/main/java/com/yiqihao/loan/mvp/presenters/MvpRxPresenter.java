package com.yiqihao.loan.mvp.presenters;

import android.support.annotation.NonNull;

import com.yiqihao.loan.mvp.views.MvpView;
import com.yiqihao.loan.net.loan.LoanApi;
import com.yiqihao.loan.net.loan.LoanFactory;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by 冯浩 on 2016/6/28.
 */
public abstract class MvpRxPresenter<V extends MvpView> implements MvpPresenter<V> {


    public static final LoanApi mApi = LoanFactory.getLoanSingleton();

    private CompositeSubscription mCompositeSubscription;

    private V view;

    @Override
    public void attachView(V view) {
        this.view = view;
    }

    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    @NonNull
    public V getView() {
        return view;
    }

    @Override
    public void detachView() {
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }



}
