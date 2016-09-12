package com.yiqihao.loan.mvp.presenters;

import com.yiqihao.loan.Constant;
import com.yiqihao.loan.entity.LoanPostResultModel;
import com.yiqihao.loan.mvp.views.LoanAppraiseView;
import com.yiqihao.loan.utils.RefreshSessionAndRetryUtils;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 冯浩 on 16/8/18.
 */
public class LoanAppraisePresenter extends MvpRxPresenter<LoanAppraiseView>{


	public void loanPost(String s, String isRepayMethod, String deadLine,String bankid) {
		getView().showProgress("借款申请中");
		Observable<LoanPostResultModel> api = mApi.loanPost(s, isRepayMethod, deadLine,bankid);
		Subscription subscribe = api
				.onErrorResumeNext(RefreshSessionAndRetryUtils.refreshSessionAndRetry(api))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<LoanPostResultModel>() {
					@Override
					public void onCompleted() {
						getView().hideProgress();
					}

					@Override
					public void onError(Throwable e) {
						getView().hideProgress();
						getView().loanApplyError(false,e.toString());
					}

					@Override
					public void onNext(LoanPostResultModel loanPostResultModel) {
						if (loanPostResultModel.getResultCode()== Constant.SUCCESS){
							getView().loanApplySuccess(loanPostResultModel.getData().getLid());
						}else {
							getView().loanApplyError(true,loanPostResultModel.getErrmsg());
						}
					}
				});
		addSubscription(subscribe);
	}


}
