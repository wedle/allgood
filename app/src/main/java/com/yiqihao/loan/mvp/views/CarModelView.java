package com.yiqihao.loan.mvp.views;


import com.yiqihao.loan.entity.BrandsModel;

import java.util.List;

/**
 * Created by 冯浩 on 2016/8/17.
 */
public interface CarModelView extends MvpView{

    /**
     * 显示内容
     */
    void showContent(List<BrandsModel> data);

    /**
     * 显示错误
     */
    void showError(boolean isShow, String e);

}
