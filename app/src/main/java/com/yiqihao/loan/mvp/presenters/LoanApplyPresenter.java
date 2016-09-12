package com.yiqihao.loan.mvp.presenters;

import com.yiqihao.loan.Constant;
import com.yiqihao.loan.entity.CarYearResultModel;
import com.yiqihao.loan.entity.LoanAppraiseResultModel;
import com.yiqihao.loan.mvp.views.LoanApplyView;
import com.yiqihao.loan.utils.RefreshSessionAndRetryUtils;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 冯浩 on 16/8/18.
 */
public class LoanApplyPresenter extends MvpRxPresenter<LoanApplyView> {


	public void loanAppraise(String carModel, String isCarLoan, String regDate,
							 double v, String carColor, String kilometers,String brand_id,String series_id,String model_id,String year) {
		getView().showProgress("借款评估中...");

		Observable<LoanAppraiseResultModel> api = mApi.loanAppraise(
				carModel, isCarLoan, regDate, String.valueOf(v),
				carColor, kilometers, brand_id, series_id, model_id, year);

		Subscription subscribe = api
				.onErrorResumeNext(RefreshSessionAndRetryUtils.refreshSessionAndRetry(api))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<LoanAppraiseResultModel>() {
					@Override
					public void onCompleted() {
						getView().hideProgress();
					}

					@Override
					public void onError(Throwable e) {
						getView().hideProgress();
						getView().loanAppraiseError(false, e.toString());
					}

					@Override
					public void onNext(LoanAppraiseResultModel loanAppraiseResultModel) {
						if (loanAppraiseResultModel.getResultCode() == Constant.SUCCESS) {
							getView().loanAppraiseSuccess(loanAppraiseResultModel.getData().getAppraise_amount());
						} else {
							getView().loanAppraiseError(true, loanAppraiseResultModel.getErrmsg());
						}
					}
				});
		addSubscription(subscribe);
	}


	public void getCarYear(final String carId) {
		getView().showProgress("获取汽车年份");
		Observable<CarYearResultModel> api = mApi.getCarYear(carId);
		Subscription subscribe = api
				.onErrorResumeNext(RefreshSessionAndRetryUtils.refreshSessionAndRetry(api))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<CarYearResultModel>() {
					@Override
					public void onCompleted() {
						getView().hideProgress();
					}

					@Override
					public void onError(Throwable throwable) {
						getView().hideProgress();
						getView().getCarListError(false,throwable.toString());
					}

					@Override
					public void onNext(CarYearResultModel carYearResultModel) {
						if (carYearResultModel.getResultCode()==Constant.SUCCESS){
							getView().getCarListSuccess(carYearResultModel.getData());
						}else {
							getView().getCarListError(true,carYearResultModel.getErrmsg());
						}
					}
				});
		addSubscription(subscribe);

	}
}
