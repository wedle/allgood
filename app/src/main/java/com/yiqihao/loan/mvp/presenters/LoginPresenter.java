package com.yiqihao.loan.mvp.presenters;


import com.yiqihao.loan.Constant;
import com.yiqihao.loan.entity.UserInfoResultModel;
import com.yiqihao.loan.mvp.views.LoginView;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 冯浩 on 16/8/17.
 */
public class LoginPresenter extends MvpRxPresenter<LoginView> {


	public void login(String phone, String password) {

		getView().showProgress("正在登录...");

		Subscription subscribe = mApi.login(phone, password)
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
		addSubscription(subscribe);

	}


}
