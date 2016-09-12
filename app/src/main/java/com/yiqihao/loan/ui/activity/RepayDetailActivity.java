package com.yiqihao.loan.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.yiqihao.loan.R;
import com.yiqihao.loan.entity.RepayModel;
import com.yiqihao.loan.mvp.presenters.RepayDetailPresenter;
import com.yiqihao.loan.mvp.views.RepayDetailView;
import com.yiqihao.loan.ui.adapter.RepayDetailAdapter;
import com.yiqihao.loan.utils.StringUtils;
import com.yiqihao.loan.widget.MultiStateView;
import com.yiqihao.loan.widget.listview.PagingListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 还款详情
 * Created by 冯浩 on 16/8/23.
 */
public class RepayDetailActivity extends MvpActivity<
		RepayDetailView, RepayDetailPresenter> implements
		RepayDetailView, PagingListView
		.Pagingable {

	private static final String TAG = "RepayDetailActivity";

	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.tv_type)
	TextView tvType;
	@BindView(R.id.tv_time_money)
	TextView tvTimeMoney;
	@BindView(R.id.tv_loan_id)
	TextView tvLoanId;
	@BindView(R.id.tv_early_commission)
	TextView tvEarlyCommission;
	@BindView(R.id.tv_loan_commission)
	TextView tvLoanCommission;
	@BindView(R.id.tv_total)
	TextView tvTotal;
	@BindView(R.id.lv_repay)
	PagingListView lvRepay;
	@BindView(R.id.mv_state)
	MultiStateView mvState;

	private String lid;
	private String deadline;
	private String allmount;
	private String creditno;
	private String creditType;

	private RepayDetailAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_repay_detail);
		ButterKnife.bind(this);
		lid = getIntent().getStringExtra("lid");
		deadline = getIntent().getStringExtra("deadline");
		allmount = getIntent().getStringExtra("allmount");
		creditno = getIntent().getStringExtra("creditno");
		creditType = getIntent().getStringExtra("creditType");
		initToolBar(toolbar, true);
		presenter.initData(true, lid);
		initView();
	}

	private void initView() {
		adapter = new RepayDetailAdapter(this);
		lvRepay.setAdapter(adapter);
		lvRepay.setPagingableListener(this);
	}

	@NonNull
	@Override
	public RepayDetailPresenter createPresenter() {
		return new RepayDetailPresenter();
	}

	@Override
	public void onLoadMoreItems() {
		presenter.loadForMore(lid);
	}

	@Override
	public void showLoading() {
		mvState.setViewState(MultiStateView.ViewState.LOADING);
	}

	@Override
	public void hideLoading() {
	}

	@Override
	public void showContent(List<RepayModel> data) {
		double total_amount = 0; //总额

		for (int i = 0; i < data.size(); i++) {
			double t = Double.valueOf(data.get(i).getAmount());
			total_amount += t;
		}

		tvTimeMoney.setText(deadline + "  " + StringUtils.formatMoney(String.valueOf(total_amount)));
		tvEarlyCommission.setText(StringUtils.formatMoney(allmount));

		double b = Double.valueOf(total_amount) - Double.valueOf(allmount);

		tvLoanCommission.setText(StringUtils.formatMoney(String.valueOf(b)));
		tvLoanId.setText("借款申请编号 " + creditno);
		tvType.setText(creditType);
		tvTotal.setText(StringUtils.formatMoney(String.valueOf(total_amount)));


		adapter.setData(data);
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
		lvRepay.onFinishLoading(b);
	}

	@Override
	public void loadMoreData(List<RepayModel> data) {
		adapter.addList(data);
	}

	@Override
	public void setBankInfo(String card, String bank) {

	}
}
