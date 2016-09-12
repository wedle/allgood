package com.yiqihao.loan.utils;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yiqihao.loan.R;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * Created by 冯浩 on 16/6/28.
 */
public class UIUtils {

    /**
     * 动态添加小圆点指示器,这种方式添加的小圆点不能跟随滑动
     *
     * @param context
     * @param dotsContainer 装载小圆点的容器
     * @param position      选中页的位置
     * @param size          小圆点个数
     * @param normalImgRes  正常显示的小圆点图标
     * @param focusImgRes   选中后小圆点的图标
     */
    public static void addNavigationDot(Context context, LinearLayout dotsContainer,
                                        int position, int size, int normalImgRes,
                                        int focusImgRes) {
        if (size > 1) {
            dotsContainer.removeAllViews();
            for (int i = 0; i < size; i++) {
                ImageView iv = new ImageView(context);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DensityUtil.dip2px(context, 7), DensityUtil.dip2px(context, 7));
                layoutParams.rightMargin = 10;
                layoutParams.leftMargin = 10;
                iv.setLayoutParams(layoutParams);
                if (i == position) {
                    iv.setImageDrawable(context.getResources().getDrawable(focusImgRes));
                } else {
                    iv.setImageDrawable(context.getResources().getDrawable(normalImgRes));
                }
                dotsContainer.addView(iv);
            }
        }
    }


    public static void ptrFrameAddHeader(Context context, PtrClassicFrameLayout ptrFrame){

        // header
        final MaterialHeader header = new MaterialHeader(context);
        int[] colors = context.getResources().getIntArray(R.array.google_colors);
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, DensityUtil.dip2px(context,15), 0, DensityUtil.dip2px(context,10));
        header.setPtrFrameLayout(ptrFrame);

        ptrFrame.setResistance(2.7f);
        ptrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        ptrFrame.setDurationToClose(150);
        ptrFrame.setDurationToCloseHeader(500);
        ptrFrame.setPullToRefresh(false);
        ptrFrame.setKeepHeaderWhenRefresh(true);
        ptrFrame.setHeaderView(header);
        ptrFrame.addPtrUIHandler(header);
        ptrFrame.disableWhenHorizontalMove(true);

    }

    /**
     * 根据银行简称得到银行logo资源
     *
     * @param name
     *            银行简称
     * @return 图片资源id
     */
    public static int bankLogo(String name) {
        if (name.equals("icbc")) {// 中国银行
            return R.drawable.icbc;
        } else if (name.equals("ccb")) {// 中国建设银行
            return R.drawable.ccb;
        } else if (name.equals("cmb")) {// 招商银行
            return R.drawable.cmb;
        } else if (name.equals("abc")) {// 中国农业银行
            return R.drawable.abc;
        } else if (name.equals("boc")) {// 中国银行
            return R.drawable.boc;
        } else if (name.equals("comm")) {// 中国交通银行
            return R.drawable.comm;
        } else if (name.equals("spdb")) {// 上海浦东发展银行
            return R.drawable.spdb;
        } else if (name.equals("cib")) {// 兴业银行
            return R.drawable.cib;
        } else if (name.equals("bob")) {// 北京银行
            return R.drawable.bob;
        } else if (name.equals("ceb")) {// 中国光大银行
            return R.drawable.ceb;
        } else if (name.equals("cmbc")) {// 中国民生银行
            return R.drawable.cmbc;
        } else if (name.equals("citic")) {// 中信实业银行
            return R.drawable.citic;
        } else if (name.equals("gdb")) {// 广东发展银行
            return R.drawable.gdb;
        } else if (name.equals("pab")) {// 平安银行
            return R.drawable.pab;
        } else if (name.equals("sdb")) {// 深圳发展银行
            return R.drawable.sdb;
        } else if (name.equals("postgc")) {// 邮储银行
            return R.drawable.postgc;
        } else if (name.equals("hxb")) {// 华夏银行
            return R.drawable.hxb;
        } else if (name.equals("other")) {// 其它银行
            return R.drawable.other;
        }  else {
            return R.drawable.other;
        }
    }

}
