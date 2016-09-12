package com.yiqihao.loan.net.yiqihao;

/**
 * Created by 冯浩 on 2016/8/17.
 */
public class YiqihaoFactory {

    private static final String TAG = "YiqihaoFactory";

    protected static final Object o = new Object();
    public static YiqihaoApi sYiqihaoSingleton = null;


    public static YiqihaoApi getYiqihaoSingleton() {
        synchronized (o) {
            if (sYiqihaoSingleton == null) {
                sYiqihaoSingleton = new YiqihaoRetrofit().getYiqihaoApi();
            }
            return sYiqihaoSingleton;
        }
    }

}
