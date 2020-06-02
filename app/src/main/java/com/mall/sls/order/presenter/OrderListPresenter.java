package com.mall.sls.order.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.OrderList;
import com.mall.sls.data.entity.WXPaySignResponse;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.data.request.OrderPayRequest;
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

    //订单信息： 0，全部订单； 1，待付款； 2，待发货； 3，待收货； 4，待评价。
    @Override
    public void getOrderList(String refreshType, String showType) {
        if (TextUtils.equals("1", refreshType)) {
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
    public void orderAliPay(String orderId, String type) {
        orderListView.showLoading(StaticData.PROCESSING);
        OrderPayRequest request=new OrderPayRequest(orderId,type);
        String sign= SignUnit.signPost(RequestUrl.ORDER_ALIPAY,gson.toJson(request));
        Disposable disposable = restApiService.orderAliPay(sign,request)
                .flatMap(new RxRemoteDataParse<String>())
                .compose(new RxSchedulerTransformer<String>())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String alipayStr) throws Exception {
                        orderListView.dismissLoading();
                        orderListView.renderOrderAliPay(alipayStr);
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
    public void orderWxPay(String orderId, String type) {
        orderListView.showLoading(StaticData.PROCESSING);
        OrderPayRequest request=new OrderPayRequest(orderId,type);
        String sign= SignUnit.signPost(RequestUrl.ORDER_WX_PAY,gson.toJson(request));
        Disposable disposable = restApiService.orderWxPay(sign,request)
                .flatMap(new RxRemoteDataParse<WXPaySignResponse>())
                .compose(new RxSchedulerTransformer<WXPaySignResponse>())
                .subscribe(new Consumer<WXPaySignResponse>() {
                    @Override
                    public void accept(WXPaySignResponse wxPaySignResponse) throws Exception {
                        orderListView.dismissLoading();
                        orderListView.renderOrderWxPay(wxPaySignResponse);
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
