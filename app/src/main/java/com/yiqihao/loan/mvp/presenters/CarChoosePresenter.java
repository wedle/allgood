package com.yiqihao.loan.mvp.presenters;

import com.yiqihao.loan.Constant;
import com.yiqihao.loan.entity.BrandsModel;
import com.yiqihao.loan.entity.CarResultModel;
import com.yiqihao.loan.mvp.views.CarChooseView;
import com.yiqihao.loan.utils.L;
import com.yiqihao.loan.utils.RefreshSessionAndRetryUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 冯浩 on 2016/8/17.
 */
public class CarChoosePresenter extends MvpRxPresenter<CarChooseView> {

	private static final String TAG = "CarChoosePresenter";

	public void initData() {

		Map<String, String> map = new HashMap<>();
		map.put("type", "1");

		Observable<CarResultModel> api = mApi.getCarInfo(map);
		Subscription s = api
				.onErrorResumeNext(RefreshSessionAndRetryUtils.refreshSessionAndRetry(api))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<CarResultModel>() {
					@Override
					public void onCompleted() {
						L.i(TAG, "onCompleted");
					}

					@Override
					public void onError(Throwable e) {
						getView().showError(false, e.toString());
					}

					@Override
					public void onNext(CarResultModel response) {


						if (response.getResultCode() == Constant.SUCCESS) {

							List<BrandsModel> brandsModels = response.getData();

							if (brandsModels == null) {
								getView().showError(false, "");
								return;
							}

							Collections.sort(brandsModels);

							if (brandsModels.size() != 0) {
								getView().showContent(brandsModels);
							}

						} else {
							getView().showError(true, response.getErrmsg());
						}


					}
				});
		addSubscription(s);


	}
}
