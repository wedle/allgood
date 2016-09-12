package com.yiqihao.loan.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yiqihao.loan.Constant;
import com.yiqihao.loan.MyApplication;
import com.yiqihao.loan.R;
import com.yiqihao.loan.entity.UserInfoResultModel;
import com.yiqihao.loan.mvp.presenters.RegisterPresenter;
import com.yiqihao.loan.mvp.views.RegisterView;
import com.yiqihao.loan.utils.PreferencesUtils;
import com.yiqihao.loan.utils.T;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册
 * Created by 冯浩 on 16/8/17.
 */
public class RegisterActivity extends MvpActivity<RegisterView, RegisterPresenter> implements RegisterView {

	private static final String TAG = "RegisterActivity";

	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.et_phone)
	EditText etPhone;
	@BindView(R.id.tv_code)
	TextView tvCode;
	@BindView(R.id.tv_get_code)
	TextView tvGetCode;
	@BindView(R.id.et_code)
	EditText etCode;
	@BindView(R.id.et_password)
	EditText etPassword;
	@BindView(R.id.et_confirm_password)
	EditText etConfirmPassword;
	@BindView(R.id.btn_register)
	Button btnRegister;

	private boolean isCodeWait = false;

	private String flag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		ButterKnife.bind(this);
		flag=getIntent().getStringExtra("flag");
		initToolBar(toolbar, true);
		initView();
	}

	private void initView() {

	}

	@NonNull
	@Override
	public RegisterPresenter createPresenter() {
		return new RegisterPresenter();
	}

	@OnClick({R.id.tv_get_code, R.id.btn_register})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.tv_get_code:
				String phone = etPhone.getText().toString().replace(" ", "");

				if (TextUtils.isEmpty(phone)) {
					T.showShort(RegisterActivity.this, "请输入手机号");
					return;
				}

				presenter.sendSmsCode(phone);

				break;
			case R.id.btn_register:

				String phoneNum = etPhone.getText().toString().replace(" ", "");

				String code = etCode.getText().toString();

				String pwd = etPassword.getText().toString();

				String confirmPwd = etConfirmPassword.getText().toString();

				if (TextUtils.isEmpty(phoneNum)) {
					T.showShort(RegisterActivity.this, "请输入手机号");
					return;
				}

				if (TextUtils.isEmpty(code)) {
					T.showShort(RegisterActivity.this, "请输入验证码");
					return;
				}

				if (TextUtils.isEmpty(pwd)) {
					T.showShort(RegisterActivity.this, "请输入密码");
					return;
				}

				if (TextUtils.isEmpty(confirmPwd)) {
					T.showShort(RegisterActivity.this, "请输入确认密码");
					return;
				}


				if (!TextUtils.equals(pwd, confirmPwd)) {
					T.showShort(RegisterActivity.this, "两次密码输入不一致");
					return;
				}

				presenter.register(phoneNum,code,pwd);


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
	public void registerSuccess() {
		T.showShort(this, "注册成功");
		getProgressDialog().setMessage("正在登陆...");

		String phoneNum = etPhone.getText().toString().replace(" ", "");

		String pwd = etPassword.getText().toString();

		presenter.login(phoneNum,pwd);
	}

	@Override
	public void registerError(boolean isShowError, String msg) {
		if (isShowError) {
			T.showShort(this, "" + msg);
		} else {
			T.showShort(this, "注册失败");
		}
	}

	@Override
	public void loginSuccess(UserInfoResultModel userInfoModel) {
		T.showShort(this, "登录成功");
		saveInfo(userInfoModel);
	}

	private void saveInfo(UserInfoResultModel userInfoModel) {
		sendBroadcast(Constant.ACTION_LOGIN_SUCCESS);//发送登录成功广播
		PreferencesUtils.putString(this, Constant.AppConfigInfo.PHONE, etPhone.getText().toString().replaceAll(" ",
				""));
		PreferencesUtils.putString(this, Constant.AppConfigInfo.PASSWORD, etPassword.getText().toString());
		MyApplication.saveLoginInfo(userInfoModel);
		if (TextUtils.equals(flag, "tabClient")) {//客户管理
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.setAction("tabClient");
			startActivity(intent);
		} else if (TextUtils.equals(flag, "tabPersonal")) {//个人中心
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.setAction("tabPersonal");
			startActivity(intent);
		}else {
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	}

	@Override
	public void loginError(boolean isShowError, String msg) {
		if (isShowError) {
			T.showShort(this, "" + msg);
		} else {
			T.showShort(this, "登录失败");
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (timer != null)
			timer.cancel();
	}

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

}
