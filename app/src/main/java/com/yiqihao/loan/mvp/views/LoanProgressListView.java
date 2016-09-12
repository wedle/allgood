package com.yiqihao.loan.mvp.views;


import com.yiqihao.loan.entity.LangZnModel;
import com.yiqihao.loan.entity.LoanInfoModel;

import java.util.List;

/**
 * Created by 冯浩 on 16/8/10.
 */
public interface LoanProgressListView extends RLView<List<LoanInfoModel>>{

	void setLangZn(LangZnModel langZn);

}
