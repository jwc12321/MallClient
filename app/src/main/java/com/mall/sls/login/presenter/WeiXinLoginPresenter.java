package com.mall.sls.login.presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mall.sls.BuildConfig;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.AppUrlInfo;
import com.mall.sls.data.entity.TokenInfo;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.data.request.LoginRequest;
import com.mall.sls.data.request.OnClickBindRequest;
import com.mall.sls.data.request.OneClickLoginRequest;
import com.mall.sls.data.request.WeiXinLoginRequest;
import com.mall.sls.login.LoginContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/5/22.
 * 描述：
 */
public class WeiXinLoginPresenter implements LoginContract.WeiXinLoginPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private LoginContract.WeiXinLoginView weiXinLoginView;
    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    @Inject
    public WeiXinLoginPresenter(RestApiService restApiService, LoginContract.WeiXinLoginView weiXinLoginView) {
        this.restApiService = restApiService;
        this.weiXinLoginView = weiXinLoginView;
    }

    @Inject
    public void setupListener() {
        weiXinLoginView.setPresenter(this);
    }

    @Override
    public void weixinLogin(String deviceId, String deviceOsVersion, String devicePlatform, String wxCode,String deviceName) {
        weiXinLoginView.showLoading(StaticData.PROCESSING);
        WeiXinLoginRequest request=new WeiXinLoginRequest(deviceId,deviceOsVersion,devicePlatform,wxCode,deviceName);
        String sign= SignUnit.signPost(RequestUrl.LOGIN_WEIXIN_URL,gson.toJson(request));
        Disposable disposable = restApiService.weiXinLogin(sign,request)
                .flatMap(new RxRemoteDataParse<TokenInfo>())
                .compose(new RxSchedulerTransformer<TokenInfo>())
                .subscribe(new Consumer<TokenInfo>() {
                    @Override
                    public void accept(TokenInfo tokenInfo) throws Exception {
                        weiXinLoginView.dismissLoading();
                        weiXinLoginView.renderWeixinLogin(tokenInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        weiXinLoginView.dismissLoading();
                        weiXinLoginView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void oneClickLogin(String accessCode, String deviceId, String deviceOsVersion, String devicePlatform,String invitationCode,String deviceName) {
        OneClickLoginRequest request=new OneClickLoginRequest(accessCode,deviceId,deviceOsVersion,devicePlatform,invitationCode,deviceName);
        String sign= SignUnit.signPost(RequestUrl.ONE_CLICK_LOGIN_URL,gson.toJson(request));
        Disposable disposable = restApiService.oneClickLogin(sign,request)
                .flatMap(new RxRemoteDataParse<TokenInfo>())
                .compose(new RxSchedulerTransformer<TokenInfo>())
                .subscribe(new Consumer<TokenInfo>() {
                    @Override
                    public void accept(TokenInfo tokenInfo) throws Exception {
                        weiXinLoginView.renderLoginIn(tokenInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        weiXinLoginView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getAppUrlInfo() {
        weiXinLoginView.showLoading(StaticData.LOADING);
        String queryString="version="+ BuildConfig.VERSION_NAME;
        String sign=SignUnit.signGet(RequestUrl.APP_URL_INFO_URL,queryString);
        Disposable disposable = restApiService.getAppUrlInfo(sign,BuildConfig.VERSION_NAME)
                .flatMap(new RxRemoteDataParse<AppUrlInfo>())
                .compose(new RxSchedulerTransformer<AppUrlInfo>())
                .subscribe(new Consumer<AppUrlInfo>() {
                    @Override
                    public void accept(AppUrlInfo appUrlInfo) throws Exception {
                        weiXinLoginView.dismissLoading();
                        weiXinLoginView.renderAppUrlInfo(appUrlInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        weiXinLoginView.dismissLoading();
                        weiXinLoginView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getInvitationOpen() {
        String queryString="null";
        String sign=SignUnit.signGet(RequestUrl.INVITATION_OPEN,queryString);
        Disposable disposable = restApiService.getInvitationOpen(sign)
                .flatMap(new RxRemoteDataParse<Boolean>())
                .compose(new RxSchedulerTransformer<Boolean>())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isBoolean) throws Exception {
                        weiXinLoginView.renderInvitationOpen(isBoolean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        weiXinLoginView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void bindOneClickLogin(String deviceId, String deviceOsVersion, String devicePlatform, String accessCode, String invitationCode, String unionId,String deviceName) {
        weiXinLoginView.showLoading(StaticData.PROCESSING);
        OnClickBindRequest request=new OnClickBindRequest(deviceId,deviceOsVersion,devicePlatform,accessCode,invitationCode,unionId,deviceName);
        String sign= SignUnit.signPost(RequestUrl.BIND_ONE_CLICK,gson.toJson(request));
        Disposable disposable = restApiService.bindOneClickLogin(sign,request)
                .flatMap(new RxRemoteDataParse<TokenInfo>())
                .compose(new RxSchedulerTransformer<TokenInfo>())
                .subscribe(new Consumer<TokenInfo>() {
                    @Override
                    public void accept(TokenInfo tokenInfo) throws Exception {
                        weiXinLoginView.dismissLoading();
                        weiXinLoginView.renderLoginIn(tokenInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        weiXinLoginView.dismissLoading();
                        weiXinLoginView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }


    @Override
    public void start() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        for (Disposable disposable : mDisposableList) {
            if (!disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }
}
