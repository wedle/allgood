package com.yiqihao.loan.mvp.presenters;

import com.yiqihao.loan.Constant;
import com.yiqihao.loan.entity.BrandsModel;
import com.yiqihao.loan.entity.CarResultModel;
import com.yiqihao.loan.mvp.views.CarModelView;
import com.yiqihao.loan.utils.RefreshSessionAndRetryUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 冯浩 on 2016/8/17.
 */
public class CarModelPresenter extends MvpRxPresenter<CarModelView> {

    public void initData(String id, String modelid) {

        Map<String,String> map=new HashMap<>();
        map.put("type","3");
        map.put("brand_id",id);
        map.put("series_id",modelid);
        Observable<CarResultModel> api = mApi.getCarInfo(map);
        Subscription s = api
                .onErrorResumeNext(RefreshSessionAndRetryUtils.refreshSessionAndRetry(api))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CarResultModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().showError(false, e.toString());
                    }

                    @Override
                    public void onNext(CarResultModel response) {

                            if (response.getResultCode()== Constant.SUCCESS) {

                                List<BrandsModel> carModels = response.getData();

                                if (carModels == null) {
                                    getView().showError(false, "carModels is null");
                                    return;
                                }

                                getView().showContent(carModels);

                            }else {
                                getView().showError(true, ""+response.getErrmsg());
                            }

                    }
                });
        addSubscription(s);

    }
}
