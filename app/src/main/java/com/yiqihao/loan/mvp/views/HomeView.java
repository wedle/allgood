package com.yiqihao.loan.mvp.views;


import com.yiqihao.loan.entity.BannerInfoModel;

import java.util.List;

/**
 * Created by 冯浩 on 16/8/29.
 */
public interface HomeView extends MvpView {

	void getBannerInfoSuccess(List<BannerInfoModel> bannerInfoModels);

	void getBannerInfoError(boolean isShow, String msg);

}
