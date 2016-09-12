package com.yiqihao.loan.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import java.io.File;

/**
 * Created by 冯浩 on 16/7/22.
 */
public class FileUtils {


	public static String getClient(Context context){
		String versionName = getAppVersionName(context);
		StringBuffer client = new StringBuffer("lrenmobile/")
				.append(versionName)
				.append("-android/")
				.append(Build.VERSION.RELEASE);
		return client.toString();
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
	 * 递归删除文件和文件夹
	 *
	 * @param file
	 *            要删除的根目录
	 */
	public static  void deleteFile(File file) {
		if (file.exists() == false) {
			return;
		} else {
			if (file.isFile()) {
				file.delete();
				return;
			}
			if (file.isDirectory()) {
				File[] childFile = file.listFiles();
				if (childFile == null || childFile.length == 0) {
					file.delete();
					return;
				}
				for (File f : childFile) {
					deleteFile(f);
				}
				file.delete();
			}
		}
	}
}
