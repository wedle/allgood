package com.yiqihao.loan.mvp.views;

import com.yiqihao.loan.entity.LoanDetailModel;

public interface LoanDetailView extends MvpView{

	void showError(boolean b, String s);

	void showContent(LoanDetailModel data);

}
