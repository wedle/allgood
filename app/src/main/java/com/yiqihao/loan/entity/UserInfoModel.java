package com.yiqihao.loan.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 冯浩 on 16/8/17.
 */
public class UserInfoModel implements Parcelable {

	/**
	 * 用户id
	 */
	private String uid;
	/**
	 * 一起好uid  为0代表没有成功贷款
	 */
	private String uuid;
	/**
	 * 用户分组：default-默认
	 */
	private String ugroup;
	/**
	 * 手机号
	 */
	private String umobile;
	/**
	 * 用户名
	 */
	private String uname;
	/**
	 * 邮箱
	 */
	private String uemail;
	/**
	 * 支付密码
	 */
	private String paypwd;
	/**
	 * 身份证号
	 */
	private String idcard;
	/**
	 * 真实姓名
	 */
	private String realname;
	/**
	 * 账户可用余额
	 */
	private String amount;
	/**
	 * 待还款本金
	 */
	private String repay_money;
	/**
	 * 冻结金额
	 */
	private String freeze;
	/**
	 * 累计赚取金额
	 */
	private String sum_commission;

	/**
	 * 待收金额
	 */
	private String wait_commission;
	/**
	 * 状态:1-未激活,2-已锁定,3-已注销,11-正常
	 */
	private String status;
	/**
	 * 所在地区
	 */
	private String province;
	/**
	 * 是否通过身份认证
	 */
	private String auth_idcard;
	/**
	 * 是否有银行卡 0 无 1有
	 */
	private String has_bank;
	/**
	 * 是否通过银行卡认证
	 */
	private String auth_bankcard;
	/**
	 * 上级UID
	 */
	private String invite_uid;
	/**
	 * 渠道
	 */
	private String channel;
	/**
	 *贡献奖励金额
	 */
	private String invite_money;
	/**
	 * 上次登录时间
	 */
	private String last_login_time;
	/**
	 * 上次登录IP
	 */
	private String last_login_ip;
	/**
	 * 登录次数
	 */
	private String login_count;
	/**
	 * 登录时间
	 */
	private String login_time;
	/**
	 * 登录ip
	 */
	private String login_ip;
	/**
	 * 错误次数
	 */
	private String error_count;
	/**
	 * 错误时间
	 */
	private String error_time;
	/**
	 * 创建时间
	 */
	private String addtime;
	/**
	 * 所在地区
	 */
	private String uptime;
	/**
	 * 更新时间
	 */
	private String version;
	/**
	 * 是否接收还款短信: 1-接收
	 */
	private String msg_repay_sms;
	/**
	 * 是否接收推送短信：1-接收
	 */
	private String msg_push_sms;
	/**
	 * 头像url
	 */
	private String avatar_url;
	/**
	 * 注册ip
	 */
	private String addip;
	/**
	 * 积分
	 */
	private String score;
	/**
	 * 信用等级
	 */
	private String credit;
	/**
	 * 新增客户
	 */
	private String success_loanuser;
	/**
	 * 成交客户
	 */
	private String ok_loanuser;
	/**
	 * 待激活客户
	 */
	private String new_loanuser;
	/**
	 * 新增贷款
	 */
	private String new_loanmoney;
	/**
	 * 新增贷款笔数
	 */
	private String new_loanmoney_num;
	/**
	 * 本月待收佣金
	 */
	private String wait_month_commission;
	/**
	 * 本月已收佣金金额
	 */
	private String success_month_commission;
	/**
	 * 本月已收佣金笔数
	 */
	private String success_month_commission_num;
	/**
	 * 待还款总金额
	 */
	private String repay_amount;
	/**
	 * 待还款笔数
	 */
	private String repay_num;
	/**
	 * 最近一笔待还款金额
	 */
	private String late_amount;
	/**
	 * 最近一笔还款时间
	 */
	private String late_time;


	public String getRepay_money() {
		return repay_money;
	}

