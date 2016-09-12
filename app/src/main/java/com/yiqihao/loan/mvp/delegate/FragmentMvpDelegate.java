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
public interface FragmentMvpDelegate<V extends MvpView, P extends MvpPresenter<V>> {

    public void onCreate(Bundle saved);


    public void onDestroy();


    public void onViewCreated(View view, @Nullable Bundle savedInstanceState);


    public void onDestroyView();


    public void onPause();


    public void onResume();


    public void onStart();


    public void onStop();


    public void onActivityCreated(Bundle savedInstanceState);


    public void onAttach(Context context);


    public void onDetach();


}
