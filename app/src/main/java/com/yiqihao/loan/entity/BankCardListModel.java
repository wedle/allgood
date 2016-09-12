package com.yiqihao.loan.entity;

import java.util.List;

/**
 * Created by 冯浩 on 16/8/11.
 */
public class BankCardListModel {

	private List<String> needbranch;

	private List<BankCardInfoModel> list;

	private List<CardBankInfoModel> cardbank;

	public List<BankCardInfoModel> getList() {
		return list;
	}

	public void setList(List<BankCardInfoModel> list) {
		this.list = list;
	}

	public List<String> getNeedbranch() {
		return needbranch;
	}

	public void setNeedbranch(List<String> needbranch) {
		this.needbranch = needbranch;
	}

	public List<CardBankInfoModel> getCardbank() {
		return cardbank;
	}

	public void setCardbank(List<CardBankInfoModel> cardbank) {
		this.cardbank = cardbank;
	}
}
