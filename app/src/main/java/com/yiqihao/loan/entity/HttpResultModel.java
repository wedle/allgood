package com.yiqihao.loan.entity;


import com.google.gson.annotations.SerializedName;

/**
 * Created by 冯浩 on 16/8/17
 */
public class HttpResultModel<T> {

	@SerializedName("return")
	private int resultCode;
	private String errmsg;
	private String msg;
	private T data;
	private String session_key;

	private String url;

	private String version;

	private String feature;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSession_key() {
		return session_key;
	}

	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getVersion() {

		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
