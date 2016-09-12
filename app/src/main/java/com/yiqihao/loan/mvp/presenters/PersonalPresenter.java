package com.yiqihao.loan.mvp.presenters;


import com.yiqihao.loan.Constant;
import com.yiqihao.loan.entity.UserInfoResultModel;
import com.yiqihao.loan.mvp.views.PersonalView;
import com.yiqihao.loan.utils.RefreshSessionAndRetryUtils;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 冯浩 on 16/8/9.
 */
public class PersonalPresenter extends MvpRxPresenter<PersonalView> {

	public void getUserInfo() {
		Observable<UserInfoResultModel> api = mApi.getUserInfo();
		Subscription subscribe = api
				.onErrorResumeNext(RefreshSessionAndRetryUtils.refreshSessionAndRetry(api))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<UserInfoResultModel>() {
					@Override
					public void onCompleted() {
						getView().hideLoading();
					}

					@Override
					public void onError(Throwable e) {
						getView().hideLoading();
						getView().getUserInfoError(false,e.toString());
					}

					@Override
					public void onNext(UserInfoResultModel userInfoResultModel) {
						if (userInfoResultModel.getResultCode()== Constant.SUCCESS){
							getView().getUserInfoSuccess(userInfoResultModel.getData());
						}else {
							String errMsg=userInfoResultModel.getErrmsg();
							getView().getUserInfoError(true,errMsg);
						}
					}
				});
		addSubscription(subscribe);

	}
}
