package com.yiqihao.loan.ui.activity;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiqihao.loan.Constant;
import com.yiqihao.loan.R;
import com.yiqihao.loan.mvp.presenters.LoanApplyPresenter;
import com.yiqihao.loan.mvp.views.LoanApplyView;
import com.yiqihao.loan.utils.T;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 借款申请
 * Created by 冯浩 on 16/8/17.
 */
public class LoanApplyActivity extends MvpActivity<LoanApplyView, LoanApplyPresenter> implements LoanApplyView {

	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.tv_car_model)
	TextView tvCarModel;
	@BindView(R.id.rl_car_model)
	RelativeLayout rlCarModel;
	@BindView(R.id.tv_is_car_loan)
	TextView tvIsCarLoan;
	@BindView(R.id.rl_is_car_loan)
	RelativeLayout rlIsCarLoan;
	@BindView(R.id.tv_reg_date)
	TextView tvRegDate;
	@BindView(R.id.rl_reg_date)
	RelativeLayout rlRegDate;
	@BindView(R.id.et_new_car_price)
	EditText etNewCarPrice;
	@BindView(R.id.et_car_color)
	EditText etCarColor;
	@BindView(R.id.et_kilometers)
	EditText etKilometers;
	@BindView(R.id.btn_submit)
	Button btnSubmit;
	@BindView(R.id.tv_year)
	TextView tvYear;
	@BindView(R.id.rl_year)
	RelativeLayout rlYear;

	//0201 无  0202 有
	private String[] isCarLoanStrs = new String[]{"无", "有"};

	private String isCarLoanSelected;

	private String brand_id;
	private String series_id;
	private String model_id;

	private MyBroadcastReceive receiver;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loan_apply);
		ButterKnife.bind(this);
		initToolBar(toolbar, true);
		initView();
		registerBroadReceive();
	}

	private void registerBroadReceive() {
		receiver = new MyBroadcastReceive();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constant.ACTION_CAR_INFO);
		registerReceiver(receiver, filter);
	}


	private class MyBroadcastReceive extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Constant.ACTION_CAR_INFO)) {//添加成功
				String carName = intent.getStringExtra("carName");
				String brand_name = intent.getStringExtra("brand_name");
				String series_name = intent.getStringExtra("series_name");
				brand_id = intent.getStringExtra("brandId");
				series_id = intent.getStringExtra("seriesId");
				model_id = intent.getStringExtra("modelId");
				tvCarModel.setText(brand_name+" · "+series_name+" · "+carName);

			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}

	@NonNull
	@Override
	public LoanApplyPresenter createPresenter() {
		return new LoanApplyPresenter();
	}

	private void initView() {
	}

	@OnClick({R.id.rl_car_model, R.id.rl_is_car_loan, R.id.rl_reg_date, R.id.btn_submit, R.id.rl_year})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.rl_car_model:
				Intent intent = new Intent(this, CarChooseActivity.class);
				startActivity(intent);
				break;
			case R.id.rl_year:

				if (TextUtils.isEmpty(model_id)) {
					T.showShort(LoanApplyActivity.this, "请先选择汽车品牌");
					return;
				}

				presenter.getCarYear(model_id);
				break;
			case R.id.rl_is_car_loan:
				new AlertDialog.Builder(this)
						.setTitle("是否有车贷")
						.setItems(isCarLoanStrs,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {

										switch (which) {
											case 0:
												isCarLoanSelected = "0201";
												tvIsCarLoan.setText(isCarLoanStrs[0]);
												break;

											case 1:
												isCarLoanSelected = "0202";
												tvIsCarLoan.setText(isCarLoanStrs[1]);
												break;

											default:
												break;
										}

									}
								}).show();
				break;
			case R.id.rl_reg_date:
				Calendar c = Calendar.getInstance();
				// 直接创建一个DatePickerDialog对话框实例，并将它显示出来
				new DatePickerDialog(this,
						// 绑定监听器
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
												  int monthOfYear, int dayOfMonth) {

								tvRegDate.setText(year + "-" + (monthOfYear + 1) + "-" +
										dayOfMonth);
							}
						}
						// 设置初始日期
						, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
						.get(Calendar.DAY_OF_MONTH)).show();
				break;
			case R.id.btn_submit:
				String carModel = tvCarModel.getText().toString();
				String isCarLoan = isCarLoanSelected;
				String regDate = tvRegDate.getText().toString();
				String price = etNewCarPrice.getText().toString();
				String carColor = etCarColor.getText().toString();
				String kilometers = etKilometers.getText().toString();
				String year = tvYear.getText().toString();

				if (TextUtils.isEmpty(carModel)) {
					T.showShort(this, "请选择品牌型号");
					return;
				}

				if (TextUtils.isEmpty(year)) {
					T.showShort(this, "请选择汽车年份");
					return;
				}

				if (TextUtils.isEmpty(isCarLoan)) {
					T.showShort(this, "请选择是否有车贷");
					return;
				}

				if (TextUtils.isEmpty(regDate)) {
					T.showShort(this, "请选择初次登记日期");
					return;
				}

				if (TextUtils.isEmpty(price)) {
					T.showShort(this, "请选择初次登记日期");
					return;
				}

				if (TextUtils.isEmpty(carColor)) {
					T.showShort(this, "请输入车身颜色");
					return;
				}

				if (TextUtils.isEmpty(kilometers)) {
					T.showShort(this, "请输入行驶公里");
					return;
				}

				presenter.loanAppraise( carModel, isCarLoan, regDate, Double.valueOf(price) * 10000,
						carColor, kilometers, brand_id, series_id, model_id, year);


				break;
		}
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
	public void loanAppraiseSuccess(String appraise_amount) {
		Intent intent = new Intent(this, LoanAppraiseResultActivity.class);
		intent.putExtra("amount", appraise_amount);
		startActivity(intent);
		finish();
	}

	@Override
	public void loanAppraiseError(boolean isShowError, String msg) {
		if (isShowError) {
			T.showShort(this, msg);
		} else {
			T.showShort(this, "评估失败");
		}
	}

	@Override
	public void getCarListSuccess(List<String> list) {

		final String[] strs = new String[list.size()];

		for (int i = 0; i < list.size(); i++) {
			strs[i] = list.get(i);
		}

		new AlertDialog.Builder(this)
				.setTitle("选择汽车年份")
				.setItems(strs,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {

								tvYear.setText(strs[which]);

							}
						}).show();
	}

	@Override
	public void getCarListError(boolean isShowError, String msg) {
		if (isShowError) {
			T.showShort(this, "" + msg);
		} else {
			T.showShort(this, "获取数据失败");
		}
	}

}
