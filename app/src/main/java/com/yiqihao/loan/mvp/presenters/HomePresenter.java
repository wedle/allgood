package com.yiqihao.loan.mvp.presenters;


import com.yiqihao.loan.Constant;
import com.yiqihao.loan.entity.BannerResultModel;
import com.yiqihao.loan.mvp.views.HomeView;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 冯浩 on 16/8/29.
 */
public class HomePresenter extends MvpRxPresenter<HomeView> {



	public void getBannerInfo() {
		Subscription subscribe = mApi.getHomeBanner("lrenmobile")
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<BannerResultModel>() {
					@Override
					public void onCompleted() {

					}

					@Override
					public void onError(Throwable e) {
						getView().getBannerInfoError(false,e.toString());
					}

					@Override
					public void onNext(BannerResultModel bannerResultModel) {
						if (bannerResultModel.getResultCode()== Constant.SUCCESS){
							getView().getBannerInfoSuccess(bannerResultModel.getData());
						}else {
							getView().getBannerInfoError(true,bannerResultModel.getErrmsg());
						}
					}
				});
		addSubscription(subscribe);
	}
}

