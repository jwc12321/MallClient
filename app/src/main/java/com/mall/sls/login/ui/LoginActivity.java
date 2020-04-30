package com.mall.sls.login.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.certify.ui.CerifyTipActivity;
import com.mall.sls.common.widget.textview.ColdDownButton;
import com.mall.sls.common.widget.textview.ConventionalEditTextView;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.AppUrlInfo;
import com.mall.sls.data.entity.OneClickInfo;
import com.mall.sls.login.DaggerLoginComponent;
import com.mall.sls.login.LoginContract;
import com.mall.sls.login.LoginModule;
import com.mall.sls.login.presenter.LoginPresenter;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter.getAppUrlInfo();
    }

    @Override
    protected void initializeInjector() {
        DaggerLoginComponent.builder()
                .applicationComponent(getApplicationComponent())
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.confirm_bt, R.id.send_vcode})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_bt://登录
                CerifyTipActivity.start(this);
                break;
            case R.id.send_vcode:
                break;
            default:
        }
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void renderLoginIn(String token) {

    }

    @Override
    public void renderVCode(String vCode) {

    }

    @Override
    public void renderAppUrlInfo(AppUrlInfo appUrlInfo) {
        if (appUrlInfo != null) {
            showMessage(appUrlInfo.getUrl());
        }
    }

    @Override
    public void renderOneClickLogin(OneClickInfo oneClickInfo) {

    }

    @Override
    public void setPresenter(LoginContract.LoginPresenter presenter) {

    }
}
