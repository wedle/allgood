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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiqihao.loan.Constant;
import com.yiqihao.loan.R;
import com.yiqihao.loan.entity.CarInfoModel;
import com.yiqihao.loan.mvp.presenters.CarInfoPresenter;
import com.yiqihao.loan.mvp.views.CarInfoView;
import com.yiqihao.loan.utils.T;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 车辆信息
 * Created by 冯浩 on 16/8/18.
 */
public class CarInfoActivity extends MvpActivity<CarInfoView, CarInfoPresenter> implements CarInfoView {

	private static final String TAG = "CarInfoActivity";

	@BindView(R.id.toolbar_title)
	TextView toolbarTitle;
	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.tv_car_model)
	TextView tvCarModel;
	@BindView(R.id.tv_kilometer)
	EditText tvKilometer;
	@BindView(R.id.tv_car_color)
	EditText tvCarColor;
	@BindView(R.id.tv_car_number)
	EditText tvCarNumber;
	@BindView(R.id.tv_chassis_number)
	EditText tvChassisNumber;
	@BindView(R.id.tv_engine_number)
	EditText tvEngineNumber;
	@BindView(R.id.tv_reg_certificate)
	EditText tvRegCertificate;
	@BindView(R.id.tv_reg_date)
	TextView tvRegDate;
	@BindView(R.id.ll_reg_date)
	LinearLayout llRegDate;
	@BindView(R.id.tv_price)
	EditText tvPrice;
	@BindView(R.id.btn_save)
	Button btnSave;
	@BindView(R.id.tv_kaipiao_date)
	TextView tvKaipiaoDate;
	@BindView(R.id.ll_kaipiao_date)
	LinearLayout llKaipiaoDate;
	@BindView(R.id.tv_baodan)
	EditText tvBaodan;
	@BindView(R.id.tv_record)
	EditText tvRecord;
	@BindView(R.id.tv_count)
	EditText tvCount;
	@BindView(R.id.tv_transfer_date)
	TextView tvTransferDate;
	@BindView(R.id.ll_transfer_date)
	LinearLayout llTransferDate;
	@BindView(R.id.tv_loan_type)
	TextView tvLoanType;
	@BindView(R.id.rl_loan_type)
	RelativeLayout rlLoanType;
	@BindView(R.id.rl_car_model)
	RelativeLayout rlCarModel;

	private String type;
	private String lid;
	private CarInfoModel carInfoModel;

	//0201 全款车  0202 按揭车
	private String[] isCarTypeStrs = new String[]{"全款车", "按揭车"};

	private String isCarLoanSelected;

	private MyBroadcastReceive receiver;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car_info);
		ButterKnife.bind(this);
		initToolBar(toolbar, true);
		type = getIntent().getStringExtra("type");
		lid = getIntent().getStringExtra("lid");
		carInfoModel = getIntent().getParcelableExtra("carInfo");
		registerBroadReceive();
		initView();
	}

	@NonNull
	@Override
	public CarInfoPresenter createPresenter() {
		return new CarInfoPresenter();
	}

	private void initView() {

		if (TextUtils.equals(type, "look")) {

			rlCarModel.setClickable(false);
			rlCarModel.setFocusable(false);
			rlCarModel.setFocusableInTouchMode(false);

			toolbarTitle.setText("车辆信息");
			btnSave.setVisibility(View.GONE);

			tvKilometer.setFocusable(false);
			tvKilometer.setFocusableInTouchMode(false);

			tvCarColor.setFocusable(false);
			tvCarColor.setFocusableInTouchMode(false);

			tvCarNumber.setFocusable(false);
			tvCarNumber.setFocusableInTouchMode(false);

			tvChassisNumber.setFocusable(false);
			tvChassisNumber.setFocusableInTouchMode(false);

			tvEngineNumber.setFocusable(false);
			tvEngineNumber.setFocusableInTouchMode(false);

			tvRegCertificate.setFocusable(false);
			tvRegCertificate.setFocusableInTouchMode(false);

			tvPrice.setFocusable(false);
			tvPrice.setFocusableInTouchMode(false);


			tvBaodan.setFocusable(false);
			tvBaodan.setFocusableInTouchMode(false);


			tvRecord.setFocusable(false);
			tvRecord.setFocusableInTouchMode(false);


			tvCount.setFocusable(false);
			tvCount.setFocusableInTouchMode(false);

			llRegDate.setClickable(false);
			llRegDate.setFocusable(false);
			llRegDate.setFocusableInTouchMode(false);

			llKaipiaoDate.setClickable(false);
			llKaipiaoDate.setFocusable(false);
			llKaipiaoDate.setFocusableInTouchMode(false);

			llTransferDate.setClickable(false);
			llTransferDate.setFocusable(false);
			llTransferDate.setFocusableInTouchMode(false);

			rlLoanType.setClickable(false);
			rlLoanType.setFocusable(false);
			rlLoanType.setFocusableInTouchMode(false);


		} else {
			toolbarTitle.setText("补充车辆信息");
			btnSave.setVisibility(View.VISIBLE);
		}

		tvCarModel.setText(carInfoModel.getCarBrandsOther());
		tvKilometer.setText(carInfoModel.getCarKilometers());
		tvCarColor.setText(carInfoModel.getCarColor());
		tvCarNumber.setText(carInfoModel.getCarPlate());
		tvChassisNumber.setText(carInfoModel.getVehicleFrameNO());
		tvEngineNumber.setText(carInfoModel.getEngineNumber());
		tvRegCertificate.setText(carInfoModel.getCertificate());
		tvRegDate.setText(carInfoModel.getInitDate());
		tvPrice.setText(carInfoModel.getInvoicePrice());
		tvKaipiaoDate.setText(carInfoModel.getInvoiceDate());
		tvBaodan.setText(carInfoModel.getInsurancePolicy());
		tvRecord.setText(carInfoModel.getEndorsement());
		tvCount.setText(carInfoModel.getTransferCount());
		tvTransferDate.setText(carInfoModel.getTransferTime());

		isCarLoanSelected = carInfoModel.getCreditType();

		if (TextUtils.equals(isCarLoanSelected, "0201")) {
			tvLoanType.setText("全款车");
		} else if (TextUtils.equals(isCarLoanSelected, "0202")) {
			tvLoanType.setText("按揭车");
		}

	}


	@OnClick(R.id.btn_save)
	public void onSaveClick() {
		String carModel = tvCarModel.getText().toString();
		String kilometer = tvKilometer.getText().toString();
		String carColor = tvCarColor.getText().toString();
		String carNum = tvCarNumber.getText().toString();
		String chassisNum = tvChassisNumber.getText().toString();
		String engineNum = tvEngineNumber.getText().toString();
		String regCertificate = tvRegCertificate.getText().toString();
		String regDate = tvRegDate.getText().toString();
		String price = tvPrice.getText().toString();
		String kaipiaoDate = tvKaipiaoDate.getText().toString();//开票日期
		String baodan = tvBaodan.getText().toString();//保单
		String record = tvRecord.getText().toString();//违章记录
		String count = tvCount.getText().toString();//过户次数
		String transferDate = tvTransferDate.getText().toString();//过户时间

		if (TextUtils.isEmpty(carModel)) {
			T.showShort(this, "请填写品牌型号");
			return;
		}

		if (TextUtils.isEmpty(kilometer)) {
			T.showShort(this, "请填公里数");
			return;
		}


		if (TextUtils.isEmpty(carColor)) {
			T.showShort(this, "请填写车身颜色");
			return;
		}


		if (TextUtils.isEmpty(carNum)) {
			T.showShort(this, "请填写车牌号");
			return;
		}

		if (TextUtils.isEmpty(chassisNum)) {
			T.showShort(this, "请填写车架号");
			return;
		}

		if (TextUtils.isEmpty(engineNum)) {
			T.showShort(this, "请填写发动机号");
			return;
		}
		if (TextUtils.isEmpty(regCertificate)) {
			T.showShort(this, "请填写登记证书");
			return;
		}

		if (TextUtils.isEmpty(regDate)) {
			T.showShort(this, "请选择初次登记日期");
			return;
		}

		if (TextUtils.isEmpty(price)) {
			T.showShort(this, "请填写开票价格");
			return;
		}


		if (TextUtils.isEmpty(kaipiaoDate)) {
			T.showShort(this, "请选择开票日期");
			return;
		}


		if (TextUtils.isEmpty(baodan)) {
			T.showShort(this, "请填写保单");
			return;
		}


		if (TextUtils.isEmpty(record)) {
			T.showShort(this, "请填写违章记录");
			return;
		}


		if (TextUtils.isEmpty(count)) {
			T.showShort(this, "请填写过户次数");
			return;
		}


		if (TextUtils.isEmpty(transferDate)) {
			T.showShort(this, "请选择过户时间");
			return;
		}


		if (TextUtils.isEmpty(isCarLoanSelected)) {
			T.showShort(this, "请选择借款类型");
			return;
		}


		Map<String, String> map = new HashMap<>();
		map.put("lid", lid);
		map.put("insurancePolicy", baodan);
		map.put("travelkilo", kilometer);
		map.put("carcolor", carColor);
		map.put("carbrand", carModel);
		map.put("endorsement", record);
		map.put("carno", carNum);
		map.put("vehicleFrameNO", chassisNum);
		map.put("engineNumber", engineNum);
		map.put("certificate", regCertificate);
		map.put("initDate", regDate);
		map.put("transferCount", count);
		map.put("transferTime", transferDate);
		map.put("invoicePrice", price);
		map.put("invoiceDate", kaipiaoDate);
		map.put("creditType", isCarLoanSelected);
		presenter.saveCarInfo(map);

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
				tvCarModel.setText(brand_name+" · "+series_name+" · "+carName);

			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}

	@OnClick({R.id.ll_kaipiao_date, R.id.ll_transfer_date, R.id.rl_loan_type, R.id.rl_car_model})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.ll_kaipiao_date:
				Calendar c = Calendar.getInstance();
				// 直接创建一个DatePickerDialog对话框实例，并将它显示出来
				new DatePickerDialog(this,
						// 绑定监听器
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
												  int monthOfYear, int dayOfMonth) {

								tvKaipiaoDate.setText(year + "-" + (monthOfYear + 1) + "-" +
										dayOfMonth);
							}
						}
						// 设置初始日期
						, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
						.get(Calendar.DAY_OF_MONTH)).show();
				break;
			case R.id.ll_transfer_date:
				Calendar c1 = Calendar.getInstance();
				// 直接创建一个DatePickerDialog对话框实例，并将它显示出来
				new DatePickerDialog(this,
						// 绑定监听器
						new DatePickerDialog.OnDateSetListener() {

							@Override
							public void onDateSet(DatePicker view, int year,
												  int monthOfYear, int dayOfMonth) {

								tvTransferDate.setText(year + "-" + (monthOfYear + 1) + "-" +
										dayOfMonth);
							}
						}
						// 设置初始日期
						, c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1
						.get(Calendar.DAY_OF_MONTH)).show();
				break;
			case R.id.rl_loan_type:
				new AlertDialog.Builder(this)
						.setTitle("请选择借款类型")
						.setItems(isCarTypeStrs,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {

										switch (which) {
											case 0:
												isCarLoanSelected = "0201";
												tvLoanType.setText(isCarTypeStrs[0]);
												break;

											case 1:
												isCarLoanSelected = "0202";
												tvLoanType.setText(isCarTypeStrs[1]);
												break;

											default:
												break;
										}

									}
								}).show();


				break;

			case R.id.rl_car_model:
				Intent intent = new Intent(this, CarChooseActivity.class);
				startActivity(intent);
				break;
		}
	}

	@OnClick(R.id.ll_reg_date)
	public void onClick() {
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
	public void saveSuccess() {
		T.showShort(this, "保存成功");
		sendBroadcast(Constant.ACTION_ADD_LOAN_SUCCESS);
		Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.setAction("tabClient");
		startActivity(intent);
	}

	@Override
	public void saveError(boolean isShowError, String msg) {
		if (isShowError) {
			T.showShort(this, "" + msg);
		} else {
			T.showShort(this, "保存失败");
		}
	}
}
