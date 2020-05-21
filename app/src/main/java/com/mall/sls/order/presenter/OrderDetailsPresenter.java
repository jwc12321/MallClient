package com.mall.sls.order.presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.GoodsOrderDetails;
import com.mall.sls.data.entity.OrderInfo;
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
    public void orderAliPay(String orderId, String type) {
        orderDetailsView.showLoading(StaticData.PROCESSING);
        OrderPayRequest request=new OrderPayRequest(orderId,type);
        String sign= SignUnit.signPost(RequestUrl.ORDER_ALIPAY,gson.toJson(request));
        Disposable disposable = restApiService.orderAliPay(sign,request)
                .flatMap(new RxRemoteDataParse<String>())
                .compose(new RxSchedulerTransformer<String>())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String alipayStr) throws Exception {
                        orderDetailsView.dismissLoading();
                        orderDetailsView.renderOrderAliPay(alipayStr);
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
