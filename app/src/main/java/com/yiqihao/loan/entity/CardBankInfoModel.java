package com.yiqihao.loan.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 冯浩 on 16/8/11.
 */
public class CardBankInfoModel implements Parcelable {

	private String code;

	private String name;

	private String limitinfo;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLimitinfo() {
		return limitinfo;
	}

	public void setLimitinfo(String limitinfo) {
		this.limitinfo = limitinfo;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.code);
		dest.writeString(this.name);
		dest.writeString(this.limitinfo);
	}

	public CardBankInfoModel() {
	}

	protected CardBankInfoModel(Parcel in) {
		this.code = in.readString();
		this.name = in.readString();
		this.limitinfo = in.readString();
	}

	public static final Creator<CardBankInfoModel> CREATOR = new Creator<CardBankInfoModel>() {
		@Override
		public CardBankInfoModel createFromParcel(Parcel source) {
			return new CardBankInfoModel(source);
		}

		@Override
		public CardBankInfoModel[] newArray(int size) {
			return new CardBankInfoModel[size];
		}
	};
}
