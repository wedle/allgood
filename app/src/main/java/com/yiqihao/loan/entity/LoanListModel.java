package com.yiqihao.loan.entity;

import java.util.List;

/**
 * Created by 冯浩 on 16/8/10.
 */
public class LoanListModel {

	/**
	 * 累计贷款金额
	 */
	private String loan_money;
	/**
	 * 待赚取佣金
	 */
	private String wait_commission;
	private PageModel page;

	private List<LoanInfoModel> list;

	private LangZnModel lang_zn;


	public LangZnModel getLang_zn() {
		return lang_zn;
	}

	public void setLang_zn(LangZnModel lang_zn) {
		this.lang_zn = lang_zn;
	}

	public String getLoan_money() {
		return loan_money;
	}

	public void setLoan_money(String loan_money) {
		this.loan_money = loan_money;
	}

	public String getWait_commission() {
		return wait_commission;
	}

	public void setWait_commission(String wait_commission) {
		this.wait_commission = wait_commission;
	}

	public PageModel getPage() {
		return page;
	}

	public void setPage(PageModel page) {
		this.page = page;
	}

	public List<LoanInfoModel> getList() {
		return list;
	}

	public void setList(List<LoanInfoModel> list) {
		this.list = list;
	}
}
