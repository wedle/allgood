package com.yiqihao.loan.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yiqihao.loan.Constant;
import com.yiqihao.loan.R;
import com.yiqihao.loan.entity.BrandsModel;
import com.yiqihao.loan.mvp.presenters.CarChoosePresenter;
import com.yiqihao.loan.mvp.views.CarChooseView;
import com.yiqihao.loan.ui.adapter.CarChooseAdapter;
import com.yiqihao.loan.utils.T;
import com.yiqihao.loan.widget.SideBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 冯浩 on 2016/8/17.
 */
public class CarChooseActivity extends MvpActivity<CarChooseView, CarChoosePresenter> implements CarChooseView {


    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;
    @BindView(R.id.lv_brands)
    ListView lvBrands;
    @BindView(R.id.tv_letter)
    TextView tvLetter;
    @BindView(R.id.sidrbar)
    SideBar sidrbar;
    @BindView(R.id.fl_brands_content)
    FrameLayout flBrandsContent;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_add)
    TextView tvAdd;

    private CarChooseAdapter adapter;

    private MyBroadcastReceive receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_choose);
        ButterKnife.bind(this);
        initToolBar(toolbar,true);
        initView();
        registerBroadReceive();
        presenter.initData();
    }

    private void initView() {

        // 设置右侧触摸监听
        sidrbar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));

                if (position != -1) {
                    lvBrands.setSelection(position);
                }

            }
        });

        sidrbar.setTextView(tvLetter);

    }

    private void registerBroadReceive() {
        receiver = new MyBroadcastReceive();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_CAR_INFO);
        registerReceiver(receiver, filter);
    }


    private class MyBroadcastReceive extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constant.ACTION_CAR_INFO)) {//添加成功
                finish();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void showContent(List<BrandsModel> data) {
        pbLoading.setVisibility(View.GONE);
        flBrandsContent.setVisibility(View.VISIBLE);

        adapter = new CarChooseAdapter(this, data);
        lvBrands.setAdapter(adapter);
    }

    @Override
    public void showError(boolean isShow, String e) {
        T.showShort(this, "加载失败");
        pbLoading.setVisibility(View.GONE);
        lvBrands.setVisibility(View.GONE);
    }

    @NonNull
    @Override
    public CarChoosePresenter createPresenter() {
        return new CarChoosePresenter();
    }

    @OnClick(R.id.tv_add)
    public void onClick() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.dialog_edittext, null);
        dialog.setView(layout);

        final EditText etCar = (EditText) layout.findViewById(R.id.et_car);
        dialog.setTitle("新增车辆信息");
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }

        });
        dialog.show();

    }

}
