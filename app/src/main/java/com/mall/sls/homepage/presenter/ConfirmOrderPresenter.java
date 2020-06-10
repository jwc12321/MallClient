package com.mall.sls.homepage.presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.ConfirmOrderDetail;
import com.mall.sls.data.entity.OrderSubmitInfo;
import com.mall.sls.data.entity.WXPaySignResponse;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.data.request.OrderPayRequest;
import com.mall.sls.data.request.OrderSubmitRequest;
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
    public void cartCheckout(String addressId, String cartId, String couponId, String userCouponId) {
        confirmOrderView.showLoading(StaticData.PROCESSING);
        String queryString="addressId="+addressId+"&cartId="+cartId+"&couponId="+couponId+"&userCouponId="+userCouponId;
        String sign= SignUnit.signGet(RequestUrl.CART_CHECKOUT_URL,queryString);
        Disposable disposable = restApiService.cartCheckout(sign,addressId,cartId,couponId,userCouponId)
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
    public void orderSubmit(String addressId, String cartId, String couponId, String userCouponId, String message) {
        confirmOrderView.showLoading(StaticData.PROCESSING);
        OrderSubmitRequest request=new OrderSubmitRequest(addressId,cartId,couponId,userCouponId,message,"android");
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
    public void orderAliPay(String orderId,String type) {
        confirmOrderView.dismissLoading();
        confirmOrderView.showLoading(StaticData.PROCESSING);
        OrderPayRequest request=new OrderPayRequest(orderId,type);
        String sign= SignUnit.signPost(RequestUrl.ORDER_ALIPAY,gson.toJson(request));
        Disposable disposable = restApiService.orderAliPay(sign,request)
                .flatMap(new RxRemoteDataParse<String>())
                .compose(new RxSchedulerTransformer<String>())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String alipayStr) throws Exception {
                        confirmOrderView.dismissLoading();
                        confirmOrderView.renderOrderAliPay(alipayStr);
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
    public void orderWxPay(String orderId, String type) {
        confirmOrderView.dismissLoading();
        confirmOrderView.showLoading(StaticData.PROCESSING);
        OrderPayRequest request=new OrderPayRequest(orderId,type);
        String sign= SignUnit.signPost(RequestUrl.ORDER_WX_PAY,gson.toJson(request));
        Disposable disposable = restApiService.orderWxPay(sign,request)
                .flatMap(new RxRemoteDataParse<WXPaySignResponse>())
                .compose(new RxSchedulerTransformer<WXPaySignResponse>())
                .subscribe(new Consumer<WXPaySignResponse>() {
                    @Override
                    public void accept(WXPaySignResponse wxPaySignResponse) throws Exception {
                        confirmOrderView.dismissLoading();
                        confirmOrderView.renderOrderWxPay(wxPaySignResponse);
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
