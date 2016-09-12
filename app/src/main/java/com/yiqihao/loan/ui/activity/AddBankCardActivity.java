package com.yiqihao.loan.ui.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yiqihao.loan.Constant;
import com.yiqihao.loan.R;
import com.yiqihao.loan.entity.BankBranchInfoModel;
import com.yiqihao.loan.entity.BankCardInfoModel;
import com.yiqihao.loan.entity.BankCardListModel;
import com.yiqihao.loan.entity.CardBankInfoModel;
import com.yiqihao.loan.entity.CityAdapter;
import com.yiqihao.loan.entity.CityInfoModel;
import com.yiqihao.loan.entity.ProvinceInfoModel;
import com.yiqihao.loan.mvp.presenters.AddBankCardPresenter;
import com.yiqihao.loan.mvp.views.AddBankCardView;
import com.yiqihao.loan.ui.adapter.BankWheelAdapter;
import com.yiqihao.loan.ui.adapter.BranchAdapter;
import com.yiqihao.loan.ui.adapter.ProvinceAdapter;
import com.yiqihao.loan.utils.PreferencesUtils;
import com.yiqihao.loan.utils.StringUtils;
import com.yiqihao.loan.utils.T;
import com.yiqihao.loan.utils.VerificationUtils;
import com.yiqihao.loan.widget.wheel.WheelView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加银行卡
 * Created by 冯浩 on 16/8/11.
 */
