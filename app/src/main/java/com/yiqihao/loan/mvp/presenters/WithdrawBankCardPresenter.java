package com.yiqihao.loan.mvp.presenters;


import com.yiqihao.loan.Constant;
import com.yiqihao.loan.entity.BankCardResultModel;
import com.yiqihao.loan.entity.HttpResultModel;
import com.yiqihao.loan.mvp.views.WithdrawBankCardView;
import com.yiqihao.loan.utils.RefreshSessionAndRetryUtils;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 冯浩 on 16/8/11.
 */
public class WithdrawBankCardPresenter extends MvpRxPresenter<WithdrawBankCardView> {

	public void initData() {

		Observable<BankCardResultModel> api = mApi.getBankCardList();
		Subscription subscribe = api
				.onErrorResumeNext(RefreshSessionAndRetryUtils.refreshSessionAndRetry(api))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<BankCardResultModel>() {
					@Override
					public void onCompleted() {

					}

					@Override
					public void onError(Throwable e) {
						getView().getBankListError(false, e.toString());
					}

					@Override
					public void onNext(BankCardResultModel bankCardResultModel) {

						if (bankCardResultModel.getResultCode() == Constant.SUCCESS) {
							getView().getBankListSuccess(bankCardResultModel.getData());
						} else {
							String errMsg = bankCardResultModel.getErrmsg();
							getView().getBankListError(true, errMsg);
						}

					}
				});
		addSubscription(subscribe);
	}

	public void setDefaultBank(String id) {
		getView().showProgress("数据请求中...");
		Observable<HttpResultModel> api = mApi.setDefaultBank(id);
		Subscription subscribe = api
				.onErrorResumeNext(RefreshSessionAndRetryUtils.refreshSessionAndRetry(api))
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
						getView().setDefaultBankError(false, e.toString());
					}

					@Override
					public void onNext(HttpResultModel httpResultModel) {
						if (httpResultModel.getResultCode() == Constant.SUCCESS) {
							getView().setDefaultBankSuccess();
						} else {
							getView().setDefaultBankError(true,httpResultModel.getErrmsg());
						}

					}
				});
		addSubscription(subscribe);


	}

	public void deleteBank(String id) {
		getView().showProgress("数据请求中...");
		Observable<HttpResultModel> api = mApi.deleteBank(id);
		Subscription subscribe = api
				.onErrorResumeNext(RefreshSessionAndRetryUtils.refreshSessionAndRetry(api))
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
						getView().deleteBankCardError(false, e.toString());
					}

					@Override
					public void onNext(HttpResultModel httpResultModel) {
						if (httpResultModel.getResultCode() == Constant.SUCCESS) {
							getView().deleteBankCardSuccess();
						} else {
							getView().deleteBankCardError(true,httpResultModel.getErrmsg());
						}

					}
				});
		addSubscription(subscribe);

	}
}
