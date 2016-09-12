package com.yiqihao.loan.entity;

/**
 * Created by 冯浩 on 16/8/10.
 */
public class LoanInfoModel {

	private String lid;
	/**
	 * 贷款类型-1抵押车
	 */
	private String type;
	/**
	 * 贷款金额
	 */
	private String amount;
	private String deadline_type;
	private String days;
	/**
	 * 贷款期限
	 */
	private String deadline;
	/**
	 * 借款时间
	 */
	private String addtime;
	/**
	 * 申请贷款状态：2待补充资料,3审批中,4审批通过,5还款中，9已结清，10审批拒绝
	 */
	private String status;

	public String getDeadline_type() {
		return deadline_type;
	}

	public void setDeadline_type(String deadline_type) {
		this.deadline_type = deadline_type;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getLid() {
		return lid;
	}

	public void setLid(String lid) {
		this.lid = lid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
