package com.yiqihao.loan.mvp.presenters;

import com.yiqihao.loan.Constant;
import com.yiqihao.loan.entity.LoanDetailResultModel;
import com.yiqihao.loan.mvp.views.LoanDetailView;
import com.yiqihao.loan.utils.RefreshSessionAndRetryUtils;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoanDetailPresenter extends MvpRxPresenter<LoanDetailView> {

    public void initData(String lid) {

        Observable<LoanDetailResultModel> api = mApi.getLoanDetail(lid);
        Subscription subscribe = api
                .onErrorResumeNext(RefreshSessionAndRetryUtils.refreshSessionAndRetry(api))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoanDetailResultModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().showError(false, e.toString());
                    }

                    @Override
                    public void onNext(LoanDetailResultModel loanDetailResultModel) {

                        if (loanDetailResultModel.getResultCode() == Constant.SUCCESS) {
                            getView().showContent(loanDetailResultModel.getData());
                        } else {
                            String errMsg = loanDetailResultModel.getErrmsg();
                            getView().showError(true, errMsg);
                        }

                    }
                });
        addSubscription(subscribe);
    }


}
