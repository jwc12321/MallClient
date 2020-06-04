package com.mall.sls.login;


import com.mall.sls.ActivityScope;
import com.mall.sls.ApplicationComponent;
import com.mall.sls.login.ui.BindPhoneActivity;
import com.mall.sls.login.ui.FillCodeActivity;
import com.mall.sls.login.ui.LoginActivity;
import com.mall.sls.login.ui.LoginFillCodeActivity;
import com.mall.sls.login.ui.PhoneLoginActivity;
import com.mall.sls.login.ui.WeixinLoginActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {LoginModule.class})
public interface LoginComponent {
    void inject(LoginActivity loginActivity);
    void inject(WeixinLoginActivity weixinLoginActivity);
    void inject(BindPhoneActivity bindPhoneActivity);
    void inject(FillCodeActivity fillCodeActivity);
    void inject(PhoneLoginActivity phoneLoginActivity);
    void inject(LoginFillCodeActivity loginFillCodeActivity);
}
