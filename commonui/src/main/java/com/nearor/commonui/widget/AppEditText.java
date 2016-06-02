package com.nearor.commonui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.nearor.commonui.R;

/**
 * Created by Nearor on 6/2/16.
 * 带有删除按钮的EditText
 */
public class AppEditText extends EditText implements
        View.OnFocusChangeListener{

    /**
     * 删除按钮的引用
     */
    private Drawable mClearDrawable;

    public AppEditText(Context context) {
        super(context);
        init();
    }

    public AppEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AppEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AppEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        //获取Editview的DrawableRight,如果没有设置就使用默认图片
        mClearDrawable = getCompoundDrawables()[2];
        if(mClearDrawable == null){
            mClearDrawable = getResources().getDrawable(R.drawable.app_edit_text_clear);
        }

        mClearDrawable.setBounds(0,0,60,60);


    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        super.setOnFocusChangeListener(l);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }
}
