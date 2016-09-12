package com.yiqihao.loan.mvp.views;


import com.yiqihao.loan.entity.BankCardListModel;

/**
 * Created by 冯浩 on 16/8/9.
 */
public interface WithdrawBankCardView extends MvpView{

	void showProgress(String msg);

	void hideProgress();

	void setDefaultBankSuccess();

	void setDefaultBankError(boolean isShow, String msg);

	void deleteBankCardSuccess();

	void deleteBankCardError(boolean isShow, String msg);


	void getBankListSuccess(BankCardListModel bankCardListModel);

	void getBankListError(boolean isShow, String msg);


}
