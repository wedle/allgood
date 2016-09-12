package com.yiqihao.loan.mvp.presenters;

import com.yiqihao.loan.Constant;
import com.yiqihao.loan.entity.RepayListResultModel;
import com.yiqihao.loan.entity.RepayModel;
import com.yiqihao.loan.mvp.views.AdvanceRepayView;
import com.yiqihao.loan.utils.RefreshSessionAndRetryUtils;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 冯浩 on 16/8/20.
 */
public class AdvanceRepayPresenter extends MvpRxPresenter<AdvanceRepayView> {

	private int mPage = 1;
	private int pagetTotal = 1;
	private boolean isNoMoreDatas;

	public void initData(boolean pullToRefresh) {
		if (pullToRefresh)
			getView().showLoading();

		isNoMoreDatas = false;
		mPage = 1;
		pagetTotal = 1;
		requestData(mPage, false);
	}

	public void loadForMore() {
		if (isNoMoreDatas) {
			return;
		}

		mPage += 1;
		requestData(mPage, true);
	}

	private void requestData(int page, final boolean isLoadForMore) {

		if (page > pagetTotal) {
			getView().showFootView(false);
			isNoMoreDatas = true;
			return;
		}

		Observable<RepayListResultModel> api = mApi.myAdvance(page);
		Subscription s = api
				.onErrorResumeNext(RefreshSessionAndRetryUtils.refreshSessionAndRetry(api))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<RepayListResultModel>() {
					@Override
					public void onCompleted() {
						getView().hideLoading();
					}

					@Override
					public void onError(Throwable e) {
						getView().hideLoading();
						getView().showError(false, e.toString());
					}

					@Override
					public void onNext(RepayListResultModel repayListResultModel) {

						if (repayListResultModel.getResultCode() == Constant.SUCCESS) {

							List<RepayModel> list = repayListResultModel.getData().getList();

							int pageSize = repayListResultModel.getData().getPage().getSize();

							pagetTotal = repayListResultModel.getData().getPage().getTotal();

							getView().setLangZn(repayListResultModel.getData().getLang_zn());

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
							getView().showError(true, repayListResultModel.getErrmsg());
						}


					}
				});

		addSubscription(s);
	}


}
