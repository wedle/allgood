package com.yiqihao.loan.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.yiqihao.loan.R;


/**
 * Created by 武昌丶鱼 on 2016/7/15.
 * Description:
 */
public class FormatEditText extends EditText implements View.OnFocusChangeListener {

    /**
     * 格式化类型,0-电话,1-卡号
     */
    private int mFormatType;
    /**
     * 默认不显示一键清除
     */
    private boolean mOpenOneKeyClear;
    /**
     * 删除按钮的图标
     */
    private Drawable mClearDrawable;

    /**
     * 控件是否有焦点
     */
    private boolean hasFoucs;
    /**
     * 是否开启格式化,默认开启
     */
    private boolean mOpenFormat;

    //是否输入
    private boolean isRun = false;
    //输入的内容
    private String inputStr = "";

    public FormatEditText(Context context) {
        this(context, null);
    }

    public FormatEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public FormatEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FormatEditText);

        mFormatType = ta.getInt(R.styleable.FormatEditText_formatType,0);
        mOpenOneKeyClear = ta.getBoolean(R.styleable.FormatEditText_openOneKeyClear,false);
        mOpenFormat = ta.getBoolean(R.styleable.FormatEditText_openFormat,true);

        ta.recycle();
        if(mFormatType==0){
            this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(13)});
        }
        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            mClearDrawable = getResources().getDrawable(R.drawable.clear);
        }
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        //默认设置隐藏图标
        setClearIconVisible(false);
        //设置焦点改变的监听
        setOnFocusChangeListener(this);
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(new TextWatcher() {
            /**
             * 当输入框里面内容发生变化的时候回调的方法
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int count,
                                      int after) {
                if(hasFoucs&&mOpenOneKeyClear){
                    setClearIconVisible(s.length() > 0);
                }

                show(s);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    /**
     * 开启银行卡号和电话号码显示方式
     */
    private void show(CharSequence s){
        if(mOpenFormat){//开启了银行卡号、电话号码显示方式
            if(isRun){
                isRun = false;
                return;
            }
            isRun = true;
            inputStr = "";
            String newStr = s.toString();
            newStr = newStr.replace(" ", "");
            int index = 0;
            if(mFormatType==0){//电话号码显示方式
                if((index + 3)< newStr.length()){
                    inputStr += (newStr.substring(index, index + 3)+ " ");
                    index += 3;
                }
            }
            while((index + 4)< newStr.length()){
                inputStr += (newStr.substring(index, index + 4)+ " ");
                index += 4;
            }
            inputStr += (newStr.substring(index, newStr.length()));
            this.setText(inputStr);
            this.setSelection(inputStr.length());
        }
    }

    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable) {
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFoucs = hasFocus;
        if (hasFocus&&mOpenOneKeyClear) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }
}
