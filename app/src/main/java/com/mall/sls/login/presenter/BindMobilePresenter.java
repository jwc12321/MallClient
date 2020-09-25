package com.mall.sls.login.presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.Ignore;
import com.mall.sls.data.entity.TokenInfo;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.data.request.MobileRequest;
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
public class BindMobilePresenter implements LoginContract.BindMobilePresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private LoginContract.BindMobileView bindMobileView;
    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    @Inject
    public BindMobilePresenter(RestApiService restApiService, LoginContract.BindMobileView bindMobileView) {
        this.restApiService = restApiService;
        this.bindMobileView = bindMobileView;
    }

    @Inject
    public void setupListener() {
        bindMobileView.setPresenter(this);
    }

    @Override
    public void sendVCode(String mobile) {
        bindMobileView.showLoading(StaticData.PROCESSING);
        MobileRequest request=new MobileRequest(mobile);
        String sign= SignUnit.signPost(RequestUrl.SEND_CODE_V_URL,gson.toJson(request));
        Disposable disposable = restApiService.sendCodeV(sign,request)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        bindMobileView.dismissLoading();
                        bindMobileView.renderVCode();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        bindMobileView.dismissLoading();
                        bindMobileView.showError(throwable);
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
                        bindMobileView.renderInvitationOpen(isBoolean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        bindMobileView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }


    @Override
    public void bindSmsCodeLogin(String deviceId, String deviceOsVersion, String devicePlatform, String mobile, String code, String invitationCode, String unionId,String deviceName) {
        bindMobileView.showLoading(StaticData.PROCESSING);
        SmsCodeBindRequest request=new SmsCodeBindRequest(deviceId,deviceOsVersion,devicePlatform,mobile,code,invitationCode,unionId,deviceName);
        String sign= SignUnit.signPost(RequestUrl.BIND_SMS_CODE_LOGIN,gson.toJson(request));
        Disposable disposable = restApiService.bindSmsCodeLogin(sign,request)
                .flatMap(new RxRemoteDataParse<TokenInfo>())
                .compose(new RxSchedulerTransformer<TokenInfo>())
                .subscribe(new Consumer<TokenInfo>() {
                    @Override
                    public void accept(TokenInfo tokenInfo) throws Exception {
                        bindMobileView.dismissLoading();
                        bindMobileView.renderLoginIn(tokenInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        bindMobileView.dismissLoading();
                        bindMobileView.showError(throwable);
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
