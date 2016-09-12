package com.yiqihao.loan.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yiqihao.loan.R;
import com.yiqihao.loan.entity.LoanRecordModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 冯浩 on 16/8/20.
 */
public class LoanRecordListAdapter extends BaseAdapter {

	private Context mContext;
	private List<LoanRecordModel> list;
	private String loanName;

	public LoanRecordListAdapter(Context context) {
		this.mContext = context;
	}

	public void addList(List<LoanRecordModel> addList) {
		if (list == null)
			return;
		list.addAll(addList);
		notifyDataSetChanged();
	}


	public List<LoanRecordModel> getData() {
		return list;
	}

	public void setData(List<LoanRecordModel> list,String loanName) {

		this.list = list;
		this.loanName = loanName;
		notifyDataSetChanged();

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list == null ? 0 : list.size();
	}

	@Override
	public LoanRecordModel getItem(int position) {
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.lv_loan_record_item, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		final LoanRecordModel model = getItem(position);

		viewHolder.tvTime.setText(model.getTime());

		viewHolder.tvRole.setText(loanName+"送审给"+model.getRole());

		return convertView;
	}


	static class ViewHolder {
		@BindView(R.id.tv_time)
		TextView tvTime;
		@BindView(R.id.tv_role)
		TextView tvRole;

		ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
	}
}
