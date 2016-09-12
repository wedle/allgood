package com.yiqihao.loan.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.yiqihao.loan.Constant;
import com.yiqihao.loan.MyApplication;
import com.yiqihao.loan.R;
import com.yiqihao.loan.entity.HttpResultModel;
import com.yiqihao.loan.net.loan.LoanFactory;
import com.yiqihao.loan.utils.T;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 账户信息
 * Created by 冯浩 on 16/8/10.
 */
public class AccountInfoActivity extends BaseActivity {

	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.tv_user_name)
	TextView tvUserName;
	@BindView(R.id.tv_id_card)
	TextView tvIdCard;
	@BindView(R.id.tv_phone_num)
	TextView tvPhoneNum;
	@BindView(R.id.btn_logout)
	Button btnLogout;

	private String userName;
	private String idCard;
	private String mobile;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_info);
		ButterKnife.bind(this);
		initToolBar(toolbar,true);
		userName=getIntent().getStringExtra("userName");
		idCard=getIntent().getStringExtra("idCard");
		mobile=getIntent().getStringExtra("mobile");
		initView();
	}

	private void initView() {
		tvUserName.setText(userName);
		tvIdCard.setText(idCard);
		tvPhoneNum.setText(mobile);
	}


	@OnClick(R.id.btn_logout)
	public void onClick() {
		getProgressDialog().setMessage("退出登录");
		getProgressDialog().show();
		Subscription subscribe = LoanFactory.getLoanSingleton().logout()
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
						T.showShort(AccountInfoActivity.this, "登出失败");
					}

					@Override
					public void onNext(HttpResultModel httpResultModel) {

						if (httpResultModel.getResultCode() == Constant.SUCCESS) {
							T.showShort(AccountInfoActivity.this, "登出成功");
							MyApplication.logout();
							finish();
						} else {
							String errorMsg = httpResultModel.getErrmsg();
							T.showShort(AccountInfoActivity.this, errorMsg);
						}
					}
				});
		addSubscription(subscribe);
	}
}
