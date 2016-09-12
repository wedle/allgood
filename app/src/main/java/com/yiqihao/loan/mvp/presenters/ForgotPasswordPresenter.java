package com.yiqihao.loan.mvp.presenters;


import com.yiqihao.loan.Constant;
import com.yiqihao.loan.entity.HttpResultModel;
import com.yiqihao.loan.mvp.views.ForgotPasswordView;
import com.yiqihao.loan.utils.RefreshSessionAndRetryUtils;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 冯浩 on 16/8/17.
 */
public class ForgotPasswordPresenter extends MvpRxPresenter<ForgotPasswordView> {


	public void sendSmsCode(String phone) {
		getView().showProgress("获取验证码中...");

		Observable<HttpResultModel> api = mApi.sendSmsCode("getpwd", phone);
		Subscription s = api
				.onErrorResumeNext(RefreshSessionAndRetryUtils.refreshSessionAndRetry(api))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<HttpResultModel>() {
					@Override
					public void onCompleted() {
						getView().hideProgress();
					}

					@Override
					public void onError(Throwable e) {
						getView().hideProgress();
						getView().sendSmsCodeError(false, e.toString());
					}

					@Override
					public void onNext(HttpResultModel httpResultModel) {

						if (httpResultModel.getResultCode() == Constant.SUCCESS) {
							getView().sendSmsCodeSuccess(httpResultModel.getMsg());
						} else {

							String errorMsg = httpResultModel.getErrmsg();

							getView().sendSmsCodeError(true, errorMsg);
						}
					}
				});
		addSubscription(s);
	}

	public void forgotPassword(String phoneNum, String code, String pwd) {

		getView().showProgress("找回密码中...");
		Observable<HttpResultModel> api = mApi.forgotPassword("mobile", phoneNum, "login", pwd,
				code);
		Subscription s = api
				.onErrorResumeNext(RefreshSessionAndRetryUtils.refreshSessionAndRetry(api))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<HttpResultModel>() {
					@Override
					public void onCompleted() {
						getView().hideProgress();
					}

					@Override
					public void onError(Throwable e) {
						getView().hideProgress();
						getView().forgotPasswordError(false, e.toString());
					}

					@Override
					public void onNext(HttpResultModel httpResultModel) {
						if (httpResultModel.getResultCode() == Constant.SUCCESS) {
							getView().forgotPasswordSuccess();
						} else {

							String errorMsg = httpResultModel.getErrmsg();

							getView().forgotPasswordError(true, errorMsg);
						}
					}
				});
		addSubscription(s);

	}
}
