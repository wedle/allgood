package com.yiqihao.loan.net.loan;

/**
 * Created by 冯浩 on 2016/8/17.
 */
public class LoanFactory {

    private static final String TAG = "LoanFactory";

    protected static final Object o = new Object();
    public static LoanApi sLoanSingleton = null;


    public static LoanApi getLoanSingleton() {
        synchronized (o) {
            if (sLoanSingleton == null) {
                sLoanSingleton = new LoanRetrofit().getLoanApi();
            }
            return sLoanSingleton;
        }
    }

}
