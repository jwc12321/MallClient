package com.mall.sls.order.presenter;

import android.text.TextUtils;

import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.RefundInfo;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.order.OrderContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/7/17.
 * 描述：
 */
public class RefundPresenter implements OrderContract.RefundPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private OrderContract.RefundView refundView;

    @Inject
    public RefundPresenter(RestApiService restApiService, OrderContract.RefundView refundView) {
        this.restApiService = restApiService;
        this.refundView = refundView;
    }

    @Inject
    public void setupListener() {
        refundView.setPresenter(this);
    }

    @Override
    public void getRefundInfo(String refreshType,String orderId) {
        if (TextUtils.equals("1", refreshType)) {
            refundView.showLoading(StaticData.LOADING);
        }
        String sign= SignUnit.signGet(RequestUrl.REFUND_LOGS+orderId,"null");
        Disposable disposable = restApiService.getRefundInfo(sign,orderId)
                .flatMap(new RxRemoteDataParse<List<RefundInfo>>())
                .compose(new RxSchedulerTransformer<List<RefundInfo>>())
                .subscribe(new Consumer<List<RefundInfo>>() {
                    @Override
                    public void accept(List<RefundInfo> refundInfos) throws Exception {
                        refundView.dismissLoading();
                        refundView.renderRefundInfo(refundInfos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        refundView.dismissLoading();
                        refundView.showError(throwable);
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
