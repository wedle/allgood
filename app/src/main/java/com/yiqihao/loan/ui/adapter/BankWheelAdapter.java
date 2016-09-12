package com.yiqihao.loan.ui.adapter;

import android.content.Context;

import com.yiqihao.loan.entity.CardBankInfoModel;
import com.yiqihao.loan.widget.wheel.adapter.AbstractWheelTextAdapter;

import java.util.List;


/**
 * Created by 武昌丶鱼 on 2016/5/16.
 * Description:
 */
public class BankWheelAdapter extends AbstractWheelTextAdapter {


    private List<CardBankInfoModel> list;

    public BankWheelAdapter(Context context, List<CardBankInfoModel> list) {
        super(context);
        this.list = list;
    }

    @Override
    public int getItemsCount() {
        // TODO Auto-generated method stub
        return list == null ? 0 : list.size();
    }

    @Override
    protected CharSequence getItemText(int index) {
        if (index >= 0 && index < list.size()) {
            return list.get(index).getName();
        }
        return null;
    }


}
