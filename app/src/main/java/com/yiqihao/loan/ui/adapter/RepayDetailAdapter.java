package com.yiqihao.loan.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yiqihao.loan.R;
import com.yiqihao.loan.entity.RepayModel;
import com.yiqihao.loan.utils.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 还款计划详情adapter
 * Created by 冯浩 on 16/8/10.
 */
public class RepayDetailAdapter extends BaseAdapter {
	private Context mContext;
	private List<RepayModel> list;

	public RepayDetailAdapter(Context context) {
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.lv_loan_repay_plan_item, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		final RepayModel model = getItem(position);

		viewHolder.tvAmount.setText(model.getAmount());

		CharSequence text = mContext.getString(R.string.loan_repay_plan_content, model.getNo(), model
				.getSum_no(), StringUtils.formatMoney(model.getMoney()), StringUtils.formatMoney(String.valueOf(Double.valueOf(model.getAmount())-Double.valueOf(model.getMoney()))), 0);

		viewHolder.tvContent.setText(text);

		return convertView;
	}


	static class ViewHolder {
		@BindView(R.id.tv_amount)
		TextView tvAmount;
		@BindView(R.id.tv_content)
		TextView tvContent;

		ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
	}
}
