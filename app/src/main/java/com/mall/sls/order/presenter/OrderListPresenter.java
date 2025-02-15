package com.mall.sls.order.presenter;

import android.text.TextUtils;

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
import com.mall.sls.data.entity.Ignore;
import com.mall.sls.data.entity.InvitationCodeInfo;
import com.mall.sls.data.entity.OrderList;
import com.mall.sls.data.entity.WXPaySignResponse;
import com.mall.sls.data.entity.WxPay;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
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
public class OrderListPresenter implements OrderContract.OrderListPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private OrderContract.OrderListView orderListView;
    private int currentIndex = 1;  //当前index
    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    @Inject
    public OrderListPresenter(RestApiService restApiService, OrderContract.OrderListView orderListView) {
        this.restApiService = restApiService;
        this.orderListView = orderListView;
    }

    @Inject
    public void setupListener() {
        orderListView.setPresenter(this);
    }

    //订单信息： 0，全部订单； 1，待付款； 2，待分享； 3，待发货； 4，待收货。5，已完成
    @Override
    public void getOrderList(String refreshType, String showType) {
        orderListView.dismissLoading();
        if (TextUtils.equals(StaticData.REFRESH_ONE, refreshType)) {
            orderListView.showLoading(StaticData.LOADING);
        }
        currentIndex=1;
        String queryString="showType="+showType+"&page="+currentIndex+"&limit="+StaticData.TEN_LIST_SIZE;
        String sign= SignUnit.signGet(RequestUrl.ORDER_LIST,queryString);
        Disposable disposable = restApiService.getOrderList(sign,showType,String.valueOf(currentIndex),StaticData.TEN_LIST_SIZE)
                .flatMap(new RxRemoteDataParse<OrderList>())
                .compose(new RxSchedulerTransformer<OrderList>())
                .subscribe(new Consumer<OrderList>() {
                    @Override
                    public void accept(OrderList orderList) throws Exception {
                        orderListView.dismissLoading();
                        orderListView.renderOrderList(orderList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        orderListView.dismissLoading();
                        orderListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreOrderList(String showType) {
        currentIndex=currentIndex+1;
        String queryString="showType="+showType+"&page="+currentIndex+"&limit="+StaticData.TEN_LIST_SIZE;
        String sign= SignUnit.signGet(RequestUrl.ORDER_LIST,queryString);
        Disposable disposable = restApiService.getOrderList(sign,showType,String.valueOf(currentIndex),StaticData.TEN_LIST_SIZE)
                .flatMap(new RxRemoteDataParse<OrderList>())
                .compose(new RxSchedulerTransformer<OrderList>())
                .subscribe(new Consumer<OrderList>() {
                    @Override
                    public void accept(OrderList orderList) throws Exception {
                        orderListView.dismissLoading();
                        orderListView.renderMoreOrderList(orderList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        orderListView.dismissLoading();
                        orderListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void cancelOrder(String orderId) {
        orderListView.showLoading(StaticData.PROCESSING);
        OrderRequest request=new OrderRequest(orderId);
        String sign= SignUnit.signPost(RequestUrl.ORDER_CANCEL,gson.toJson(request));
        Disposable disposable = restApiService.cancelOrder(sign,request)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        orderListView.dismissLoading();
                        orderListView.renderCancelOrder();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        orderListView.dismissLoading();
                        orderListView.showError(throwable);
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
                        orderListView.renderInvitationCodeInfo(invitationCodeInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        orderListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getWxPay(String orderId, String orderType, String paymentMethod) {
        orderListView.showLoading(StaticData.PROCESSING);
        PayRequest request=new PayRequest(orderId,orderType,paymentMethod);
        String sign= SignUnit.signPost(RequestUrl.BEGIN_PAY,gson.toJson(request));
        Disposable disposable = restApiService.getWxPay(sign,request)
                .flatMap(new RxRemoteDataParse<WxPay>())
                .compose(new RxSchedulerTransformer<WxPay>())
                .subscribe(new Consumer<WxPay>() {
                    @Override
                    public void accept(WxPay wxPay) throws Exception {
                        orderListView.dismissLoading();
                        orderListView.renderWxPay(wxPay);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        orderListView.dismissLoading();
                        orderListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getAliPay(String orderId, String orderType, String paymentMethod) {
        orderListView.showLoading(StaticData.PROCESSING);
        PayRequest request=new PayRequest(orderId,orderType,paymentMethod);
        String sign= SignUnit.signPost(RequestUrl.BEGIN_PAY,gson.toJson(request));
        Disposable disposable = restApiService.getAliPay(sign,request)
                .flatMap(new RxRemoteDataParse<AliPay>())
                .compose(new RxSchedulerTransformer<AliPay>())
                .subscribe(new Consumer<AliPay>() {
                    @Override
                    public void accept(AliPay aliPay) throws Exception {
                        orderListView.dismissLoading();
                        orderListView.renderAliPay(aliPay);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        orderListView.dismissLoading();
                        orderListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getBaoFuPay(String orderId, String orderType, String paymentMethod) {
        orderListView.showLoading(StaticData.PROCESSING);
        PayRequest request=new PayRequest(orderId,orderType,paymentMethod);
        String sign= SignUnit.signPost(RequestUrl.BEGIN_PAY,gson.toJson(request));
        Disposable disposable = restApiService.getBaoFuPay(sign,request)
                .flatMap(new RxRemoteDataParse<BaoFuPay>())
                .compose(new RxSchedulerTransformer<BaoFuPay>())
                .subscribe(new Consumer<BaoFuPay>() {
                    @Override
                    public void accept(BaoFuPay baoFuPay) throws Exception {
                        orderListView.dismissLoading();
                        orderListView.renderBaoFuPay(baoFuPay);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        orderListView.dismissLoading();
                        orderListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }


    @Override
    public void getAiNongPay(String orderId, String orderType, String paymentMethod) {
        orderListView.showLoading(StaticData.PROCESSING);
        PayRequest request=new PayRequest(orderId,orderType,paymentMethod);
        String sign= SignUnit.signPost(RequestUrl.BEGIN_PAY,gson.toJson(request));
        Disposable disposable = restApiService.getAiNongPay(sign,request)
                .flatMap(new RxRemoteDataParse<AiNongPay>())
                .compose(new RxSchedulerTransformer<AiNongPay>())
                .subscribe(new Consumer<AiNongPay>() {
                    @Override
                    public void accept(AiNongPay aiNongPay) throws Exception {
                        orderListView.dismissLoading();
                        orderListView.renderAiNongPay(aiNongPay);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        orderListView.dismissLoading();
                        orderListView.showError(throwable);
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
