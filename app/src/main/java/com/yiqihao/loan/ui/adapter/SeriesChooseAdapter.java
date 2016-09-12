package com.yiqihao.loan.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yiqihao.loan.R;
import com.yiqihao.loan.entity.BrandsModel;
import com.yiqihao.loan.ui.activity.CarModelActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 冯浩 on 2016/8/17.
 */
public class SeriesChooseAdapter extends BaseAdapter {

	private static final String TAG = "SeriesChooseAdapter";

	private List<BrandsModel> list;
	private Context mContext;
	private String id;
	private String brand_name;

	public SeriesChooseAdapter(Context mContext, String id,String brand_name) {
		this.mContext = mContext;
		this.id = id;
		this.brand_name = brand_name;
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

		String catalog;
		String lastCatalog;

//        if (position != 0) {
//             /* 首字拼音 */
//            catalog = list.get(position).getLetter();
//            /*
//             * 下一个汉字首字拼音，如果两个汉字的首字母相等，下一个汉字的tv_catalog隐藏，这里其实就是把相同首字母的汉字叠加在一起
//			 */
//            lastCatalog = list.get(position - 1).getLetter();
//
//            if (catalog.equals(lastCatalog)) {
//                holder.catalog.setVisibility(View.GONE);
//            } else {
//                holder.catalog.setText(model.getLetter());
//                holder.catalog.setVisibility(View.VISIBLE);
//            }
//        } else {
//            holder.catalog.setText(model.getLetter());
//            holder.catalog.setVisibility(View.VISIBLE);
//        }
		holder.catalog.setVisibility(View.GONE);
		holder.title.setText(model.getSeries_name());


		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Intent intent = new Intent(mContext, CarModelActivity.class);
				intent.putExtra("brand_id", id);
				intent.putExtra("brand_name", brand_name);
				intent.putExtra("series_id", model.getSeries_id());
				intent.putExtra("series_name", model.getSeries_name());
				mContext.startActivity(intent);

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
