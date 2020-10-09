package com.mall.sls.certify.presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mall.sls.certify.CertifyContract;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.AliPay;
import com.mall.sls.data.entity.BaoFuPay;
import com.mall.sls.data.entity.PayMethodInfo;
import com.mall.sls.data.entity.WxPay;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.data.request.PayRequest;
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
    public void getPayMethod(String devicePlatform,String orderType) {
        certifyPayView.showLoading(StaticData.LOADING);
        String queryString="devicePlatform="+devicePlatform+"&orderType"+orderType;
        String sign = SignUnit.signGet(RequestUrl.PAY_METHOD, queryString);
        Disposable disposable = restApiService.getPayMethod(sign,devicePlatform,orderType)
                .flatMap(new RxRemoteDataParse<List<PayMethodInfo>>())
                .compose(new RxSchedulerTransformer<List<PayMethodInfo>>())
                .subscribe(new Consumer<List<PayMethodInfo>>() {
                    @Override
                    public void accept(List<PayMethodInfo> payMethods) throws Exception {
                        certifyPayView.dismissLoading();
                        certifyPayView.renderPayMethod(payMethods);
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
    public void getWxPay(String orderId, String orderType, String paymentMethod) {
        certifyPayView.showLoading(StaticData.PROCESSING);
        PayRequest request=new PayRequest(orderId,orderType,paymentMethod);
        String sign= SignUnit.signPost(RequestUrl.BEGIN_PAY,gson.toJson(request));
        Disposable disposable = restApiService.getWxPay(sign,request)
                .flatMap(new RxRemoteDataParse<WxPay>())
                .compose(new RxSchedulerTransformer<WxPay>())
                .subscribe(new Consumer<WxPay>() {
                    @Override
                    public void accept(WxPay wxPay) throws Exception {
                        certifyPayView.dismissLoading();
                        certifyPayView.renderWxPay(wxPay);
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
    public void getAliPay(String orderId, String orderType, String paymentMethod) {
        certifyPayView.showLoading(StaticData.PROCESSING);
        PayRequest request=new PayRequest(orderId,orderType,paymentMethod);
        String sign= SignUnit.signPost(RequestUrl.BEGIN_PAY,gson.toJson(request));
        Disposable disposable = restApiService.getAliPay(sign,request)
                .flatMap(new RxRemoteDataParse<AliPay>())
                .compose(new RxSchedulerTransformer<AliPay>())
                .subscribe(new Consumer<AliPay>() {
                    @Override
                    public void accept(AliPay aliPay) throws Exception {
                        certifyPayView.dismissLoading();
                        certifyPayView.renderAliPay(aliPay);
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
    public void getBaoFuPay(String orderId, String orderType, String paymentMethod) {
        certifyPayView.showLoading(StaticData.PROCESSING);
        PayRequest request=new PayRequest(orderId,orderType,paymentMethod);
        String sign= SignUnit.signPost(RequestUrl.BEGIN_PAY,gson.toJson(request));
        Disposable disposable = restApiService.getBaoFuPay(sign,request)
                .flatMap(new RxRemoteDataParse<BaoFuPay>())
                .compose(new RxSchedulerTransformer<BaoFuPay>())
                .subscribe(new Consumer<BaoFuPay>() {
                    @Override
                    public void accept(BaoFuPay baoFuPay) throws Exception {
                        certifyPayView.dismissLoading();
                        certifyPayView.renderBaoFuPay(baoFuPay);
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
