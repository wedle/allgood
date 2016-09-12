package com.yiqihao.loan.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yiqihao.loan.Constant;
import com.yiqihao.loan.R;
import com.yiqihao.loan.utils.L;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 冯浩 on 16/8/16.
 */
public class WebViewActivity extends BaseActivity {

	@BindView(R.id.toolbar_title)
	TextView toolbarTitle;
	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.webview)
	WebView webview;
	@BindView(R.id.webview_progress)
	ProgressBar webviewProgress;

	String url;

	private static final String TAG = "WebViewActivity";

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		ButterKnife.bind(this);
		initToolBar(toolbar, true);
		url = getIntent().getStringExtra(Constant.WEBVIEW_URL);
		initView();
	}

	private void initView() {

		L.i(TAG,Constant.BASEURL + url);
		webview.loadUrl(Constant.BASEURL + url);
		WebSettings settings = webview.getSettings();
		settings.setJavaScriptEnabled(true);// 这样网页就可加载JavaScript了
		webview.setWebViewClient(new WebViewClient());

		webview.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onReceivedTitle(WebView view, String title) { // 获取到Title
				super.onReceivedTitle(view, title);
				toolbarTitle.setText(title);
			}

			@Override
			public void onReceivedIcon(WebView view, Bitmap icon) { // 获取到图标
				super.onReceivedIcon(view, icon);
			}

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				webviewProgress.setVisibility(View.VISIBLE);
				webviewProgress.setProgress(newProgress);
				if (newProgress == 100) {
					webviewProgress.setVisibility(View.GONE);
				}
			}
		});


	}
}
