package com.yiqihao.loan.mvp.views;


import com.yiqihao.loan.entity.UserInfoModel;

/**
 * Created by 冯浩 on 16/8/18.
 */
public interface PersonalView extends MvpView{

	void getUserInfoSuccess(UserInfoModel userInfoModel);

	void getUserInfoError(boolean isShow, String msg);

	void hideLoading();

}
