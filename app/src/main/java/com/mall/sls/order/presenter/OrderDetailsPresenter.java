package com.mall.sls.order.presenter;

import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.GoodsOrderDetails;
import com.mall.sls.data.entity.OrderInfo;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
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
                .flatMap(new RxRemoteDataParse<OrderInfo>())
                .compose(new RxSchedulerTransformer<OrderInfo>())
                .subscribe(new Consumer<OrderInfo>() {
                    @Override
                    public void accept(OrderInfo orderInfo) throws Exception {
                        orderDetailsView.dismissLoading();
                        orderDetailsView.renderOrderDetails(orderInfo);
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
