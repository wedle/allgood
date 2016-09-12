package com.yiqihao.loan.entity;

/**
 * Created by Gu on 2016/8/18.
 */
public class LoanDetailModel {

    private String lid;
    /**
     * 借款人名称
     */
    private String loanname;
    /**
     * 申请贷款金额
     */
    private String amount;
    /**
     * 申请贷款期限
     */
    private String deadline;
    /**
     * 为d时就是天标
     */
    private String deadline_type;
    private String creditno;
    /**
     * 天数
     */
    private String days;
    /**
     * 申请贷款状态：2待补充资料,3审批中,4审批通过,5还款中，9已结清，10审批拒绝
     */
    private String status;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 理财系统贷款编号
     */
    private String llid;
    /**
     * 合同webview地址
     */
    private String lurl;
    /**
     * 产品类型
     */
    private String type;
    /**
     * 借款人性别 0-未知,1-男,2-女
     */
    private String loangarden;
    /**
     * 审批记录
     */
    private LoanRecordListModel record;
    /**
     * 车辆信息
     */
    private CarInfoModel carinfo;
    /**
     * 佣金明细
     */
    private CommissionListModel commission;

    private LangZnModel lang_zn;

    public String getDeadline_type() {
        return deadline_type;
    }

    public void setDeadline_type(String deadline_type) {
        this.deadline_type = deadline_type;
    }

    public String getDays() {
        return days;
    }

    public String getCreditno() {
        return creditno;
    }

    public void setCreditno(String creditno) {
        this.creditno = creditno;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public LangZnModel getLang_zn() {
        return lang_zn;
    }

    public void setLang_zn(LangZnModel lang_zn) {
        this.lang_zn = lang_zn;
    }

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public String getLoanname() {
        return loanname;
    }

    public void setLoanname(String loanname) {
        this.loanname = loanname;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLlid() {
        return llid;
    }

    public void setLlid(String llid) {
        this.llid = llid;
    }

    public String getLurl() {
        return lurl;
    }

    public void setLurl(String lurl) {
        this.lurl = lurl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLoangarden() {
        return loangarden;
    }

    public void setLoangarden(String loangarden) {
        this.loangarden = loangarden;
    }

    public LoanRecordListModel getRecord() {
        return record;
    }

    public void setRecord(LoanRecordListModel record) {
        this.record = record;
    }

    public CarInfoModel getCarinfo() {
        return carinfo;
    }

    public void setCarinfo(CarInfoModel carinfo) {
        this.carinfo = carinfo;
    }

    public CommissionListModel getCommission() {
        return commission;
    }

    public void setCommission(CommissionListModel commission) {
        this.commission = commission;
    }
}
