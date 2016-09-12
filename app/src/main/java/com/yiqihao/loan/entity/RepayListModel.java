package com.yiqihao.loan.entity;

import java.util.List;

/**
 * Created by 冯浩 on 16/8/20.
 */
public class RepayListModel {

	private PageModel page;

	private List<RepayModel> list;

	private LangZnModel lang_zn;

	private String card;

	private String bank;

	public LangZnModel getLang_zn() {
		return lang_zn;
	}

	public void setLang_zn(LangZnModel lang_zn) {
		this.lang_zn = lang_zn;
	}

	public PageModel getPage() {
		return page;
	}

	public void setPage(PageModel page) {
		this.page = page;
	}

	public List<RepayModel> getList() {
		return list;
	}

	public void setList(List<RepayModel> list) {
		this.list = list;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}
}
