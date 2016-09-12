package com.yiqihao.loan.mvp.views;

import com.yiqihao.loan.entity.LangZnModel;
import com.yiqihao.loan.entity.RepayModel;

import java.util.List;

/**
 * Created by 冯浩 on 16/8/20.
 */
public interface AdvanceRepayView extends RLView<List<RepayModel>> {

	void setLangZn(LangZnModel langZn);
}
