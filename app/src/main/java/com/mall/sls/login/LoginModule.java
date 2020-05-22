package com.mall.sls.login;
import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {
    private LoginContract.LoginView loginView;
    private LoginContract.WeiXinLoginView weiXinLoginView;
    private LoginContract.BindMobileView bindMobileView;
    private LoginContract.RegisterLoginView registerLoginView;

    public LoginModule(LoginContract.LoginView loginView) {
        this.loginView = loginView;
    }

    public LoginModule(LoginContract.WeiXinLoginView weiXinLoginView) {
        this.weiXinLoginView = weiXinLoginView;
    }

    public LoginModule(LoginContract.BindMobileView bindMobileView) {
        this.bindMobileView = bindMobileView;
    }

    public LoginModule(LoginContract.RegisterLoginView registerLoginView) {
        this.registerLoginView = registerLoginView;
    }

    @Provides
    LoginContract.LoginView provideLoginView() {
        return loginView;
    }

    @Provides
    LoginContract.WeiXinLoginView provideWeiXinLoginView(){
        return weiXinLoginView;
    }

    @Provides
    LoginContract.BindMobileView provideBindMobileView(){
        return bindMobileView;
    }

    @Provides
    LoginContract.RegisterLoginView provideRegisterLoginView(){
        return registerLoginView;
    }


}
