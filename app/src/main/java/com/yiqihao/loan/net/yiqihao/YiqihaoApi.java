package com.yiqihao.loan.net.yiqihao;

import com.yiqihao.loan.entity.HttpResultModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by 冯浩 on 16/8/18.
 */
public interface YiqihaoApi {

	/**
	 * 登录
	 *
	 * @param luid   就是当前登录的uid
	 * @param ren_key 就是当前登录的session_key
	 * @param uid  默认10014
	 * @return
	 */
	@FormUrlEncoded
	@POST("/index.php/user/login/loginren")
	Observable<HttpResultModel> loginRen(@Field("luid") String luid, @Field("ren_key") String ren_key, @Field("uid") String uid);


//	String uid= PreferencesUtils.getString(this, Constant.AppConfigInfo.UID);
//	String sessionKey= PreferencesUtils.getString(this, Constant.AppConfigInfo.SESSION_KEY);
//
//	Subscription subscribe = YiqihaoFactory.getYiqihaoSingleton()
//			.loginRen(uid, sessionKey, "10014")
//			.subscribeOn(Schedulers.io())
//			.observeOn(AndroidSchedulers.mainThread())
//			.subscribe(new Subscriber<HttpResultModel>() {
//				@Override
//				public void onCompleted() {
//
//				}
//
//				@Override
//				public void onError(Throwable e) {
//
//				}
//
//				@Override
//				public void onNext(HttpResultModel httpResultModel) {
//
//				}
//			});
//	addSubscription(subscribe);



}
