package com.yiqihao.loan;

/**
 * Created by 冯浩 on 16/8/17.
 */
public class Constant {

	//杨宇鹏本地
	public static final String BASEURL = "http://ren.demo.yiqihao.com/";
//	public static final String BASEURL = "http://192.168.10.41:8080";

	public static final String BASEURL_YIQIHAO ="http://120.24.56.254:8080";

	/**
	 * BroadcastReceiver
	 */
	public static final String ACTION_LOGIN_SUCCESS = "action_login_success";// 登录成功
	public static final String ACTION_ADD_LOAN_SUCCESS = "action_add_loan_success";// 添加借款成功
	public static final String ACTION_CAR_INFO = "action_car_info";// 汽车添加嘻嘻嘻
	public static final String ACTION_ADD_BANK = "action_add_bank";// 添加银行卡
	public static final String ACTION_BIND_BANK_SUCCESS="action_bind_bank_success";//添加银行卡成功
	public static final String ACTION_CERTIFICATION_SUCCESS = "action_certification_success";//实名认证成功
	public static final String ACTION_REFRESH_TAB_HOME="action_refresh_tab_home";//刷新主页
	public static final String ACTION_REFRESH_TAB_LOAN="action_refresh_tab_loan";//刷新借款进度
	public static final String ACTION_REFRESH_TAB_PERSONAL="action_refresh_tab_personal";//刷新个人中心
	public static final String ACTION_ADVANCE_REPAY_SUCCESS="action_advance_repay_success";//提前还款成功



	public static final int SUCCESS = 1;
	public static final int ERROR = 0;

	public static final String WEBVIEW_URL = "webview_url";

	public static final String PRE_ADD_BANK ="pre_add_bank";//选择银行卡前置流程


	/**
	 * 应用配置信息
	 */
	public static final class AppConfigInfo {

		/**
		 * 是否第一次进入应用
		 */
		public static final String ISFIRST = "isfirst";

		public static final String SESSION_KEY = "session_key";

		public static final String SESSION_KEY_YIQIHAO = "session_key_yiqihao";

		/**
		 * 是否登录
		 */
		public static final String ISLOGIN = "isLogin";

		/**
		 * 手机号
		 */
		public static final String PHONE = "phone";

		/**
		 * 密码
		 */
		public static final String PASSWORD = "password";
		/**
		 * 用户uid
		 */
		public static final String UID="uid";
		/**
		 * 一起好用户uid  0标示没有成功借款
 		 */
		public static final String UUID="uuid";

		/**
		 * 支付密码
		 **/
		public static final String PAYPWD = "PAYPWD";

		/**
		 * 是否身份验证不为0：身份已验证
		 **/
		public static final String AUTHIDCARD = "authIdcard";

		/**
		 * 银行卡数量
		 **/
		public static final String BANK_CARD_NUM = "bank_card_num";


	}

}
