package com.yiqihao.loan.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.yiqihao.loan.R;
import com.yiqihao.loan.entity.BrandsModel;
import com.yiqihao.loan.mvp.presenters.CarModelPresenter;
import com.yiqihao.loan.mvp.views.CarModelView;
import com.yiqihao.loan.ui.adapter.CarModelChooseAdapter;
import com.yiqihao.loan.utils.T;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 冯浩 on 2016/8/17.
 */
public class CarModelActivity extends MvpActivity<CarModelView, CarModelPresenter> implements CarModelView {


	@BindView(R.id.pb_loading)
	ProgressBar pbLoading;
	@BindView(R.id.lv_model)
	ListView lvModel;
	@BindView(R.id.toolbar)
	Toolbar toolbar;

	private CarModelChooseAdapter adapter;

	private String brand_id;
	private String brand_name;
	private String series_id;
	private String series_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car_model);
		ButterKnife.bind(this);
		initToolBar(toolbar,true);
		brand_id = getIntent().getStringExtra("brand_id");
		brand_name = getIntent().getStringExtra("brand_name");
		series_id = getIntent().getStringExtra("series_id");
		series_name = getIntent().getStringExtra("series_name");


		initView();
		presenter.initData(brand_id, series_id);

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

	private void initView() {
		adapter = new CarModelChooseAdapter(this, brand_id, series_id, brand_name, series_name);
		lvModel.setAdapter(adapter);
	}


	@Override
	public void showContent(List<BrandsModel> data) {
		pbLoading.setVisibility(View.GONE);
		lvModel.setVisibility(View.VISIBLE);

		adapter.setData(data);

	}

	@Override
	public void showError(boolean isShow, String e) {
		T.showShort(this, "加载失败");
		pbLoading.setVisibility(View.GONE);
		lvModel.setVisibility(View.GONE);
	}

	@NonNull
	@Override
	public CarModelPresenter createPresenter() {
		return new CarModelPresenter();
	}
}
