package com.yiqihao.loan;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.yiqihao.loan.entity.UserInfoModel;
import com.yiqihao.loan.entity.UserInfoResultModel;
import com.yiqihao.loan.net.loan.LoanFactory;
import com.yiqihao.loan.ui.activity.BaseActivity;
import com.yiqihao.loan.utils.PreferencesUtils;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 冯浩 on 16/8/17.
 */
public class MyApplication extends Application {

	private static final String TAG = "MyApplication";

	private static MyApplication instance = null;


	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		boolean isLogin = PreferencesUtils.getBoolean(this, Constant.AppConfigInfo.ISLOGIN);
		if (isLogin) {
			refreshSessionKey();
		}
	}

	public static void refreshSessionKey() {
		String phone=PreferencesUtils.getString(MyApplication.getInstance(),Constant.AppConfigInfo.PHONE);
		String password=PreferencesUtils.getString(MyApplication.getInstance(),Constant.AppConfigInfo.PASSWORD);

		LoanFactory.getLoanSingleton().refreshSessionKey(phone,password)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<UserInfoResultModel>() {
					@Override
					public void onCompleted() {

					}

					@Override
					public void onError(Throwable e) {

					}

					@Override
					public void onNext(UserInfoResultModel userInfoResultModel) {
						if (userInfoResultModel.getResultCode() == Constant.SUCCESS) {
							Intent intent = new Intent(Constant.ACTION_LOGIN_SUCCESS);
							MyApplication.getInstance().getApplicationContext().sendBroadcast(intent);
							saveLoginInfo(userInfoResultModel);
						} else {
							//TODO 退出登录
						}

					}
				});
	}

	public static void saveLoginInfo(UserInfoResultModel userInfoModel) {
		Context context = MyApplication.getInstance().getApplicationContext();
		String sessionKey = userInfoModel.getSession_key();
		UserInfoModel infoModel = userInfoModel.getData();
		PreferencesUtils.putString(context, Constant.AppConfigInfo.SESSION_KEY, sessionKey);
		PreferencesUtils.putBoolean(context, Constant.AppConfigInfo.ISLOGIN, true);
		saveUserInfo(infoModel);

	}

	public static void saveUserInfo(UserInfoModel infoModel){
		Context context = MyApplication.getInstance().getApplicationContext();
		PreferencesUtils.putString(context, Constant.AppConfigInfo.UID, infoModel.getUid());
		PreferencesUtils.putString(context, Constant.AppConfigInfo.UUID, infoModel.getUuid());
		PreferencesUtils.putString(context, Constant.AppConfigInfo.PAYPWD, infoModel.getPaypwd());
		PreferencesUtils.putString(context, Constant.AppConfigInfo.AUTHIDCARD, infoModel.getAuth_idcard());
	}

	/**
	 * 自动登录失败，跳到登录界面
	 */
	public static void logout() {
		Context context = MyApplication.getInstance().getApplicationContext();

		PreferencesUtils.putBoolean(context, Constant.AppConfigInfo.ISLOGIN,
				false);
		PreferencesUtils.putString(context, Constant.AppConfigInfo.SESSION_KEY,
				"");
		PreferencesUtils.putString(context, Constant.AppConfigInfo.PASSWORD, "");

		Intent intent = new Intent(BaseActivity.EXITACTION);
		context.sendBroadcast(intent);

		Intent i = context.getPackageManager().getLaunchIntentForPackage
				(context
						.getPackageName());
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(i);

	}


	public static synchronized MyApplication getInstance() {
		return instance;
	}

}
