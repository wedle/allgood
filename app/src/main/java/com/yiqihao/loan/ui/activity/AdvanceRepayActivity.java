package com.yiqihao.loan.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiqihao.loan.Constant;
import com.yiqihao.loan.R;
import com.yiqihao.loan.entity.LangZnModel;
import com.yiqihao.loan.entity.RepayModel;
import com.yiqihao.loan.mvp.presenters.AdvanceRepayPresenter;
import com.yiqihao.loan.mvp.views.AdvanceRepayView;
import com.yiqihao.loan.ui.adapter.AdvancedRepayAdapter;
import com.yiqihao.loan.utils.StringUtils;
import com.yiqihao.loan.utils.T;
import com.yiqihao.loan.utils.UIUtils;
import com.yiqihao.loan.widget.MultiStateView;
import com.yiqihao.loan.widget.listview.PagingListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 提前还款
 * Created by 冯浩 on 16/9/5.
 */
public class AdvanceRepayActivity extends MvpActivity<AdvanceRepayView, AdvanceRepayPresenter> implements
		AdvanceRepayView,
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
	@BindView(R.id.tv_amount)
	TextView tvAmount;
	@BindView(R.id.btn_submit)
	Button btnSubmit;
	@BindView(R.id.rl_amount)
	RelativeLayout rlAmount;

	private AdvancedRepayAdapter adapter;

	private LangZnModel langZn;

	private RepayModel repayModel;

	private MyBroadcastReceive receiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_advance_repay);
		ButterKnife.bind(this);
		initToolBar(toolbar, true);
		initToolBar(toolbar, true);
		initView();
		presenter.initData(true);
		registerBroadReceive();
	}

	private void registerBroadReceive() {
		receiver = new MyBroadcastReceive();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constant.ACTION_ADVANCE_REPAY_SUCCESS);
		registerReceiver(receiver, filter);
	}


	private class MyBroadcastReceive extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Constant.ACTION_ADVANCE_REPAY_SUCCESS)) {
				presenter.initData(false);
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}

	private void initView() {
		adapter = new AdvancedRepayAdapter(this);
		adapter.setSelectedListener(new AdvancedRepayAdapter.SelectedListener() {
			@Override
			public void onSelected(RepayModel model) {
				repayModel = model;
				changeAmount(repayModel.getWait_loanmoney());
			}
		});
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
	public AdvanceRepayPresenter createPresenter() {
		return new AdvanceRepayPresenter();
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
		rlAmount.setVisibility(View.VISIBLE);
		adapter.setData(data, langZn);
		mvState.setViewState(MultiStateView.ViewState.CONTENT);
		if (data.size() > 0) {
			adapter.setChecked(0);
			repayModel = data.get(0);
			changeAmount(repayModel.getWait_loanmoney());
		}
	}

	private void changeAmount(String amount) {
		StringUtils.setTextColor(this, tvAmount,"共" + amount + "元", R.color.red, 1, amount.length());
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

	@OnClick(R.id.btn_submit)
	public void onClick() {

		if (TextUtils.isEmpty(repayModel.getCard())){
			T.showShort(this,"银行卡状态异常,请联系客服");
			return;
		}
		Intent intent=new Intent(this,AdvanceRepayDetailActivity.class);
		intent.putExtra("RepayModel",repayModel);
		startActivity(intent);
	}

}
