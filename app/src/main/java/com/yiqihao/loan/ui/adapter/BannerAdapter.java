package com.yiqihao.loan.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yiqihao.loan.Constant;
import com.yiqihao.loan.R;
import com.yiqihao.loan.entity.BannerInfoModel;
import com.yiqihao.loan.ui.activity.WebViewActivity;
import com.yiqihao.loan.utils.DensityUtil;

import java.util.List;

/**
 * * Created by 冯浩 on 16/8/5.
 */
public class BannerAdapter extends PagerAdapter {

	private Context context;
	private List<BannerInfoModel> imageIdList;

	private int w;

	public BannerAdapter(Context context, List<BannerInfoModel> imageIdList) {
		this.context = context;
		this.imageIdList = imageIdList;
		w = DensityUtil.getScreenWidth(context);
	}

	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}


	@Override
	public Object instantiateItem(ViewGroup container, int position) {

		ImageView bannerIv = (ImageView) LayoutInflater.from(context).inflate(R.layout.vp_banner_layout,
				null);

		final BannerInfoModel model = imageIdList.get(position % imageIdList.size());

		Picasso.with(context)
				.load(model.getImg())
				.placeholder(R.drawable.ic_img_loading)
				.error(R.drawable.ic_img_error)
				.fit()
				.centerCrop()
				.into(bannerIv);

		// 为每一个page添加点击事件
		bannerIv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(context, WebViewActivity.class);
				intent.putExtra(Constant.WEBVIEW_URL,model.getUrl());
				context.startActivity(intent);			}
		});

		container.addView(bannerIv);
		return bannerIv;
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}
}