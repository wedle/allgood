package com.yiqihao.loan.entity;

/**
 * Created by 武昌丶鱼 on 2016/5/17.
 * Description:
 */
public class BankBranchInfoModel {

    /**
     * no : 104501022023
     * branch : 中国银行焦作市解放路支行
     */

    private String no;
    private String branch;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    @Override
    public String toString() {
        return "BankBranchInfoModel{" +
                "no='" + no + '\'' +
                ", branch='" + branch + '\'' +
                '}';
    }
}
