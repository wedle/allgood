package com.yiqihao.loan.net.yiqihao.gsonConverter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.StringReader;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by 冯浩 on 2016/4/25.
 */
final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private static final String TAG = "GsonResponseBodyConverter";

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {

        String s = value.string();

//        HttpResultModel result = gson.fromJson(s, HttpResultModel.class);
//
//        if (result != null) {
//            int resultCode = result.getResultCode();
//            if (resultCode == Constant.ERROR) {
//                String errorMsg = result.getErrmsg();
//                if ("用户未登录或会话已过期".equals(errorMsg)) {
//
//                    String account = PreferencesUtils.getString(MyApplication.getInstance().getApplicationContext(),
//                            Constant.AppConfigInfo.PHONE);
//
//                    if (!TextUtils.isEmpty(account)) {
//                        //重新获取sessionKey
//                        MyApplication.refreshSessionKey();
//                    }
//
//                } else if (errorMsg.contains("账户状态异常")) {
//                    //TODO 账号异常,退出登录
//                }
//
//            }
//        }
        JsonReader jsonReader = gson.newJsonReader(new StringReader(s));
        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }
}

