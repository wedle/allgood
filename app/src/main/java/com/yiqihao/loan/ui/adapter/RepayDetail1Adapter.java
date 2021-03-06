package com.yiqihao.loan.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yiqihao.loan.R;
import com.yiqihao.loan.entity.RepayModel;
import com.yiqihao.loan.utils.DateUtils;
import com.yiqihao.loan.utils.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 还款计划详情adapter
 * Created by 冯浩 on 16/8/10.
 */
public class RepayDetail1Adapter extends BaseAdapter {
	private Context mContext;
	private List<RepayModel> list;

	public RepayDetail1Adapter(Context context) {
		this.mContext = context;
	}

	public void addList(List<RepayModel> addList) {
		if (list == null)
			return;
		list.addAll(addList);
		notifyDataSetChanged();
	}


	public List<RepayModel> getData() {
		return list;
	}

	public void setData(List<RepayModel> list) {
		this.list = list;
		notifyDataSetChanged();

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list == null ? 0 : list.size();
	}

	@Override
	public RepayModel getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.lv_loan_repay_plan_item1, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		final RepayModel model = getItem(position);

		viewHolder.tvAmount.setText(model.getAmount()+"元");

		viewHolder.tvTime.setText(DateUtils.formatDateMonOfDay(model.getTime()));

		viewHolder.tvLixi.setText("含利息"+StringUtils.formatMoney(String.valueOf(Double
				.valueOf(model.getAmount()) - Double.valueOf(model.getMoney()))));

		return convertView;
	}


	static class ViewHolder {
		@BindView(R.id.tv_time)
		TextView tvTime;
		@BindView(R.id.tv_amount)
		TextView tvAmount;
		@BindView(R.id.tv_lixi)
		TextView tvLixi;
		@BindView(R.id.tv_benqi)
		TextView tvBenqi;

		ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
	}
}
