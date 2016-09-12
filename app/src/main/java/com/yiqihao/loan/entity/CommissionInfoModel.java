package com.yiqihao.loan.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class CommissionInfoModel implements Parcelable {

    private String cid;
    private String lid;
    private String luid;
    /**
     * 第几期，0代表前期佣金，1,2,3代表每一期还款获得的佣金
     */
    private String no;
    private String deadline;

    /**
     * 贷款类型
     */
    private String type;

    /**
     * 为d时就是天标
     */
    private String deadline_type;
    /**
     * 天数
     */
    private String days;
    /**
     * 计算佣金金额
     */
    private String aprmoney;
    /**
     * 剩余期数
     */
    private String wait_no;
    /**
     * 贷款金额
     */
    private String amount;
    /**
     * 贷款总金额
     */
    private String allmount;
    /**
     * 本单共赚取
     */
    private String total_commission;
    /**
     * 佣金比例
     */
    private String apr;
    /**
     * 利息
     */
    private String money;
    /**
     * 佣金
     */
    private String commission;
    /**
     * 总期数
     */
    private String sum_no;
    /**
     * 还款时间
     */
    private String addtime;

    private String time;
    /**
     * 还款状态：1待还款，2已还款，3逾期
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

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getAllmount() {
        return allmount;
    }

    public void setAllmount(String allmount) {
        this.allmount = allmount;
    }

    public String getLuid() {
        return luid;
    }

    public void setLuid(String luid) {
        this.luid = luid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAprmoney() {
        return aprmoney;
    }

    public String getTotal_commission() {
        return total_commission;
    }

    public void setTotal_commission(String total_commission) {
        this.total_commission = total_commission;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setAprmoney(String aprmoney) {
        this.aprmoney = aprmoney;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getApr() {
        return apr;
    }

    public void setApr(String apr) {
        this.apr = apr;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getSum_no() {
        return sum_no;
    }

    public void setSum_no(String sum_no) {
        this.sum_no = sum_no;
    }

    public CommissionInfoModel() {
    }

    public String getWait_no() {
        return wait_no;
    }

    public void setWait_no(String wait_no) {
        this.wait_no = wait_no;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cid);
        dest.writeString(this.lid);
        dest.writeString(this.luid);
        dest.writeString(this.no);
        dest.writeString(this.deadline);
        dest.writeString(this.type);
        dest.writeString(this.deadline_type);
        dest.writeString(this.days);
        dest.writeString(this.aprmoney);
        dest.writeString(this.wait_no);
        dest.writeString(this.amount);
        dest.writeString(this.allmount);
        dest.writeString(this.total_commission);
        dest.writeString(this.apr);
        dest.writeString(this.money);
        dest.writeString(this.commission);
        dest.writeString(this.sum_no);
        dest.writeString(this.addtime);
        dest.writeString(this.time);
        dest.writeString(this.status);
    }

    protected CommissionInfoModel(Parcel in) {
        this.cid = in.readString();
        this.lid = in.readString();
        this.luid = in.readString();
        this.no = in.readString();
        this.deadline = in.readString();
        this.type = in.readString();
        this.deadline_type = in.readString();
        this.days = in.readString();
        this.aprmoney = in.readString();
        this.wait_no = in.readString();
        this.amount = in.readString();
        this.allmount = in.readString();
        this.total_commission = in.readString();
        this.apr = in.readString();
        this.money = in.readString();
        this.commission = in.readString();
        this.sum_no = in.readString();
        this.addtime = in.readString();
        this.time = in.readString();
        this.status = in.readString();
    }

    public static final Creator<CommissionInfoModel> CREATOR = new Creator<CommissionInfoModel>() {
        @Override
        public CommissionInfoModel createFromParcel(Parcel source) {
            return new CommissionInfoModel(source);
        }

        @Override
        public CommissionInfoModel[] newArray(int size) {
            return new CommissionInfoModel[size];
        }
    };
}
