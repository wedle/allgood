package com.yiqihao.loan.ui.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.yiqihao.loan.Constant;
import com.yiqihao.loan.R;
import com.yiqihao.loan.entity.HttpResultModel;
import com.yiqihao.loan.net.loan.LoanFactory;
import com.yiqihao.loan.service.UpdateService;
import com.yiqihao.loan.ui.fragment.HomeFragment;
import com.yiqihao.loan.ui.fragment.LoanProgressFragment;
import com.yiqihao.loan.ui.fragment.PersonalFragment;
import com.yiqihao.loan.utils.PreferencesUtils;
import com.yiqihao.loan.utils.RefreshSessionAndRetryUtils;
import com.yiqihao.loan.utils.StringUtils;
import com.yiqihao.loan.utils.T;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 主页
 */
public class MainActivity extends BaseActivity {

	@BindView(R.id.toolbar_title)
	TextView toolbarTitle;
	@BindView(R.id.toolbar_right_title)
	TextView toolbarRightTitle;
	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.content)
	FrameLayout content;
	@BindView(R.id.iv_home)
	ImageView ivHome;
	@BindView(R.id.tv_home_text)
	TextView tvHomeText;
	@BindView(R.id.ll_home)
	LinearLayout llHome;
	@BindView(R.id.iv_loan_progress)
	ImageView ivLoanProgress;
	@BindView(R.id.tv_loan_progress_text)
	TextView tvLoanProgressText;
	@BindView(R.id.ll_loan_progress)
	LinearLayout llLoanProgress;
	@BindView(R.id.iv_personal)
	ImageView ivPersonal;
	@BindView(R.id.tv_personal_text)
	TextView tvPersonalText;
	@BindView(R.id.ll_personal)
	LinearLayout llPersonal;

	private Fragment[] fragments;

	private HomeFragment homeFragment;

	private LoanProgressFragment loanProgressFragment;

	private PersonalFragment personalFragment;

	private ImageView[] imageButtons;

	private TextView[] textViews;

	private int index;

	private int currentTabIndex;// 当前fragment的index

	public static final int tabTextColorNormal = 0xFF929292;

	public static final int tabTextColorPressed = 0xFF929292;

	private MyBroadcastReceive receiver;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
				getWindow().setStatusBarColor(getResources().getColor(R.color.themeColor));
			} else {
				Window window = getWindow();
				window.setFlags(
						WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
						WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			}
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		initToolBar(toolbar, false);
		initTabView(savedInstanceState);
		getSwipeBackLayout().setEnableGesture(false);
		checkLogin();
		registerBroadReceive();
		handleIntent();
//检查新版本
		checkNewVersion();
	}

	private void checkLogin() {
		boolean isLogin = PreferencesUtils.getBoolean(this, Constant.AppConfigInfo.ISLOGIN);
		if (isLogin) {
			getCardNum();//获取银行卡数量
			toolbarRightTitle.setVisibility(View.GONE);
		} else {
			toolbarRightTitle.setVisibility(View.VISIBLE);
		}
	}

	private void registerBroadReceive() {
		receiver = new MyBroadcastReceive();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constant.ACTION_LOGIN_SUCCESS);
		registerReceiver(receiver, filter);
	}

	private class MyBroadcastReceive extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(Constant.ACTION_LOGIN_SUCCESS)) {//登录成功通知
				checkLogin();
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}

	private void initTabView(Bundle savedInstanceState) {

		imageButtons = new ImageView[3];
		imageButtons[0] = ivHome;
		imageButtons[1] = ivLoanProgress;
		imageButtons[2] = ivPersonal;

		textViews = new TextView[3];
		textViews[0] = tvHomeText;
		textViews[1] = tvLoanProgressText;
		textViews[2] = tvPersonalText;

		imageButtons[0].setSelected(true);//默认选中第一项
		textViews[0].setTextColor(tabTextColorPressed);

		if (savedInstanceState != null) {
			List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
			for (Fragment fragment : fragmentList) {
				if (fragment instanceof HomeFragment) {
					homeFragment = (HomeFragment) fragment;
				} else if (fragment instanceof LoanProgressFragment) {
					loanProgressFragment = (LoanProgressFragment) fragment;
				} else if (fragment instanceof PersonalFragment) {
					personalFragment = (PersonalFragment) fragment;
				}
			}
			// 解决内存重启重叠问题
			getSupportFragmentManager().beginTransaction()
					.show(homeFragment)
					.hide(loanProgressFragment)
					.hide(personalFragment)
					.commit();
		} else {
			homeFragment = new HomeFragment();
			loanProgressFragment = new LoanProgressFragment();
			personalFragment = new PersonalFragment();
			// 添加显示第一个fragment
			getSupportFragmentManager().beginTransaction()
					.add(R.id.content, homeFragment)
					.add(R.id.content, loanProgressFragment)
					.add(R.id.content, personalFragment)
					.hide(loanProgressFragment)
					.hide(personalFragment)
					.show(homeFragment)
					.commit();
		}

		fragments = new Fragment[]{homeFragment, loanProgressFragment, personalFragment};

	}

	@OnClick({R.id.toolbar_right_title, R.id.ll_home, R.id.ll_loan_progress, R.id.ll_personal})
	public void onClick(View view) {
		boolean isLogin = PreferencesUtils.getBoolean(this, Constant.AppConfigInfo.ISLOGIN);
		switch (view.getId()) {
			case R.id.toolbar_right_title:
				startActivity(new Intent(MainActivity.this, LoginActivity.class));

				break;
			case R.id.ll_home:
				index = 0;
				setTabSelection(index);
				toolbarTitle.setText("借款申请");
				if (isLogin) {
					toolbarRightTitle.setVisibility(View.GONE);
				} else {
					toolbarRightTitle.setVisibility(View.VISIBLE);
				}
				break;
			case R.id.ll_loan_progress:

				if (isLogin) {
					toolbarRightTitle.setVisibility(View.GONE);
					index = 1;
					setTabSelection(index);
					toolbarTitle.setText(getString(R.string.tab_loan_progress));
				} else {
					Intent intent = new Intent(MainActivity.this, LoginActivity.class);
					intent.putExtra("flag", "tabClient");
					startActivity(intent);
				}
				break;
			case R.id.ll_personal:
				if (isLogin) {
					toolbarRightTitle.setVisibility(View.GONE);
					index = 2;
					setTabSelection(index);
					toolbarTitle.setText(getString(R.string.tab_personal));
				} else {
					Intent intent = new Intent(MainActivity.this, LoginActivity.class);
					intent.putExtra("flag", "tabPersonal");
					startActivity(intent);
				}
				break;
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		handleIntent();
	}

	private void handleIntent() {

		Intent intent = getIntent();

		if ("tabClient".equals(intent.getAction())) {
			toolbarRightTitle.setVisibility(View.GONE);
			index = 1;
			setTabSelection(index);
			toolbarTitle.setText(getString(R.string.tab_loan_progress));
		} else if ("tabPersonal".equals(intent.getAction())) {
			toolbarRightTitle.setVisibility(View.GONE);
			index = 2;
			setTabSelection(index);
			toolbarTitle.setText(getString(R.string.tab_personal));
		}

	}

	private void setTabSelection(int index) {
		if (currentTabIndex != index) {
			refreshTabFragment(index);
			FragmentTransaction trx = getSupportFragmentManager()
					.beginTransaction();
			trx.hide(fragments[currentTabIndex]);
			if (!fragments[index].isAdded()) {
				trx.add(R.id.content, fragments[index]);
			}
			trx.show(fragments[index]).commit();
		}
		imageButtons[currentTabIndex].setSelected(false);
		// 把当前tab设为选中状态
		imageButtons[index].setSelected(true);
		textViews[currentTabIndex].setTextColor(tabTextColorNormal);
		textViews[index].setTextColor(tabTextColorPressed);
		currentTabIndex = index;
	}

	/**
	 * 点击底部tab刷新相应界面
	 * @param index
	 */
	private void refreshTabFragment(int index) {
		boolean isLogin = PreferencesUtils.getBoolean(this, Constant.AppConfigInfo.ISLOGIN);
		if (isLogin){
			if (index==0){
				sendBroadcast(Constant.ACTION_REFRESH_TAB_HOME);
			}else if (index==1){
				sendBroadcast(Constant.ACTION_REFRESH_TAB_LOAN);
			}else if (index==2){
				sendBroadcast(Constant.ACTION_REFRESH_TAB_PERSONAL);
			}
		}
	}


	private void getCardNum() {
		Observable<HttpResultModel> cardNum = LoanFactory.getLoanSingleton().getCardNum();
		Subscription subscribe = cardNum
				.onErrorResumeNext(RefreshSessionAndRetryUtils.refreshSessionAndRetry(cardNum))
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<HttpResultModel>() {
					@Override
					public void onCompleted() {

					}

					@Override
					public void onError(Throwable e) {

					}

					@Override
					public void onNext(HttpResultModel httpResultModel) {
						if (httpResultModel.getResultCode() == Constant.SUCCESS) {
							PreferencesUtils.putInt(MainActivity.this, Constant.AppConfigInfo.BANK_CARD_NUM, Integer
									.valueOf(httpResultModel.getData().toString()));
						}


					}
				});
		addSubscription(subscribe);
	}

	private void checkNewVersion() {


		Subscription subscribe = LoanFactory.getLoanSingleton().checkVersion()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<HttpResultModel>() {
					@Override
					public void onCompleted() {

					}

					@Override
					public void onError(Throwable e) {

					}

					@Override
					public void onNext(HttpResultModel httpResultModel) {
						if (httpResultModel.getResultCode() == Constant.SUCCESS) {

							String url = httpResultModel.getUrl();
							String versionCode = httpResultModel.getVersion();
							String feature = httpResultModel.getFeature();

							String currentVersion = StringUtils.getAppVersionName(MainActivity.this);

							if (!TextUtils.isEmpty(url)) {

								if (!TextUtils.isEmpty(versionCode)) {

									if (!TextUtils.isEmpty(currentVersion) && !TextUtils.equals(currentVersion,
											versionCode)) {

										//这里要先判断更新服务是否已经启动，如果已经在后台启动，则不要弹窗提示，否则会存在问题
										if (!StringUtils.isServiceRunning(MainActivity.this, UpdateService.class)) {
											showUpdateDialog(url, feature);
										}
									}

								}

							}


						}
					}
				});
		addSubscription(subscribe);
	}



	private void showUpdateDialog(String updateUrl, String updateDes) {


		new AlertDialog.Builder(this).setTitle("检查到新版本").setMessage(updateDes)
				.setPositiveButton("立即更新", (dialog, which) -> {
//              立即更新--开启下载服务去服务端下载更新

					//检测是否有读写权限
					if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
							!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest
							.permission.WRITE_EXTERNAL_STORAGE)
							!= PackageManager.PERMISSION_GRANTED) {

						new AlertDialog.Builder(this)
								.setMessage(R.string.permission_help_storage)
								.setPositiveButton("下一步", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {

										RxPermissions.getInstance(MainActivity.this)
												.request(Manifest.permission.READ_EXTERNAL_STORAGE,
														Manifest.permission.WRITE_EXTERNAL_STORAGE)
												.subscribe(new Action1<Boolean>() {
													@Override
													public void call(Boolean aBoolean) {

														if (aBoolean) {
															startUpdateService(updateUrl);
														} else {
															showPmsGuideDialog();
														}
													}
												});
									}
								})
								.setNegativeButton("取消", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
									}
								}).show();


					}else {
						startUpdateService(updateUrl);
					}

					dialog.dismiss();

				}).setNegativeButton("下次再说", (dialog, which) -> {
			dialog.dismiss();
		}).show();
	}

	private void startUpdateService(String updateUrl){
		Intent intent = new Intent(this, UpdateService.class);
		intent.putExtra("url", updateUrl);
		startService(intent);
	}

	/**
	 * 显示引导用户设置权限dialog
	 */
	private void showPmsGuideDialog() {
		new AlertDialog.Builder(this).setTitle("提醒").setMessage(R.string.permission_guide)
				.setNegativeButton("退出", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				}).setPositiveButton("设置", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).create().show();
	}


	private static boolean mBackKeyPressed = false;//记录是否有首次按键

	@Override
	public void onBackPressed() {
		if (!mBackKeyPressed) {
			T.showShort(this, "再按一次退出程序");
			mBackKeyPressed = true;
			new Timer().schedule(new TimerTask() {//延时两秒，如果超出则擦错第一次按键记录
				@Override
				public void run() {
					mBackKeyPressed = false;
				}
			}, 2000);
		} else {//退出程序
			finish();
			Process.killProcess(Process.myPid());
		}
	}
}
