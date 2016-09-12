package com.yiqihao.loan.entity;

import java.util.Map;

/**
 * Created by 冯浩 on 16/8/15.
 */
public class LangZnModel {

	private Map<Integer, String> status_zn;
	private Map<Integer, String> type_zn;
	private Map<Integer, String> luid_zn;


	public Map<Integer, String> getType_zn() {
		return type_zn;
	}

	public void setType_zn(Map<Integer, String> type_zn) {
		this.type_zn = type_zn;
	}

	public Map<Integer, String> getLuid_zn() {
		return luid_zn;
	}

	public void setLuid_zn(Map<Integer, String> luid_zn) {
		this.luid_zn = luid_zn;
	}

	public Map<Integer, String> getStatus_zn() {
		return status_zn;
	}

	public void setStatus_cn(Map<Integer, String> status_zn) {
		this.status_zn = status_zn;
	}

}
