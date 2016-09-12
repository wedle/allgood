package com.yiqihao.loan.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.yiqihao.loan.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 贷款信息
 * Created by 冯浩 on 16/8/18.
 */
public class LoanInfoActivity extends BaseActivity {

	@BindView(R.id.toolbar_title)
	TextView toolbarTitle;
	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.tv_loan_id)
	TextView tvLoanId;
	@BindView(R.id.tv_loan_type)
	TextView tvLoanType;
	@BindView(R.id.tv_loan_name)
	TextView tvLoanName;
	@BindView(R.id.tv_loan_amount)
	TextView tvLoanAmount;
	@BindView(R.id.tv_loan_deadline)
	TextView tvLoanDeadline;
	@BindView(R.id.tv_sex)
	TextView tvSex;

	private String creditNo;
	private String loanType;
	private String loanName;
	private String amount;
	private String deadLine;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loan_info);
		ButterKnife.bind(this);
		initToolBar(toolbar, true);
		creditNo = getIntent().getStringExtra("creditNo");
		loanType = getIntent().getStringExtra("loanType");
		loanName = getIntent().getStringExtra("loanName");
		amount = getIntent().getStringExtra("amount");
		deadLine = getIntent().getStringExtra("deadLine");
		initView();
	}

	private void initView() {
		tvLoanId.setText(creditNo);
		tvLoanType.setText(loanType);
		tvLoanName.setText(loanName);
		tvLoanAmount.setText(amount);
		tvLoanDeadline.setText(deadLine);
	}

}
