package com.yiqihao.loan.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yiqihao.loan.R;
import com.yiqihao.loan.entity.LangZnModel;
import com.yiqihao.loan.entity.RepayModel;
import com.yiqihao.loan.ui.activity.RepayDetail1Activity;
import com.yiqihao.loan.utils.DateUtils;
import com.yiqihao.loan.utils.LangZnUtils;
import com.yiqihao.loan.utils.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 还款计划adapter
 * Created by 冯浩 on 16/8/10.
 */
public class RepayPlanAdapter extends BaseAdapter {
	private Context mContext;
	private List<RepayModel> list;
	private LangZnModel langZn;

	public RepayPlanAdapter(Context context) {
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.lv_repay_plan_item, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		final RepayModel model = getItem(position);

		viewHolder.tvAmount.setText("本期:"+StringUtils.formatMoney(model.getAmount()));

		String deadline;

		if (TextUtils.equals(model.getDeadline_type(), "d")) {
			deadline = model.getDays() + "天";
		} else {
			deadline = model.getDeadline() + "个月";
		}

		CharSequence text = mContext.getString(R.string.repay_plan_content, model.getNo(), model
				.getSum_no(), StringUtils.formatMoneyWan(model.getAllmount()), deadline);

		viewHolder.tvContent.setText(text);

		String lastDay = String.valueOf(DateUtils.dateInterval(System.currentTimeMillis(), Long.parseLong(model
				.getTime() + "000")));

		lastDay = "剩余" + lastDay + "天";

		viewHolder.tvDeadline.setText(lastDay);

		viewHolder.tvNo.setText("借款编号: "+model.getCreditno());

		convertView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Intent intent=new Intent(mContext, RepayDetail1Activity.class);
				intent.putExtra("lid",model.getLid());
				if (TextUtils.equals(model.getDeadline_type(),"d")){
					intent.putExtra("deadline",model.getDays()+"天");
				}else {
					intent.putExtra("deadline",model.getDeadline()+"个月");
				}
				intent.putExtra("allmount",model.getAllmount());
				intent.putExtra("creditno",model.getCreditno());
				intent.putExtra("creditType",LangZnUtils.typeZnToString(langZn,Integer.valueOf(model.getType())));
				mContext.startActivity(intent);

			}
		});

		return convertView;
	}


	static class ViewHolder {
		@BindView(R.id.tv_amount)
		TextView tvAmount;
		@BindView(R.id.tv_content)
		TextView tvContent;
		@BindView(R.id.tv_no)
		TextView tvNo;
		@BindView(R.id.tv_deadline)
		TextView tvDeadline;

		ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
	}

}
