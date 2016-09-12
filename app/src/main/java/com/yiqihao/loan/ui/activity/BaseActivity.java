package com.yiqihao.loan.ui.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.yiqihao.loan.widget.SwipBackLayout.SwipeBackLayout;
import com.yiqihao.loan.widget.SwipBackLayout.app.SwipeBackActivity;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * activity基类
 * Created by 冯浩 on 16/8/17.
 */
public class BaseActivity extends SwipeBackActivity {


	protected ProgressDialog pg = null;

	private CompositeSubscription mCompositeSubscription;

	public static final String EXITACTION = "action.exit";

	private ExitReceiver exitReceiver = new ExitReceiver();

	protected InputMethodManager manager;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initExitEvent();
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
	}

	/**
	 * 退出应用
	 */
	private void initExitEvent() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(EXITACTION);
		registerReceiver(exitReceiver, filter);
	}

	class ExitReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			BaseActivity.this.finish();
		}

	}

	public void sendBroadcast(String action) {
		Intent intent = new Intent(action);
		sendBroadcast(intent);
	}

	public void initToolBar(Toolbar toolbar, boolean isShowBackIcon) {
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(isShowBackIcon);
	}


	public ProgressDialog getProgressDialog() {
		if (pg == null) {
			pg = new ProgressDialog(this);
		}
		return pg;
	}

	/**
	 * 关闭软键盘
	 */
	public void closeInput() {
		if (manager != null && this.getCurrentFocus() != null) {
			manager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/**
	 * 打开软键盘
	 */
	public void openInput(final EditText editText) {
		if (manager != null) {
			manager.showSoftInputFromInputMethod(editText.getWindowToken(), 0);
		}
	}


	public void addSubscription(Subscription s) {
		if (this.mCompositeSubscription == null) {
			this.mCompositeSubscription = new CompositeSubscription();
		}

		this.mCompositeSubscription.add(s);
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();

		unregisterReceiver(exitReceiver);

		if (this.mCompositeSubscription != null) {
			this.mCompositeSubscription.unsubscribe();
		}

		if (pg != null) {
			pg.dismiss();
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

}
