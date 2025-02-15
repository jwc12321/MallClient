package com.mall.sls.homepage.presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.AiNongPay;
import com.mall.sls.data.entity.AliPay;
import com.mall.sls.data.entity.BaoFuPay;
import com.mall.sls.data.entity.BaoFuPayInfo;
import com.mall.sls.data.entity.ConfirmOrderDetail;
import com.mall.sls.data.entity.OrderSubmitInfo;
import com.mall.sls.data.entity.WXPaySignResponse;
import com.mall.sls.data.entity.WxPay;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.data.request.OrderPayRequest;
import com.mall.sls.data.request.OrderSubmitRequest;
import com.mall.sls.data.request.PayRequest;
import com.mall.sls.data.request.UserPayDtoRequest;
import com.mall.sls.homepage.HomepageContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/5/15.
 * 描述：
 */
public class ConfirmOrderPresenter implements HomepageContract.ConfirmOrderPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private HomepageContract.ConfirmOrderView confirmOrderView;
    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    @Inject
    public ConfirmOrderPresenter(RestApiService restApiService, HomepageContract.ConfirmOrderView confirmOrderView) {
        this.restApiService = restApiService;
        this.confirmOrderView = confirmOrderView;
    }

    @Inject
    public void setupListener() {
        confirmOrderView.setPresenter(this);
    }



    @Override
    public void cartCheckout(String addressId, String cartId, String couponId, String userCouponId,String shipChannel) {
        confirmOrderView.showLoading(StaticData.PROCESSING);
        String queryString="addressId="+addressId+"&cartId="+cartId+"&couponId="+couponId+"&userCouponId="+userCouponId+"&shipChannel="+shipChannel;
        String sign= SignUnit.signGet(RequestUrl.CART_CHECKOUT_URL,queryString);
        Disposable disposable = restApiService.cartCheckout(sign,addressId,cartId,couponId,userCouponId,shipChannel)
                .flatMap(new RxRemoteDataParse<ConfirmOrderDetail>())
                .compose(new RxSchedulerTransformer<ConfirmOrderDetail>())
                .subscribe(new Consumer<ConfirmOrderDetail>() {
                    @Override
                    public void accept(ConfirmOrderDetail confirmOrderDetail) throws Exception {
                        confirmOrderView.dismissLoading();
                        confirmOrderView.renderCartCheckout(confirmOrderDetail);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        confirmOrderView.dismissLoading();
                        confirmOrderView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void orderSubmit(String addressId, String cartId, String couponId, String userCouponId, String message,String shipChannel) {
        confirmOrderView.showLoading(StaticData.PROCESSING);
        OrderSubmitRequest request=new OrderSubmitRequest(addressId,cartId,couponId,userCouponId,message,"android",shipChannel);
        String sign= SignUnit.signPost(RequestUrl.ORDER_UBMIT_URL,gson.toJson(request));
        Disposable disposable = restApiService.orderSubmit(sign,request)
                .flatMap(new RxRemoteDataParse<OrderSubmitInfo>())
                .compose(new RxSchedulerTransformer<OrderSubmitInfo>())
                .subscribe(new Consumer<OrderSubmitInfo>() {
                    @Override
                    public void accept(OrderSubmitInfo orderSubmitInfo) throws Exception {
                        confirmOrderView.dismissLoading();
                        confirmOrderView.renderOrderSubmit(orderSubmitInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        confirmOrderView.dismissLoading();
                        confirmOrderView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }


    @Override
    public void getWxPay(String orderId, String orderType, String paymentMethod) {
        confirmOrderView.dismissLoading();
        confirmOrderView.showLoading(StaticData.PROCESSING);
        PayRequest request=new PayRequest(orderId,orderType,paymentMethod);
        String sign= SignUnit.signPost(RequestUrl.BEGIN_PAY,gson.toJson(request));
        Disposable disposable = restApiService.getWxPay(sign,request)
                .flatMap(new RxRemoteDataParse<WxPay>())
                .compose(new RxSchedulerTransformer<WxPay>())
                .subscribe(new Consumer<WxPay>() {
                    @Override
                    public void accept(WxPay wxPay) throws Exception {
                        confirmOrderView.dismissLoading();
                        confirmOrderView.renderWxPay(wxPay);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        confirmOrderView.dismissLoading();
                        confirmOrderView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getAliPay(String orderId, String orderType, String paymentMethod) {
        confirmOrderView.dismissLoading();
        confirmOrderView.showLoading(StaticData.PROCESSING);
        PayRequest request=new PayRequest(orderId,orderType,paymentMethod);
        String sign= SignUnit.signPost(RequestUrl.BEGIN_PAY,gson.toJson(request));
        Disposable disposable = restApiService.getAliPay(sign,request)
                .flatMap(new RxRemoteDataParse<AliPay>())
                .compose(new RxSchedulerTransformer<AliPay>())
                .subscribe(new Consumer<AliPay>() {
                    @Override
                    public void accept(AliPay aliPay) throws Exception {
                        confirmOrderView.dismissLoading();
                        confirmOrderView.renderAliPay(aliPay);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        confirmOrderView.dismissLoading();
                        confirmOrderView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getBaoFuPay(String orderId, String orderType, String paymentMethod) {
        confirmOrderView.dismissLoading();
        confirmOrderView.showLoading(StaticData.PROCESSING);
        PayRequest request=new PayRequest(orderId,orderType,paymentMethod);
        String sign= SignUnit.signPost(RequestUrl.BEGIN_PAY,gson.toJson(request));
        Disposable disposable = restApiService.getBaoFuPay(sign,request)
                .flatMap(new RxRemoteDataParse<BaoFuPay>())
                .compose(new RxSchedulerTransformer<BaoFuPay>())
                .subscribe(new Consumer<BaoFuPay>() {
                    @Override
                    public void accept(BaoFuPay baoFuPay) throws Exception {
                        confirmOrderView.dismissLoading();
                        confirmOrderView.renderBaoFuPay(baoFuPay);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        confirmOrderView.dismissLoading();
                        confirmOrderView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getAiNongPay(String orderId, String orderType, String paymentMethod) {
        confirmOrderView.dismissLoading();
        confirmOrderView.showLoading(StaticData.PROCESSING);
        PayRequest request=new PayRequest(orderId,orderType,paymentMethod);
        String sign= SignUnit.signPost(RequestUrl.BEGIN_PAY,gson.toJson(request));
        Disposable disposable = restApiService.getAiNongPay(sign,request)
                .flatMap(new RxRemoteDataParse<AiNongPay>())
                .compose(new RxSchedulerTransformer<AiNongPay>())
                .subscribe(new Consumer<AiNongPay>() {
                    @Override
                    public void accept(AiNongPay aiNongPay) throws Exception {
                        confirmOrderView.dismissLoading();
                        confirmOrderView.renderAiNongPay(aiNongPay);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        confirmOrderView.dismissLoading();
                        confirmOrderView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getDeliveryMethod() {
        String queryString = "null";
        String sign = SignUnit.signGet(RequestUrl.COMMON_EXPRESS, queryString);
        Disposable disposable = restApiService.getDeliveryMethod(sign)
                .flatMap(new RxRemoteDataParse<List<String>>())
                .compose(new RxSchedulerTransformer<List<String>>())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> methods) throws Exception {
                        confirmOrderView.renderDeliveryMethod(methods);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        confirmOrderView.showError(throwable);
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
