package com.yiqihao.loan.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yiqihao.loan.R;
import com.yiqihao.loan.entity.LangZnModel;
import com.yiqihao.loan.entity.LoanInfoModel;
import com.yiqihao.loan.ui.activity.LoanDetailActivity;
import com.yiqihao.loan.utils.DateUtils;
import com.yiqihao.loan.utils.LangZnUtils;
import com.yiqihao.loan.utils.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 贷款列表adapter
 * Created by 冯浩 on 16/8/10.
 */
public class LoanListAdapter extends BaseAdapter {
	private Context mContext;
	private List<LoanInfoModel> list;
	private LangZnModel langZn;

	public LoanListAdapter(Context context) {
		this.mContext = context;
	}

	public void addList(List<LoanInfoModel> addList) {
		if (list == null)
			return;
		list.addAll(addList);
		notifyDataSetChanged();
	}


	public List<LoanInfoModel> getData() {
		return list;
	}

	public void setData(List<LoanInfoModel> list, LangZnModel langZn) {

		this.list = list;
		this.langZn = langZn;
		notifyDataSetChanged();

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list == null ? 0 : list.size();
	}

	@Override
	public LoanInfoModel getItem(int position) {
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.lv_loan_list_item, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		final LoanInfoModel model = getItem(position);

		viewHolder.tvTime.setText(DateUtils.formatDate(model.getAddtime()) + "借");

		int status = Integer.valueOf(model.getStatus());

		viewHolder.tvStatus.setText(LangZnUtils.statusZnToString(langZn, status));

		int type = Integer.valueOf(model.getType());

		if (TextUtils.equals(model.getDeadline_type(), "d")) {
			viewHolder.tvContent.setText(StringUtils.formatMoneyWan(model.getAmount()) + " ･ " + LangZnUtils
					.typeZnToString(langZn, type) + " ･ " + model.getDays() + "天");
		} else {
			viewHolder.tvContent.setText(StringUtils.formatMoneyWan(model.getAmount()) + " ･ " + LangZnUtils
					.typeZnToString(langZn, type) + " ･ " + model.getDeadline() + "个月");

		}


		if (status == 2) {//待补充资料
			viewHolder.tvStatus.setTextColor(Color.parseColor("#2DBCFF"));
		} else if (status == 3) {//审批中
			viewHolder.tvStatus.setTextColor(Color.parseColor("#0053DC"));
		} else if (status == 4) {//审批通过
			viewHolder.tvStatus.setTextColor(Color.parseColor("#00C277"));
		} else if (status == 5) {//还款中
			viewHolder.tvStatus.setTextColor(Color.parseColor("#999999"));
		} else if (status == 9) {//已结清
			viewHolder.tvStatus.setTextColor(Color.parseColor("#999999"));
		} else if (status == 10) {//审批拒绝
			viewHolder.tvStatus.setTextColor(Color.parseColor("#FF5D44"));
		} else {
			viewHolder.tvStatus.setTextColor(Color.parseColor("#999999"));
		}

		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, LoanDetailActivity.class);
				intent.putExtra("lid", model.getLid());
				mContext.startActivity(intent);
			}
		});

		return convertView;
	}


	static class ViewHolder {
		@BindView(R.id.tv_content)
		TextView tvContent;
		@BindView(R.id.tv_time)
		TextView tvTime;
		@BindView(R.id.iv_arrow)
		ImageView ivArrow;
		@BindView(R.id.tv_status)
		TextView tvStatus;

		ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
	}
}
