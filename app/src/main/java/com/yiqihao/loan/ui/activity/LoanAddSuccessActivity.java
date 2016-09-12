package com.yiqihao.loan.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.yiqihao.loan.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 申请已提交
 * Created by 冯浩 on 16/8/18.
 */
public class LoanAddSuccessActivity extends BaseActivity {


	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.btn)
	Button btn;

	String lid;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loan_add_success);
		ButterKnife.bind(this);
		initToolBar(toolbar,true);
		lid = getIntent().getStringExtra("lid");
	}

	@OnClick(R.id.btn)
	public void onClick() {
		Intent intent = new Intent(this, LoanDetailActivity.class);
		intent.putExtra("lid", lid);
		startActivity(intent);
		finish();
	}
}
