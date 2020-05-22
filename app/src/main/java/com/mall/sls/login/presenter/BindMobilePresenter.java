package com.mall.sls.login.presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.data.request.MobileRequest;
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
                .flatMap(new RxRemoteDataParse<String>())
                .compose(new RxSchedulerTransformer<String>())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String vCode) throws Exception {
                        bindMobileView.dismissLoading();
                        bindMobileView.renderVCode(vCode);
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
