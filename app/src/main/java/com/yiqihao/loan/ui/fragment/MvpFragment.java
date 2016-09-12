package com.yiqihao.loan.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.yiqihao.loan.mvp.delegate.FragmentMvpDelegate;
import com.yiqihao.loan.mvp.delegate.FragmentMvpDelegateImpl;
import com.yiqihao.loan.mvp.delegate.MvpDelegateCallback;
import com.yiqihao.loan.mvp.presenters.MvpPresenter;
import com.yiqihao.loan.mvp.views.MvpView;


/**
 * Description:mvp fragment
 * Created by 冯浩 on 2016/8/17.
 */
public abstract class MvpFragment<V extends MvpView, P extends MvpPresenter<V>>
        extends BaseFragment implements MvpDelegateCallback<V, P> {

    protected FragmentMvpDelegate<V, P> mvpDelegate;

    protected P presenter;

    public abstract P createPresenter();


    @NonNull
    protected FragmentMvpDelegate<V, P> getMvpDelegate() {
        if (mvpDelegate == null) {
            mvpDelegate = new FragmentMvpDelegateImpl<>(this);
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getMvpDelegate().onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getMvpDelegate().onDestroyView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMvpDelegate().onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getMvpDelegate().onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        getMvpDelegate().onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        getMvpDelegate().onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        getMvpDelegate().onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        getMvpDelegate().onStop();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMvpDelegate().onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getMvpDelegate().onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        getMvpDelegate().onDetach();
    }
}
