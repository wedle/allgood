package com.yiqihao.loan.net.loan;


import com.yiqihao.loan.entity.AddBankInfoResultModel;
import com.yiqihao.loan.entity.BankBranchResultModel;
import com.yiqihao.loan.entity.BankCardResultModel;
import com.yiqihao.loan.entity.BannerResultModel;
import com.yiqihao.loan.entity.CarResultModel;
import com.yiqihao.loan.entity.CarYearResultModel;
import com.yiqihao.loan.entity.HttpResultModel;
import com.yiqihao.loan.entity.LoanAppraiseResultModel;
import com.yiqihao.loan.entity.LoanDetailResultModel;
import com.yiqihao.loan.entity.LoanListResultModel;
import com.yiqihao.loan.entity.LoanPostResultModel;
import com.yiqihao.loan.entity.RepayListResultModel;
import com.yiqihao.loan.entity.UserInfoResultModel;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by 冯浩 on 2016/8/17.
 */
public interface LoanApi {


	/**
	 * 注册
	 *
	 * @param mobile   手机号
	 * @param password 密码
	 * @param smscode  短信验证码
	 * @return
	 */
	@FormUrlEncoded
	@POST("user/reg/mobile")
	Observable<UserInfoResultModel> register(@Field("utype") int utype, @Field("mobile") String mobile, @Field
			("password") String password, @Field
			("smscode") String smscode);

	/**
	 * 发送短信验证码
	 *
	 * @param type 可以是：reg,auth,bankcard,getpwd,drawcash等
	 * @return
	 */
	@FormUrlEncoded
	@POST("send/smscode")
	Observable<HttpResultModel> sendSmsCode(@Field("type") String type, @Field("mobile") String mobile);

	/**
	 * 登录
	 *
	 * @return
	 */
	@FormUrlEncoded
	@POST("user/login")
	Observable<UserInfoResultModel> login(@Field("account") String account, @Field
			("password") String password);


	/**
	 * 获取会话密钥
	 *
	 * @return
	 */
	@FormUrlEncoded
	@POST("user/auth")
	Observable<UserInfoResultModel> refreshSessionKey(@Field("account") String account, @Field("password") String
			password);

	/**
	 * 找回密码
	 *
	 * @param method
	 * @param mobile
	 * @param type
	 * @param newpassword
	 * @param smscode
	 * @return
	 */
	@FormUrlEncoded
	@POST("user/getpwd")
	Observable<HttpResultModel> forgotPassword(@Field("method") String method, @Field("mobile") String mobile, @Field
			("type") String type, @Field("newpassword") String newpassword, @Field("smscode") String smscode);


	/**
	 * 车辆品牌系列级联
	 */
	@FormUrlEncoded
	@POST("get/carinfo")
	Observable<CarResultModel> getCarInfo(@FieldMap Map<String, String> params);

	/**
	 * 获取用户信息
	 *
	 * @return
	 */
	@GET("user/getinfo")
	Observable<UserInfoResultModel> getUserInfo();

	/**
	 * 登出
	 *
	 * @return
	 */
	@GET("user/logout")
	Observable<HttpResultModel> logout();

	/**
	 * 评估借款额度
	 * @param carbrand 品牌型号
	 * @param creditType 是否有车贷 0201:无 0202:有
	 * @param initDate 初次登记日期
	 * @param carbuymoney 新车购入价
	 * @param carcolor 车身颜色
	 * @param travelkilo 行驶公里
	 * @return
	 */
	@FormUrlEncoded
	@POST("loan/appraise")
	Observable<LoanAppraiseResultModel> loanAppraise(
			@Field("carbrand") String carbrand,
			@Field("creditType") String creditType,
			@Field("initDate") String initDate,
			@Field("carbuymoney") String carbuymoney,
			@Field("carcolor") String carcolor,
			@Field("travelkilo") String travelkilo,
			@Field("brand_id") String brand_id,
			@Field("series_id") String series_id,
			@Field("model_id") String model_id,
			@Field("year") String year
	);

	/**
	 * 贷款列表
	 *
	 * @param type 贷款列表类别，all-全部(默认),doing-审批中,fail-审批拒绝,success-审批成功,repay-还款中,ok-已结清
	 * @param p
	 * @return
	 */
	@FormUrlEncoded
	@POST("loan/mylist")
	Observable<LoanListResultModel> getLoanList(@Field("type") String type, @Field("p")
	int p);


