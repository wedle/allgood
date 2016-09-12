package com.yiqihao.loan.entity;

import android.content.Context;

import com.yiqihao.loan.widget.wheel.adapter.AbstractWheelTextAdapter;

import java.util.List;


/**
 * Created by 武昌丶鱼 on 2016/5/17.
 * Description:
 */
public class CityAdapter extends AbstractWheelTextAdapter {
    private List<CityInfoModel> list;

    public CityAdapter(Context context, List<CityInfoModel> list) {
        super(context);
        this.list = list;
    }

    public void setData(List<CityInfoModel> list){
        this.list = list;
//		notifyDataChangedEvent();
        notifyDataInvalidatedEvent();
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