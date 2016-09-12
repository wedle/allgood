package com.yiqihao.loan.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yiqihao.loan.R;
import com.yiqihao.loan.entity.LangZnModel;
import com.yiqihao.loan.entity.RepayModel;
import com.yiqihao.loan.utils.StringUtils;

import java.util.List;

/**
 * 提前还款adapter
 * Created by 冯浩 on 16/8/10.
 */
public class AdvancedRepayAdapter extends BaseAdapter {
	private Context mContext;
	private List<RepayModel> list;
	private LangZnModel langZn;

	private SelectedListener listener;

	public void setSelectedListener(SelectedListener listener) {
		this.listener = listener;
	}


	public AdvancedRepayAdapter(Context context) {
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

	public void setData(List<RepayModel> list, LangZnModel langZn) {
		this.list = list;
		this.langZn = langZn;
		notifyDataSetChanged();

	}

	public void setChecked(int position) {
		RepayModel repayModel = list.get(position);
		repayModel.setChecked(true);
		list.set(position, repayModel);
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
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.lv_advance_repay, parent, false);
		}

		final RepayModel model = getItem(position);


		ImageView ivChecked = (ImageView) convertView.findViewById(R.id.iv_checked);
		TextView tvDeadline = (TextView) convertView.findViewById(R.id.tv_deadline);
		TextView tvContent = (TextView) convertView.findViewById(R.id.tv_content);
		TextView tvAmount = (TextView) convertView.findViewById(R.id.tv_amount);
		TextView tvNo = (TextView) convertView.findViewById(R.id.tv_no);


		if (model.isChecked()) {
			ivChecked.setImageResource(R.drawable.checkbox_checked);
		} else {
			ivChecked.setImageResource(R.drawable.checkbox_unchecked);
		}

		String deadline;

		if (TextUtils.equals(model.getDeadline_type(), "d")) {
			deadline = model.getDays() + "天";
		} else {
			deadline = model.getDeadline() + "个月";
		}

		tvDeadline.setText(StringUtils.formatMoneyWan(model.getAllmount()) + " ･ " + deadline);

		tvContent.setText("借款编号:" + model.getCreditno());

		tvAmount.setText("未还本金:"+model.getWait_loanmoney()+"元");

		StringUtils.setTextColor(mContext, tvNo,"剩余" + model.getWait_no() + "期", R.color.red, 2, model.getWait_no().length());


		convertView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				for (int i = 0; i < list.size(); i++) {
					list.get(i).setChecked(false);
				}
				list.get(position).setChecked(true);
				notifyDataSetChanged();

				listener.onSelected(model);

			}
		});

		return convertView;
	}

	public interface SelectedListener {

		void onSelected(RepayModel repayModel);

	}
}
