package com.mall.sls.login;


import com.mall.sls.BasePresenter;
import com.mall.sls.BaseView;
import com.mall.sls.common.unit.VerifyManager;
import com.mall.sls.data.entity.AppUrlInfo;
import com.mall.sls.data.entity.TokenInfo;

public interface LoginContract {

    interface LoginPresenter extends BasePresenter {
        void loginIn(String deviceId, String deviceOsVersion, String devicePlatform, String mobile, String code,String invitationCode);
        void sendVCode(String mobile);
        void getAppUrlInfo();
        void oneClickLogin(String accessCode, String deviceId, String deviceOsVersion, String devicePlatform,String invitationCode);
        void getInvitationCode();
    }

    interface LoginView extends BaseView<LoginPresenter> {
        void renderLoginIn(TokenInfo tokenInfo);
        void renderVCode(String vCode);
        void renderAppUrlInfo(AppUrlInfo appUrlInfo);
        void renderInvitationCode(String invitationCode);
    }

    interface WeiXinLoginPresenter extends BasePresenter{
        void weixinLogin(String deviceId, String deviceOsVersion, String devicePlatform,String wxCode);
        void oneClickLogin(String accessCode, String deviceId, String deviceOsVersion, String devicePlatform,String invitationCode);
    }

    interface WeiXinLoginView extends BaseView<WeiXinLoginPresenter>{
        void renderWeixinLogin(TokenInfo tokenInfo);
        void renderLoginIn(TokenInfo tokenInfo);
    }

    interface BindMobilePresenter extends BasePresenter{
        void sendVCode(String mobile);
    }

    interface BindMobileView extends BaseView<BindMobilePresenter>{
        void renderVCode(String vCode);
    }

    interface RegisterLoginPresenter extends BasePresenter{
        void getInvitationCode();
        void bindSmsCodeLogin(String deviceId, String deviceOsVersion, String devicePlatform, String mobile, String code, String invitationCode, String unionId);
        void bindOneClickLogin(String deviceId, String deviceOsVersion, String devicePlatform, String accessCode, String invitationCode, String unionId);
    }

    interface RegisterLoginView extends BaseView<RegisterLoginPresenter>{
        void renderInvitationCode(String invitationCode);
        void renderLoginIn(TokenInfo tokenInfo);

    }

}
