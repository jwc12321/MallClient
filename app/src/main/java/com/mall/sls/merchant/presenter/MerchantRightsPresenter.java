package com.mall.sls.merchant.presenter;

import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.IntegralPointsInfo;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.merchant.MerchantContract;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/10/23.
 * 描述：
 */
public class MerchantRightsPresenter implements MerchantContract.MerchantRightsPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private MerchantContract.MerchantRightsView merchantRightsView;

    @Inject
    public MerchantRightsPresenter(RestApiService restApiService, MerchantContract.MerchantRightsView merchantRightsView) {
        this.restApiService = restApiService;
        this.merchantRightsView = merchantRightsView;
    }

    @Inject
    public void setupListener() {
        merchantRightsView.setPresenter(this);
    }

    @Override
    public void getIntegralPointsInfo() {
        merchantRightsView.showLoading(StaticData.LOADING);
        String queryString = "null";
        String sign = SignUnit.signGet(RequestUrl.INTEGRAL_POINTS, queryString);
        Disposable disposable = restApiService.getIntegralPointsInfo(sign)
                .flatMap(new RxRemoteDataParse<IntegralPointsInfo>())
                .compose(new RxSchedulerTransformer<IntegralPointsInfo>())
                .subscribe(new Consumer<IntegralPointsInfo>() {
                    @Override
                    public void accept(IntegralPointsInfo integralPointsInfo) throws Exception {
                        merchantRightsView.dismissLoading();
                        merchantRightsView.renderMerchantRights(integralPointsInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        merchantRightsView.dismissLoading();
                        merchantRightsView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void merchantCancel() {
        merchantRightsView.showLoading(StaticData.PROCESSING);
        String sign= SignUnit.signPost(RequestUrl.MERCHANT_CANCEL,"null");
        Disposable disposable = restApiService.merchantCancel(sign)
                .flatMap(new RxRemoteDataParse<Boolean>())
                .compose(new RxSchedulerTransformer<Boolean>())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isBoolean) throws Exception {
                        merchantRightsView.dismissLoading();
                        merchantRightsView.renderMerchantCancel(isBoolean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        merchantRightsView.dismissLoading();
                        merchantRightsView.showError(throwable);
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
