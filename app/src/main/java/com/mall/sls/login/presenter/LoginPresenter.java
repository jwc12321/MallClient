package com.mall.sls.login.presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mall.sls.BuildConfig;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.AppUrlInfo;
import com.mall.sls.data.entity.OneClickInfo;
import com.mall.sls.data.entity.TokenInfo;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.data.request.LoginRequest;
import com.mall.sls.data.request.MobileRequest;
import com.mall.sls.data.request.OneClickLoginRequest;
import com.mall.sls.login.LoginContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class LoginPresenter implements LoginContract.LoginPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private LoginContract.LoginView loginView;
    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    @Inject
    public LoginPresenter(RestApiService restApiService, LoginContract.LoginView loginView) {
        this.restApiService = restApiService;
        this.loginView = loginView;
    }

    @Inject
    public void setupListener() {
        loginView.setPresenter(this);
    }


    @Override
    public void getAppUrlInfo() {
        loginView.showLoading(StaticData.LOADING);
        String queryString="version="+BuildConfig.VERSION_NAME;
        String sign=SignUnit.signGet(RequestUrl.APP_URL_INFO_URL,queryString);
        Disposable disposable = restApiService.getAppUrlInfo(sign,BuildConfig.VERSION_NAME)
                .flatMap(new RxRemoteDataParse<AppUrlInfo>())
                .compose(new RxSchedulerTransformer<AppUrlInfo>())
                .subscribe(new Consumer<AppUrlInfo>() {
                    @Override
                    public void accept(AppUrlInfo appUrlInfo) throws Exception {
                        loginView.dismissLoading();
                        loginView.renderAppUrlInfo(appUrlInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        loginView.dismissLoading();
                        loginView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void oneClickLogin(String accessCode, String deviceId, String deviceOsVersion, String devicePlatform) {
        OneClickLoginRequest request=new OneClickLoginRequest(accessCode,deviceId,deviceOsVersion,devicePlatform);
        String sign= SignUnit.signPost(RequestUrl.ONE_CLICK_LOGIN_URL,gson.toJson(request));
        Disposable disposable = restApiService.oneClickLogin(sign,request)
                .flatMap(new RxRemoteDataParse<OneClickInfo>())
                .compose(new RxSchedulerTransformer<OneClickInfo>())
                .subscribe(new Consumer<OneClickInfo>() {
                    @Override
                    public void accept(OneClickInfo oneClickInfo) throws Exception {
                        loginView.renderOneClickLogin(oneClickInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        loginView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void loginIn(String deviceId, String deviceOsVersion, String devicePlatform, String mobile, String code) {
        loginView.showLoading(StaticData.PROCESSING);
        LoginRequest request=new LoginRequest(deviceId,deviceOsVersion,devicePlatform,mobile,code);
        String sign= SignUnit.signPost(RequestUrl.LOGIN_IN_URL,gson.toJson(request));
        Disposable disposable = restApiService.loginIn(sign,request)
                .flatMap(new RxRemoteDataParse<TokenInfo>())
                .compose(new RxSchedulerTransformer<TokenInfo>())
                .subscribe(new Consumer<TokenInfo>() {
                    @Override
                    public void accept(TokenInfo tokenInfo) throws Exception {
                        loginView.dismissLoading();
                        loginView.renderLoginIn(tokenInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        loginView.dismissLoading();
                        loginView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void sendVCode(String mobile) {
        loginView.showLoading(StaticData.PROCESSING);
        MobileRequest request=new MobileRequest(mobile);
        String sign= SignUnit.signPost(RequestUrl.SEND_CODE_V_URL,gson.toJson(request));
        Disposable disposable = restApiService.sendCodeV(sign,request)
                .flatMap(new RxRemoteDataParse<String>())
                .compose(new RxSchedulerTransformer<String>())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String vCode) throws Exception {
                        loginView.dismissLoading();
                        loginView.renderVCode(vCode);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        loginView.dismissLoading();
                        loginView.showError(throwable);
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
