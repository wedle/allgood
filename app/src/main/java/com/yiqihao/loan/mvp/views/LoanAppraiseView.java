package com.yiqihao.loan.mvp.views;

/**
 * Created by 冯浩 on 16/8/18.
 */
public interface LoanAppraiseView extends MvpView {

	void showProgress(String msg);

	void hideProgress();

	void loanApplySuccess(String lid);

	void loanApplyError(boolean isShowError, String msg);

}
