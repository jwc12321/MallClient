package com.mall.sls.certify.presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mall.sls.certify.CertifyContract;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.WXPaySignResponse;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.data.request.UserPayDtoRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/5/18.
 * 描述：
 */
public class CertifyPayPresenter implements CertifyContract.CertifyPayPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private CertifyContract.CertifyPayView certifyPayView;
    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    @Inject
    public CertifyPayPresenter(RestApiService restApiService, CertifyContract.CertifyPayView certifyPayView) {
        this.restApiService = restApiService;
        this.certifyPayView = certifyPayView;
    }

    @Inject
    public void setupListener() {
        certifyPayView.setPresenter(this);
    }

    @Override
    public void aliPay(String orderType, String payType) {
        certifyPayView.showLoading(StaticData.PROCESSING);
        UserPayDtoRequest request=new UserPayDtoRequest(orderType,payType);
        String sign= SignUnit.signPost(RequestUrl.ALIPAY_URL,gson.toJson(request));
        Disposable disposable = restApiService.alipayMember(sign,request)
                .flatMap(new RxRemoteDataParse<String>())
                .compose(new RxSchedulerTransformer<String>())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String alipayStr) throws Exception {
                        certifyPayView.dismissLoading();
                        certifyPayView.renderAliPay(alipayStr);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        certifyPayView.dismissLoading();
                        certifyPayView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void wxPay(String orderType, String payType) {
        certifyPayView.showLoading(StaticData.PROCESSING);
        UserPayDtoRequest request=new UserPayDtoRequest(orderType,payType);
        String sign= SignUnit.signPost(RequestUrl.USER_PAY_WX,gson.toJson(request));
        Disposable disposable = restApiService.wxPayMember(sign,request)
                .flatMap(new RxRemoteDataParse<WXPaySignResponse>())
                .compose(new RxSchedulerTransformer<WXPaySignResponse>())
                .subscribe(new Consumer<WXPaySignResponse>() {
                    @Override
                    public void accept(WXPaySignResponse wxPaySignResponse) throws Exception {
                        certifyPayView.dismissLoading();
                        certifyPayView.renderWxPay(wxPaySignResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        certifyPayView.dismissLoading();
                        certifyPayView.showError(throwable);
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
