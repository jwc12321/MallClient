package com.mall.sls.homepage.presenter;

import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.PayMethodInfo;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.homepage.HomepageContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/8/6.
 * 描述：
 */
public class PayMethodPresenter implements HomepageContract.PayMethodPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private HomepageContract.PayMethodView payMethodView;

    @Inject
    public PayMethodPresenter(RestApiService restApiService, HomepageContract.PayMethodView payMethodView) {
        this.restApiService = restApiService;
        this.payMethodView = payMethodView;
    }

    @Inject
    public void setupListener() {
        payMethodView.setPresenter(this);
    }

    @Override
    public void getPayMethod(String devicePlatform,String orderType) {
        payMethodView.showLoading(StaticData.LOADING);
        String queryString="devicePlatform="+devicePlatform+"&orderType="+orderType;
        String sign = SignUnit.signGet(RequestUrl.PAY_METHOD, queryString);
        Disposable disposable = restApiService.getPayMethod(sign,devicePlatform,orderType)
                .flatMap(new RxRemoteDataParse<List<PayMethodInfo>>())
                .compose(new RxSchedulerTransformer<List<PayMethodInfo>>())
                .subscribe(new Consumer<List<PayMethodInfo>>() {
                    @Override
                    public void accept(List<PayMethodInfo> payMethods) throws Exception {
                        payMethodView.dismissLoading();
                        payMethodView.renderPayMethod(payMethods);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        payMethodView.dismissLoading();
                        payMethodView.showError(throwable);
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
