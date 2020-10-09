package com.mall.sls.login.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.igexin.sdk.PushManager;
import com.igexin.sdk.Tag;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.AvatarUrlManager;
import com.mall.sls.common.unit.BindWxManager;
import com.mall.sls.common.unit.MobileManager;
import com.mall.sls.common.unit.PhoneUnit;
import com.mall.sls.common.unit.SystemUtil;
import com.mall.sls.common.unit.TCAgentUnit;
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
import com.mall.sls.webview.ui.WebViewActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author jwc on 2020/5/6.
 * 描述：手机号登录
 */
public class PhoneLoginActivity extends BaseActivity implements LoginContract.LoginView {

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
    @BindView(R.id.choice_item)
    CheckBox choiceItem;
    @BindView(R.id.register_tv)
    ConventionalTextView registerTv;
    @BindView(R.id.privacy_tv)
    ConventionalTextView privacyTv;

    private String phoneNumber;
    private String smsCode;
    private String deviceId;
    private String deviceOsVersion;
    private String devicePlatform;
    private String deviceName;
    @Inject
    LoginPresenter loginPresenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, PhoneLoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        deviceId = SystemUtil.getAndroidId(this);
        deviceName=SystemUtil.getDeviceName(this);
        deviceOsVersion = SystemUtil.getSystemVersion();
        devicePlatform = SystemUtil.getChannel(this);
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


    @OnClick({R.id.back, R.id.confirm_bt, R.id.send_vcode, R.id.register_tv, R.id.privacy_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.confirm_bt:
                confirmBt();
                TCAgentUnit.setEventId(this,getString(R.string.phone_login));
                break;
            case R.id.send_vcode:
                sendVcode();
                break;
            case R.id.register_tv://用户协议
                WebViewActivity.start(this, StaticData.USER_AGREEMENT);
                break;
            case R.id.privacy_tv://隐私政策
                WebViewActivity.start(this, StaticData.USER_PRIVACY);
                break;
            default:
        }
    }

    private void confirmBt() {
        if (TextUtils.isEmpty(phoneNumber)) {
            showMessage(getString(R.string.input_phone_number));
            return;
        }
        if (!PhoneUnit.isPhone(phoneNumber)) {
            showMessage(getString(R.string.input_right_phone_number));
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
        loginPresenter.loginIn(deviceId, deviceOsVersion, devicePlatform, phoneNumber, smsCode, "",deviceName);
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
        if (tokenInfo != null) {
            //设置个推的别名和标签
            if (!TextUtils.isEmpty(tokenInfo.getToken())) {
                PushManager.getInstance().bindAlias(this, tokenInfo.getUserInfo().getMobile());
                Tag[] tags = new Tag[1];
                tags[0] = new Tag().setName("all");
                PushManager.getInstance().setTag(this, tags, String.valueOf(System.currentTimeMillis()));
                MobileManager.saveMobile(tokenInfo.getUserInfo().getMobile());
                TokenManager.saveToken(tokenInfo.getToken());
                BindWxManager.saveBindWx(tokenInfo.getBindWx() ? StaticData.REFRESH_ONE : StaticData.REFRESH_ZERO);
                if(tokenInfo.getUserInfo()!=null) {
                    AvatarUrlManager.saveAvatarUrl(tokenInfo.getUserInfo().getAvatarUrl());
                }
                WeixinLoginActivity.finishActivity();
                MainFrameActivity.start(this);
                finish();
            } else {
                LoginFillCodeActivity.start(this,"",phoneNumber,smsCode,StaticData.REFRESH_ZERO);
            }
        }
    }

    @Override
    public void renderVCode() {
        sendVcode.startCold();
    }

    @Override
    public void renderAppUrlInfo(AppUrlInfo appUrlInfo) {

    }

    @Override
    public void renderInvitationCode(String invitationCode) {

    }

    @Override
    public void setPresenter(LoginContract.LoginPresenter presenter) {

    }
}
