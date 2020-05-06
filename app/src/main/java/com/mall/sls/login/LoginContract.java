package com.mall.sls.login;


import com.mall.sls.BasePresenter;
import com.mall.sls.BaseView;
import com.mall.sls.data.entity.AppUrlInfo;
import com.mall.sls.data.entity.OneClickInfo;
import com.mall.sls.data.entity.TokenInfo;

public interface LoginContract {

    interface LoginPresenter extends BasePresenter {
        void loginIn(String deviceId, String deviceOsVersion, String devicePlatform, String mobile, String code);
        void sendVCode(String mobile);
        void getAppUrlInfo();
        void oneClickLogin(String accessCode, String deviceId, String deviceOsVersion, String devicePlatform);
    }

    interface LoginView extends BaseView<LoginPresenter> {
        void renderLoginIn(TokenInfo tokenInfo);
        void renderVCode(String vCode);
        void renderAppUrlInfo(AppUrlInfo appUrlInfo);
        void renderOneClickLogin(OneClickInfo oneClickInfo);
    }


}
