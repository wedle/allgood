package com.yiqihao.loan.mvp.presenters;


import com.yiqihao.loan.Constant;
import com.yiqihao.loan.entity.HttpResultModel;
import com.yiqihao.loan.entity.UserInfoResultModel;
import com.yiqihao.loan.mvp.views.RegisterView;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 冯浩 on 16/8/17.
 */
public class RegisterPresenter extends MvpRxPresenter<RegisterView> {


	public void sendSmsCode(String phone) {
		getView().showProgress("获取验证码中...");

		Subscription s = mApi.sendSmsCode("reg", phone)
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

	public void register(String phoneNum, String code, String pwd) {
		getView().showProgress("正在注册...");
		Subscription s = mApi.register(1,phoneNum, pwd, code)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<UserInfoResultModel>() {
					@Override
					public void onCompleted() {
						getView().hideProgress();
					}

					@Override
					public void onError(Throwable e) {
						getView().hideProgress();
						getView().registerError(false, e.toString());
					}

					@Override
					public void onNext(UserInfoResultModel userInfoResultModel) {
						if (userInfoResultModel.getResultCode() == Constant.SUCCESS) {
							getView().registerSuccess();
						} else {

							String errorMsg = userInfoResultModel.getErrmsg();

							getView().registerError(true, errorMsg);
						}
					}
				});
		addSubscription(s);
	}


	public void login(String phoneNum, String pwd) {
		getView().showProgress("正在登录...");
		Subscription s = mApi.login(phoneNum, pwd)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<UserInfoResultModel>() {
					@Override
					public void onCompleted() {
						getView().hideProgress();
					}

					@Override
					public void onError(Throwable e) {
						getView().hideProgress();
						getView().loginError(false, e.toString());
					}

					@Override
					public void onNext(UserInfoResultModel userInfoResultModel) {
						if (userInfoResultModel.getResultCode() == Constant.SUCCESS) {
							getView().loginSuccess(userInfoResultModel);
						} else {

							String errorMsg = userInfoResultModel.getErrmsg();

							getView().loginError(true, errorMsg);
						}
					}
				});
		addSubscription(s);

	}
}
