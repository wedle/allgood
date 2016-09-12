package com.yiqihao.loan.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yiqihao.loan.Constant;
import com.yiqihao.loan.MyApplication;
import com.yiqihao.loan.R;
import com.yiqihao.loan.entity.UserInfoResultModel;
import com.yiqihao.loan.mvp.presenters.LoginPresenter;
import com.yiqihao.loan.mvp.views.LoginView;
import com.yiqihao.loan.utils.PreferencesUtils;
import com.yiqihao.loan.utils.T;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录
 * Created by 冯浩 on 16/8/17.
 */
public class LoginActivity extends MvpActivity<LoginView,LoginPresenter> implements LoginView{


	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.et_phone)
	EditText etPhone;
	@BindView(R.id.et_password)
	EditText etPassword;
	@BindView(R.id.btn_login)
	Button btnLogin;
	@BindView(R.id.tv_register)
	TextView tvRegister;
	@BindView(R.id.tv_forgot_password)
	TextView tvForgotPassword;

	private String flag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ButterKnife.bind(this);
		flag=getIntent().getStringExtra("flag");
		initToolBar(toolbar, true);
		initView();
	}

	private void initView() {
//		etPhone.setText(PreferencesUtils.getString(this, Constant.AppConfigInfo.PHONE));
//		etPhone.setSelection(etPhone.getText().toString().length());
	}

	@OnClick(R.id.tv_register)
	public void onRegisterClick() {
		Intent intent=new Intent(this,RegisterActivity.class);
		intent.putExtra("flag",flag);
		startActivity(intent);
	}

	@OnClick(R.id.tv_forgot_password)
	public void onForgotPasswordClick() {
		Intent intent=new Intent(this,ForgotPasswordActivity.class);
		startActivity(intent);
	}




	@OnClick(R.id.btn_login)
	public void onClick() {
		String phone = etPhone.getText().toString().replace(" ", "");

		String password = etPassword.getText().toString();

		if (TextUtils.isEmpty(phone)) {
			T.showShort(LoginActivity.this, "手机号不能为空");
			return;
		}

		if (TextUtils.isEmpty(password)) {
			T.showShort(LoginActivity.this, "密码不能为空");
			return;
		}

		presenter.login(phone, password);

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
	public void loginSuccess(UserInfoResultModel userInfoResultModel) {
		T.showShort(this, "登录成功");
		saveInfo(userInfoResultModel);
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

	@NonNull
	@Override
	public LoginPresenter createPresenter() {
		return new LoginPresenter();
	}
}
