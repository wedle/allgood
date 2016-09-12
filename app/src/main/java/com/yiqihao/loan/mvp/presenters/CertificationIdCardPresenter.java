package com.yiqihao.loan.mvp.presenters;

import com.yiqihao.loan.Constant;
import com.yiqihao.loan.entity.HttpResultModel;
import com.yiqihao.loan.mvp.views.CertificationIdCardView;
import com.yiqihao.loan.utils.RefreshSessionAndRetryUtils;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 冯浩 on 16/8/16.
 */
public class CertificationIdCardPresenter extends MvpRxPresenter<CertificationIdCardView> {


	public void certificationIdCard(String realName, String idCard) {
		getView().showProgress("提交实名认证信息中");
		Observable<HttpResultModel> api = mApi.certificationIdCard(1, realName, idCard);
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
						getView().certificationIdCardError(false,e.toString());
					}

					@Override
					public void onNext(HttpResultModel httpResultModel) {
						if (httpResultModel.getResultCode()== Constant.SUCCESS){
							getView().certificationIdCardSuccess();
						}else {
							

							getView().certificationIdCardError(true,httpResultModel.getErrmsg());
						}
					}
				});
		addSubscription(subscribe);
	}


}
