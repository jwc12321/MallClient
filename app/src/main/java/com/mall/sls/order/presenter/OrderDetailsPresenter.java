package com.mall.sls.order.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.AliPay;
import com.mall.sls.data.entity.BaoFuPay;
import com.mall.sls.data.entity.BaoFuPayInfo;
import com.mall.sls.data.entity.GoodsOrderDetails;
import com.mall.sls.data.entity.Ignore;
import com.mall.sls.data.entity.InvitationCodeInfo;
import com.mall.sls.data.entity.OrderAddCartInfo;
import com.mall.sls.data.entity.OrderInfo;
import com.mall.sls.data.entity.WXPaySignResponse;
import com.mall.sls.data.entity.WxPay;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.data.request.OrderAddCartRequest;
import com.mall.sls.data.request.OrderIdRequest;
import com.mall.sls.data.request.OrderPayRequest;
import com.mall.sls.data.request.OrderRequest;
import com.mall.sls.data.request.PayRequest;
import com.mall.sls.order.OrderContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/5/18.
 * 描述：
 */
public class OrderDetailsPresenter implements OrderContract.OrderDetailsPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private OrderContract.OrderDetailsView orderDetailsView;
    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    @Inject
    public OrderDetailsPresenter(RestApiService restApiService, OrderContract.OrderDetailsView orderDetailsView) {
        this.restApiService = restApiService;
        this.orderDetailsView = orderDetailsView;
    }

    @Inject
    public void setupListener() {
        orderDetailsView.setPresenter(this);
    }

    @Override
    public void getOrderDetails(String orderId) {
        orderDetailsView.showLoading(StaticData.LOADING);
        String queryString="orderId="+orderId;
        String sign= SignUnit.signGet(RequestUrl.ORDER_DETAILS,queryString);
        Disposable disposable = restApiService.getGoodsOrderDetails(sign,orderId)
                .flatMap(new RxRemoteDataParse<GoodsOrderDetails>())
                .compose(new RxSchedulerTransformer<GoodsOrderDetails>())
                .subscribe(new Consumer<GoodsOrderDetails>() {
                    @Override
                    public void accept(GoodsOrderDetails goodsOrderDetails) throws Exception {
                        orderDetailsView.dismissLoading();
                        orderDetailsView.renderOrderDetails(goodsOrderDetails);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        orderDetailsView.dismissLoading();
                        orderDetailsView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void cancelOrder(String orderId) {
        orderDetailsView.showLoading(StaticData.PROCESSING);
        OrderRequest request=new OrderRequest(orderId);
        String sign= SignUnit.signPost(RequestUrl.ORDER_CANCEL,gson.toJson(request));
        Disposable disposable = restApiService.cancelOrder(sign,request)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        orderDetailsView.dismissLoading();
                        orderDetailsView.renderCancelOrder();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        orderDetailsView.dismissLoading();
                        orderDetailsView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getInvitationCodeInfo() {
        String queryString = "null";
        String sign = SignUnit.signGet(RequestUrl.WX_INVITATION_CODE, queryString);
        Disposable disposable = restApiService.getInvitationCodeInfo(sign)
                .flatMap(new RxRemoteDataParse<InvitationCodeInfo>())
                .compose(new RxSchedulerTransformer<InvitationCodeInfo>())
                .subscribe(new Consumer<InvitationCodeInfo>() {
                    @Override
                    public void accept(InvitationCodeInfo invitationCodeInfo) throws Exception {
                        orderDetailsView.renderInvitationCodeInfo(invitationCodeInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        orderDetailsView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void addCartBatch(String orderId) {
        orderDetailsView.showLoading(StaticData.PROCESSING);
        OrderIdRequest request=new OrderIdRequest(orderId);
        String sign= SignUnit.signPost(RequestUrl.ADD_CART_BATCH,gson.toJson(request));
        Disposable disposable = restApiService.addCartBatch(sign,request)
                .flatMap(new RxRemoteDataParse<Boolean>())
                .compose(new RxSchedulerTransformer<Boolean>())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isBoolean) throws Exception {
                        orderDetailsView.dismissLoading();
                        orderDetailsView.renderAddCartBatch(isBoolean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        orderDetailsView.dismissLoading();
                        orderDetailsView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void orderAddCart(String orderId, Boolean forceAdd) {
        orderDetailsView.showLoading(StaticData.PROCESSING);
        OrderAddCartRequest request=new OrderAddCartRequest(orderId,forceAdd);
        String sign= SignUnit.signPost(RequestUrl.ORDER_ADD_CART,gson.toJson(request));
        Disposable disposable = restApiService.orderAddCart(sign,request)
                .flatMap(new RxRemoteDataParse<OrderAddCartInfo>())
                .compose(new RxSchedulerTransformer<OrderAddCartInfo>())
                .subscribe(new Consumer<OrderAddCartInfo>() {
                    @Override
                    public void accept(OrderAddCartInfo orderAddCartInfo) throws Exception {
                        orderDetailsView.dismissLoading();
                        orderDetailsView.renderOrderAddCart(orderAddCartInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        orderDetailsView.dismissLoading();
                        orderDetailsView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getWxPay(String orderId, String orderType, String paymentMethod) {
        orderDetailsView.showLoading(StaticData.PROCESSING);
        PayRequest request=new PayRequest(orderId,orderType,paymentMethod);
        String sign= SignUnit.signPost(RequestUrl.BEGIN_PAY,gson.toJson(request));
        Disposable disposable = restApiService.getWxPay(sign,request)
                .flatMap(new RxRemoteDataParse<WxPay>())
                .compose(new RxSchedulerTransformer<WxPay>())
                .subscribe(new Consumer<WxPay>() {
                    @Override
                    public void accept(WxPay wxPay) throws Exception {
                        orderDetailsView.dismissLoading();
                        orderDetailsView.renderWxPay(wxPay);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        orderDetailsView.dismissLoading();
                        orderDetailsView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getAliPay(String orderId, String orderType, String paymentMethod) {
        orderDetailsView.showLoading(StaticData.PROCESSING);
        PayRequest request=new PayRequest(orderId,orderType,paymentMethod);
        String sign= SignUnit.signPost(RequestUrl.BEGIN_PAY,gson.toJson(request));
        Disposable disposable = restApiService.getAliPay(sign,request)
                .flatMap(new RxRemoteDataParse<AliPay>())
                .compose(new RxSchedulerTransformer<AliPay>())
                .subscribe(new Consumer<AliPay>() {
                    @Override
                    public void accept(AliPay aliPay) throws Exception {
                        orderDetailsView.dismissLoading();
                        orderDetailsView.renderAliPay(aliPay);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        orderDetailsView.dismissLoading();
                        orderDetailsView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getBaoFuPay(String orderId, String orderType, String paymentMethod) {
        orderDetailsView.showLoading(StaticData.PROCESSING);
        PayRequest request=new PayRequest(orderId,orderType,paymentMethod);
        String sign= SignUnit.signPost(RequestUrl.BEGIN_PAY,gson.toJson(request));
        Disposable disposable = restApiService.getBaoFuPay(sign,request)
                .flatMap(new RxRemoteDataParse<BaoFuPay>())
                .compose(new RxSchedulerTransformer<BaoFuPay>())
                .subscribe(new Consumer<BaoFuPay>() {
                    @Override
                    public void accept(BaoFuPay baoFuPay) throws Exception {
                        orderDetailsView.dismissLoading();
                        orderDetailsView.renderBaoFuPay(baoFuPay);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        orderDetailsView.dismissLoading();
                        orderDetailsView.showError(throwable);
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
