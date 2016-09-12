package com.yiqihao.loan.mvp.presenters;


import com.yiqihao.loan.Constant;
import com.yiqihao.loan.entity.AddBankInfoResultModel;
import com.yiqihao.loan.entity.BankBranchResultModel;
import com.yiqihao.loan.entity.BankCardResultModel;
import com.yiqihao.loan.entity.HttpResultModel;
import com.yiqihao.loan.mvp.views.AddBankCardView;
import com.yiqihao.loan.utils.RefreshSessionAndRetryUtils;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 冯浩 on 16/8/12.
 */
public class AddBankCardPresenter extends MvpRxPresenter<AddBankCardView> {


	public void searchBankList() {

		getView().showProgress("获取银行列表");
		Observable<BankCardResultModel> api = mApi.getBankCardList();
		Subscription subscribe = api
				.onErrorResumeNext(RefreshSessionAndRetryUtils.refreshSessionAndRetry(api))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<BankCardResultModel>() {
					@Override
					public void onCompleted() {
						getView().hideProgress();
					}

					@Override
					public void onError(Throwable e) {
						getView().hideProgress();
						getView().getBankListError(false,e.toString());
					}

					@Override
					public void onNext(BankCardResultModel bankCardResultModel) {

						if (bankCardResultModel.getResultCode()== Constant.SUCCESS){
							getView().getBankListSuccess(bankCardResultModel.getData());
						}else {
							String errMsg=bankCardResultModel.getErrmsg();
							getView().getBankListError(true,errMsg);
						}

					}
				});
		addSubscription(subscribe);
	}

	public void searchBranchName(String cityID, String bankCode) {
		getView().showProgress("正在搜索支行信息");
		Observable<BankBranchResultModel> api = mApi.searchBranchName(cityID, bankCode);
		Subscription subscribe = api
				.onErrorResumeNext(RefreshSessionAndRetryUtils.refreshSessionAndRetry(api))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<BankBranchResultModel>() {
					@Override
					public void onCompleted() {
						getView().hideProgress();
					}

					@Override
					public void onError(Throwable e) {
						getView().hideProgress();
						getView().onGetBranchInfoError(false,e.toString());
					}

					@Override
					public void onNext(BankBranchResultModel model) {

						if (model.getResultCode()== Constant.SUCCESS){
							getView().onGetBranchInfoSucceed(model.getData());
						}else {
							getView().onGetBranchInfoError(true,model.getErrmsg());
						}

					}
				});
		addSubscription(subscribe);
	}

	public void sendSmsCode(String phone) {
		getView().showProgress("获取验证码中...");

		Observable<HttpResultModel> api = mApi.sendSmsCode("bankcard", phone);
		Subscription s = api
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
						getView().sendSmsCodeError(false, e.toString());
					}

					@Override
					public void onNext(HttpResultModel httpResultModel) {

						if (httpResultModel.getResultCode() == Constant.SUCCESS) {
							getView().sendSmsCodeSuccess(httpResultModel.getMsg());
						} else {

							String errorMsg = httpResultModel.getErrmsg();

							getView().sendSmsCodeError(true, errorMsg);
						}
					}
				});
		addSubscription(s);
	}


	public void addBankCard(Map<String, String> params) {

		getView().showProgress("添加银行卡");
		Observable<AddBankInfoResultModel> api = mApi.addBankCard(params);
		Subscription subscribe = api
				.onErrorResumeNext(RefreshSessionAndRetryUtils.refreshSessionAndRetry(api))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<AddBankInfoResultModel>() {
					@Override
					public void onCompleted() {
						getView().hideProgress();
					}

					@Override
					public void onError(Throwable e) {
						getView().hideProgress();
						getView().onAddBankError(false,e.toString());
					}

					@Override
					public void onNext(AddBankInfoResultModel httpResultModel) {

						if (httpResultModel.getResultCode()==Constant.SUCCESS){
							getView().onAddBankSucceed(httpResultModel.getData().getBankid());
						}else {
							getView().onAddBankError(true,httpResultModel.getErrmsg());
						}

					}
				});
		addSubscription(subscribe);


	}

	public void editBankCard(Map<String, String> params) {
		getView().showProgress("编辑银行卡");
		Observable<HttpResultModel> api = mApi.editBankCard(params);
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
						getView().onEditBankError(false,e.toString());
					}

					@Override
					public void onNext(HttpResultModel httpResultModel) {

						if (httpResultModel.getResultCode()==Constant.SUCCESS){
							getView().onEditBankSucceed();
						}else {
							getView().onEditBankError(true,httpResultModel.getErrmsg());
						}

					}
				});
		addSubscription(subscribe);
	}
}
