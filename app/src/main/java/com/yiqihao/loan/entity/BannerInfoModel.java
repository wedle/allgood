package com.yiqihao.loan.entity;

public class BannerInfoModel {

	private static final String TAG = "BannerInfoModel";

	/**
	 * 图片url
	 **/
	private String img;
	/**
	 * 图片详情
	 **/
	private String url;
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
