package com.yiqihao.loan.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 冯浩 on 16/8/20.
 */
public class RepayModel implements Parcelable {

	private String cid;
	private String no;
	private String sum_no;
	private String money;
	private String amount;
	private String creditno;
	private String type;
	private String time;
	private String status;
	private String lid;
	private String wait_no;
	private String allmount;
	private String deadline;
	private String deadline_type;
	private String days;
	private String wait_loanmoney;
	private String wait_loanfee;
	private String commission;
	private boolean isChecked;
	private String card;
	private String bank;


	public String getCreditno() {
		return creditno;
	}

	public void setCreditno(String creditno) {
		this.creditno = creditno;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWait_no() {
		return wait_no;
	}

	public void setWait_no(String wait_no) {
		this.wait_no = wait_no;
	}

	public String getAllmount() {
		return allmount;
	}

	public void setAllmount(String allmount) {
		this.allmount = allmount;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

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

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getSum_no() {
		return sum_no;
	}

	public void setSum_no(String sum_no) {
		this.sum_no = sum_no;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean checked) {
		isChecked = checked;
	}

	public String getWait_loanmoney() {
		return wait_loanmoney;
	}

	public void setWait_loanmoney(String wait_loanmoney) {
		this.wait_loanmoney = wait_loanmoney;
	}

	public String getCommission() {
		return commission;
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}


	public String getWait_loanfee() {
		return wait_loanfee;
	}

	public void setWait_loanfee(String wait_loanfee) {
		this.wait_loanfee = wait_loanfee;
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

	public RepayModel() {
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.cid);
		dest.writeString(this.no);
		dest.writeString(this.sum_no);
		dest.writeString(this.money);
		dest.writeString(this.amount);
		dest.writeString(this.creditno);
		dest.writeString(this.type);
		dest.writeString(this.time);
		dest.writeString(this.status);
		dest.writeString(this.lid);
		dest.writeString(this.wait_no);
		dest.writeString(this.allmount);
		dest.writeString(this.deadline);
		dest.writeString(this.deadline_type);
		dest.writeString(this.days);
		dest.writeString(this.wait_loanmoney);
		dest.writeString(this.wait_loanfee);
		dest.writeString(this.commission);
		dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
		dest.writeString(this.card);
		dest.writeString(this.bank);
	}

	protected RepayModel(Parcel in) {
		this.cid = in.readString();
		this.no = in.readString();
		this.sum_no = in.readString();
		this.money = in.readString();
		this.amount = in.readString();
		this.creditno = in.readString();
		this.type = in.readString();
		this.time = in.readString();
		this.status = in.readString();
		this.lid = in.readString();
		this.wait_no = in.readString();
		this.allmount = in.readString();
		this.deadline = in.readString();
		this.deadline_type = in.readString();
		this.days = in.readString();
		this.wait_loanmoney = in.readString();
		this.wait_loanfee = in.readString();
		this.commission = in.readString();
		this.isChecked = in.readByte() != 0;
		this.card = in.readString();
		this.bank = in.readString();
	}

	public static final Creator<RepayModel> CREATOR = new Creator<RepayModel>() {
		@Override
		public RepayModel createFromParcel(Parcel source) {
			return new RepayModel(source);
		}

		@Override
		public RepayModel[] newArray(int size) {
			return new RepayModel[size];
		}
	};
}
