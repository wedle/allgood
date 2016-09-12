package com.yiqihao.loan.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.yiqihao.loan.Constant;
import com.yiqihao.loan.R;
import com.yiqihao.loan.entity.BannerInfoModel;
import com.yiqihao.loan.mvp.presenters.HomePresenter;
import com.yiqihao.loan.mvp.views.HomeView;
import com.yiqihao.loan.ui.activity.LoanApplyActivity;
import com.yiqihao.loan.ui.activity.RegisterActivity;
import com.yiqihao.loan.ui.adapter.BannerAdapter;
import com.yiqihao.loan.utils.DensityUtil;
import com.yiqihao.loan.utils.PreferencesUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 首页
 * Created by 冯浩 on 16/8/17.
 */
public class HomeFragment extends MvpFragment<HomeView, HomePresenter> implements HomeView {

    @BindView(R.id.btn_loan)
    Button btnLoan;
    @BindView(R.id.vp_poster)
    ViewPager vpPoster;
    @BindView(R.id.indicator)
    LinearLayout indicator;

    private View view;
    private MyBroadcastReceive receiver;
    private boolean isLogin;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        presenter.getBannerInfo();
        registerBroadReceive();
    }

    @Override
    public HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(receiver);
    }

    @OnClick(R.id.btn_loan)
    public void onClick() {
        isLogin = PreferencesUtils.getBoolean(getContext(), Constant.AppConfigInfo.ISLOGIN);
        if (isLogin)
            startActivity(new Intent(getContext(), LoanApplyActivity.class));
        else
            startActivity(new Intent(getContext(), RegisterActivity.class));
    }


    private void initView() {
        isLogin = PreferencesUtils.getBoolean(getContext(), Constant.AppConfigInfo.ISLOGIN);
        if (isLogin) {
            changeContent(true);
        } else {
            changeContent(false);
        }
        int w = DensityUtil.getScreenWidth(getContext());

        vpPoster.setLayoutParams(new FrameLayout.LayoutParams(w, w * 28 / 75));
    }

    /**
     * 上一个被选中的小圆点的索引，默认值为0
     */
    private int preDotPosition = 0;

    @Override
    public void getBannerInfoSuccess(final List<BannerInfoModel> list) {

        if (list.size() == 0)
            return;

        View dot;

        FrameLayout.LayoutParams params;

        for (int i = 0; i < list.size(); i++) {
            // 每循环一次添加一个点到线行布局中
            dot = new View(getContext());
            dot.setBackgroundResource(R.drawable.dot_bg_selector);
            params = new FrameLayout.LayoutParams(20, 20);
            params.leftMargin = 10;
            dot.setEnabled(false);
            dot.setLayoutParams(params);
            indicator.addView(dot); // 向线性布局中添加"点"
        }

        vpPoster.setAdapter(new BannerAdapter(getContext(), list));

        vpPoster.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 取余后的索引，得到新的page的索引
                int newPositon = position % list.size();
                // 把上一个点设置为被选中
                indicator.getChildAt(preDotPosition).setEnabled(false);
                // 根据索引设置那个点被选中
                indicator.getChildAt(newPositon).setEnabled(true);
                // 新索引赋值给上一个索引的位置
                preDotPosition = newPositon;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        indicator.getChildAt(0).setEnabled(true);
        vpPoster.setCurrentItem(0);

        if (list.size() != 1) {
            startBannerScrollThread();
        }
    }

    /**
     * 开启Banner滚动
     */
    private void startBannerScrollThread() {

        Subscription subscribe = Observable.interval(0, 2000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        int newindex = vpPoster.getCurrentItem() + 1;
                        vpPoster.setCurrentItem(newindex);
                    }
                });
        addSubscription(subscribe);
    }

    @Override
    public void getBannerInfoError(boolean isShow, String msg) {

    }

    private class MyBroadcastReceive extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constant.ACTION_LOGIN_SUCCESS)) {//登录成功通知
                changeContent(true);
            }
        }
    }

    /**
     * 改变按钮状态
     *
     * @param b
     */
    private void changeContent(boolean b) {
        if (b) {
            btnLoan.setText("立即申请借款");
        } else {
            btnLoan.setText("立即注册");
        }
    }

    private void registerBroadReceive() {
        receiver = new MyBroadcastReceive();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_LOGIN_SUCCESS);
        getContext().registerReceiver(receiver, filter);
    }
}
