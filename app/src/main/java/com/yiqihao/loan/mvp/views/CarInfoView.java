package com.yiqihao.loan.mvp.views;


/**
 * Created by 冯浩 on 16/8/17.
 */
public interface CarInfoView extends MvpView {

	void showProgress(String msg);

	void hideProgress();

	void saveSuccess();

	void saveError(boolean isShowError, String msg);

}
