package com.yiqihao.loan.entity;

import java.util.List;

public class CommissionListModel {
    /**
     * 累计贷款金额
     */
    private String sum_commission;
    /**
     * 待赚取佣金
     */
    private String wait_commission;

    private int sum_no;

    private List<CommissionInfoModel> list;

    private LangZnModel lang_zn;

    public LangZnModel getLang_zn() {
        return lang_zn;
    }

    public void setLang_zn(LangZnModel lang_zn) {
        this.lang_zn = lang_zn;
    }

    public String getSum_commission() {
        return sum_commission;
    }

    public void setSum_commission(String sum_commission) {
        this.sum_commission = sum_commission;
    }

    public String getWait_commission() {
        return wait_commission;
    }

    public void setWait_commission(String wait_commission) {
        this.wait_commission = wait_commission;
    }


    public List<CommissionInfoModel> getList() {
        return list;
    }

    public void setList(List<CommissionInfoModel> list) {
        this.list = list;
    }

    public int getSum_no() {
        return sum_no;
    }

    public void setSum_no(int sum_no) {
        this.sum_no = sum_no;
    }
}
