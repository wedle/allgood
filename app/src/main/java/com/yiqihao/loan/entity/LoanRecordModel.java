package com.yiqihao.loan.entity;

public class LoanRecordModel {

	/**
	 * 审批角色
	 */
	private String role;
	/**
	 * 审批结果
	 */
	private String status;
	/**
	 * 审批备注
	 */
	private String remark;
	/**
	 * 审批时间
	 */
	private String time;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
