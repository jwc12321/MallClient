package com.mall.sls.login;


import com.mall.sls.BasePresenter;
import com.mall.sls.BaseView;
import com.mall.sls.common.unit.VerifyManager;
import com.mall.sls.data.entity.AppUrlInfo;
import com.mall.sls.data.entity.TokenInfo;

public interface LoginContract {

    interface LoginPresenter extends BasePresenter {
        void loginIn(String deviceId, String deviceOsVersion, String devicePlatform, String mobile, String code,String invitationCode,String deviceName);
        void sendVCode(String mobile);
        void getAppUrlInfo();
        void oneClickLogin(String accessCode, String deviceId, String deviceOsVersion, String devicePlatform,String invitationCode,String deviceName);
        void getInvitationCode();
    }

    interface LoginView extends BaseView<LoginPresenter> {
        void renderLoginIn(TokenInfo tokenInfo);
        void renderVCode();
        void renderAppUrlInfo(AppUrlInfo appUrlInfo);
        void renderInvitationCode(String invitationCode);
    }

    interface WeiXinLoginPresenter extends BasePresenter{
        void weixinLogin(String deviceId, String deviceOsVersion, String devicePlatform,String wxCode,String deviceName);
        void oneClickLogin(String accessCode, String deviceId, String deviceOsVersion, String devicePlatform,String invitationCode,String deviceName);
        void getAppUrlInfo();
        void getInvitationOpen();
        void bindOneClickLogin(String deviceId, String deviceOsVersion, String devicePlatform, String accessCode, String invitationCode, String unionId,String deviceName);
    }

    interface WeiXinLoginView extends BaseView<WeiXinLoginPresenter>{
        void renderWeixinLogin(TokenInfo tokenInfo);
        void renderLoginIn(TokenInfo tokenInfo);
        void renderAppUrlInfo(AppUrlInfo appUrlInfo);
        void renderInvitationOpen(Boolean isBoolean);
    }

    interface BindMobilePresenter extends BasePresenter{
        void sendVCode(String mobile);
        void getInvitationOpen();
        void bindSmsCodeLogin(String deviceId, String deviceOsVersion, String devicePlatform, String mobile, String code, String invitationCode, String unionId,String deviceName);
    }

    interface BindMobileView extends BaseView<BindMobilePresenter>{
        void renderVCode();
        void renderInvitationOpen(Boolean isBoolean);
        void renderLoginIn(TokenInfo tokenInfo);
    }

    interface RegisterLoginPresenter extends BasePresenter{
        void getInvitationCode();
        void bindSmsCodeLogin(String deviceId, String deviceOsVersion, String devicePlatform, String mobile, String code, String invitationCode, String unionId,String deviceName);
        void bindOneClickLogin(String deviceId, String deviceOsVersion, String devicePlatform, String accessCode, String invitationCode, String unionId,String deviceName);
    }

    interface RegisterLoginView extends BaseView<RegisterLoginPresenter>{
        void renderInvitationCode(String invitationCode);
        void renderLoginIn(TokenInfo tokenInfo);

    }

}
