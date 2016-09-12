package com.yiqihao.loan.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.yiqihao.loan.Constant;
import com.yiqihao.loan.R;
import com.yiqihao.loan.utils.PreferencesUtils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 启动页
 * Created by 冯浩 on 16/8/17.
 */
public class SplashActivity extends BaseActivity {


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				Window window = getWindow();
				window.setFlags(
						WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
						WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		} else {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		getSwipeBackLayout().setEnableGesture(false);
		Subscription mSubscription =
				Observable.timer(2, TimeUnit.SECONDS)
						.map(new Func1<Long, Boolean>() {
							@Override
							public Boolean call(Long aLong) {
								return PreferencesUtils.getBoolean(SplashActivity.this, Constant.AppConfigInfo.ISFIRST);
							}
						})
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new Action1<Boolean>() {
							@Override
							public void call(Boolean aBoolean) {
								if (aBoolean) {
									//TODO 引导页
								} else {
									startActivity(new Intent(SplashActivity.this, MainActivity.class));
								}
								finish();
							}
						});
		addSubscription(mSubscription);

	}
}
