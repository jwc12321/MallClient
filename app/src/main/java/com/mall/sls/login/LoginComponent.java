package com.mall.sls.login;


import com.mall.sls.ActivityScope;
import com.mall.sls.ApplicationComponent;
import com.mall.sls.login.ui.LoginActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {LoginModule.class})
public interface LoginComponent {
    void inject(LoginActivity loginActivity);
}
