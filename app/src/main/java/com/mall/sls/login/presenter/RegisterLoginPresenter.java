package com.mall.sls.login.presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.TokenInfo;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.data.request.OnClickBindRequest;
import com.mall.sls.data.request.SmsCodeBindRequest;
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
public class RegisterLoginPresenter implements LoginContract.RegisterLoginPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private LoginContract.RegisterLoginView registerLoginView;
    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    @Inject
    public RegisterLoginPresenter(RestApiService restApiService, LoginContract.RegisterLoginView registerLoginView) {
        this.restApiService = restApiService;
        this.registerLoginView = registerLoginView;
    }

    @Inject
    public void setupListener() {
        registerLoginView.setPresenter(this);
    }

    @Override
    public void getInvitationCode() {
        registerLoginView.showLoading(StaticData.LOADING);
        String queryString="null";
        String sign= SignUnit.signGet(RequestUrl.INVITATION_CODE_URL,queryString);
        Disposable disposable = restApiService.getInvitationCode(sign)
                .flatMap(new RxRemoteDataParse<String>())
                .compose(new RxSchedulerTransformer<String>())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String invitationCode) throws Exception {
                        registerLoginView.dismissLoading();
                        registerLoginView.renderInvitationCode(invitationCode);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        registerLoginView.dismissLoading();
                        registerLoginView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void bindSmsCodeLogin(String deviceId, String deviceOsVersion, String devicePlatform, String mobile, String code, String invitationCode, String unionId) {
        registerLoginView.showLoading(StaticData.PROCESSING);
        SmsCodeBindRequest request=new SmsCodeBindRequest(deviceId,deviceOsVersion,devicePlatform,mobile,code,invitationCode,unionId);
        String sign= SignUnit.signPost(RequestUrl.BIND_SMS_CODE_LOGIN,gson.toJson(request));
        Disposable disposable = restApiService.bindSmsCodeLogin(sign,request)
                .flatMap(new RxRemoteDataParse<TokenInfo>())
                .compose(new RxSchedulerTransformer<TokenInfo>())
                .subscribe(new Consumer<TokenInfo>() {
                    @Override
                    public void accept(TokenInfo tokenInfo) throws Exception {
                        registerLoginView.dismissLoading();
                        registerLoginView.renderLoginIn(tokenInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        registerLoginView.dismissLoading();
                        registerLoginView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void bindOneClickLogin(String deviceId, String deviceOsVersion, String devicePlatform, String accessCode, String invitationCode, String unionId) {
        registerLoginView.showLoading(StaticData.PROCESSING);
        OnClickBindRequest request=new OnClickBindRequest(deviceId,deviceOsVersion,devicePlatform,accessCode,invitationCode,unionId);
        String sign= SignUnit.signPost(RequestUrl.BIND_ONE_CLICK,gson.toJson(request));
        Disposable disposable = restApiService.bindOneClickLogin(sign,request)
                .flatMap(new RxRemoteDataParse<TokenInfo>())
                .compose(new RxSchedulerTransformer<TokenInfo>())
                .subscribe(new Consumer<TokenInfo>() {
                    @Override
                    public void accept(TokenInfo tokenInfo) throws Exception {
                        registerLoginView.dismissLoading();
                        registerLoginView.renderLoginIn(tokenInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        registerLoginView.dismissLoading();
                        registerLoginView.showError(throwable);
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
