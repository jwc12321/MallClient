package com.mall.sls.homepage.presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.BaoFuPayInfo;
import com.mall.sls.data.entity.ConfirmCartOrderDetail;
import com.mall.sls.data.entity.OrderSubmitInfo;
import com.mall.sls.data.entity.WXPaySignResponse;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.data.request.CartGeneralCheckedRequest;
import com.mall.sls.data.request.CartOrderSubmitRequest;
import com.mall.sls.data.request.OrderPayRequest;
import com.mall.sls.data.request.PayRequest;
import com.mall.sls.homepage.HomepageContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/6/24.
 * 描述：
 */
public class CartConfirmOrderPresenter implements HomepageContract.CartConfirmOrderPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private HomepageContract.CartConfirmOrderView cartConfirmOrderView;
    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    @Inject
    public CartConfirmOrderPresenter(RestApiService restApiService, HomepageContract.CartConfirmOrderView cartConfirmOrderView) {
        this.restApiService = restApiService;
        this.cartConfirmOrderView = cartConfirmOrderView;
    }
    @Inject
    public void setupListener() {
        cartConfirmOrderView.setPresenter(this);
    }


    @Override
    public void cartGeneralChecked(String addressId, List<String> ids, String userCouponId) {
        cartConfirmOrderView.showLoading(StaticData.PROCESSING);
        CartGeneralCheckedRequest request = new CartGeneralCheckedRequest(addressId,ids, userCouponId);
        String sign = SignUnit.signPost(RequestUrl.CART_GENERAL_CHECKED, gson.toJson(request));
        Disposable disposable = restApiService.cartGeneralChecked(sign, request)
                .flatMap(new RxRemoteDataParse<ConfirmCartOrderDetail>())
                .compose(new RxSchedulerTransformer<ConfirmCartOrderDetail>())
                .subscribe(new Consumer<ConfirmCartOrderDetail>() {
                    @Override
                    public void accept(ConfirmCartOrderDetail confirmCartOrderDetail) throws Exception {
                        cartConfirmOrderView.dismissLoading();
                        cartConfirmOrderView.renderCartGeneralChecked(confirmCartOrderDetail);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        cartConfirmOrderView.dismissLoading();
                        cartConfirmOrderView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void cartOrderSubmit(String addressId, List<String> ids, String userCouponId, String message) {
        cartConfirmOrderView.showLoading(StaticData.PROCESSING);
        CartOrderSubmitRequest request = new CartOrderSubmitRequest(addressId,ids, userCouponId,message,"android");
        String sign = SignUnit.signPost(RequestUrl.ORDER_GENERAL_SUBMIT, gson.toJson(request));
        Disposable disposable = restApiService.cartOrderSubmit(sign, request)
                .flatMap(new RxRemoteDataParse<OrderSubmitInfo>())
                .compose(new RxSchedulerTransformer<OrderSubmitInfo>())
                .subscribe(new Consumer<OrderSubmitInfo>() {
                    @Override
                    public void accept(OrderSubmitInfo orderSubmitInfo) throws Exception {
                        cartConfirmOrderView.dismissLoading();
                        cartConfirmOrderView.renderCartOrderSubmit(orderSubmitInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        cartConfirmOrderView.dismissLoading();
                        cartConfirmOrderView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getWxPay(String orderId, String orderType, String paymentMethod) {
        cartConfirmOrderView.dismissLoading();
        cartConfirmOrderView.showLoading(StaticData.PROCESSING);
        PayRequest request=new PayRequest(orderId,orderType,paymentMethod);
        String sign= SignUnit.signPost(RequestUrl.BEGIN_PAY,gson.toJson(request));
        Disposable disposable = restApiService.getWxPay(sign,request)
                .flatMap(new RxRemoteDataParse<WXPaySignResponse>())
                .compose(new RxSchedulerTransformer<WXPaySignResponse>())
                .subscribe(new Consumer<WXPaySignResponse>() {
                    @Override
                    public void accept(WXPaySignResponse wxPaySignResponse) throws Exception {
                        cartConfirmOrderView.dismissLoading();
                        cartConfirmOrderView.renderWxPay(wxPaySignResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        cartConfirmOrderView.dismissLoading();
                        cartConfirmOrderView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getAliPay(String orderId, String orderType, String paymentMethod) {
        cartConfirmOrderView.dismissLoading();
        cartConfirmOrderView.showLoading(StaticData.PROCESSING);
        PayRequest request=new PayRequest(orderId,orderType,paymentMethod);
        String sign= SignUnit.signPost(RequestUrl.BEGIN_PAY,gson.toJson(request));
        Disposable disposable = restApiService.getAliPay(sign,request)
                .flatMap(new RxRemoteDataParse<String>())
                .compose(new RxSchedulerTransformer<String>())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String aliPayStr) throws Exception {
                        cartConfirmOrderView.dismissLoading();
                        cartConfirmOrderView.renderAliPay(aliPayStr);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        cartConfirmOrderView.dismissLoading();
                        cartConfirmOrderView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getBaoFuPay(String orderId, String orderType, String paymentMethod) {
        cartConfirmOrderView.dismissLoading();
        cartConfirmOrderView.showLoading(StaticData.PROCESSING);
        PayRequest request=new PayRequest(orderId,orderType,paymentMethod);
        String sign= SignUnit.signPost(RequestUrl.BEGIN_PAY,gson.toJson(request));
        Disposable disposable = restApiService.getBaoFuPay(sign,request)
                .flatMap(new RxRemoteDataParse<BaoFuPayInfo>())
                .compose(new RxSchedulerTransformer<BaoFuPayInfo>())
                .subscribe(new Consumer<BaoFuPayInfo>() {
                    @Override
                    public void accept(BaoFuPayInfo baoFuPayInfo) throws Exception {
                        cartConfirmOrderView.dismissLoading();
                        cartConfirmOrderView.renderBaoFuPay(baoFuPayInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        cartConfirmOrderView.dismissLoading();
                        cartConfirmOrderView.showError(throwable);
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