	/**
	 * 申请贷款
	 * @param amount 贷款金额
	 * @param deadline 贷款期限 范围1-36
	 * @param repay_method 还款方式
	 * @return
	 */
	@FormUrlEncoded
	@POST("loan/post")
	Observable<LoanPostResultModel> loanPost(@Field("amount") String amount, @Field("deadline") String deadline, @Field("repay_method")
	String repay_method,@Field("bankid") String bankid);

	/**
	 * 贷款详情
	 *
	 * @param lid 贷款编号
	 * @return
	 */
	@FormUrlEncoded
	@POST("loan/detail")
	Observable<LoanDetailResultModel> getLoanDetail(@Field("lid") String lid);


	/**
	 * 补充车辆信息
	 * @param params
	 * @return
	 */
	@FormUrlEncoded
	@POST("loan/carinfo")
	Observable<LoanDetailResultModel> saveCarInfo(@FieldMap Map<String, String> params);


	/**
	 * 车的年份
	 * @param model_id
	 * @return
	 */
	@FormUrlEncoded
	@POST("get/caryear")
	Observable<CarYearResultModel> getCarYear(@Field("model_id") String model_id);


	/**
	 * 用户贷款还款计划
	 * @param lid 申请贷款lid，不传返回此用户所有的还款计划
	 * @return
	 */
	@FormUrlEncoded
	@POST("user/commission/mylist")
	Observable<RepayListResultModel> repayPlan(@Field("lid") String lid
			, @Field("p") int p);

	/**
	 * 还款计划总表
	 * @param p
	 * @return
	 */
	@FormUrlEncoded
	@POST("user/commission/myindex")
	Observable<RepayListResultModel> myRepayPlan( @Field("p") int p);

	/**
	 * 获取银行卡信息
	 *
	 * @return
	 */
	@GET("user/bank/info")
	Observable<BankCardResultModel> getBankCardList();

	/**
	 * 搜索支行名称
	 *
	 * @return
	 */
	@FormUrlEncoded
	@POST("user/bank/search")
	Observable<BankBranchResultModel> searchBranchName(@Field("city") String city, @Field("code") String code);

	/**
	 * 添加银行卡
	 *
	 * @return
	 */
	@FormUrlEncoded
	@POST("user/bank/add")
	Observable<AddBankInfoResultModel> addBankCard(@FieldMap Map<String, String> params);

	/**
	 * 编辑银行卡
	 *
	 * @return
	 */
	@FormUrlEncoded
	@POST("user/bank/edit")
	Observable<HttpResultModel> editBankCard(@FieldMap Map<String, String> params);

	/**
	 * 设置默认银行卡
	 * @param bankid 银行卡ID
	 * @return
	 */
	@FormUrlEncoded
	@POST("user/bank/setdef")
	Observable<HttpResultModel> setDefaultBank(@Field("bankid") String
													   bankid);

	/**
	 * 设置默认银行卡
	 * @param bankid 银行卡ID
	 * @return
	 */
	@FormUrlEncoded
	@POST("user/bank/del")
	Observable<HttpResultModel> deleteBank(@Field("bankid") String
												   bankid);

	/**
	 * 获取银行卡数量
	 *
	 * @return
	 */
	@POST("user/recharge/cardnum")
	Observable<HttpResultModel> getCardNum();

	/**
	 * 获取首页的banner数据
	 */
	@FormUrlEncoded
	@POST("get/mbanner")
	Observable<BannerResultModel> getHomeBanner(@Field("type") String renmobile);

	/**
	 * 身份认证
	 *
	 * @param method   认证方式（0：人工认证， 1：自动认证）
	 * @param realname 真实姓名
	 * @param idcard   身份证号
	 * @return
	 */
	@FormUrlEncoded
	@POST("user/pauth/realname")
	Observable<HttpResultModel> certificationIdCard(@Field("method") int method, @Field("realname") String realname,
													@Field("idcard") String idcard);


	/**
	 * 还款计划总表
	 * @param p
	 * @return
	 */
	@FormUrlEncoded
	@POST("user/commission/myadvance")
	Observable<RepayListResultModel> myAdvance( @Field("p") int p);

	/**
	 * 还款计划总表
	 * @return
	 */
	@FormUrlEncoded
	@POST("user/commission/advance")
	Observable<HttpResultModel> advance( @Field("lid") String lid);

	/**
	 *  检查版本
	 */
	@POST("mobile/version")
	Observable<HttpResultModel> checkVersion();

}