	public void setRepay_money(String repay_money) {
		this.repay_money = repay_money;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getPaypwd() {
		return paypwd;
	}


	public void setPaypwd(String paypwd) {
		this.paypwd = paypwd;
	}

	public String getHas_bank() {
		return has_bank;
	}

	public void setHas_bank(String has_bank) {
		this.has_bank = has_bank;
	}

	public String getUmobile() {
		return umobile;
	}

	public void setUmobile(String umobile) {
		this.umobile = umobile;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getAddip() {
		return addip;
	}

	public void setAddip(String addip) {
		this.addip = addip;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getFreeze() {
		return freeze;
	}

	public void setFreeze(String freeze) {
		this.freeze = freeze;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getUgroup() {
		return ugroup;
	}

	public void setUgroup(String ugroup) {
		this.ugroup = ugroup;
	}

	public String getUemail() {
		return uemail;
	}

	public void setUemail(String uemail) {
		this.uemail = uemail;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getAuth_idcard() {
		return auth_idcard;
	}

	public void setAuth_idcard(String auth_idcard) {
		this.auth_idcard = auth_idcard;
	}

	public String getAuth_bankcard() {
		return auth_bankcard;
	}

	public void setAuth_bankcard(String auth_bankcard) {
		this.auth_bankcard = auth_bankcard;
	}

	public String getInvite_uid() {
		return invite_uid;
	}

	public void setInvite_uid(String invite_uid) {
		this.invite_uid = invite_uid;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getInvite_money() {
		return invite_money;
	}

	public void setInvite_money(String invite_money) {
		this.invite_money = invite_money;
	}

	public String getLast_login_time() {
		return last_login_time;
	}

	public void setLast_login_time(String last_login_time) {
		this.last_login_time = last_login_time;
	}

	public String getLast_login_ip() {
		return last_login_ip;
	}

	public void setLast_login_ip(String last_login_ip) {
		this.last_login_ip = last_login_ip;
	}

	public String getLogin_count() {
		return login_count;
	}

	public void setLogin_count(String login_count) {
		this.login_count = login_count;
	}

	public String getLogin_time() {
		return login_time;
	}

	public void setLogin_time(String login_time) {
		this.login_time = login_time;
	}

	public String getLogin_ip() {
		return login_ip;
	}

	public void setLogin_ip(String login_ip) {
		this.login_ip = login_ip;
	}

	public String getError_count() {
		return error_count;
	}

	public void setError_count(String error_count) {
		this.error_count = error_count;
	}

	public String getError_time() {
		return error_time;
	}

	public void setError_time(String error_time) {
		this.error_time = error_time;
	}

	public String getUptime() {
		return uptime;
	}

	public void setUptime(String uptime) {
		this.uptime = uptime;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMsg_repay_sms() {
		return msg_repay_sms;
	}

	public void setMsg_repay_sms(String msg_repay_sms) {
		this.msg_repay_sms = msg_repay_sms;
	}

	public String getMsg_push_sms() {
		return msg_push_sms;
	}

	public void setMsg_push_sms(String msg_push_sms) {
		this.msg_push_sms = msg_push_sms;
	}

	public String getAvatar_url() {
		return avatar_url;
	}

	public void setAvatar_url(String avatar_url) {
		this.avatar_url = avatar_url;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getWait_commission() {
		return wait_commission;
	}

	public void setWait_commission(String wait_commission) {
		this.wait_commission = wait_commission;
	}

	public String getSum_commission() {
		return sum_commission;
	}

	public void setSum_commission(String sum_commission) {
		this.sum_commission = sum_commission;
	}

	public String getSuccess_loanuser() {
		return success_loanuser;
	}

	public void setSuccess_loanuser(String success_loanuser) {
		this.success_loanuser = success_loanuser;
	}

	public String getOk_loanuser() {
		return ok_loanuser;
	}

	public void setOk_loanuser(String ok_loanuser) {
		this.ok_loanuser = ok_loanuser;
	}

	public String getNew_loanuser() {
		return new_loanuser;
	}

	public void setNew_loanuser(String new_loanuser) {
		this.new_loanuser = new_loanuser;
	}

	public String getNew_loanmoney() {
		return new_loanmoney;
	}

	public void setNew_loanmoney(String new_loanmoney) {
		this.new_loanmoney = new_loanmoney;
	}

	public String getNew_loanmoney_num() {
		return new_loanmoney_num;
	}

	public void setNew_loanmoney_num(String new_loanmoney_num) {
		this.new_loanmoney_num = new_loanmoney_num;
	}

	public String getWait_month_commission() {
		return wait_month_commission;
	}

	public void setWait_month_commission(String wait_month_commission) {
		this.wait_month_commission = wait_month_commission;
	}

	public String getSuccess_month_commission() {
		return success_month_commission;
	}

	public void setSuccess_month_commission(String success_month_commission) {
		this.success_month_commission = success_month_commission;
	}

	public String getSuccess_month_commission_num() {
		return success_month_commission_num;
	}

	public void setSuccess_month_commission_num(String success_month_commission_num) {
		this.success_month_commission_num = success_month_commission_num;
	}

	public String getRepay_amount() {
		return repay_amount;
	}

	public void setRepay_amount(String repay_amount) {
		this.repay_amount = repay_amount;
	}

	public String getRepay_num() {
		return repay_num;
	}

	public void setRepay_num(String repay_num) {
		this.repay_num = repay_num;
	}


	public String getLate_time() {
		return late_time;
	}

	public void setLate_time(String late_time) {
		this.late_time = late_time;
	}

	public String getLate_amount() {
		return late_amount;
	}

	public void setLate_amount(String late_amount) {
		this.late_amount = late_amount;
	}

	public UserInfoModel() {
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.uid);
		dest.writeString(this.uuid);
		dest.writeString(this.ugroup);
		dest.writeString(this.umobile);
		dest.writeString(this.uname);
		dest.writeString(this.uemail);
		dest.writeString(this.paypwd);
		dest.writeString(this.idcard);
		dest.writeString(this.realname);
		dest.writeString(this.amount);
		dest.writeString(this.repay_money);
		dest.writeString(this.freeze);
		dest.writeString(this.sum_commission);
		dest.writeString(this.wait_commission);
		dest.writeString(this.status);
		dest.writeString(this.province);
		dest.writeString(this.auth_idcard);
		dest.writeString(this.has_bank);
		dest.writeString(this.auth_bankcard);
		dest.writeString(this.invite_uid);
		dest.writeString(this.channel);
		dest.writeString(this.invite_money);
		dest.writeString(this.last_login_time);
		dest.writeString(this.last_login_ip);
		dest.writeString(this.login_count);
		dest.writeString(this.login_time);
		dest.writeString(this.login_ip);
		dest.writeString(this.error_count);
		dest.writeString(this.error_time);
		dest.writeString(this.addtime);
		dest.writeString(this.uptime);
		dest.writeString(this.version);
		dest.writeString(this.msg_repay_sms);
		dest.writeString(this.msg_push_sms);
		dest.writeString(this.avatar_url);
		dest.writeString(this.addip);
		dest.writeString(this.score);
		dest.writeString(this.credit);
		dest.writeString(this.success_loanuser);
		dest.writeString(this.ok_loanuser);
		dest.writeString(this.new_loanuser);
		dest.writeString(this.new_loanmoney);
		dest.writeString(this.new_loanmoney_num);
		dest.writeString(this.wait_month_commission);
		dest.writeString(this.success_month_commission);
		dest.writeString(this.success_month_commission_num);
		dest.writeString(this.repay_amount);
		dest.writeString(this.repay_num);
		dest.writeString(this.late_amount);
		dest.writeString(this.late_time);
	}

	protected UserInfoModel(Parcel in) {
		this.uid = in.readString();
		this.uuid = in.readString();
		this.ugroup = in.readString();
		this.umobile = in.readString();
		this.uname = in.readString();
		this.uemail = in.readString();
		this.paypwd = in.readString();
		this.idcard = in.readString();
		this.realname = in.readString();
		this.amount = in.readString();
		this.repay_money = in.readString();
		this.freeze = in.readString();
		this.sum_commission = in.readString();
		this.wait_commission = in.readString();
		this.status = in.readString();
		this.province = in.readString();
		this.auth_idcard = in.readString();
		this.has_bank = in.readString();
		this.auth_bankcard = in.readString();
		this.invite_uid = in.readString();
		this.channel = in.readString();
		this.invite_money = in.readString();
		this.last_login_time = in.readString();
		this.last_login_ip = in.readString();
		this.login_count = in.readString();
		this.login_time = in.readString();
		this.login_ip = in.readString();
		this.error_count = in.readString();
		this.error_time = in.readString();
		this.addtime = in.readString();
		this.uptime = in.readString();
		this.version = in.readString();
		this.msg_repay_sms = in.readString();
		this.msg_push_sms = in.readString();
		this.avatar_url = in.readString();
		this.addip = in.readString();
		this.score = in.readString();
		this.credit = in.readString();
		this.success_loanuser = in.readString();
		this.ok_loanuser = in.readString();
		this.new_loanuser = in.readString();
		this.new_loanmoney = in.readString();
		this.new_loanmoney_num = in.readString();
		this.wait_month_commission = in.readString();
		this.success_month_commission = in.readString();
		this.success_month_commission_num = in.readString();
		this.repay_amount = in.readString();
		this.repay_num = in.readString();
		this.late_amount = in.readString();
		this.late_time = in.readString();
	}

	public static final Creator<UserInfoModel> CREATOR = new Creator<UserInfoModel>() {
		@Override
		public UserInfoModel createFromParcel(Parcel source) {
			return new UserInfoModel(source);
		}

		@Override
		public UserInfoModel[] newArray(int size) {
			return new UserInfoModel[size];
		}
	};
}
