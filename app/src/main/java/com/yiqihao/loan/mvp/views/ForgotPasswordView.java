package com.yiqihao.loan.mvp.views;

/**
 * Created by 冯浩 on 16/8/17.
 */
public interface ForgotPasswordView extends MvpView{

	void showProgress(String msg);

	void hideProgress();

	void sendSmsCodeSuccess(String msg);

	void sendSmsCodeError(boolean isShowError, String msg);

	void forgotPasswordSuccess();

	void forgotPasswordError(boolean isShowError, String msg);
}
