package com.yiqihao.loan.mvp.delegate;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.yiqihao.loan.mvp.presenters.MvpPresenter;
import com.yiqihao.loan.mvp.views.MvpView;


/**
 * Created by 冯浩 on 2016/6/28.
 */
public class FragmentMvpDelegateImpl<V extends MvpView, P extends MvpPresenter<V>>
        implements FragmentMvpDelegate<V, P> {

    protected MvpDelegateCallback<V, P> delegateCallback;
    protected MvpInternalDelegate<V, P> internalDelegate;

    public FragmentMvpDelegateImpl(MvpDelegateCallback<V, P> delegateCallback) {
        if (delegateCallback == null) {
            throw new NullPointerException("MvpDelegateCallback is null!");
        }

        this.delegateCallback = delegateCallback;
    }

    protected MvpInternalDelegate<V, P> getInternalDelegate() {
        if (internalDelegate == null) {
            internalDelegate = new MvpInternalDelegate<>(delegateCallback);
        }

        return internalDelegate;
    }

    @Override
    public void onCreate(Bundle saved) {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getInternalDelegate().createPresenter();
        getInternalDelegate().attachView();
    }


    @Override
    public void onDestroyView() {
        getInternalDelegate().detachView();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

    }

    @Override
    public void onAttach(Context context) {

    }

    @Override
    public void onDetach() {

    }

}