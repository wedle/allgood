package com.yiqihao.loan.mvp.views;

import com.yiqihao.loan.entity.RepayModel;

import java.util.List;

/**
 * Created by 冯浩 on 16/8/23.
 */
public interface RepayDetailView extends  RLView<List<RepayModel>>{

	void setBankInfo(String card,String bank);

}
