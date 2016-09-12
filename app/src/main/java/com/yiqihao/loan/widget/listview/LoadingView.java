package com.yiqihao.loan.widget.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yiqihao.loan.R;


/**
 * Created by 冯浩 on 2015/12/1.
 */
public class LoadingView extends LinearLayout {

    RelativeLayout rlLodingForMore;
    RelativeLayout rlNoData;

    public LoadingView(Context context) {
        super(context);
        init();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View view = inflate(getContext(), R.layout.loding_for_more, this);
        rlLodingForMore = (RelativeLayout) view.findViewById(R.id.rl_loding_for_more);
        rlNoData = (RelativeLayout) view.findViewById(R.id.rl_no_data);
    }

    public void setShowLoadView(boolean isShow) {

        if (isShow) {
            rlLodingForMore.setVisibility(View.VISIBLE);
            rlNoData.setVisibility(View.GONE);
        } else {
            rlLodingForMore.setVisibility(View.GONE);
            rlNoData.setVisibility(View.VISIBLE);

        }

    }


}