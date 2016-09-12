package com.yiqihao.loan.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yiqihao.loan.R;
import com.yiqihao.loan.ui.adapter.LoanProgressPagerAdapter;
import com.yiqihao.loan.widget.PagerSlidingTabStrip;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 贷款进度
 * Created by 冯浩 on 16/8/17.
 */
public class LoanProgressFragment extends BaseFragment {

	@BindView(R.id.tabs)
	PagerSlidingTabStrip tabs;
	@BindView(R.id.pager)
	ViewPager pager;
	private View view;

	private LoanProgressPagerAdapter adapter;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
			savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_loan_progress, null);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	private void initView() {
		adapter = new LoanProgressPagerAdapter(getChildFragmentManager());
		pager.setAdapter(adapter);
		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
				.getDisplayMetrics());
		pager.setPageMargin(pageMargin);
		pager.setOffscreenPageLimit(5);
		tabs.setViewPager(pager);
	}

}
