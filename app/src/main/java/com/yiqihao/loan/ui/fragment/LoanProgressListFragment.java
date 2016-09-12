package com.yiqihao.loan.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yiqihao.loan.Constant;
import com.yiqihao.loan.R;
import com.yiqihao.loan.entity.LangZnModel;
import com.yiqihao.loan.entity.LoanInfoModel;
import com.yiqihao.loan.mvp.presenters.LoanProgressListPresenter;
import com.yiqihao.loan.mvp.views.LoanProgressListView;
import com.yiqihao.loan.ui.adapter.LoanListAdapter;
import com.yiqihao.loan.utils.PreferencesUtils;
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
 * 贷款进度列表
 * Created by 冯浩 on 16/8/18.
 */
public class LoanProgressListFragment extends MvpFragment<LoanProgressListView, LoanProgressListPresenter> implements
		LoanProgressListView, PagingListView
		.Pagingable {

	@BindView(R.id.lv_loan)
	PagingListView lvLoan;
	@BindView(R.id.mv_state)
	MultiStateView mvState;
	@BindView(R.id.ptr_frame)
	PtrClassicFrameLayout ptrFrame;

	private LangZnModel langZn;

	private MyBroadcastReceive receiver;

	@Override
	public void setLangZn(LangZnModel langZn) {
		this.langZn=langZn;
	}


	public static LoanProgressListFragment newInstance(String status) {
		LoanProgressListFragment fragment = new LoanProgressListFragment();
		Bundle args = new Bundle();
		args.putString("status", status);
		fragment.setArguments(args);
		return fragment;
	}


	private View view;

	private String uid;
	private String status;
	private LoanListAdapter adapter;

	/**
	 * 为Fragment加载布局时调用
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_loan_list, container, false);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public LoanProgressListPresenter createPresenter() {
		return new LoanProgressListPresenter();
	}

	/**
	 * 当Activity中的onCreate方法执行完后调用
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		status = getArguments().getString("status");
		uid= PreferencesUtils.getString(getContext(), Constant.AppConfigInfo.UID);
		initView();
		presenter.initData(true, status);
		registerBroadReceive();
	}

	private void registerBroadReceive() {
		receiver = new MyBroadcastReceive();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constant.ACTION_ADD_LOAN_SUCCESS);
		getContext().registerReceiver(receiver, filter);
	}


	private class MyBroadcastReceive extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Constant.ACTION_ADD_LOAN_SUCCESS)) {//添加成功
				presenter.initData(false, status);
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		getContext().unregisterReceiver(receiver);
	}

	private void initView() {
		adapter = new LoanListAdapter(getContext());
		lvLoan.setAdapter(adapter);
		lvLoan.setPagingableListener(this);
		UIUtils.ptrFrameAddHeader(getContext(), ptrFrame);
		ptrFrame.setPtrHandler(new PtrHandler() {
			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
				return PtrDefaultHandler.checkContentCanBePulledDown(frame, lvLoan, header);
			}

			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				presenter.initData(false, status);
			}
		});
	}

	@Override
	public void onLoadMoreItems() {
		presenter.loadForMore( status);
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
	public void showContent(List<LoanInfoModel> data) {
		adapter.setData(data,langZn);
		mvState.setViewState(MultiStateView.ViewState.CONTENT);
	}

	@Override
	public void showError(boolean isShow,String msg) {
		mvState.setViewState(MultiStateView.ViewState.ERROR);
		TextView tv = (TextView) mvState.getView(MultiStateView.ViewState.ERROR).findViewById(R.id.tv_error);
		if (isShow){
			tv.setText(msg);
		}else {
			tv.setText(R.string.loading_data_error);
		}

	}

	@Override
	public void showEmpty() {
		mvState.setViewState(MultiStateView.ViewState.EMPTY);
	}

	@Override
	public void showFootView(boolean b) {
		lvLoan.onFinishLoading(b);
	}

	@Override
	public void loadMoreData(List<LoanInfoModel> data) {
		adapter.addList(data);
	}
}
