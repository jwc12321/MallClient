package com.mall.sls.login.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.Tag;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.MobileManager;
import com.mall.sls.common.unit.PayTypeInstalledUtils;
import com.mall.sls.common.unit.SystemUtil;
import com.mall.sls.common.unit.TokenManager;
import com.mall.sls.common.unit.WXShareManager;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.TokenInfo;
import com.mall.sls.homepage.ui.ConfirmOrderActivity;
import com.mall.sls.login.DaggerLoginComponent;
import com.mall.sls.login.LoginContract;
import com.mall.sls.login.LoginModule;
import com.mall.sls.login.presenter.WeiXinLoginPresenter;
import com.mall.sls.mainframe.ui.MainFrameActivity;
import com.mobile.auth.gatewayauth.AuthUIConfig;
import com.mobile.auth.gatewayauth.AuthUIControlClickListener;
import com.mobile.auth.gatewayauth.PhoneNumberAuthHelper;
import com.mobile.auth.gatewayauth.TokenResultListener;
import com.mobile.auth.gatewayauth.model.TokenRet;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/21.
 * 描述：
 */
public class WeixinLoginActivity extends BaseActivity implements LoginContract.WeiXinLoginView {


    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.tv)
    MediumThickTextView tv;
    @BindView(R.id.confirm_bt)
    LinearLayout confirmBt;
    @BindView(R.id.choice_item)
    CheckBox choiceItem;
    @BindView(R.id.register_tv)
    ConventionalTextView registerTv;
    @BindView(R.id.privacy_tv)
    ConventionalTextView privacyTv;

    // 微信登录
    private static IWXAPI WXapi;
    @Inject
    WeiXinLoginPresenter weiXinLoginPresenter;

    private String deviceId;
    private String deviceOsVersion;
    private String devicePlatform;
    private WXShareManager wxShareManager;

    private PhoneNumberAuthHelper mAlicomAuthHelper;
    private TokenResultListener mTokenListener;
    private String loginToken;
    private String loginKey = "+3do8fAWK3Tv3vsx/iNPefkUhXOaZOWlrRv8rWUt1muMwnRX/NVlymCa7pvf2fh2qC/XwPQ6fkhNmI+Ke/85gonr7bUw6Tti+4PDbDt8znZtVuhApw2gerNIiAKbeXbF89PBAS/4xAHxPcd1vnZJwfL0YQ9teT74JT+7GT4qsi83hgeS4K8C9MXoqzrhqTWVLdSk7aUAgx/gXrXKQ8PoMDZsGpjuJ02eRyRdaoiGX8fZIWQYq3RlEavsu++RnbULX4OguGRxDn8YDTNvmmdIDA==";
    private boolean checkRet;
    private String unionId;


    public static void start(Context context) {
        Intent intent = new Intent(context, WeixinLoginActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weixin_login);
        ButterKnife.bind(this);
        setHeight(null, title, null);
        initView();
        wxShareManager = WXShareManager.getInstance(this);
    }

    private void initView() {
        sActivityRef = new WeakReference<>(this);
        EventBus.getDefault().register(this);
        deviceId = SystemUtil.getAndroidId(this);
        deviceOsVersion = SystemUtil.getSystemVersion();
        devicePlatform = "android";
        TokenManager.saveToken("");
        PushManager.getInstance().unBindAlias(this, MobileManager.getMobile(), true);
        init();
    }

    @OnClick({R.id.confirm_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_bt://登录
                WXLogin();
//                Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.app_icon);
//                wxShareManager.shareUrlToWX(false,"https://www.baidu.com",bitmap,"百度","我是百度");
                break;
            default:
        }
    }

    /**
     * 登录微信
     */
    private void WXLogin() {
        if (!choiceItem.isChecked()) {
            showMessage(getString(R.string.choice_login));
            return;
        }
        if (PayTypeInstalledUtils.isWeixinAvilible(WeixinLoginActivity.this)) {
            WXapi = WXAPIFactory.createWXAPI(this, StaticData.WX_APP_ID, true);
            WXapi.registerApp(StaticData.WX_APP_ID);
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo";
            WXapi.sendReq(req);
        } else {
            showMessage(getString(R.string.install_weixin));
        }

    }

    @Override
    protected void initializeInjector() {
        DaggerLoginComponent.builder()
                .applicationComponent(getApplicationComponent())
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    //支付成功
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginSuccess(String code) {
        if (!TextUtils.isEmpty(code)) {
            weiXinLoginPresenter.weixinLogin(deviceId, deviceOsVersion, devicePlatform, code);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void renderWeixinLogin(TokenInfo tokenInfo) {
        if (tokenInfo != null) {
            //设置个推的别名和标签
            if (!TextUtils.isEmpty(tokenInfo.getToken())) {
                PushManager.getInstance().bindAlias(this, tokenInfo.getUserInfo().getMobile());
                Tag[] tags = new Tag[1];
                tags[0] = new Tag().setName("all");
                PushManager.getInstance().setTag(this, tags, String.valueOf(System.currentTimeMillis()));
                MobileManager.saveMobile(tokenInfo.getUserInfo().getMobile());
                TokenManager.saveToken(tokenInfo.getToken());
                MainFrameActivity.start(this);
                finish();
            } else {
                unionId=tokenInfo.getUnionId();
                if (checkRet) {
                    configLoginTokenPort();
                    mAlicomAuthHelper.getLoginToken(WeixinLoginActivity.this, 5000);
                }else {
                    BindPhoneActivity.start(this, unionId);
                }
            }
        }
    }

    @Override
    public void setPresenter(LoginContract.WeiXinLoginPresenter presenter) {

    }

    private static WeakReference<WeixinLoginActivity> sActivityRef;

    public static void finishActivity() {
        if (sActivityRef != null && sActivityRef.get() != null) {
            sActivityRef.get().finish();
        }
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
                    FillCodeActivity.start(WeixinLoginActivity.this,unionId,loginToken,"","",StaticData.REFLASH_ONE);
                }
                mAlicomAuthHelper.quitLoginPage();
            }

            @Override
            public void onTokenFailed(final String ret) {
                WeixinLoginActivity.this.runOnUiThread(new Runnable() {
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
        /**
         * 控件点击事件回调
         */
        mAlicomAuthHelper.setUIClickListener(new AuthUIControlClickListener() {
            @Override
            public void onClick(String code, Context context, JSONObject jsonObj) {
                Log.e("authSDK", "OnUIControlClick:code=" + code + ", jsonObj=" + (jsonObj == null ? "" : jsonObj.toJSONString()));
                if (TextUtils.equals("700001", code)) {
                    BindPhoneActivity.start(WeixinLoginActivity.this, unionId);
                }
            }
        });
    }


    private String registerAgreeTxt = "\n《注册协议》";
    private String privacyPolicyTxt = "《隐私协议》";

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
                .setAppPrivacyTwo(privacyPolicyTxt, StaticData.USER_AGREEMENT)
                .create());
    }
}
