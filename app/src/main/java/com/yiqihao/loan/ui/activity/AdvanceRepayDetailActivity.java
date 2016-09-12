package com.yiqihao.loan.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.yiqihao.loan.Constant;
import com.yiqihao.loan.R;
import com.yiqihao.loan.entity.HttpResultModel;
import com.yiqihao.loan.entity.RepayModel;
import com.yiqihao.loan.net.loan.LoanFactory;
import com.yiqihao.loan.utils.RefreshSessionAndRetryUtils;
import com.yiqihao.loan.utils.StringUtils;
import com.yiqihao.loan.utils.T;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 提前还款详细
 * Created by 冯浩 on 16/9/5.
 */
public class AdvanceRepayDetailActivity extends BaseActivity {

	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.tv_tips)
	TextView tvTips;
	@BindView(R.id.tv_benjin)
	TextView tvBenjin;
	@BindView(R.id.tv_lixi)
	TextView tvLixi;
	@BindView(R.id.tv_shouxufei)
	TextView tvShouxufei;
	@BindView(R.id.tv_zonge)
	TextView tvZonge;
	@BindView(R.id.btn_submit)
	Button btnSubmit;

	private RepayModel repayModel;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_advance_repay_detail);
		ButterKnife.bind(this);
		initToolBar(toolbar,true);
		repayModel = getIntent().getParcelableExtra("RepayModel");
		initView();
	}

	private void initView() {

		String str1 = repayModel.getCard().substring(repayModel.getCard().length() - 4, repayModel.getCard().length())
				+ repayModel.getBank();

		String str = "将从您尾号" + str1 + "银行卡中扣除借款,请确保卡内余额充足";

		StringUtils.setTextColor(this, tvTips, str, R.color.red, 5, str1.length());

		String benjin = repayModel.getWait_loanmoney();
		String lixi = repayModel.getCommission();
		String shouxufei = repayModel.getWait_loanfee();

		tvBenjin.setText(benjin + "元");
		tvLixi.setText(lixi + "元");
		tvShouxufei.setText(shouxufei + "元");
		tvZonge.setText(StringUtils.formatMoney(String.valueOf(Double.valueOf(benjin) + Double.valueOf(lixi) + Double
				.valueOf(shouxufei))));
	}


	@OnClick(R.id.btn_submit)
	public void onClick() {
		getProgressDialog().setMessage("");
		getProgressDialog().show();
		Subscription subscribe = LoanFactory.getLoanSingleton().advance(repayModel.getLid())
				.onErrorResumeNext(RefreshSessionAndRetryUtils.refreshSessionAndRetry(LoanFactory.getLoanSingleton()
						.advance(repayModel.getLid())))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<HttpResultModel>() {
					@Override
					public void onCompleted() {
						getProgressDialog().dismiss();
					}

					@Override
					public void onError(Throwable e) {
						getProgressDialog().dismiss();
						T.showShort(AdvanceRepayDetailActivity.this, "请求失败");
					}

					@Override
					public void onNext(HttpResultModel httpResultModel) {
						if (httpResultModel.getResultCode() == Constant.SUCCESS) {
							T.showShort(AdvanceRepayDetailActivity.this, "还款请求成功");
							sendBroadcast(Constant.ACTION_ADVANCE_REPAY_SUCCESS);
							finish();
						} else {
							T.showShort(AdvanceRepayDetailActivity.this, httpResultModel.getErrmsg());
						}
					}
				});
		addSubscription(subscribe);


	}


}