public class AddBankCardActivity extends MvpActivity<AddBankCardView, AddBankCardPresenter> implements AddBankCardView {

	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.tv_bank_card_no)
	TextView tvBankCardNo;
	@BindView(R.id.et_bank_card_no)
	EditText etBankCardNo;
	@BindView(R.id.tv_bank)
	TextView tvBank;
	@BindView(R.id.iv_back)
	ImageView ivBack;
	@BindView(R.id.tv_bank_name)
	TextView tvBankName;
	@BindView(R.id.rl_bank)
	RelativeLayout rlBank;
	@BindView(R.id.tv_bank_provice)
	TextView tvBankProvice;
	@BindView(R.id.iv_back_provice)
	ImageView ivBackProvice;
	@BindView(R.id.tv_bank_provice_name)
	TextView tvBankProviceName;
	@BindView(R.id.rl_bank_provice)
	RelativeLayout rlBankProvice;
	@BindView(R.id.tv_bank_city)
	TextView tvBankCity;
	@BindView(R.id.iv_back_city)
	ImageView ivBackCity;
	@BindView(R.id.tv_bank_city_name)
	TextView tvBankCityName;
	@BindView(R.id.rl_bank_city)
	RelativeLayout rlBankCity;
	@BindView(R.id.et_bank_branch_name)
	EditText etBankBranchName;
	@BindView(R.id.et_phone_num)
	EditText etPhoneNum;
	@BindView(R.id.tv_code)
	TextView tvCode;
	@BindView(R.id.tv_get_code)
	TextView tvGetCode;
	@BindView(R.id.et_code)
	EditText etCode;
	@BindView(R.id.ll_bank_detail)
	LinearLayout llBankDetail;
	@BindView(R.id.btn_add)
	Button btnAdd;
	@BindView(R.id.root_layout)
	ScrollView rootLayout;
	@BindView(R.id.rl_bank_branch)
	RelativeLayout rlBankBranch;
	@BindView(R.id.toolbar_title)
	TextView toolbarTitle;

	/**
	 * 银行Pop
	 **/
	private PopupWindow bankPop;
	private WheelView bankWV;
	private BankWheelAdapter bankWheelAdapter;
	private int bankPos = -1;
	private List<CardBankInfoModel> cardBankList;
	private List<String> needbranchList;


	/**
	 * 省pop
	 **/
	private PopupWindow provincePop;
	private WheelView provinceWV;
	private ProvinceAdapter provinceAdapter;
	private List<ProvinceInfoModel> provinceList;
	/**
	 * 选择省的位置
	 **/
	private int provincePos = -1;

	/**
	 * 市Pop
	 **/
	private PopupWindow cityPop;
	private WheelView cityWV;
	private CityAdapter cityAdapter;
	private int cityPos = -1;

	/**
	 * 支行pop
	 **/
	private PopupWindow branchesPop;
	private WheelView branchWV;
	private BranchAdapter bankBranchAdapter;
	private List<BankBranchInfoModel> bankBranchList;
	private int branchPos = -1;

	private String pre;
	private String jump;
	private String type;

	private BankCardInfoModel bankInfo;


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_bank_card);
		ButterKnife.bind(this);
		initToolBar(toolbar, true);
		needbranchList = getIntent().getStringArrayListExtra("needbranch");
		cardBankList = getIntent().getParcelableArrayListExtra("cardBank");
		pre = getIntent().getStringExtra("pre");
		jump = getIntent().getStringExtra("jump");
		type = getIntent().getStringExtra("type");
		provinceList = StringUtils.getProvinceList(this);
		initView();
	}

	@NonNull
	@Override
	public AddBankCardPresenter createPresenter() {
		return new AddBankCardPresenter();
	}

	private void initView() {

		if (TextUtils.equals(type, "edit")) {

//			llTransferDate.setClickable(false);
//			llTransferDate.setFocusable(false);
//			llTransferDate.setFocusableInTouchMode(false);


			toolbarTitle.setText("编辑银行卡");
			bankInfo = getIntent().getParcelableExtra("bankinfo");
			tvBankName.setText(bankInfo.getBank());
			etBankCardNo.setText(bankInfo.getCard());
			etPhoneNum.setText(bankInfo.getMobile());
			if (isHaveBranch(bankInfo.getCode())) {
				llBankDetail.setVisibility(View.VISIBLE);
				tvBankProviceName.setText(StringUtils.getProvinceNameById(this,
						bankInfo.getArea()));
				tvBankCityName.setText(StringUtils.getCityNameById(this,
						bankInfo.getArea(), bankInfo.getCity()));
				etBankBranchName.setText(bankInfo.getBranch());
			} else {
				llBankDetail.setVisibility(View.GONE);
			}
		}

	}

	/**
	 * 显示银行列表
	 */
	private void showBankPop() {
		initBankPop();
		closeInput();
		bankPop.showAtLocation(rootLayout, Gravity.BOTTOM, 0, 0);
	}

	/**
	 * 初始化银行Pop
	 */
	private void initBankPop() {
		View bankView = LayoutInflater.from(this).inflate(
				R.layout.pop_add_bank_list, null);
		TextView cancelBtn = (TextView) bankView
				.findViewById(R.id.tv_pop_cancel);
		TextView confirmBtn = (TextView) bankView
				.findViewById(R.id.tv_pop_complete);
		bankWV = (WheelView) bankView
				.findViewById(R.id.wheel_pop);
		bankWV.setVisibleItems(7);
		bankPop = new PopupWindow(bankView, LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT, true);
		bankPop.setOutsideTouchable(true);
		bankPop.setBackgroundDrawable(new BitmapDrawable());
		bankPop.setAnimationStyle(R.style.PopAnimation);
		bankWheelAdapter = new BankWheelAdapter(this, cardBankList);
		bankWV.setViewAdapter(bankWheelAdapter);
		bankWV.setCurrentItem(0);
		cancelBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				bankPop.dismiss();
			}
		});
		confirmBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				bankPos = bankWV.getCurrentItem();
				etBankBranchName.setText("");
				tvBankCityName.setText("");
				tvBankProviceName.setText("");
				tvBankName.setText(cardBankList.get(bankPos).getName());
				if (isHaveBranch(cardBankList.get(bankPos).getCode())) {
					llBankDetail.setVisibility(View.VISIBLE);
				} else {
					llBankDetail.setVisibility(View.GONE);
				}
				bankPop.dismiss();
			}
		});
	}

	/**
	 * 显示省份列表
	 */
	private void showProvincePop() {
		if (provincePop == null) {
			initProvicePop();
		}
		closeInput();
		provincePop.showAtLocation(rootLayout, Gravity.BOTTOM, 0, 0);
	}

	/**
	 * 初始化省Pop
	 */
	private void initProvicePop() {
		View provinceView = LayoutInflater.from(this).inflate(
				R.layout.pop_add_bank_province, null);
		TextView cancelBtn = (TextView) provinceView
				.findViewById(R.id.tv_province_cancel);
		TextView confirmBtn = (TextView) provinceView
				.findViewById(R.id.tv_province_complete);
		provinceWV = (WheelView) provinceView
				.findViewById(R.id.wheel_province);
		provinceWV.setVisibleItems(7);
		provincePop = new PopupWindow(provinceView, LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT, true);
		// 设置点击pop外pop消失
		provincePop.setOutsideTouchable(true);
		provincePop.setBackgroundDrawable(new BitmapDrawable());
		provincePop.setAnimationStyle(R.style.PopAnimation);

		provinceAdapter = new ProvinceAdapter(this, provinceList);
		provinceWV.setViewAdapter(provinceAdapter);
		provinceWV.setCurrentItem(0);
		cancelBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				provincePop.dismiss();
			}
		});
		confirmBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				provincePos = provinceWV.getCurrentItem();
				cityPos = -1;
				tvBankCityName.setText("");
				etBankBranchName.setText("");
				tvBankProviceName.setText(provinceList.get(
						provinceWV.getCurrentItem()).getName());
				provincePop.dismiss();
			}
		});
	}

	/**
	 * 显示市Pop
	 */
	private void showCityPop(List<CityInfoModel> list) {
		initCityPop(list);
		closeInput();
		cityPop.showAtLocation(rootLayout, Gravity.BOTTOM, 0, 0);

	}

	private void initCityPop(final List<CityInfoModel> list) {
		View cityView = LayoutInflater.from(this).inflate(
				R.layout.pop_add_bank_city, null);
		TextView cancelBtn = (TextView) cityView
				.findViewById(R.id.tv_city_cancel);
		TextView confirmBtn = (TextView) cityView
				.findViewById(R.id.tv_city_complete);
		cityWV = (WheelView) cityView.findViewById(R.id.wheel_city);
		cityWV.setVisibleItems(7);
		cityPop = new PopupWindow(cityView, LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT, true);
		cityPop.setOutsideTouchable(true);
		cityPop.setBackgroundDrawable(new BitmapDrawable());
		cityPop.setAnimationStyle(R.style.PopAnimation);
		cityAdapter = new CityAdapter(this, list);
		cityWV.setViewAdapter(cityAdapter);
		cityWV.setCurrentItem(0);
		cancelBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				cityPop.dismiss();
			}
		});
		confirmBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				cityPos = cityWV.getCurrentItem();
				etBankBranchName.setText("");
				tvBankCityName.setText(list.get(cityPos).getName());
				cityPop.dismiss();
			}
		});
	}

	/***
	 * 银行支行搜索
	 */
	private void getBankBranches() {

		String cityID = tvBankCity.getText().toString();
		if (cityPos > -1) {
			if (provincePos > -1) {
				cityID = provinceList.get(provincePos).getCityList()
						.get(cityPos).getId();
			} else {
				if (type.equals("edit")) {
					cityID = StringUtils
							.getCityListByProvinceId(this, bankInfo.getArea())
							.get(cityPos).getId();
				} else {
					T.showShort(this, "请选择省份");
					return;
				}
			}
		} else {
			if (type.equals("edit")) {
				cityID = bankInfo.getCity();
			} else {
				T.showShort(this, "请选择城市");
				return;
			}
		}
		String bankCode = "";
		if (bankPos > -1) {
			bankCode = cardBankList.get(bankPos).getCode();
		} else {
			if (type.equals("edit")) {
				bankCode = bankInfo.getCode();
			} else {
				T.showShort(this, "请选择银行");
				return;
			}
		}

		presenter.searchBranchName(cityID, bankCode);
	}

	/**
	 * 初始化银行支行Pop
	 */
	private void initBankBranchPop(List<BankBranchInfoModel> list) {
		showBankBranchPop(list);
		closeInput();
		branchesPop.showAtLocation(rootLayout, Gravity.BOTTOM, 0, 0);
	}

	/**
	 * 显示银行支行pop
	 */
	private void showBankBranchPop(final List<BankBranchInfoModel> list) {
		View branchView = LayoutInflater.from(this).inflate(
				R.layout.pop_add_bank_branch, null);
		TextView cancelBtn = (TextView) branchView
				.findViewById(R.id.tv_branch_cancel);
		TextView confirmBtn = (TextView) branchView
				.findViewById(R.id.tv_branch_complete);
		TextView handInputBtn = (TextView) branchView
				.findViewById(R.id.tv_branch_handinput);

		branchWV = (WheelView) branchView
				.findViewById(R.id.wheel_branch);
		branchWV.setVisibleItems(7);
		branchesPop = new PopupWindow(branchView, LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT, true);
		branchesPop.setOutsideTouchable(true);
		branchesPop.setBackgroundDrawable(new BitmapDrawable());
		branchesPop.setAnimationStyle(R.style.PopAnimation);
		bankBranchAdapter = new BranchAdapter(this, list);
		branchWV.setViewAdapter(bankBranchAdapter);
		branchWV.setCurrentItem(0);
		etBankBranchName.setHorizontallyScrolling(false);
		etBankBranchName.setSingleLine(false);
		cancelBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				branchesPop.dismiss();
			}
		});
		handInputBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				branchesPop.dismiss();
				etBankBranchName.setVisibility(View.VISIBLE);
				etBankBranchName.setFocusable(true);
				etBankBranchName.setFocusableInTouchMode(true);
				etBankBranchName.setHorizontallyScrolling(false);
				etBankBranchName.setSingleLine(false);
				etBankBranchName.clearFocus();
				etBankBranchName.requestFocus();
				etBankBranchName.setSelection(etBankBranchName.getText().toString()
						.length());
				showInputMethod(etBankBranchName);
			}
		});
		confirmBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				branchPos = branchWV.getCurrentItem();
				if (list.size() - 1 >= branchPos)
					etBankBranchName.setText(list.get(branchPos).getBranch());
				branchesPop.dismiss();
			}
		});

	}


	/**
	 * 判断是否要输入支行信息
	 *
	 * @param bankCode
	 * @return
	 */
	private boolean isHaveBranch(String bankCode) {
		for (int i = 0; i < needbranchList.size(); i++) {
			String bank = needbranchList.get(i);
			if (bank.equals(bankCode)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 显示软键盘
	 *
	 * @param editText
	 */
	private void showInputMethod(EditText editText) {
		InputMethodManager iManager = ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE));
		iManager.hideSoftInputFromWindow(editText.getWindowToken(), 0); //有效 如果显示就隐藏，如果隐藏就显示
		iManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}

	@OnClick({R.id.rl_bank, R.id.rl_bank_provice, R.id.rl_bank_city, R.id.tv_get_code, R.id.btn_add, R.id
			.et_bank_branch_name})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.rl_bank://获取银行卡列表

				if (cardBankList == null) {
					presenter.searchBankList();
				} else {
					showBankPop();
				}

				break;
			case R.id.rl_bank_provice://获取省份列表
				showProvincePop();
				break;
			case R.id.rl_bank_city://获取市
				if (provincePos > -1) {
					showCityPop(provinceList.get(provincePos).getCityList());
				}
				break;
			case R.id.et_bank_branch_name:
				getBankBranches();
				break;
			case R.id.tv_get_code:
				String phoneNum = etPhoneNum.getText().toString().replace(" ", "");

				if (TextUtils.isEmpty(phoneNum)) {
					T.showShort(this, "请输入预留手机号");
					return;
				}

				presenter.sendSmsCode(phoneNum);

				break;
			case R.id.btn_add:


				String card = etBankCardNo.getText().toString().replace(" ", "");
				String smscode = etCode.getText().toString();
				String phone = etPhoneNum.getText().toString().replace(" ", "");

				if (TextUtils.isEmpty(card)) {
					T.showShort(this, "请输入银行卡号");
					return;
				}

				if (TextUtils.isEmpty(phone)) {
					T.showShort(this, "请输入预留手机号");
					return;
				}

				if (TextUtils.isEmpty(smscode)) {
					T.showShort(this, "请输入短信验证码");
					return;
				}

				String code;
				if (bankPos > -1) {
					code = cardBankList.get(bankPos).getCode();
				} else {
					if (type.equals("edit")) {
						code = bankInfo.getCode();
					} else {
						T.showShort(this, "请选择银行");
						return;
					}
				}

				Map<String, String> params = new HashMap<String, String>();
				params.put("code", code);
				params.put("card", card);
				params.put("mobile", phone);
				params.put("smscode", smscode);

				String province;
				String city;
				String bankName;

				if (llBankDetail.isShown()) {
					if (provincePos > -1) {
						province = provinceList.get(provincePos).getId();
						params.put("province", province);
					} else {

						if (type.equals("edit")) {
							province = bankInfo.getArea();
							params.put("province", province);

						}else {
							T.showShort(this, "请选择省份");
							return;
						}

					}

					if (cityPos > -1) {
						if (provincePos > -1) {// 修改过城市以及省
							city = provinceList.get(provincePos).getCityList()
									.get(cityPos).getId();
							params.put("city", city);
						} else {// 没有修改过省份，修改过城市
							city = StringUtils
									.getCityListByProvinceId(this, bankInfo.getArea())
									.get(cityPos).getId();
							params.put("city", city);
						}
					} else {// 没有修改过城市
						if (type.equals("edit")) {
							city = bankInfo.getCity();
							params.put("city", city);
						}else {
							T.showShort(this, "请选择城市");
							return;
						}
					}


					if (branchPos > -1) {
						bankName = bankBranchList.get(branchPos).getBranch();
						params.put("bankname", bankName);
					} else {
						bankName = etBankBranchName.getText().toString();
						if (TextUtils.isEmpty(bankName)) {
							T.showShort(this, "请选择开户行");
							return;
						}
						params.put("bankname", bankName);

					}

				}


				if (TextUtils.equals(type, "edit")) {
					params.put("bankid", bankInfo.getId());

					presenter.editBankCard(params);

				} else {
					presenter.addBankCard(params);
				}

				break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (timer != null)
			timer.cancel();
	}

	private boolean isCodeWait = false;

	int timecode;
	Timer timer;

	private void stactTimer() {
		isCodeWait = true;
		timecode = 60;
		if (timer != null)
			timer.cancel();
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
			}
		}, 0, 1000);
		tvGetCode.setFocusable(false);
		tvGetCode.setClickable(false);
	}

	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			switch (msg.what) {
				case 1:
					timecode--;
					if (timecode != 0) {
						tvGetCode.setText(getString(R.string.afresh_obtain_seccode) + "(" + timecode + ")");
					} else {
						tvGetCode.setText(getString(R.string.afresh_obtain_seccode));
						cancelSendCode();
					}
					break;
			}
			super.handleMessage(msg);
		}
	};

	private void cancelSendCode() {
		isCodeWait = false;

		if (timer != null)
			timer.cancel();

		btnCodeStatus(true);
		tvGetCode.setText("获取验证码");
	}

	/**
	 * 改变验证码按钮
	 *
	 * @param flag
	 */
	private void btnCodeStatus(boolean flag) {
		if (flag) {
			tvGetCode.setFocusable(true);
			tvGetCode.setClickable(true);
		} else {
			tvGetCode.setFocusable(false);
			tvGetCode.setClickable(false);
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
	public void getBankListSuccess(BankCardListModel bankCardListModel) {
		needbranchList = bankCardListModel.getNeedbranch();
		cardBankList = bankCardListModel.getCardbank();
		showBankPop();
	}

	@Override
	public void getBankListError(boolean isShow, String msg) {
		if (isShow) {
			T.showShort(this, msg);
		} else {
			T.showShort(this, "获取银行信息失败");
		}
	}

	@Override
	public void onGetBranchInfoSucceed(List<BankBranchInfoModel> list) {

		if (list == null || list.size() == 0) {
			T.showShort(this, "暂无支行信息,请手动输入");
			etBankBranchName.setVisibility(View.VISIBLE);
			etBankBranchName.setFocusable(true);
			etBankBranchName.setFocusableInTouchMode(true);
			etBankBranchName.setHorizontallyScrolling(false);
			etBankBranchName.setSingleLine(false);
			etBankBranchName.clearFocus();
			etBankBranchName.requestFocus();
			etBankBranchName.setSelection(etBankBranchName.getText().toString()
					.length());
			showInputMethod(etBankBranchName);
		} else {
			etBankBranchName.setInputType(InputType.TYPE_NULL);
			bankBranchList = list;
			initBankBranchPop(bankBranchList);
		}
	}

	@Override
	public void onGetBranchInfoError(boolean isShow, String msg) {

		if (isShow) {
			T.showShort(this, msg);
		} else {
			T.showShort(this, "获取支行信息失败");
		}
	}

	@Override
	public void sendSmsCodeSuccess(String msg) {
		stactTimer();
	}


	@Override
	public void sendSmsCodeError(boolean isShowError, String msg) {
		cancelSendCode();
		if (isShowError) {
			T.showShort(this, "" + msg);
		} else {
			T.showShort(this, "验证码发送失败");
		}
	}

	@Override
	public void onAddBankSucceed(String bankId) {
		T.showShort(this, "添加银行卡成功");
		sendBroadcast(Constant.ACTION_BIND_BANK_SUCCESS);
		PreferencesUtils.putInt(this, Constant.AppConfigInfo.BANK_CARD_NUM, 1);

		if (TextUtils.equals(pre, "pre")) {
			VerificationUtils.prefixCondition(this, jump);
			Intent intent=new Intent(Constant.ACTION_ADD_BANK);
			intent.putExtra("bankId",bankId);
			intent.putExtra("bankCode",etBankCardNo.getText().toString().replace(" ", ""));
			intent.putExtra("bankName",tvBankName.getText().toString());
			sendBroadcast(intent);
		}

		finish();
	}

	@Override
	public void onAddBankError(boolean b, String errorMsg) {
		if (b) {
			T.showShort(this, errorMsg);
		} else {
			T.showShort(this, "添加银行卡失败");
		}

	}

	@Override
	public void onEditBankSucceed() {
		T.showShort(this, "修改银行卡成功");



		sendBroadcast(Constant.ACTION_BIND_BANK_SUCCESS);
		finish();
	}

	@Override
	public void onEditBankError(boolean b, String errorMsg) {
		if (b) {
			T.showShort(this, errorMsg);
		} else {
			T.showShort(this, "修改银行卡失败");
		}
	}
}
