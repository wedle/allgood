package com.yiqihao.loan.ui.adapter;

import android.content.Context;

import com.yiqihao.loan.entity.BankBranchInfoModel;
import com.yiqihao.loan.widget.wheel.adapter.AbstractWheelTextAdapter;

import java.util.List;


/**
 * Created by 武昌丶鱼 on 2016/5/17.
 * Description:
 */
public class BranchAdapter extends AbstractWheelTextAdapter {

    private List<BankBranchInfoModel> list;

    public BranchAdapter(Context context, List<BankBranchInfoModel> list) {
        super(context);
        this.list = list;
    }
    public void setData(List<BankBranchInfoModel> list){
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
            return list.get(index).getBranch();
        }
        return null;
    }


}
