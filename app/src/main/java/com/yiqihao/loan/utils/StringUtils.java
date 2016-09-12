package com.yiqihao.loan.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.yiqihao.loan.entity.CityInfoModel;
import com.yiqihao.loan.entity.ProvinceInfoModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by 冯浩 on 16/8/10.
 */
public class StringUtils {

	/**
	 * 格式化钱，
	 *
	 * @param num
	 * @return
	 */
	public static String formatMoney(String num) {
//		float i = Float.parseFloat(num);
//		if (i >= 10000) {
//			float f = (float) (i / 10000.0);
//			String str = String.valueOf(f);
//			String[] s = str.split("\\.");
//			boolean b = true;
//			char[] c = s[1].toCharArray();
//			for (int j = 0; j < c.length; j++) {
//				if (c[j] != '0') {
//					b = false;
//					break;
//				}
//			}
//
//			if (b) {
//				return s[0] + "万元";
//			} else {
//				return f + "万元";
//			}
//		}
		return new BigDecimal(num).setScale(2, BigDecimal
				.ROUND_HALF_UP) + "元";
	}


	public static String formatMoneyWan(String num) {
		float i = Float.parseFloat(num);
		if (i >= 10000) {
			float f = (float) (i / 10000.0);
			String str = String.valueOf(f);
			String[] s = str.split("\\.");
			boolean b = true;
			char[] c = s[1].toCharArray();
			for (int j = 0; j < c.length; j++) {
				if (c[j] != '0') {
					b = false;
					break;
				}
			}

			if (b) {
				return s[0] + "万元";
			} else {
				return f + "万元";
			}
		}
		return new BigDecimal(num).setScale(2, BigDecimal
				.ROUND_HALF_UP) + "元";
	}

	/**
	 * 格式化钱
	 *
	 * @param num
	 * @return
	 */
	public static String formatMoney1(String num) {
		float i = Float.parseFloat(num);
		if (i >= 10000) {
			float f = (float) (i / 10000.0);
			String str = String.valueOf(f);
			String[] s = str.split("\\.");
			boolean b = true;
			char[] c = s[1].toCharArray();
			for (int j = 0; j < c.length; j++) {
				if (c[j] != '0') {
					b = false;
					break;
				}
			}

			if (b) {
				return s[0] + "";
			} else {
				return f + "";
			}
		}
		return new BigDecimal(num).setScale(2, BigDecimal
				.ROUND_HALF_UP) + "";
	}

	/**
	 * 获取版本号
	 *
	 * @param context
	 * @return
	 */
	public static String getAppVersionName(Context context) {
		String versionName = "";
		try {
			// ---get the package info---
			PackageManager pm = context.getPackageManager();
			PackageInfo packInfo = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = packInfo.versionName;
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;
	}

	/**
	 * 判断服务是否在运行
	 *
	 * @param context
	 * @param serviceClass
	 * @return
	 */
	public static boolean isServiceRunning(Context context, Class<?> serviceClass) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> list = am.getRunningServices(Integer.MAX_VALUE);
		if (list.size() == 0) {
			return false;
		}
		for (ActivityManager.RunningServiceInfo service : list) {
			if (serviceClass.getSimpleName().equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}


	/**
	 * 得到省份列表
	 *
	 * @param context
	 * @return
	 */
	public static List<ProvinceInfoModel> getProvinceList(Context context) {
		try {
			String provinceStr = convertStreamToString(context.getAssets()
					.open("province2.txt"));
			JSONObject areaJson = new JSONObject(provinceStr);
			List<ProvinceInfoModel> provinceList = new ProvinceInfoModel().getProvinces(areaJson
					.optJSONArray("data"));
			return provinceList;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据省ID得到城市列表
	 *
	 * @param context
	 * @param provinceId
	 * @return
	 */
	public static List<CityInfoModel> getCityListByProvinceId(Context context, String provinceId) {
		try {
			String provinceStr = convertStreamToString(context.getAssets()
					.open("province2.txt"));
			JSONObject areaJson = new JSONObject(provinceStr);
			List<ProvinceInfoModel> provinceList = new ProvinceInfoModel().getProvinces(areaJson
					.optJSONArray("data"));
			for (int i = 0; i < provinceList.size(); i++) {
				if (provinceList.get(i).getId().equals(provinceId)) {
					return provinceList.get(i).getCityList();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据省id得到省的名称
	 *
	 * @param context
	 * @param id
	 * @return
	 */
	public static String getProvinceNameById(Context context, String id) {
		try {
			String provinceStr = convertStreamToString(context.getAssets()
					.open("province2.txt"));
			JSONObject areaJson = new JSONObject(provinceStr);
			List<ProvinceInfoModel> provinceList = new ProvinceInfoModel().getProvinces(areaJson
					.optJSONArray("data"));
			for (int i = 0; i < provinceList.size(); i++) {
				if (provinceList.get(i).getId().equals(id)) {
					return provinceList.get(i).getName();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 根据城市id得到城市名称
	 *
	 * @param context
	 * @param provinceId
	 * @param cityId
	 * @return
	 */
	public static String getCityNameById(Context context, String provinceId,
										 String cityId) {
		try {
			String provinceStr = convertStreamToString(context.getAssets()
					.open("province2.txt"));
			JSONObject areaJson = new JSONObject(provinceStr);
			List<ProvinceInfoModel> provinceList = new ProvinceInfoModel().getProvinces(areaJson
					.optJSONArray("data"));
			for (int i = 0; i < provinceList.size(); i++) {
				if (provinceList.get(i).getId().equals(provinceId)) {
					List<CityInfoModel> cityList = provinceList.get(i).getCityList();
					for (int j = 0; j < cityList.size(); j++) {
						if (cityList.get(j).getId().equals(cityId)) {
							return cityList.get(j).getName();
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String convertStreamToString(InputStream is)
			throws IOException {
		/**
		 * 为了将InputStream转换成String我们使用函数BufferedReader.readLine().
		 * 我们迭代调用BufferedReader直到其返回null, null意味着没有其他的数据要读取了.
		 * 每一行将会追加到StringBuilder的末尾, StringBuilder将作为String返回。
		 */
		if (is != null) {
			StringBuilder sb = new StringBuilder();
			String line;
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "UTF-8"));
				while ((line = reader.readLine()) != null) {
					sb.append(line).append("\n");
				}
			} finally {
				is.close();
			}
			return sb.toString();
		} else {
			return "";
		}
	}

	/**
	 * 设置textview中的文字局部颜色
	 *
	 * @param context
	 * @param textView
	 * @param content   要设置的文本
	 * @param textColor 要设置的颜色
	 * @param from      从第几个字符开始（0开始计数，中文1个字为一字符，英文一个字母为一字符，空格也计算）
	 * @param length    要设置的字符长度
	 */
	public static void setTextColor(Context context, TextView textView, String content, int textColor, int from, int
			length) {
		SpannableStringBuilder builder = new SpannableStringBuilder(content);
		ForegroundColorSpan span = new ForegroundColorSpan(context.getResources().getColor(textColor));
		if (from < content.length() && from + length <= content.length()) {
			builder.setSpan(span, from, from + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			textView.setText(builder);
		} else {
			textView.setText(content);
		}
	}

}
