package com.yiqihao.loan.ui.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.yiqihao.loan.Constant;
import com.yiqihao.loan.R;
import com.yiqihao.loan.entity.BrandsModel;
import com.yiqihao.loan.entity.CarResultModel;
import com.yiqihao.loan.net.loan.LoanFactory;
import com.yiqihao.loan.ui.adapter.SeriesChooseAdapter;
import com.yiqihao.loan.utils.T;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 车系dialog
 * Created by 冯浩 on 2016/8/17.
 */
public class CarSeriesDialogFragment extends DialogFragment {

	private static final String TAG = "CarSeriesDialogFragment";

	@BindView(R.id.lv_series)
	ListView lvSeries;
	@BindView(R.id.pb_loading)
	ProgressBar pbLoading;

	private SeriesChooseAdapter adapter;

	private CompositeSubscription compositeSubscription;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		View view = inflater.inflate(R.layout.dialog_car_choose, container);
		ButterKnife.bind(this, view);
		return view;
	}

	private String id;
	private String name;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Bundle bundle = getArguments();
		id = bundle.getString("brand_id");
		name = bundle.getString("brand_name");
		initView();
		initData(id);
	}

	private void initView() {

		adapter = new SeriesChooseAdapter(getActivity(), id,name);
		lvSeries.setAdapter(adapter);
	}

	private void initData(final String id) {
		Map<String, String> map = new HashMap<>();
		map.put("type", "2");
		map.put("brand_id", id);
		Subscription s = LoanFactory.getLoanSingleton().getCarInfo(map)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<CarResultModel>() {
					@Override
					public void onCompleted() {
					}

					@Override
					public void onError(Throwable e) {
						pbLoading.setVisibility(View.GONE);
						lvSeries.setVisibility(View.GONE);
						T.showShort(getActivity(), "加载失败");
					}

					@Override
					public void onNext(CarResultModel response) {

						if (response.getResultCode()== Constant.SUCCESS) {

							List<BrandsModel> seriesModels = response.getData();

							pbLoading.setVisibility(View.GONE);
							lvSeries.setVisibility(View.VISIBLE);
							if (seriesModels.size() != 0) {
								adapter.setData(seriesModels);
							}
						} else {
							pbLoading.setVisibility(View.GONE);
							lvSeries.setVisibility(View.GONE);
							T.showShort(getActivity(), "" + response.getErrmsg());
						}


					}
				});


		compositeSubscription = new CompositeSubscription();
		compositeSubscription.add(s);


	}

	@Override
	public void onStart() {
		super.onStart();
		Dialog dialog = getDialog();
		if (dialog != null) {
			Window window = dialog.getWindow();
			window.setGravity(Gravity.RIGHT); // 设置dialog显示的位置
			window.setWindowAnimations(R.style.AnimRight); // 添加动画
			window.setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
		}
	}


	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (this.compositeSubscription != null) {
			this.compositeSubscription.unsubscribe();
		}
	}
}
