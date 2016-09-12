package com.yiqihao.loan.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yiqihao.loan.Constant;
import com.yiqihao.loan.R;
import com.yiqihao.loan.entity.BankCardInfoModel;
import com.yiqihao.loan.entity.BankCardListModel;
import com.yiqihao.loan.mvp.presenters.WithdrawBankCardPresenter;
import com.yiqihao.loan.mvp.views.WithdrawBankCardView;
import com.yiqihao.loan.ui.adapter.BankCardAdapter;
import com.yiqihao.loan.utils.T;
import com.yiqihao.loan.utils.VerificationUtils;
import com.yiqihao.loan.widget.MultiStateView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 提现银行卡
 * Created by 冯浩 on 16/8/11.
 */
public class WithdrawBankCardActivity extends MvpActivity<WithdrawBankCardView, WithdrawBankCardPresenter> implements
		WithdrawBankCardView {

	@BindView(R.id.iv_add)
	ImageView ivAdd;
	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.lv_bank_list)
	ListView lvBankList;
	@BindView(R.id.tv_unbind_card)
	TextView tvUnbindCard;
	@BindView(R.id.mv_state)
	MultiStateView mvState;

	private BankCardListModel bankCardListModel;

	private MyBroadcastReceive receiver;

	private String type;
	private BankCardAdapter adapter;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_withdraw_bankcard);
		ButterKnife.bind(this);
		initToolBar(toolbar, true);
		type = getIntent().getStringExtra("type");
		registerBroadReceive();
		initView();
		presenter.initData();
	}

	private void initView() {
		adapter = new BankCardAdapter(this);
		lvBankList.setOnItemClickListener(new AdapterView.OnItemClickListener() {//跳转到编辑银行卡界面
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//				adapter.closeAllItems();

				if (TextUtils.equals(type, "selected")) {
					Intent data = new Intent();
					data.putExtra("bankId", bankCardListModel.getList().get(position).getId());
					data.putExtra("bankCode", bankCardListModel.getList().get(position).getCard());
					data.putExtra("bankName", bankCardListModel.getList().get(position).getBank());
					setResult(RESULT_OK, data);
					finish();

				}else {
					Intent intent = new Intent(WithdrawBankCardActivity.this, AddBankCardActivity.class);
					intent.putExtra("type","edit");
					intent.putExtra("bankinfo",bankCardListModel.getList().get(position));
					intent.putStringArrayListExtra("needbranch", (ArrayList<String>) bankCardListModel.getNeedbranch());
					intent.putParcelableArrayListExtra("cardBank", (ArrayList<? extends Parcelable>) bankCardListModel
							.getCardbank());
					startActivity(intent);
				}


			}
		});
	}

	@NonNull
	@Override
	public WithdrawBankCardPresenter createPresenter() {
		return new WithdrawBankCardPresenter();
	}

	private void registerBroadReceive() {
		receiver = new MyBroadcastReceive();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constant.ACTION_BIND_BANK_SUCCESS);
		filter.addAction(Constant.ACTION_CERTIFICATION_SUCCESS);
		registerReceiver(receiver, filter);
	}


	private class MyBroadcastReceive extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Constant.ACTION_BIND_BANK_SUCCESS)) {//添加成功
				presenter.initData();
			}else if(action.equals(Constant.ACTION_CERTIFICATION_SUCCESS)){//实名认证成功
				addBank();
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}

	@OnClick(R.id.iv_add)
	public void onClick() {

		if (VerificationUtils.isCertificated()) {
			addBank();
		}else {
			Intent intent=new Intent(this,CertificationIdCardActivity.class);
			intent.putExtra("jump","addBank");
			startActivity(intent);
		}

	}


	private void addBank(){
		if (bankCardListModel != null && bankCardListModel.getList().size() < 10) {
			Intent intent = new Intent(this, AddBankCardActivity.class);
			intent.putStringArrayListExtra("needbranch", (ArrayList<String>) bankCardListModel.getNeedbranch());
			intent.putParcelableArrayListExtra("cardBank", (ArrayList<? extends Parcelable>) bankCardListModel
					.getCardbank());
			startActivity(intent);
		} else {
			T.showShort(this, "最多只能绑定10张银行卡!");
		}
	}

	@OnClick(R.id.tv_unbind_card)
	public void onTelClick() {
		Intent intent1 = new Intent(Intent.ACTION_DIAL,
				Uri.parse("tel:400-900-1717"));
		startActivity(intent1);
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
	public void setDefaultBankSuccess() {
		T.showShort(this, "设置成功");
		presenter.initData();
	}

	@Override
	public void setDefaultBankError(boolean isShow, String msg) {
		if (isShow) {
			T.showShort(this, msg);
		} else {
			T.showShort(this, "设置失败");
		}
	}

	@Override
	public void deleteBankCardSuccess() {
		T.showShort(this, "删除成功");
		presenter.initData();
	}

	@Override
	public void deleteBankCardError(boolean isShow, String msg) {
		if (isShow) {
			T.showShort(this, msg);
		} else {
			T.showShort(this, "删除失败");
		}
	}

	@Override
	public void getBankListSuccess(final BankCardListModel bankCardListModel) {

		this.bankCardListModel = bankCardListModel;
		List<BankCardInfoModel> list = bankCardListModel.getList();

		ivAdd.setVisibility(View.VISIBLE);

		if (list.size() == 0) {
			mvState.setViewState(MultiStateView.ViewState.EMPTY);
			TextView tv = (TextView) mvState.getView(MultiStateView.ViewState.EMPTY).findViewById(R.id.tv_empty);
			tv.setText("您还没有添加银行卡\n请点击右上角添加");
		} else {
			mvState.setViewState(MultiStateView.ViewState.CONTENT);
			lvBankList.setAdapter(adapter);
			adapter.setData(bankCardListModel.getList());
			adapter.setOnSwipeItemClickListener(new BankCardAdapter.OnSwipeItemClickListener() {
				@Override
				public void callBack(int id, final BankCardInfoModel model) {

					if (id==BankCardAdapter.DELETE){
						new AlertDialog.Builder(WithdrawBankCardActivity.this).setTitle("确定删除该银行卡吗?")
								.setNegativeButton("取消", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {

									}
								}).setPositiveButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								presenter.deleteBank(model.getId());
							}
						}).create().show();
					}else if(id==BankCardAdapter.DEFAULT){
						presenter.setDefaultBank(model.getId());
					}


				}
			});
		}

	}

	@Override
	public void getBankListError(boolean isShow, String msg) {
		mvState.setViewState(MultiStateView.ViewState.ERROR);
		if (isShow) {
			T.showShort(this, msg);
		} else {
			T.showShort(this, "加载失败");
		}
	}


}
