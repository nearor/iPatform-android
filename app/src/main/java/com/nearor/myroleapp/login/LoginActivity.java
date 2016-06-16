package com.nearor.myroleapp.login;


import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nearor.common.api.PassportService;
import com.nearor.common.api.entity.LoginResponse;
import com.nearor.common.module.Module;
import com.nearor.common.module.RoleAppModuleMap;
import com.nearor.commonui.ativity.AppActivity;
import com.nearor.framwork.network.APICallBack;
import com.nearor.framwork.network.APIResponse;
import com.nearor.framwork.preference.GlobalValue;
import com.nearor.framwork.util.Lg;
import com.nearor.framwork.util.ToastUtil;
import com.nearor.myroleapp.R;

import java.util.Map;

public class LoginActivity extends AppActivity {

    private static final String TAG = Lg.makeLogTag(AppActivity.class);
    private EditText loginPhone;
    private EditText loginPass;
    private Button register;

    private PassportService mPassportService = new PassportService(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        loginPhone=(EditText)findViewById(R.id.et_login_phone);
        loginPass=(EditText)findViewById(R.id.et_login_pass);
        setHintSize();
        loginPhone.addTextChangedListener(new BindingTextWatcher());
        loginPhone.setOnEditorActionListener(new BindingEditorActionListener());
        loginPass.addTextChangedListener(new BindingTextWatcher());
        loginPass.setOnEditorActionListener(new BindingEditorActionListener());

        register=(Button)findViewById(R.id.bt_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Access();
            }
        });
    }


    private class BindingEditorActionListener implements TextView.OnEditorActionListener {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (v == loginPhone && actionId == EditorInfo.IME_ACTION_NEXT) {
                loginPass.requestFocus();
            } else if (v == loginPass && actionId == EditorInfo.IME_ACTION_DONE) {
                Access();
            }
            return true;
        }

    }

    private void setHintSize() {
        // 新建一个可以添加属性的文本对象
        SpannableString ss = new SpannableString("请输入您的手机号");
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(14,true);
        // 附加属性到文本
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        loginPhone.setHint(new SpannedString(ss));
        SpannableString ssp = new SpannableString("请输入您的密码");
        AbsoluteSizeSpan assp = new AbsoluteSizeSpan(14,true);
        // 附加属性到文本
        ssp.setSpan(assp, 0, ssp.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        loginPass.setHint(new SpannedString(ssp));

    }


    private void Access() {
        mPassportService.passwordLogin(
                loginPhone.getText().toString(),
                loginPass.getText().toString(),
                new APICallBack<LoginResponse>() {
                    @Override
                    public void onSuccess(LoginResponse object) {
                        Module targetModule ;
                        if (GlobalValue.isPM()) {
                            targetModule = RoleAppModuleMap.getInstance().getModule(RoleAppModuleMap.MODULE_NAME_HOME_PM);
                        } else {
                            targetModule = RoleAppModuleMap.getInstance().getModule(RoleAppModuleMap.MODULE_NAME_HOME_PM);
                        }

                        Map<String, String> params = getModuleParams();
                        if (params != null && !TextUtils.isEmpty(params.get("target"))) {
                            targetModule = RoleAppModuleMap.getInstance().getModule(params.get("target"));
                        }

                        if (targetModule != null) {
                            startModule(targetModule.getName());
                        //    overridePendingTransition(R.anim.commonui_fade_in, R.anim.commonui_fade_out);
                           // hideLoading();
                            finish();
                        } else {
                           // hideLoading();
                            ToastUtil.showMessage("登录成功");
                            Lg.i(TAG,"登录成功");
                       }
                    }

                    @Override
                    public void onError(Throwable throwable, APIResponse apiResponse) {
                        ToastUtil.showMessage(apiResponse == null ? throwable.getLocalizedMessage() : apiResponse.getLocalizedMessage());

                    }
                }
        );

    }


    private class BindingTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            register.setEnabled(loginPhone.getText().length() == 11 && loginPass.getText().length() > 0);
        }
    }
}
