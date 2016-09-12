package com.yiqihao.loan.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiqihao.loan.Constant;
import com.yiqihao.loan.R;
import com.yiqihao.loan.entity.HttpResultModel;
import com.yiqihao.loan.mvp.presenters.LoanAppraisePresenter;
import com.yiqihao.loan.mvp.views.LoanAppraiseView;
import com.yiqihao.loan.net.loan.LoanFactory;
import com.yiqihao.loan.utils.PreferencesUtils;
import com.yiqihao.loan.utils.StringUtils;
import com.yiqihao.loan.utils.T;
import com.yiqihao.loan.utils.VerificationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 借款评估结果
 * Created by 冯浩 on 16/8/18.
 */
public class LoanAppraiseResultActivity extends MvpActivity<LoanAppraiseView, LoanAppraisePresenter> implements
		LoanAppraiseView {

	private static final int REQUEST_BANK = 3;

	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.tv_amount)
	TextView tvAmount;
	@BindView(R.id.et_price)
	EditText etPrice;
	@BindView(R.id.tv_repay_type)
	TextView tvRepayType;
	@BindView(R.id.rl_repay_type)
	RelativeLayout rlRepayType;
	@BindView(R.id.et_deadline)
	EditText etDeadline;
	@BindView(R.id.tv_pact)
	TextView tvPact;
	@BindView(R.id.btn_submit)
	Button btnSubmit;
	@BindView(R.id.tv_bank_title)
	TextView tvBankTitle;
	@BindView(R.id.tv_bank_name)
	TextView tvBankName;
	@BindView(R.id.rl_choose_bank)
	RelativeLayout rlChooseBank;

	private String amount;
	//还款方式 m-按月分期   i-按月付息,到期还本  e-到期还本息
	private String[] repayMethod = new String[]{"按月分期", "按月付息,到期还本", "到期还本息"};

	private String isRepayMethod = "m";

	private MyBroadcastReceive receiver;

	private String bankId;
	private String bankName;
	private String bankCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loan_appraise);
		ButterKnife.bind(this);
		initToolBar(toolbar, true);
		amount = getIntent().getStringExtra("amount");
		initView();
		registerBroadReceive();
	}

	private void initView() {
		tvPact.setText(Html.fromHtml("同意 <font color=\"#0066ff\">一起好借款相关协议</font>"));
		tvAmount.setText(StringUtils.formatMoney1(amount));
		tvRepayType.setText("按月分期");
	}

	@NonNull
	@Override
	public LoanAppraisePresenter createPresenter() {
		return new LoanAppraisePresenter();
	}

	@OnClick({R.id.rl_repay_type, R.id.tv_pact, R.id.btn_submit, R.id.rl_choose_bank})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.rl_repay_type:
				new AlertDialog.Builder(this)
						.setTitle("选择还款方式")
						.setItems(repayMethod,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {

										switch (which) {
											case 0:
												isRepayMethod = "m";
												tvRepayType.setText(repayMethod[0]);
												break;

											case 1:
												isRepayMethod = "i";
												tvRepayType.setText(repayMethod[1]);
												break;

											case 2:
												isRepayMethod = "e";
												tvRepayType.setText(repayMethod[2]);
												break;

											default:
												break;
										}

									}
								}).show();
				break;
			case R.id.tv_pact:
				break;
			case R.id.rl_choose_bank:

				int cardNum = PreferencesUtils.getInt(this, Constant.AppConfigInfo.BANK_CARD_NUM, -1);

				if (cardNum == -1) {
					getProgressDialog().setMessage("数据验证中...");
					getProgressDialog().show();
					Subscription subscribe = LoanFactory.getLoanSingleton().getCardNum()
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
									T.showShort(LoanAppraiseResultActivity.this, "数据验证失败,请稍后重试!");
								}

								@Override
								public void onNext(HttpResultModel httpResultModel) {
									if (httpResultModel.getResultCode() == Constant.SUCCESS) {
										PreferencesUtils.putInt(LoanAppraiseResultActivity.this, Constant
														.AppConfigInfo.BANK_CARD_NUM,
												Integer
														.valueOf(httpResultModel.getData().toString()));
										process();
									} else {
										T.showShort(LoanAppraiseResultActivity.this, "数据验证失败,请稍后重试!");
									}
								}
							});
					addSubscription(subscribe);
				} else {
					process();
				}

				break;
			case R.id.btn_submit:
				loanPost();
				break;
		}
	}


	/**
	 * 判断是否有银行卡,有就选择,没有就新增
	 */
	private void process() {
		if (VerificationUtils.isBindCard()) {
			Intent intent = new Intent(this, WithdrawBankCardActivity.class);
			intent.putExtra("type", "selected");
			startActivityForResult(intent, REQUEST_BANK);
		} else {
			VerificationUtils.prefixCondition(this, Constant.PRE_ADD_BANK);
		}
	}

	private void registerBroadReceive() {
		receiver = new MyBroadcastReceive();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constant.ACTION_ADD_BANK);
		registerReceiver(receiver, filter);
	}


	private class MyBroadcastReceive extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Constant.ACTION_ADD_BANK)) {//选择银行卡

				bankId = intent.getStringExtra("bankId");

				bankName = intent.getStringExtra("bankName");

				bankCode = intent.getStringExtra("bankCode");

				tvBankName.setText(bankName +" " +bankCode.substring(bankCode.length() - 4, bankCode.length()));

			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}


	private void loanPost() {
		String price = etPrice.getText().toString();
		String deadLine = etDeadline.getText().toString();

		if (TextUtils.isEmpty(price)) {
			T.showShort(this, "请填写借款金额");
			return;
		}

		if (TextUtils.isEmpty(deadLine)) {
			T.showShort(this, "请填写借款期限");
			return;
		}

		if (TextUtils.isEmpty(bankId)) {
			T.showShort(this, "请选择银行卡");
			return;
		}

		presenter.loanPost(String.valueOf(Double.valueOf(price) * 10000), deadLine, isRepayMethod, bankId);
	}

	@Override
	public void showProgress(String msg) {
		getProgressDialog().setMessage(msg);
		getProgressDialog().show();
	}

	@Override
	public void hideProgress() {
		getProgressDialog().dismiss();
	}

	@Override
	public void loanApplySuccess(String lid) {
		T.showShort(this, "借款申请成功");
		sendBroadcast(Constant.ACTION_ADD_LOAN_SUCCESS);
		Intent intent = new Intent(this, LoanAddSuccessActivity.class);
		intent.putExtra("lid", lid);
		startActivity(intent);
		finish();
	}

	@Override
	public void loanApplyError(boolean isShowError, String msg) {
		if (isShowError) {
			T.showShort(this, msg);
		} else {
			T.showShort(this, "借款申请失败");
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_BANK) {
			if (resultCode == RESULT_OK) {
				if (data == null)
					return;

				bankId = data.getStringExtra("bankId");

				bankName = data.getStringExtra("bankName");

				bankCode = data.getStringExtra("bankCode");

				tvBankName.setText(bankName + " " +bankCode.substring(bankCode.length() - 4, bankCode.length()));

			}
		}
	}

}
