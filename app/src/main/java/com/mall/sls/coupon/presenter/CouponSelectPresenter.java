package com.mall.sls.coupon.presenter;

import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.coupon.CouponContract;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.CouponInfo;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/5/15.
 * 描述：
 */
public class CouponSelectPresenter implements CouponContract.CouponSelectPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private CouponContract.CouponSelectView couponSelectView;

    @Inject
    public CouponSelectPresenter(RestApiService restApiService, CouponContract.CouponSelectView couponSelectView) {
        this.restApiService = restApiService;
        this.couponSelectView = couponSelectView;
    }

    @Inject
    public void setupListener() {
        couponSelectView.setPresenter(this);
    }

    @Override
    public void getCouponSelect(List<String> cartIds) {
        couponSelectView.showLoading(StaticData.LOADING);
        String queryString = "cartIds=" +cartIds;
        String sign = SignUnit.signGet(RequestUrl.COUPON_SELECT_LIST_URL, queryString);
        Disposable disposable = restApiService.getCouponSelect(sign,cartIds)
                .flatMap(new RxRemoteDataParse<List<CouponInfo>>())
                .compose(new RxSchedulerTransformer<List<CouponInfo>>())
                .subscribe(new Consumer<List<CouponInfo>>() {
                    @Override
                    public void accept(List<CouponInfo> couponInfos) throws Exception {
                        couponSelectView.dismissLoading();
                        couponSelectView.renderCouponSelect(couponInfos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        couponSelectView.dismissLoading();
                        couponSelectView.showError(throwable);
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
