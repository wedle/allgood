package com.yiqihao.loan.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.yiqihao.loan.Constant;
import com.yiqihao.loan.R;
import com.yiqihao.loan.mvp.presenters.CertificationIdCardPresenter;
import com.yiqihao.loan.mvp.views.CertificationIdCardView;
import com.yiqihao.loan.utils.PreferencesUtils;
import com.yiqihao.loan.utils.T;
import com.yiqihao.loan.utils.VerificationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 身份认证
 * Created by 冯浩 on 16/8/16.
 */
public class CertificationIdCardActivity extends MvpActivity<CertificationIdCardView, CertificationIdCardPresenter>
		implements CertificationIdCardView {


	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.et_name)
	EditText etName;
	@BindView(R.id.et_id_card)
	EditText etIdCard;
	@BindView(R.id.btn_submit)
	Button btnSubmit;

	private String pre;
	private String jump;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_certification_idcard);
		ButterKnife.bind(this);
		pre=getIntent().getStringExtra("pre");
		jump=getIntent().getStringExtra("jump");
		initToolBar(toolbar, true);
	}

	@NonNull
	@Override
	public CertificationIdCardPresenter createPresenter() {
		return new CertificationIdCardPresenter();
	}

	@OnClick(R.id.btn_submit)
	public void onClick() {
		String realName = etName.getText().toString();
		String idCard=etIdCard.getText().toString();

		if (TextUtils.isEmpty(realName)){
			T.showShort(this, "姓名不能为空");
			return;
		}

		if (TextUtils.isEmpty(idCard)){
			T.showShort(this, "身份证号不能为空");
			return;
		}

		presenter.certificationIdCard(realName,idCard);

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
	public void certificationIdCardSuccess() {
		T.showShort(this,"提交实名认证信息成功!");
		if (TextUtils.equals(jump,"addBank")){//添加银行卡前判断是否实名认证,不走前置流程
			sendBroadcast(Constant.ACTION_CERTIFICATION_SUCCESS);
		}else {
			VerificationUtils.prefixCondition(this,jump);
		}
		PreferencesUtils.putString(this, Constant.AppConfigInfo.AUTHIDCARD, etIdCard.getText().toString().trim());
		finish();
	}

	@Override
	public void certificationIdCardError(boolean isShow, String msg) {
		if (isShow){
			T.showShort(this,msg);
		}else {
			T.showShort(this,"提交实名认证信息失败!");
		}
	}
}
