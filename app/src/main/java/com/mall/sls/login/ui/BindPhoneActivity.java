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
    private PhoneNumberAuthHelper mAlicomAuthHelper;
    private TokenResultListener mTokenListener;
    private String loginToken;
    private String loginKey ="+3do8fAWK3Tv3vsx/iNPefkUhXOaZOWlrRv8rWUt1muMwnRX/NVlymCa7pvf2fh2qC/XwPQ6fkhNmI+Ke/85gonr7bUw6Tti+4PDbDt8znZtVuhApw2gerNIiAKbeXbF89PBAS/4xAHxPcd1vnZJwfL0YQ9teT74JT+7GT4qsi83hgeS4K8C9MXoqzrhqTWVLdSk7aUAgx/gXrXKQ8PoMDZsGpjuJ02eRyRdaoiGX8fZIWQYq3RlEavsu++RnbULX4OguGRxDn8YDTNvmmdIDA==";
    private boolean checkRet;
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
        init();
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
        FillCodeActivity.start(BindPhoneActivity.this,unionId,loginToken,phoneNumber,smsCode,StaticData.REFLASH_ZERO);
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
        vcodeEt.setText(vCode);
        sendVcode.startCold();
    }

    @Override
    public void setPresenter(LoginContract.BindMobilePresenter presenter) {

    }

    private void init() {
        mTokenListener = new TokenResultListener() {
            @Override
            public void onTokenSuccess(final String ret) {
                Log.e("xxxxxx", "onTokenSuccess:" + ret);
                TokenRet tokenRet = null;
                try {
                    tokenRet = JSON.parseObject(ret, TokenRet.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (tokenRet != null && !("600001").equals(tokenRet.getCode())) {
                    loginToken = tokenRet.getToken();
                    FillCodeActivity.start(BindPhoneActivity.this,unionId,loginToken,phoneNumber,smsCode,StaticData.REFLASH_ONE);
                    BindPhoneActivity.this.finish();
                }
                mAlicomAuthHelper.quitLoginPage();
            }

            @Override
            public void onTokenFailed(final String ret) {
                BindPhoneActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAlicomAuthHelper.hideLoginLoading();
                    }
                });
            }
        };
        mAlicomAuthHelper = PhoneNumberAuthHelper.getInstance(this, mTokenListener);
        mAlicomAuthHelper.setAuthSDKInfo(loginKey);
        checkRet = mAlicomAuthHelper.checkEnvAvailable();
        mAlicomAuthHelper.setAuthListener(mTokenListener);
        if (checkRet) {
            configLoginTokenPort();
            mAlicomAuthHelper.getLoginToken(BindPhoneActivity.this, 5000);
        }
        /**
         * 控件点击事件回调
         */
        mAlicomAuthHelper.setUIClickListener(new AuthUIControlClickListener() {
            @Override
            public void onClick(String code, Context context, JSONObject jsonObj) {
                Log.e("authSDK", "OnUIControlClick:code=" + code + ", jsonObj=" + (jsonObj == null ? "" : jsonObj.toJSONString()));
                if(TextUtils.equals("700001",code)){

                }
            }
        });
    }


    private String registerAgreeTxt="\n《注册协议》";
    private String privacyPolicyTxt="《隐私协议》";
    private void configLoginTokenPort() {
        mAlicomAuthHelper.setAuthUIConfig(new AuthUIConfig.Builder()
                .setStatusBarColor(getResources().getColor(R.color.backGround1))
                .setLightColor(true)
                .setStatusBarUIFlag(View.SYSTEM_UI_FLAG_LOW_PROFILE)
                .setNavColor(getResources().getColor(R.color.backGround1))
                .setNavText(getString(R.string.bind_mobile))
                .setNavTextColor(getResources().getColor(R.color.appText1))
                .setNavTextSize(16)
                .setNavReturnHidden(true)
                .setLogoImgPath("icon_login_iv")
                .setLogoWidth(200)
                .setLogoHeight(99)
                .setLogoOffsetY(92)
                .setLogoScaleType(ImageView.ScaleType.FIT_XY)
                .setSloganTextColor(getResources().getColor(R.color.backGround8))
                .setNumberColor(getResources().getColor(R.color.appText3))
                .setNumberSize(20)
                .setNumFieldOffsetY(231)
                .setNumberLayoutGravity(Gravity.CENTER_HORIZONTAL)
                .setLogBtnText(getString(R.string.bind_onclick_mobile))
                .setLogBtnTextColor(getResources().getColor(R.color.appText1))
                .setLogBtnTextSize(16)
                .setLogBtnBackgroundPath("confirm_bt_select")
                .setLogBtnHeight(54)
                .setLogBtnMarginLeftAndRight(15)
                .setLogBtnOffsetY(291)
                .setSwitchAccText(getString(R.string.code_bind))
                .setSwitchAccTextColor(getResources().getColor(R.color.appText4))
                .setSwitchAccTextSize(14)
                .setSwitchOffsetY(360)
                .setCheckboxHidden(false)
                .setCheckBoxWidth(16)
                .setCheckBoxHeight(16)
                .setCheckedImgPath("icon_login_true")
                .setUncheckedImgPath("icon_login_false")
                .setAppPrivacyColor(getResources().getColor(R.color.appText3), getResources().getColor(R.color.backGround1))
                .setPrivacyOffsetY_B(35)
                .setPrivacyTextSize(12)
                .setVendorPrivacyPrefix("《")
                .setVendorPrivacySuffix("》")
                .setAppPrivacyOne(registerAgreeTxt, StaticData.USER_AGREEMENT)
                .setAppPrivacyTwo(privacyPolicyTxt,StaticData.USER_AGREEMENT)
                .create());
    }
}
