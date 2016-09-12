package com.yiqihao.loan.mvp.views;


import com.yiqihao.loan.entity.BankBranchInfoModel;
import com.yiqihao.loan.entity.BankCardListModel;

import java.util.List;

/**
 * Created by 冯浩 on 16/8/12.
 */
public interface AddBankCardView extends MvpView{

	void showProgress(String msg);

	void hideProgress();

	void getBankListSuccess(BankCardListModel bankCardListModel);

	void getBankListError(boolean isShow, String msg);

	/**
	 * 获取银行支行信息成功
	 */
	void onGetBranchInfoSucceed(List<BankBranchInfoModel> list);

	/**
	 * 获取银行支行信息失败
	 */
	void onGetBranchInfoError(boolean isShow, String msg);

	void sendSmsCodeSuccess(String msg);

	void sendSmsCodeError(boolean isShowError, String msg);

	/**
	 * 添加银行卡成功
	 */
	void onAddBankSucceed(String bankId);

	/**
	 * 添加银行卡失败
	 * @param b
	 * @param errorMsg
	 */
	void onAddBankError(boolean b, String errorMsg);

	/**
	 * 编辑银行卡成功
	 */
	void onEditBankSucceed();

	/**
	 * 编辑银行卡失败
	 * @param b
	 * @param errorMsg
	 */
	void onEditBankError(boolean b, String errorMsg);

}
