package com.yiqihao.loan.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.yiqihao.loan.R;
import com.yiqihao.loan.entity.LangZnModel;
import com.yiqihao.loan.entity.RepayModel;
import com.yiqihao.loan.mvp.presenters.RepayPlanPresenter;
import com.yiqihao.loan.mvp.views.RepayPlanView;
import com.yiqihao.loan.ui.adapter.RepayPlanAdapter;
import com.yiqihao.loan.utils.UIUtils;
import com.yiqihao.loan.widget.MultiStateView;
import com.yiqihao.loan.widget.listview.PagingListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 还款计划
 * Created by 冯浩 on 16/8/19.
 */
public class RepaymentPlanActivity extends MvpActivity<RepayPlanView, RepayPlanPresenter> implements RepayPlanView,
		PagingListView
		.Pagingable {

	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.lv_repay_plan)
	PagingListView lvRepayPlan;
	@BindView(R.id.mv_state)
	MultiStateView mvState;
	@BindView(R.id.ptr_frame)
	PtrClassicFrameLayout ptrFrame;

	private RepayPlanAdapter adapter;

	private LangZnModel langZn;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_repay_plan);
		ButterKnife.bind(this);
		initToolBar(toolbar, true);
		initView();
		presenter.initData(true);
	}

	private void initView() {
		adapter = new RepayPlanAdapter(this);
		lvRepayPlan.setAdapter(adapter);
		lvRepayPlan.setPagingableListener(this);
		UIUtils.ptrFrameAddHeader(this, ptrFrame);
		ptrFrame.setPtrHandler(new PtrHandler() {
			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
				return PtrDefaultHandler.checkContentCanBePulledDown(frame, lvRepayPlan, header);
			}

			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				presenter.initData(false);
			}
		});
	}

	@NonNull
	@Override
	public RepayPlanPresenter createPresenter() {
		return new RepayPlanPresenter();
	}

	@Override
	public void onLoadMoreItems() {
		presenter.loadForMore();
	}

	@Override
	public void showLoading() {
		mvState.setViewState(MultiStateView.ViewState.LOADING);
	}

	@Override
	public void hideLoading() {
		ptrFrame.refreshComplete();
	}

	@Override
	public void showContent(List<RepayModel> data) {
		adapter.setData(data,langZn);
		mvState.setViewState(MultiStateView.ViewState.CONTENT);
	}

	@Override
	public void showError(boolean isShow, String msg) {
		mvState.setViewState(MultiStateView.ViewState.ERROR);
		TextView tv = (TextView) mvState.getView(MultiStateView.ViewState.ERROR).findViewById(R.id.tv_error);
		if (isShow) {
			tv.setText(msg);
		} else {
			tv.setText(R.string.loading_data_error);
		}

	}

	@Override
	public void showEmpty() {
		mvState.setViewState(MultiStateView.ViewState.EMPTY);
	}

	@Override
	public void showFootView(boolean b) {
		lvRepayPlan.onFinishLoading(b);
	}

	@Override
	public void loadMoreData(List<RepayModel> data) {
		adapter.addList(data);
	}

	@Override
	public void setLangZn(LangZnModel langZn) {
		this.langZn = langZn;
	}
}
