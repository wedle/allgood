package com.yiqihao.loan.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 冯浩 on 16/8/11.
 */
public class BankCardInfoModel implements Parcelable {
	private String id;
	/**
	 * 是否为默认银行1-是,0或空-否
	 */
	private String isdef;
	private String uid;
	/**
	 * 开户名为空，表示是本人，目前只能绑定本人银行卡，所以不用管此字段
	 */
	private String name;
	/**
	 * 银行名称
	 */
	private String bank;
	/**
	 * 开户省份
	 */
	private String area;
	/**
	 * 开户城市
	 */
	private String city;
	/**
	 * 开户支行名称
	 */
	private String branch;
	/**
	 * 银行标识码
	 */
	private String code;
	/**
	 * 银行卡号
	 */
	private String card;
	/**
	 * 银行卡预留手机号
	 */
	private String mobile;
	/**
	 * 状态:11-待审核,21-已审核-22-已绑定同卡进出
	 */
	private String status;
	private String addtime;
	private String uptime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsdef() {
		return isdef;
	}

	public void setIsdef(String isdef) {
		this.isdef = isdef;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getUptime() {
		return uptime;
	}

	public void setUptime(String uptime) {
		this.uptime = uptime;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.id);
		dest.writeString(this.isdef);
		dest.writeString(this.uid);
		dest.writeString(this.name);
		dest.writeString(this.bank);
		dest.writeString(this.area);
		dest.writeString(this.city);
		dest.writeString(this.branch);
		dest.writeString(this.code);
		dest.writeString(this.card);
		dest.writeString(this.mobile);
		dest.writeString(this.status);
		dest.writeString(this.addtime);
		dest.writeString(this.uptime);
	}

	public BankCardInfoModel() {
	}

	protected BankCardInfoModel(Parcel in) {
		this.id = in.readString();
		this.isdef = in.readString();
		this.uid = in.readString();
		this.name = in.readString();
		this.bank = in.readString();
		this.area = in.readString();
		this.city = in.readString();
		this.branch = in.readString();
		this.code = in.readString();
		this.card = in.readString();
		this.mobile = in.readString();
		this.status = in.readString();
		this.addtime = in.readString();
		this.uptime = in.readString();
	}

	public static final Creator<BankCardInfoModel> CREATOR = new Creator<BankCardInfoModel>() {
		@Override
		public BankCardInfoModel createFromParcel(Parcel source) {
			return new BankCardInfoModel(source);
		}

		@Override
		public BankCardInfoModel[] newArray(int size) {
			return new BankCardInfoModel[size];
		}
	};
}
