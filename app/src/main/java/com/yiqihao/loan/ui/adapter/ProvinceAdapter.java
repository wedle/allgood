package com.yiqihao.loan.ui.adapter;

import android.content.Context;

import com.yiqihao.loan.entity.ProvinceInfoModel;
import com.yiqihao.loan.widget.wheel.adapter.AbstractWheelTextAdapter;

import java.util.List;


/**
 * Created by 武昌丶鱼 on 2016/5/17.
 * Description:
 */
public class ProvinceAdapter extends AbstractWheelTextAdapter {

    private List<ProvinceInfoModel> list;

    public ProvinceAdapter(Context context, List<ProvinceInfoModel> list) {
        super(context);
        this.list = list;
    }

    @Override
    public int getItemsCount() {
        return list == null ? 0 :list.size();
    }

    @Override
    protected CharSequence getItemText(int index) {
        if (index >= 0 && index < list.size()) {
            return list.get(index).getName();
        }
        return null;
    }

}