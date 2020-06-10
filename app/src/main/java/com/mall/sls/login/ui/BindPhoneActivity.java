package com.mall.sls.login.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.ColdDownButton;
import com.mall.sls.common.widget.textview.ConventionalEditTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.login.DaggerLoginComponent;
import com.mall.sls.login.LoginContract;
import com.mall.sls.login.LoginModule;
import com.mall.sls.login.presenter.BindMobilePresenter;
import com.mobile.auth.gatewayauth.AuthUIConfig;
import com.mobile.auth.gatewayauth.AuthUIControlClickListener;
import com.mobile.auth.gatewayauth.PhoneNumberAuthHelper;
import com.mobile.auth.gatewayauth.TokenResultListener;
import com.mobile.auth.gatewayauth.model.TokenRet;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author jwc on 2020/5/6.
 * 描述：绑定手机号
 */
public class BindPhoneActivity extends BaseActivity implements LoginContract.BindMobileView {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.phone_et)
    ConventionalEditTextView phoneEt;
    @BindView(R.id.vcode_et)
    ConventionalEditTextView vcodeEt;
    @BindView(R.id.send_vcode)
    ColdDownButton sendVcode;
    @BindView(R.id.confirm_bt)
    MediumThickTextView confirmBt;

    private String phoneNumber;
    private String smsCode;
    @Inject
    BindMobilePresenter bindMobilePresenter;
    private String unionId;

    public static void start(Context context,String unionId) {
        Intent intent = new Intent(context, BindPhoneActivity.class);
        intent.putExtra(StaticData.UNION_ID,unionId);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        unionId=getIntent().getStringExtra(StaticData.UNION_ID);
    }

    @Override
    protected void initializeInjector() {
        DaggerLoginComponent.builder()
                .applicationComponent(getApplicationComponent())
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    /**
     * 监听手机输入框
     */
    @OnTextChanged({R.id.phone_et})
    public void checkPhoneEnable() {
        phoneNumber = phoneEt.getText().toString().trim();
    }

    /**
     * 监听手机输入框
     */
    @OnTextChanged({R.id.vcode_et})
    public void checkVCOdeEnable() {
        smsCode = vcodeEt.getText().toString().trim();
    }



    @OnClick({R.id.back, R.id.confirm_bt, R.id.send_vcode})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.confirm_bt:
                confirmBt();
                break;
            case R.id.send_vcode:
                sendVcode();
                break;
            default:
        }
    }

    private void confirmBt() {
        if (TextUtils.isEmpty(phoneNumber)) {
            showMessage(getString(R.string.input_phone_number));
            return;
        }
        if (TextUtils.isEmpty(smsCode)) {
            showMessage(getString(R.string.input_vcode));
            return;
        }
        FillCodeActivity.start(BindPhoneActivity.this,unionId,"",phoneNumber,smsCode,StaticData.REFLASH_ZERO);
        finish();
    }

    private void sendVcode() {
        if (TextUtils.isEmpty(phoneNumber)) {
            showError(getString(R.string.input_phone_number));
        } else {
            bindMobilePresenter.sendVCode(phoneNumber);
        }
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void renderVCode(String vCode) {
//        vcodeEt.setText(vCode);
        sendVcode.startCold();
    }

    @Override
    public void setPresenter(LoginContract.BindMobilePresenter presenter) {

    }
}
