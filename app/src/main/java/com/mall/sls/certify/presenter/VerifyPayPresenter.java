package com.mall.sls.certify.presenter;

import com.mall.sls.certify.CertifyContract;
import com.mall.sls.data.remote.RestApiService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * @author jwc on 2020/5/18.
 * 描述：
 */
public class VerifyPayPresenter implements CertifyContract.VerifyPayPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private CertifyContract.VerifyPayView verifyPayView;

    @Inject
    public VerifyPayPresenter(RestApiService restApiService, CertifyContract.VerifyPayView verifyPayView) {
        this.restApiService = restApiService;
        this.verifyPayView = verifyPayView;
    }


    @Inject
    public void setupListener() {
        verifyPayView.setPresenter(this);
    }

    @Override
    public void getVerifyPay() {


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
