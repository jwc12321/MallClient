package com.mall.sls.login.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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
import com.mall.sls.common.unit.SystemUtil;
import com.mall.sls.common.unit.TokenManager;
import com.mall.sls.common.widget.textview.ConventionalEditTextView;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.AppUrlInfo;
import com.mall.sls.data.entity.TokenInfo;
import com.mall.sls.login.DaggerLoginComponent;
import com.mall.sls.login.LoginContract;
import com.mall.sls.login.LoginModule;
import com.mall.sls.login.presenter.LoginPresenter;
import com.mall.sls.login.presenter.RegisterLoginPresenter;
import com.mall.sls.mainframe.ui.MainFrameActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author jwc on 2020/5/22.
 * 描述：手机号登录注册填写邀请码
 */
public class LoginFillCodeActivity extends BaseActivity implements LoginContract.LoginView {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.vcode_et)
    ConventionalEditTextView vcodeEt;
    @BindView(R.id.no_code_bt)
    ConventionalTextView noCodeBt;
    @BindView(R.id.confirm_bt)
    MediumThickTextView confirmBt;
    private String invitationCode;
    private String deviceId;
    private String deviceOsVersion;
    private String devicePlatform;
    private String accessCode;
    private String mobile;
    private String smsCode;
    private String choiceType;  //0：手机验证码登录 1：一键登录
    private String deviceName;

    @Inject
    LoginPresenter loginPresenter;

    public static void start(Context context,String accessCode,String mobile,String smsCode,String choiceType) {
        Intent intent = new Intent(context, LoginFillCodeActivity.class);
        intent.putExtra(StaticData.ACCESS_CODE,accessCode);
        intent.putExtra(StaticData.MOBILE,mobile);
        intent.putExtra(StaticData.SMS_CODE,smsCode);
        intent.putExtra(StaticData.CHOICE_TYPE,choiceType);
        context.startActivity(intent);
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_fill_code);
        ButterKnife.bind(this);
        setHeight(back, null, null);
        initView();
    }

    private void initView(){
        deviceId = SystemUtil.getAndroidId(this);
        deviceName=SystemUtil.getDeviceName(this);
        deviceOsVersion = SystemUtil.getSystemVersion();
        devicePlatform = "android";
        accessCode=getIntent().getStringExtra(StaticData.ACCESS_CODE);
        mobile=getIntent().getStringExtra(StaticData.MOBILE);
        smsCode=getIntent().getStringExtra(StaticData.SMS_CODE);
        choiceType=getIntent().getStringExtra(StaticData.CHOICE_TYPE);
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
    @OnTextChanged({R.id.vcode_et})
    public void checkVCOdeEnable() {
        invitationCode = vcodeEt.getText().toString().trim();
        confirmBt.setEnabled(!TextUtils.isEmpty(invitationCode));
        noCodeBt.setEnabled(TextUtils.isEmpty(invitationCode));
    }

    @OnClick({R.id.back, R.id.confirm_bt,R.id.no_code_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.confirm_bt:
                confirm();
                break;
            case R.id.no_code_bt:
                loginPresenter.getInvitationCode();
                break;
            default:
        }
    }

    private void confirm(){
        if(TextUtils.equals(StaticData.REFLASH_ZERO,choiceType)){
            loginPresenter.loginIn(deviceId,deviceOsVersion,devicePlatform,mobile,smsCode,invitationCode,deviceName);
        }else {
            loginPresenter.oneClickLogin(accessCode,deviceId,deviceOsVersion,devicePlatform,invitationCode,deviceName);
        }
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }


    @Override
    public void renderLoginIn(TokenInfo tokenInfo) {
        if(tokenInfo!=null){
            //设置个推的别名和标签
            PushManager.getInstance().bindAlias(this,tokenInfo.getUserInfo().getMobile());
            Tag[] tags=new Tag[1];
            tags[0]=new Tag().setName("all");
            PushManager.getInstance().setTag(this,tags, String.valueOf(System.currentTimeMillis()));
            MobileManager.saveMobile(tokenInfo.getUserInfo().getMobile());
            TokenManager.saveToken(tokenInfo.getToken());
            BindWxManager.saveBindWx(tokenInfo.getBindWx() ? StaticData.REFLASH_ONE : StaticData.REFLASH_ZERO);
            if(tokenInfo.getUserInfo()!=null) {
                AvatarUrlManager.saveAvatarUrl(tokenInfo.getUserInfo().getAvatarUrl());
            }
            WeixinLoginActivity.finishActivity();
            MainFrameActivity.start(this);
            finish();
        }
    }

    @Override
    public void renderVCode(String vCode) {
    }

    @Override
    public void renderAppUrlInfo(AppUrlInfo appUrlInfo) {

    }

    @Override
    public void renderInvitationCode(String invitationCode) {
        vcodeEt.setText(invitationCode);
    }

    @Override
    public void setPresenter(LoginContract.LoginPresenter presenter) {

    }
}
