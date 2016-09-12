package com.yiqihao.loan.net.yiqihao;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yiqihao.loan.Constant;
import com.yiqihao.loan.MyApplication;
import com.yiqihao.loan.net.loan.rxJavaCallAdapter.RxJavaCallAdapterFactory;
import com.yiqihao.loan.net.yiqihao.gsonConverter.GsonConverterFactory;
import com.yiqihao.loan.utils.FileUtils;
import com.yiqihao.loan.utils.PreferencesUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by 冯浩 on 2016/6/28.
 */
public class YiqihaoRetrofit {

	private static final String TAG = "YiqihaoRetrofit";

	public static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();

	private static final int CONNECT_TIMEOUT = 15;
	private static final int WRITE_TIMEOUT = 60;
	private static final int READ_TIMEOUT = 60;
	private static final String FORMAT = "json";

	YiqihaoApi yiqihaoApi;

	public YiqihaoRetrofit() {

		Interceptor mInterceptor = new Interceptor() {
			@Override
			public Response intercept(Chain chain) throws IOException {

				Request originalRequest = chain.request();

				String sessionKey = PreferencesUtils.getString(MyApplication.getInstance().getApplicationContext(),
						Constant
						.AppConfigInfo.SESSION_KEY_YIQIHAO);


				if (!TextUtils.isEmpty(sessionKey)) {

					//登录和自动登录不需要带sessionkey
					if (TextUtils.equals(Constant.BASEURL+"/index.php/user/login/loginren",originalRequest.url().toString())){
						HttpUrl originalHttpUrl = originalRequest.url();
						HttpUrl newHttpUrl = originalHttpUrl.newBuilder()
								.setQueryParameter("format", FORMAT)
								.setQueryParameter("client", FileUtils.getClient(MyApplication.getInstance()))
								.build();

						Request newRequest = originalRequest.newBuilder()
								.url(newHttpUrl)
								.build();

						Response newResponse = chain.proceed(newRequest);

						return newResponse;
					}

					HttpUrl originalHttpUrl = originalRequest.url();
					HttpUrl newHttpUrl = originalHttpUrl.newBuilder()
							.setQueryParameter("session_key", sessionKey)
							.setQueryParameter("format", FORMAT)
							.setQueryParameter("client", FileUtils.getClient(MyApplication.getInstance()))
							.build();

					Request newRequest = originalRequest.newBuilder()
							.url(newHttpUrl)
							.build();

					Response newResponse = chain.proceed(newRequest);

					return newResponse;
				} else {
					HttpUrl originalHttpUrl = originalRequest.url();
					HttpUrl newHttpUrl = originalHttpUrl.newBuilder()
							.setQueryParameter("format", FORMAT)
							.setQueryParameter("client", FileUtils.getClient(MyApplication.getInstance()))
							.build();

					Request newRequest = originalRequest.newBuilder()
							.url(newHttpUrl)
							.build();

					Response newResponse = chain.proceed(newRequest);

					return newResponse;
				}
			}
		};


		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		OkHttpClient client = new OkHttpClient.Builder()
				.addInterceptor(interceptor)
				.addInterceptor(mInterceptor)
				.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
				.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
				.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
				.build();


		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(Constant.BASEURL_YIQIHAO)
				.client(client)
				.addConverterFactory(GsonConverterFactory.create(gson))
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.build();

		yiqihaoApi = retrofit.create(YiqihaoApi.class);

	}

	public YiqihaoApi getYiqihaoApi() {
		return yiqihaoApi;
	}

}
