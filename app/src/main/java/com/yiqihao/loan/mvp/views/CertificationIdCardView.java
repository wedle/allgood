package com.yiqihao.loan.mvp.views;

/**
 * Created by 冯浩 on 16/8/16.
 */
public interface CertificationIdCardView extends MvpView {

	void showProgress(String msg);

	void hideProgress();

	void certificationIdCardSuccess();

	void certificationIdCardError(boolean isShow, String msg);

}
