package com.yiqihao.loan.mvp.presenters;

import com.yiqihao.loan.Constant;
import com.yiqihao.loan.entity.LoanDetailResultModel;
import com.yiqihao.loan.mvp.views.CarInfoView;
import com.yiqihao.loan.utils.RefreshSessionAndRetryUtils;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 冯浩 on 16/8/19.
 */
public class CarInfoPresenter extends MvpRxPresenter<CarInfoView> {


	public void saveCarInfo(Map<String, String> map) {
		getView().showProgress("保存车辆信息中");
		Observable<LoanDetailResultModel> api = mApi.saveCarInfo(map);
		Subscription subscribe = api
				.onErrorResumeNext(RefreshSessionAndRetryUtils.refreshSessionAndRetry(api))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<LoanDetailResultModel>() {
					@Override
					public void onCompleted() {
						getView().hideProgress();
					}

					@Override
					public void onError(Throwable throwable) {
						getView().hideProgress();
						getView().saveError(false, throwable.toString());
					}

					@Override
					public void onNext(LoanDetailResultModel loanDetailResultModel) {
						if (loanDetailResultModel.getResultCode() == Constant.SUCCESS) {
							getView().saveSuccess();
						} else {
							getView().saveError(true, loanDetailResultModel.getErrmsg());
						}
					}
				});
		addSubscription(subscribe);

	}


}
