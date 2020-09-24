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
import com.mall.sls.common.unit.MobileManager;
import com.mall.sls.common.unit.SystemUtil;
import com.mall.sls.common.unit.TokenManager;
import com.mall.sls.common.widget.textview.ConventionalEditTextView;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.TokenInfo;
import com.mall.sls.login.DaggerLoginComponent;
import com.mall.sls.login.LoginContract;
import com.mall.sls.login.LoginModule;
import com.mall.sls.login.presenter.RegisterLoginPresenter;
import com.mall.sls.mainframe.ui.MainFrameActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author jwc on 2020/5/22.
 * 描述：
 */
public class FillCodeActivity extends BaseActivity implements LoginContract.RegisterLoginView {


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
    private String unionId;
    private String accessCode;
    private String mobile;
    private String smsCode;
    private String choiceType;  //0：手机验证码绑定 1：一键绑定
    private String deviceName;

    @Inject
    RegisterLoginPresenter registerLoginPresenter;

    public static void start(Context context,String unionId,String accessCode,String mobile,String smsCode,String choiceType) {
        Intent intent = new Intent(context, FillCodeActivity.class);
        intent.putExtra(StaticData.UNION_ID,unionId);
        intent.putExtra(StaticData.ACCESS_CODE,accessCode);
        intent.putExtra(StaticData.MOBILE,mobile);
        intent.putExtra(StaticData.SMS_CODE,smsCode);
        intent.putExtra(StaticData.CHOICE_TYPE,choiceType);
        context.startActivity(intent);
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_code);
        ButterKnife.bind(this);
        setHeight(back, null, null);
        initView();
    }

    private void initView(){
        deviceId = SystemUtil.getAndroidId(this);
        deviceName=SystemUtil.getDeviceName(this);
        deviceOsVersion = SystemUtil.getSystemVersion();
        devicePlatform = "android";
        unionId=getIntent().getStringExtra(StaticData.UNION_ID);
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
                registerLoginPresenter.getInvitationCode();
                break;
            default:
        }
    }

    private void confirm(){
        if(TextUtils.equals(StaticData.REFRESH_ZERO,choiceType)){
            registerLoginPresenter.bindSmsCodeLogin(deviceId,deviceOsVersion,devicePlatform,mobile,smsCode,invitationCode,unionId,deviceName);
        }else {
            registerLoginPresenter.bindOneClickLogin(deviceId,deviceOsVersion,devicePlatform,accessCode,invitationCode,unionId,deviceName);
        }
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void renderInvitationCode(String invitationCode) {
        vcodeEt.setText(invitationCode);
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
            if(tokenInfo.getUserInfo()!=null) {
                AvatarUrlManager.saveAvatarUrl(tokenInfo.getUserInfo().getAvatarUrl());
            }
            WeixinLoginActivity.finishActivity();
            MainFrameActivity.start(this);
            finish();
        }
    }

    @Override
    public void setPresenter(LoginContract.RegisterLoginPresenter presenter) {

    }
}
