package com.yiqihao.loan.utils;


import com.yiqihao.loan.entity.LangZnModel;

import java.util.Map;

/**
 * Created by 冯浩 on 16/8/15.
 */
public class LangZnUtils {

	public static String typeZnToString(LangZnModel langZnModel, int type) {
		Map<Integer, String> type_zn = langZnModel.getType_zn();
		return type_zn.get(type);
	}

	public static String statusZnToString(LangZnModel langZnModel,int status) {
		Map<Integer, String> status_cn = langZnModel.getStatus_zn();
		return status_cn.get(status);
	}

	public static String luidZnToString(LangZnModel langZnModel,int luid) {
		Map<Integer, String> luid_zn = langZnModel.getLuid_zn();
		return luid_zn.get(luid);
	}


}
