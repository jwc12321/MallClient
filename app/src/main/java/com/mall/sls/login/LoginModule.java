package com.mall.sls.login;
import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {
    private LoginContract.LoginView loginView;

    public LoginModule(LoginContract.LoginView loginView) {
        this.loginView = loginView;
    }

    @Provides
    LoginContract.LoginView provideLoginView() {
        return loginView;
    }
}
