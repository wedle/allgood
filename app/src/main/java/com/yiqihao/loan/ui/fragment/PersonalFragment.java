package com.yiqihao.loan.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.yiqihao.loan.Constant;
import com.yiqihao.loan.R;
import com.yiqihao.loan.entity.UserInfoModel;
import com.yiqihao.loan.mvp.presenters.PersonalPresenter;
import com.yiqihao.loan.mvp.views.PersonalView;
import com.yiqihao.loan.ui.activity.AccountInfoActivity;
import com.yiqihao.loan.ui.activity.RepaymentActivity;
import com.yiqihao.loan.ui.activity.WithdrawBankCardActivity;
import com.yiqihao.loan.utils.PreferencesUtils;
import com.yiqihao.loan.utils.UIUtils;
import com.yiqihao.loan.utils.VerificationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 个人中心
 * Created by 冯浩 on 16/8/17.
 */
public class PersonalFragment extends MvpFragment<PersonalView, PersonalPresenter> implements PersonalView {

	private static final String TAG = "PersonalFragment";

	@BindView(R.id.tv_commission)
	TextView tvCommission;
	@BindView(R.id.tv_week_commission)
	TextView tvWeekCommission;
	@BindView(R.id.tv_user_name)
	TextView tvUserName;
	@BindView(R.id.rl_personal_name)
	RelativeLayout rlPersonalName;
	@BindView(R.id.rl_my_commission)
	RelativeLayout rlMyCommission;
	@BindView(R.id.rl_personal_card)
	RelativeLayout rlPersonalCard;
	@BindView(R.id.ptr_frame)
	PtrClassicFrameLayout ptrFrame;
	@BindView(R.id.sv_content)
	ScrollView svContent;
	@BindView(R.id.btn)
	Button btn;
	private View view;

	private UserInfoModel userInfoModel;

	private MyBroadcastReceive receiver;


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
			savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_personal, null);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public PersonalPresenter createPresenter() {
		return new PersonalPresenter();
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		boolean isLogin = PreferencesUtils.getBoolean(getContext(), Constant.AppConfigInfo.ISLOGIN);
		if (isLogin) {
			presenter.getUserInfo();
		}
		initView();
		registerBroadReceive();

	}


	private void initView() {
		UIUtils.ptrFrameAddHeader(getContext(), ptrFrame);
		ptrFrame.setPtrHandler(new PtrHandler() {
			@Override
			public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
				return PtrDefaultHandler.checkContentCanBePulledDown(frame, svContent, header);
			}

			@Override
			public void onRefreshBegin(PtrFrameLayout frame) {
				presenter.getUserInfo();
			}
		});
	}

	private void registerBroadReceive() {
		receiver = new MyBroadcastReceive();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constant.ACTION_REFRESH_TAB_PERSONAL);
		getContext().registerReceiver(receiver, filter);
	}


	private class MyBroadcastReceive extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if(action.equals(Constant.ACTION_REFRESH_TAB_PERSONAL)){
				boolean isLogin = PreferencesUtils.getBoolean(getContext(), Constant.AppConfigInfo.ISLOGIN);
				if (isLogin) {
					presenter.getUserInfo();
				}
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		getContext().unregisterReceiver(receiver);
	}

	@Override
	public void onResume() {
		super.onResume();
		boolean isLogin = PreferencesUtils.getBoolean(getContext(), Constant.AppConfigInfo.ISLOGIN);
		if (isLogin) {
			presenter.getUserInfo();
		}
	}


	@OnClick({R.id.rl_personal_name, R.id.rl_my_commission, R.id
			.rl_personal_card})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.rl_personal_name:
				if (userInfoModel != null) {
					Intent intent = new Intent(getContext(), AccountInfoActivity.class);
					if (VerificationUtils.isCertificated()) {
						intent.putExtra("userName", userInfoModel.getRealname());
					} else {
						intent.putExtra("userName", userInfoModel.getUmobile());
					}

					intent.putExtra("idCard", userInfoModel.getIdcard());
					intent.putExtra("mobile", userInfoModel.getUmobile());
					startActivity(intent);
				}
				break;
			case R.id.rl_my_commission://还款计划
				Intent i=new Intent(getContext(),RepaymentActivity.class);
				i.putExtra("UserInfoModel",userInfoModel);
				startActivity(i);
				break;
			case R.id.rl_personal_card:
				Intent intent = new Intent(getContext(), WithdrawBankCardActivity.class);
				startActivity(intent);
				break;
		}
	}

	@Override
	public void getUserInfoSuccess(UserInfoModel userInfoModel) {
		this.userInfoModel = userInfoModel;
		tvCommission.setText(userInfoModel.getRepay_money());//待还款本金
		tvWeekCommission.setText(userInfoModel.getLate_amount());
		if (VerificationUtils.isCertificated()) {
			tvUserName.setText(userInfoModel.getRealname());
		} else {
			tvUserName.setText(userInfoModel.getUmobile());
		}

	}

	@Override
	public void getUserInfoError(boolean isShow, String msg) {
		if (isShow) {
//			T.showShort(getContext(), msg);
		} else {
//			T.showShort(getContext(), "获取用户信息失败");
		}
	}

	@Override
	public void hideLoading() {
		ptrFrame.refreshComplete();
	}

	@OnClick(R.id.btn)
	public void onClick() {


	}


}
