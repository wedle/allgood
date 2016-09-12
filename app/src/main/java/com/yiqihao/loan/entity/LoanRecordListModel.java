package com.yiqihao.loan.entity;

import java.util.List;

public class LoanRecordListModel {

    private List<LoanRecordModel> list;

    private LangZnModel lang_zn;

    public LangZnModel getLang_zn() {
        return lang_zn;
    }

    public void setLang_zn(LangZnModel lang_zn) {
        this.lang_zn = lang_zn;
    }

    public List<LoanRecordModel> getList() {
        return list;
    }

    public void setList(List<LoanRecordModel> list) {
        this.list = list;
    }
}
