package com.yiqihao.loan.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yiqihao.loan.ui.fragment.LoanProgressListFragment;

/**
 * Created by 冯浩 on 16/8/10.
 */
public class LoanProgressPagerAdapter extends FragmentPagerAdapter {


	/**
	 * all--全部，doing--审批中，fail--审批拒绝，success-审批成功   repay-还款中  ok-已结清
	 */
	public static final String ALL="all";
	public static final String DOING="doing";
	public static final String FAIL="fail";
	public static final String REPAY="repay";
	public static final String DELAY="delay";
	public static final String OK="ok";

	private final String[] TITLES = {"全部", "审批中","审批拒绝","还款中","逾期","已结清"};

	public LoanProgressPagerAdapter(FragmentManager fragmentManager) {
		super(fragmentManager);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return TITLES[position];
	}

	@Override
	public int getCount() {
		return TITLES.length;
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
			case 0:
				return LoanProgressListFragment.newInstance(ALL);
			case 1:
				return LoanProgressListFragment.newInstance(DOING);
			case 2:
				return LoanProgressListFragment.newInstance(FAIL);
			case 3:
				return LoanProgressListFragment.newInstance(REPAY);
			case 4:
				return LoanProgressListFragment.newInstance(DELAY);
			case 5:
				return LoanProgressListFragment.newInstance(OK);

			default:
				return null;
		}
	}
}
