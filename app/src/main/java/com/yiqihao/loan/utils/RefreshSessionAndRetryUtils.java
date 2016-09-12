package com.yiqihao.loan.utils;

import com.yiqihao.loan.Constant;
import com.yiqihao.loan.MyApplication;
import com.yiqihao.loan.entity.UserInfoResultModel;
import com.yiqihao.loan.net.loan.LoanFactory;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by 冯浩 on 16/8/24.
 */
public class RefreshSessionAndRetryUtils {

	public static <T> Func1<Throwable, ? extends Observable<? extends T>> refreshSessionAndRetry(final Observable<T>
																										 toBeResumed) {
		return new Func1<Throwable, Observable<? extends T>>() {
			@Override
			public Observable<? extends T> call(Throwable throwable) {
				throwable.printStackTrace();
				if (isSessionKeyFail(throwable)) {
					String phone = PreferencesUtils.getString(MyApplication.getInstance(), Constant.AppConfigInfo
							.PHONE);
					String password = PreferencesUtils.getString(MyApplication.getInstance(), Constant.AppConfigInfo
							.PASSWORD);
					return LoanFactory.getLoanSingleton().refreshSessionKey(phone, password)
							.flatMap(new Func1<UserInfoResultModel, Observable<? extends T>>() {
								@Override
								public Observable<? extends T> call(UserInfoResultModel userInfoResultModel) {
									if (userInfoResultModel.getResultCode()==Constant.SUCCESS){
										MyApplication.saveLoginInfo(userInfoResultModel);
										return toBeResumed;
									}else {
										return Observable.error(new Exception("refresh sessionKey error"));
									}
								}
							});
				}
				return Observable.error(throwable);
			}

			public boolean isSessionKeyFail(Throwable throwable) {
				boolean b = false;

				if (throwable instanceof SessionKeyException) {
					b = true;
				}
				return b;
			}

		};
	}

}
