package com.yiqihao.loan.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yiqihao.loan.Constant;
import com.yiqihao.loan.R;
import com.yiqihao.loan.entity.BrandsModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 冯浩 on 2016/8/17.
 */
public class CarModelChooseAdapter extends BaseAdapter {

    private static final String TAG = "CarModelChooseAdapter";

    private List<BrandsModel> list;
    private Context mContext;

    private String brand_id;
    private String series_id;
    private String brand_name;
    private String series_name;

    public CarModelChooseAdapter(Context mContext,String brand_id,String series_id,String brand_name, String series_name) {
        this.mContext = mContext;
        this.brand_id = brand_id;
        this.series_id = series_id;
        this.brand_name = brand_name;
        this.series_name = series_name;
    }


    public void setData(List<BrandsModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public BrandsModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.lv_item_car_choose, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final BrandsModel model = getItem(position);

        holder.catalog.setVisibility(View.GONE);
        holder.title.setText(model.getModel_name());


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(Constant.ACTION_CAR_INFO);
                intent1.putExtra("carName", model.getModel_name());
                intent1.putExtra("brand_name",brand_name);
                intent1.putExtra("series_name", series_name);
                intent1.putExtra("modelId", model.getModel_id());
                intent1.putExtra("brandId", brand_id);
                intent1.putExtra("seriesId", series_id);
                mContext.sendBroadcast(intent1);
                ((Activity) mContext).finish();

            }
        });

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.catalog)
        TextView catalog;
        @BindView(R.id.title)
        TextView title;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
