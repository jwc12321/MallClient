package com.mall.sls.login.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.address.ui.AddAddressActivity;
import com.mall.sls.address.ui.AddressManageActivity;
import com.mall.sls.address.ui.SelectAddressActivity;
import com.mall.sls.assemble.ui.AssembleHomeActivity;
import com.mall.sls.certify.ui.CerifyTipActivity;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SystemUtil;
import com.mall.sls.common.unit.TokenManager;
import com.mall.sls.common.widget.textview.ColdDownButton;
import com.mall.sls.common.widget.textview.ConventionalEditTextView;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.AppUrlInfo;
import com.mall.sls.data.entity.TokenInfo;
import com.mall.sls.login.DaggerLoginComponent;
import com.mall.sls.login.LoginContract;
import com.mall.sls.login.LoginModule;
import com.mall.sls.login.presenter.LoginPresenter;
import com.mall.sls.mainframe.ui.MainFrameActivity;
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

public class LoginActivity extends BaseActivity implements LoginContract.LoginView {
    @Inject
    LoginPresenter loginPresenter;
    @BindView(R.id.phone_et)
    ConventionalEditTextView phoneEt;
    @BindView(R.id.vcode_et)
    ConventionalEditTextView vcodeEt;
    @BindView(R.id.send_vcode)
    ColdDownButton sendVcode;
    @BindView(R.id.login_weixin_iv)
    ImageView loginWeixinIv;
    @BindView(R.id.choice_item)
    CheckBox choiceItem;
    @BindView(R.id.register_tv)
    ConventionalTextView registerTv;
    @BindView(R.id.privacy_tv)
    ConventionalTextView privacyTv;
    @BindView(R.id.confirm_bt)
    MediumThickTextView confirmBt;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    private String phoneNumber;
    private String smsCode;
    private String deviceId;
    private String deviceOsVersion;
    private String devicePlatform;

    private PhoneNumberAuthHelper mAlicomAuthHelper;
    private TokenResultListener mTokenListener;
    private String loginToken;
    private boolean checkRet;
    private String loginKey ="+3do8fAWK3Tv3vsx/iNPefkUhXOaZOWlrRv8rWUt1muMwnRX/NVlymCa7pvf2fh2qC/XwPQ6fkhNmI+Ke/85gonr7bUw6Tti+4PDbDt8znZtVuhApw2gerNIiAKbeXbF89PBAS/4xAHxPcd1vnZJwfL0YQ9teT74JT+7GT4qsi83hgeS4K8C9MXoqzrhqTWVLdSk7aUAgx/gXrXKQ8PoMDZsGpjuJ02eRyRdaoiGX8fZIWQYq3RlEavsu++RnbULX4OguGRxDn8YDTNvmmdIDA==";
    private Gson gson=new Gson();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
//        loginPresenter.getAppUrlInfo();
    }

    private void initView(){
        deviceId = SystemUtil.getAndroidId(this);
        deviceOsVersion = SystemUtil.getSystemVersion();
        devicePlatform = "android";
        TokenManager.saveToken("");
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

    @OnClick({R.id.confirm_bt, R.id.send_vcode,R.id.login_weixin_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_bt://登录
//                confirmBt();
                MainFrameActivity.start(this);
                break;
            case R.id.send_vcode:
                sendVcode();
                break;
            case R.id.login_weixin_iv://微信登录
                BindPhoneActivity.start(this);
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
        if (!choiceItem.isChecked()) {
            showMessage(getString(R.string.choice_login));
            return;
        }
        loginPresenter.loginIn(deviceId, deviceOsVersion, devicePlatform, phoneNumber, smsCode);
    }

    private void sendVcode() {
        if (TextUtils.isEmpty(phoneNumber)) {
            showError(getString(R.string.input_phone_number));
        } else {
            loginPresenter.sendVCode(phoneNumber);
        }
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void renderLoginIn(TokenInfo tokenInfo) {
        if(tokenInfo!=null){
            TokenManager.saveToken(tokenInfo.getToken());
            CerifyTipActivity.start(this);
        }
    }

    @Override
    public void renderVCode(String vCode) {
        vcodeEt.setText(vCode);
        sendVcode.startCold();
    }

    @Override
    public void renderAppUrlInfo(AppUrlInfo appUrlInfo) {
        if (appUrlInfo != null) {
            showMessage(appUrlInfo.getUrl());
        }
    }

    @Override
    public void setPresenter(LoginContract.LoginPresenter presenter) {

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
                    loginPresenter.oneClickLogin(loginToken, deviceId, deviceOsVersion, devicePlatform);
                }
            }

            @Override
            public void onTokenFailed(final String ret) {
                LoginActivity.this.runOnUiThread(new Runnable() {
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
            mAlicomAuthHelper.getLoginToken(LoginActivity.this, 5000);
        }
        /**
         * 控件点击事件回调
         */
        mAlicomAuthHelper.setUIClickListener(new AuthUIControlClickListener() {
            @Override
            public void onClick(String code, Context context, JSONObject jsonObj) {
                Log.e("authSDK", "OnUIControlClick:code=" + code + ", jsonObj=" + (jsonObj == null ? "" : jsonObj.toJSONString()));
                if(TextUtils.equals("700001",code)){
                    loginPresenter.getAppUrlInfo();
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
                .setNavText(getString(R.string.login_regist))
                .setNavTextColor(getResources().getColor(R.color.appText1))
                .setNavTextSize(16)
                .setNavReturnHidden(true)
                .setLogoImgPath("icon_one_click_login")
                .setLogoWidth(200)
                .setLogoHeight(99)
                .setLogoOffsetY(92)
                .setLogoScaleType(ImageView.ScaleType.FIT_XY)
                .setSloganTextColor(getResources().getColor(R.color.backGround8))
                .setNumberColor(getResources().getColor(R.color.appText3))
                .setNumberSize(20)
                .setNumFieldOffsetY(231)
                .setNumberLayoutGravity(Gravity.CENTER_HORIZONTAL)
                .setLogBtnText(getString(R.string.login_regist_bt))
                .setLogBtnTextColor(getResources().getColor(R.color.appText1))
                .setLogBtnTextSize(16)
                .setLogBtnBackgroundPath("confirm_bt_select")
                .setLogBtnHeight(54)
                .setLogBtnMarginLeftAndRight(15)
                .setLogBtnOffsetY(291)
                .setSwitchAccText(getString(R.string.other_login))
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
