package com.yiqihao.loan.mvp.views;

import java.util.List;

/**
 * Created by 冯浩 on 16/8/18.
 */
public interface LoanApplyView extends MvpView{

	void showProgress(String msg);

	void hideProgress();

	void loanAppraiseSuccess(String appraise_amount);

	void loanAppraiseError(boolean isShowError, String msg);

	void getCarListSuccess(List<String> list);

	void getCarListError(boolean isShowError, String msg);

}
