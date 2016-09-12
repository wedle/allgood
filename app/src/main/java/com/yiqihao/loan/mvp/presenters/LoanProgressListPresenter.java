package com.yiqihao.loan.mvp.presenters;

import com.yiqihao.loan.Constant;
import com.yiqihao.loan.entity.LoanInfoModel;
import com.yiqihao.loan.entity.LoanListResultModel;
import com.yiqihao.loan.mvp.views.LoanProgressListView;
import com.yiqihao.loan.utils.RefreshSessionAndRetryUtils;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 冯浩 on 16/8/10.
 */
public class LoanProgressListPresenter extends MvpRxPresenter<LoanProgressListView> {

	private static final String TAG = "LoanProgressListPresenter";

	private int mPage = 1;
	private int pagetTotal = 1;
	private boolean isNoMoreDatas;

	public void initData(boolean pullToRefresh,String status) {
		if (pullToRefresh)
			getView().showLoading();

		isNoMoreDatas = false;
		mPage = 1;
		pagetTotal = 1;
		requestData(mPage, false, status);
	}

	public void loadForMore(String status) {
		if (isNoMoreDatas) {
			return;
		}

		mPage += 1;
		requestData(mPage, true,status);
	}

	private void requestData(int page, final boolean isLoadForMore,String status) {

		if (page > pagetTotal){
			getView().showFootView(false);
			isNoMoreDatas = true;
			return;
		}


		Observable<LoanListResultModel> api = mApi.getLoanList(status, page);
		Subscription s = api
				.onErrorResumeNext(RefreshSessionAndRetryUtils.refreshSessionAndRetry(api))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<LoanListResultModel>() {
					@Override
					public void onCompleted() {
						getView().hideLoading();
					}

					@Override
					public void onError(Throwable e) {
						getView().hideLoading();
						getView().showError(false,e.toString());
					}

					@Override
					public void onNext(LoanListResultModel loanListResultModel) {

						if (loanListResultModel.getResultCode() == Constant.SUCCESS) {

							List<LoanInfoModel> list = loanListResultModel.getData().getList();

							int pageSize = loanListResultModel.getData().getPage().getSize();

							pagetTotal = loanListResultModel.getData().getPage().getTotal();

							getView().setLangZn(loanListResultModel.getData().getLang_zn());

							if (list.size() == 0) {

								if (isLoadForMore) {
									getView().showFootView(false);
									isNoMoreDatas = true;
								} else {
									getView().showEmpty();
								}

							} else {

								if (list.size() < pageSize) {
									getView().showFootView(false);
									isNoMoreDatas = true;
								} else {
									getView().showFootView(true);
								}

								if (isLoadForMore) {
									getView().loadMoreData(list);
								} else {
									getView().showContent(list);
								}

							}
						} else {
							getView().showError(true,loanListResultModel.getErrmsg());
						}


					}
				});

		addSubscription(s);
	}


}
