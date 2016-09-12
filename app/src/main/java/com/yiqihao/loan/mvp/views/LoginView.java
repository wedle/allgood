package com.yiqihao.loan.mvp.views;


import com.yiqihao.loan.entity.UserInfoResultModel;

/**
 * Created by 冯浩 on 16/8/17.
 */
public interface LoginView extends MvpView{

	void showProgress(String msg);

	void hideProgress();

	void loginSuccess(UserInfoResultModel userInfoResultModel);

	void loginError(boolean isShowError, String msg);

}
