package com.yiqihao.loan.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiqihao.loan.R;
import com.yiqihao.loan.entity.UserInfoModel;
import com.yiqihao.loan.mvp.presenters.RepaymentPresenter;
import com.yiqihao.loan.mvp.views.RepaymentView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 还款
 * Created by 冯浩 on 16/9/1.
 */
public class RepaymentActivity extends MvpActivity<RepaymentView, RepaymentPresenter> implements RepaymentView {


	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.tv_money)
	TextView tvMoney;
	@BindView(R.id.tv_repay_count)
	TextView tvRepayCount;
	@BindView(R.id.tv_period)
	TextView tvPeriod;
	@BindView(R.id.rl_repay_period)
	RelativeLayout rlRepayPeriod;
	@BindView(R.id.tv_count)
	TextView tvCount;
	@BindView(R.id.rl_repay_advance)
	RelativeLayout rlRepayAdvance;

	private UserInfoModel userInfoModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_repayment);
		ButterKnife.bind(this);
		initToolBar(toolbar, true);
		userInfoModel = getIntent().getParcelableExtra("UserInfoModel");
		initView();
	}

	private void initView() {
		tvMoney.setText(userInfoModel.getRepay_money());
		tvRepayCount.setText("共" + userInfoModel.getRepay_num() + "笔借款未结清");
		tvPeriod.setText("本期" + userInfoModel.getLate_amount() + "元");
		tvCount.setText("共" + userInfoModel.getRepay_num() + "笔");
	}

	@NonNull
	@Override
	public RepaymentPresenter createPresenter() {
		return new RepaymentPresenter();
	}


	@OnClick({R.id.rl_repay_period, R.id.rl_repay_advance})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.rl_repay_period:
				startActivity(new Intent(this,RepaymentPlanActivity.class));
				break;
			case R.id.rl_repay_advance:
				startActivity(new Intent(this,AdvanceRepayActivity.class));
				break;
		}
	}
}
