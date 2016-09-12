package com.yiqihao.loan.mvp.views;


import com.yiqihao.loan.entity.UserInfoResultModel;

/**
 * Created by 冯浩 on 16/8/17.
 */
public interface RegisterView extends MvpView{

	void showProgress(String msg);

	void hideProgress();

	void sendSmsCodeSuccess(String msg);

	void sendSmsCodeError(boolean isShowError, String msg);

	void registerSuccess();

	void registerError(boolean isShowError, String msg);

	void loginSuccess(UserInfoResultModel userInfoModel);

	void loginError(boolean isShowError, String msg);

}
