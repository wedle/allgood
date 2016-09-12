package com.yiqihao.loan.ui.adapter;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yiqihao.loan.R;
import com.yiqihao.loan.entity.BrandsModel;
import com.yiqihao.loan.ui.activity.CarChooseActivity;
import com.yiqihao.loan.ui.fragment.CarSeriesDialogFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 冯浩 on 2016/4/13.
 */
public class CarChooseAdapter extends BaseAdapter {

	private static final String TAG = "CarChooseAdapter";

	private List<BrandsModel> list;
	private CarChooseActivity activity;

	public CarChooseAdapter(CarChooseActivity activity, List<BrandsModel> list) {
		this.list = list;
		this.activity = activity;
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
			convertView = LayoutInflater.from(activity).inflate(R.layout.lv_item_car_choose, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final BrandsModel model = getItem(position);

		String catalog;
		String lastCatalog;

		if (position != 0) {
			 /* 首字拼音 */
			catalog = list.get(position).getLetter();
            /*
             * 下一个汉字首字拼音，如果两个汉字的首字母相等，下一个汉字的tv_catalog隐藏，这里其实就是把相同首字母的汉字叠加在一起
			 */
			lastCatalog = list.get(position - 1).getLetter();

			if (catalog.equals(lastCatalog)) {
				holder.catalog.setVisibility(View.GONE);
			} else {
				holder.catalog.setText(model.getLetter());
				holder.catalog.setVisibility(View.VISIBLE);
			}
		} else {
			holder.catalog.setText(model.getLetter());
			holder.catalog.setVisibility(View.VISIBLE);
		}

		holder.title.setText(model.getName());


		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				CarSeriesDialogFragment carSeriesDialogFragment = new CarSeriesDialogFragment();
				Bundle bundle = new Bundle();
				bundle.putString("brand_id", model.getId());
				bundle.putString("brand_name", model.getName());
				carSeriesDialogFragment.setArguments(bundle);
				carSeriesDialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.customDialog);
				carSeriesDialogFragment.show(activity.getFragmentManager(), "CarSeriesDialogFragment");


			}
		});

		return convertView;
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getLetter();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		return -1;
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
