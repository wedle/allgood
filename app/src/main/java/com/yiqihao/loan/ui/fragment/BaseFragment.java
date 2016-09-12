package com.yiqihao.loan.ui.fragment;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by 冯浩 on 16/8/17.
 */
public class BaseFragment extends Fragment {

	private CompositeSubscription mCompositeSubscription;
	protected ProgressDialog pg = null;


	public void addSubscription(Subscription s) {
		if (this.mCompositeSubscription == null) {
			this.mCompositeSubscription = new CompositeSubscription();
		}

		this.mCompositeSubscription.add(s);
	}

	public ProgressDialog getProgressDialog() {
		if (pg == null) {
			pg = new ProgressDialog(getContext());
		}
		return pg;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (this.mCompositeSubscription != null) {
			this.mCompositeSubscription.unsubscribe();
		}
	}
}
