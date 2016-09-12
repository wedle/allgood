package com.yiqihao.loan.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.yiqihao.loan.R;
import com.yiqihao.loan.entity.BankCardInfoModel;
import com.yiqihao.loan.utils.UIUtils;

import java.util.List;

/**
 * 银行卡管理列表adapter
 * Created by 冯浩 on 16/8/10.
 */
public class BankCardAdapter extends BaseSwipeAdapter {

	public static final int DEFAULT=0;
	public static final int DELETE=1;

	private List<BankCardInfoModel> mList;
	private Context mContext;

	private OnSwipeItemClickListener onSwipeItemClickListener;


	public void setOnSwipeItemClickListener(OnSwipeItemClickListener onSwipeItemClickListener) {
		this.onSwipeItemClickListener = onSwipeItemClickListener;
	}

	public BankCardAdapter(Context context) {
		this.mContext = context;
	}

	public void setData(List<BankCardInfoModel> list) {
		this.mList = list;
		notifyDataSetChanged();
	}

	public void addData(List<BankCardInfoModel> addList) {
		if (addList == null) {
			return;
		}
		mList.addAll(addList);
		notifyDataSetChanged();
	}

	public List<BankCardInfoModel> getData() {
		return mList;
	}

	@Override
	public int getSwipeLayoutResourceId(int position) {
		return R.id.swipe;
	}

	@Override
	public View generateView(int position, ViewGroup parent) {
		View view = LayoutInflater.from(mContext).inflate(R.layout.lv_bank_card, null);

		return view;
	}

	@Override
	public void fillValues(int position, final View convertView) {

		ImageView ivBankLogo = (ImageView) convertView.findViewById(R.id.iv_bank_logo);
		TextView tvBankName = (TextView) convertView.findViewById(R.id.tv_bank_name);
		TextView tvDef = (TextView) convertView.findViewById(R.id.tv_def);
		TextView tvBankCardNo = (TextView) convertView.findViewById(R.id.tv_bank_card_no);
		TextView tvSetDefault = (TextView) convertView.findViewById(R.id.tv_set_default);
		ImageView ivDelete = (ImageView) convertView.findViewById(R.id.iv_delete);
		SwipeLayout swipeLayout = (SwipeLayout) convertView.findViewById(R.id.swipe);
		swipeLayout.setClickToClose(true);

		final BankCardInfoModel model = mList.get(position);

		tvBankCardNo.setText(model.getCard());

		tvBankName.setText(model.getBank());

		tvSetDefault.setText("设为默认");

		if (TextUtils.equals(model.getIsdef(), "1")) {
			tvDef.setVisibility(View.VISIBLE);
//			tvSetDefault.setVisibility(View.GONE);
		} else {

//			tvSetDefault.setVisibility(View.VISIBLE);
			tvDef.setVisibility(View.GONE);
		}

		ivBankLogo.setBackgroundResource(UIUtils.bankLogo(mList.get(position).getCode()));


		tvSetDefault.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					((SwipeLayout) convertView).close();
					onSwipeItemClickListener.callBack(DEFAULT, model);
				}
			});

		ivDelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((SwipeLayout) convertView).close();
				onSwipeItemClickListener.callBack(DELETE, model);
			}
		});

	}

	@Override
	public int getCount() {
		return mList == null ? 0 : mList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}


	public interface OnSwipeItemClickListener {
		void callBack(int id, BankCardInfoModel model);
	}
}
