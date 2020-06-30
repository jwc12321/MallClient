package com.mall.sls.order.presenter;

import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.OrderPackageInfo;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.order.OrderContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/6/30.
 * 描述：
 */
public class OrderLogisticsPresenter implements OrderContract.OrderLogisticsPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private OrderContract.OrderLogisticsView orderLogisticsView;

    @Inject
    public OrderLogisticsPresenter(RestApiService restApiService, OrderContract.OrderLogisticsView orderLogisticsView) {
        this.restApiService = restApiService;
        this.orderLogisticsView = orderLogisticsView;
    }


    @Inject
    public void setupListener() {
        orderLogisticsView.setPresenter(this);
    }

    @Override
    public void getOrderLogistics(String id) {
        orderLogisticsView.showLoading(StaticData.LOADING);
        String sign= SignUnit.signGet(RequestUrl.ORDER_LOGISTICS+id,"null");
        Disposable disposable = restApiService.getOrderLogistics(sign,id)
                .flatMap(new RxRemoteDataParse<List<OrderPackageInfo>>())
                .compose(new RxSchedulerTransformer<List<OrderPackageInfo>>())
                .subscribe(new Consumer<List<OrderPackageInfo>>() {
                    @Override
                    public void accept(List<OrderPackageInfo> orderPackageInfos) throws Exception {
                        orderLogisticsView.dismissLoading();
                        orderLogisticsView.renderOrderLogistics(orderPackageInfos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        orderLogisticsView.dismissLoading();
                        orderLogisticsView.showError(throwable);
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
