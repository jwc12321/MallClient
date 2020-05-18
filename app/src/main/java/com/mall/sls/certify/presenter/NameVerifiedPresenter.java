package com.mall.sls.certify.presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mall.sls.certify.CertifyContract;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.data.request.CertifyIdRequest;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class NameVerifiedPresenter implements CertifyContract.NameVerifiedPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private CertifyContract.NameVerifiedView nameVerifiedView;
    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    @Inject
    public NameVerifiedPresenter(RestApiService restApiService, CertifyContract.NameVerifiedView nameVerifiedView) {
        this.restApiService = restApiService;
        this.nameVerifiedView = nameVerifiedView;
    }

    @Inject
    public void setupListener() {
        nameVerifiedView.setPresenter(this);
    }

    @Override
    public void getCertifyId(String certName, String certNo, String metaInfo) {
        nameVerifiedView.showLoading(StaticData.PROCESSING);
        CertifyIdRequest request=new CertifyIdRequest(certName,certNo,metaInfo);
        String sign= SignUnit.signPost(RequestUrl.GET_CERTIFY_ID_URL,gson.toJson(request));
        Disposable disposable = restApiService.getCertifyId(sign,request)
                .flatMap(new RxRemoteDataParse<String>())
                .compose(new RxSchedulerTransformer<String>())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String token) throws Exception {
                        nameVerifiedView.dismissLoading();
                        nameVerifiedView.renderCertifyId(token);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        nameVerifiedView.dismissLoading();
                        nameVerifiedView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getUsersRpStatus() {
        nameVerifiedView.showLoading(StaticData.LOADING);
        String sign= SignUnit.signGet(RequestUrl.USER_RP_STATUS_URL,"null");
        Disposable disposable = restApiService.getUsersRpStatus(sign)
                .flatMap(new RxRemoteDataParse<Boolean>())
                .compose(new RxSchedulerTransformer<Boolean>())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isBoolean) throws Exception {
                        nameVerifiedView.renderUsesRpStatus(isBoolean);
                        nameVerifiedView.dismissLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        nameVerifiedView.showError(throwable);
                        nameVerifiedView.dismissLoading();
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
