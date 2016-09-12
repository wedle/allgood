package com.yiqihao.loan.ui.activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiqihao.loan.R;
import com.yiqihao.loan.entity.LangZnModel;
import com.yiqihao.loan.entity.LoanDetailModel;
import com.yiqihao.loan.mvp.presenters.LoanDetailPresenter;
import com.yiqihao.loan.mvp.views.LoanDetailView;
import com.yiqihao.loan.ui.adapter.LoanRecordListAdapter;
import com.yiqihao.loan.utils.LangZnUtils;
import com.yiqihao.loan.utils.StringUtils;
import com.yiqihao.loan.utils.T;
import com.yiqihao.loan.widget.MultiStateView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 借款详情
 */

public class LoanDetailActivity extends MvpActivity<LoanDetailView, LoanDetailPresenter> implements LoanDetailView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_loan_id)
    TextView tvLoanId;
    @BindView(R.id.tv_time_money)
    TextView tvTimeMoney;
    @BindView(R.id.lv_record)
    ListView lvRecord;
    @BindView(R.id.mv_state)
    MultiStateView mvState;
    @BindView(R.id.loan_info)
    RelativeLayout rlLoanInfo;
    @BindView(R.id.tv_car_info)
    TextView tvLoanInfo;
    @BindView(R.id.car_info)
    RelativeLayout rlCarInfo;
    @BindView(R.id.refund_plan)
    RelativeLayout rlRefundPlan;
    @BindView(R.id.rl_kefu)
    RelativeLayout rlKefu;
    @BindView(R.id.tv_record)
    TextView tvRecord;

    private String lid;
    private LoanDetailModel loanDetailModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_detail);
        ButterKnife.bind(this);
        lid = getIntent().getStringExtra("lid");
        initToolBar(toolbar, true);
        presenter.initData(lid);
    }

    @Override
    public void showError(boolean b, String s) {
        mvState.setViewState(MultiStateView.ViewState.ERROR);
        if (b) {
            T.showShort(this, s);
        } else {
            T.showShort(this, "加载失败");
        }
    }

    @OnClick({R.id.loan_info, R.id.car_info, R.id.refund_plan, R.id.rl_kefu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loan_info://借款信息
                LangZnModel lang_zn = loanDetailModel.getLang_zn();
                int type = Integer.valueOf(loanDetailModel.getType());
                Intent intent = new Intent(LoanDetailActivity.this, LoanInfoActivity.class);
                intent.putExtra("creditNo", loanDetailModel.getCreditno());
                intent.putExtra("loanType", LangZnUtils.typeZnToString(lang_zn, type));
                intent.putExtra("loanName", loanDetailModel.getLoanname());
                intent.putExtra("amount", StringUtils.formatMoneyWan(loanDetailModel.getAmount()));
                if (TextUtils.equals(loanDetailModel.getDeadline_type(), "d")) {
                    intent.putExtra("deadLine", loanDetailModel.getDays() + "天");
                } else {
                    intent.putExtra("deadLine", loanDetailModel.getDeadline() + "个月");
                }
                startActivity(intent);
                break;
            case R.id.car_info://车辆信息
                int status = Integer.valueOf(loanDetailModel.getStatus());
                if (status == 2) {
                    goCarInfo("edit");
                } else {
                    goCarInfo("look");
                }
                break;
            case R.id.refund_plan://还款计划
                Intent intent1 = new Intent(LoanDetailActivity.this, LoanRepayPlanActivity.class);
                intent1.putExtra("lid", loanDetailModel.getLid());
                startActivity(intent1);
                break;
            case R.id.rl_kefu:
                Intent it = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getString(R.string.kefu_phone)));
                startActivity(it);
                break;

        }
    }

    private void goCarInfo(String type) {
        Intent intent1 = new Intent(LoanDetailActivity.this, CarInfoActivity.class);
        intent1.putExtra("type", type);
        intent1.putExtra("lid", loanDetailModel.getLid());
        intent1.putExtra("carInfo", loanDetailModel.getCarinfo());

        startActivity(intent1);
    }

    @Override
    public void showContent(LoanDetailModel data) {
        mvState.setViewState(MultiStateView.ViewState.CONTENT);
        loanDetailModel = data;
        LangZnModel lang_zn = data.getLang_zn();
        int status = Integer.valueOf(data.getStatus());
        selectBaseInfo(status);
        tvStatus.setText(LangZnUtils.statusZnToString(lang_zn, status));

        int type = Integer.valueOf(data.getType());
        tvType.setText(LangZnUtils.typeZnToString(lang_zn, type));

        tvLoanId.setText("借款申请编号 " + data.getCreditno());

        if (TextUtils.equals(data.getDeadline_type(), "d")) {
            tvTimeMoney.setText(data.getDays() + "天   " + StringUtils.formatMoneyWan(data.getAmount()));
        } else {
            tvTimeMoney.setText(data.getDeadline() + "个月   " + StringUtils.formatMoneyWan(data.getAmount()));
        }

        if (data.getRecord() != null || data.getRecord().getList() != null || data.getRecord().getList().size() != 0) {
            LoanRecordListAdapter adapter = new LoanRecordListAdapter(this);
            lvRecord.setAdapter(adapter);
            adapter.setData(data.getRecord().getList(), data.getLoanname());
        } else {
            tvRecord.setVisibility(View.GONE);
        }
    }

    private void selectBaseInfo(int status) {
        switch (status) {
            //待补充资料
            case 2:
                rlLoanInfo.setVisibility(View.VISIBLE);
                rlCarInfo.setVisibility(View.VISIBLE);
                tvLoanInfo.setVisibility(View.VISIBLE);
                rlRefundPlan.setVisibility(View.GONE);
                tvRecord.setVisibility(View.GONE);
                break;
            //审批中
            case 3:
                rlLoanInfo.setVisibility(View.VISIBLE);
                rlCarInfo.setVisibility(View.VISIBLE);
                tvLoanInfo.setVisibility(View.GONE);
                rlRefundPlan.setVisibility(View.GONE);
                tvRecord.setVisibility(View.GONE);
                break;
            //审批通过
            case 5:
                rlLoanInfo.setVisibility(View.VISIBLE);
                rlCarInfo.setVisibility(View.VISIBLE);
                tvLoanInfo.setVisibility(View.GONE);
                rlRefundPlan.setVisibility(View.GONE);
                break;
            //还款中
            case 6:
                rlLoanInfo.setVisibility(View.VISIBLE);
                rlCarInfo.setVisibility(View.VISIBLE);
                tvLoanInfo.setVisibility(View.GONE);
                rlRefundPlan.setVisibility(View.VISIBLE);
                break;
            //提前还款中
            case 7:
                rlLoanInfo.setVisibility(View.VISIBLE);
                rlCarInfo.setVisibility(View.VISIBLE);
                tvLoanInfo.setVisibility(View.GONE);
                rlRefundPlan.setVisibility(View.GONE);
                break;
            //已结清
            case 9:
                rlLoanInfo.setVisibility(View.VISIBLE);
                rlCarInfo.setVisibility(View.VISIBLE);
                tvLoanInfo.setVisibility(View.GONE);
                rlRefundPlan.setVisibility(View.VISIBLE);
                break;
            //审批拒绝
            case 4:
                rlLoanInfo.setVisibility(View.VISIBLE);
                rlCarInfo.setVisibility(View.VISIBLE);
                tvLoanInfo.setVisibility(View.GONE);
                rlRefundPlan.setVisibility(View.GONE);
                break;
        }
    }

    @NonNull
    @Override
    public LoanDetailPresenter createPresenter() {
        return new LoanDetailPresenter();
    }
}
