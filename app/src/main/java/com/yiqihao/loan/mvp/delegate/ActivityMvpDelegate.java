package com.yiqihao.loan.mvp.delegate;

import android.os.Bundle;

import com.yiqihao.loan.mvp.presenters.MvpPresenter;
import com.yiqihao.loan.mvp.views.MvpView;


/**
 * Created by 冯浩 on 2016/6/28.
 */
public interface ActivityMvpDelegate<V extends MvpView, P extends MvpPresenter<V>> {


    void onCreate(Bundle bundle);


    void onDestroy();


    void onPause();


    void onResume();


    void onStart();


    void onStop();


    void onRestart();

}
