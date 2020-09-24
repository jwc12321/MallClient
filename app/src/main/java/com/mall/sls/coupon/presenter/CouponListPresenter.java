package com.mall.sls.coupon.presenter;

import android.text.TextUtils;

import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.coupon.CouponContract;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.MyCouponInfo;
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
public class CouponListPresenter implements CouponContract.CouponListPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private CouponContract.CouponListView couponListView;
    private int currentIndex = 1;  //当前index

    @Inject
    public CouponListPresenter(RestApiService restApiService, CouponContract.CouponListView couponListView) {
        this.restApiService = restApiService;
        this.couponListView = couponListView;
    }

    @Inject
    public void setupListener() {
        couponListView.setPresenter(this);
    }

    @Override
    public void getCouponInfos(String refreshType, String status) {
        if (TextUtils.equals(StaticData.REFRESH_ONE, refreshType)) {
            couponListView.showLoading(StaticData.LOADING);
        }
        currentIndex=1;
        String queryString="status="+status+"&page="+currentIndex+"&limit="+StaticData.TEN_LIST_SIZE;
        String sign= SignUnit.signGet(RequestUrl.COUPON_MY_LIST_URL,queryString);
        Disposable disposable = restApiService.getCouponInfos(sign,status,String.valueOf(currentIndex),StaticData.TEN_LIST_SIZE)
                .flatMap(new RxRemoteDataParse<MyCouponInfo>())
                .compose(new RxSchedulerTransformer<MyCouponInfo>())
                .subscribe(new Consumer<MyCouponInfo>() {
                    @Override
                    public void accept(MyCouponInfo myCouponInfo) throws Exception {
                        couponListView.dismissLoading();
                        couponListView.renderCouponInfos(myCouponInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        couponListView.dismissLoading();
                        couponListView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreCouponInfos(String status) {
        currentIndex=currentIndex+1;
        String queryString="status="+status+"&page="+currentIndex+"&limit="+StaticData.TEN_LIST_SIZE;
        String sign= SignUnit.signGet(RequestUrl.COUPON_MY_LIST_URL,queryString);
        Disposable disposable = restApiService.getCouponInfos(sign,status,String.valueOf(currentIndex),StaticData.TEN_LIST_SIZE)
                .flatMap(new RxRemoteDataParse<MyCouponInfo>())
                .compose(new RxSchedulerTransformer<MyCouponInfo>())
                .subscribe(new Consumer<MyCouponInfo>() {
                    @Override
                    public void accept(MyCouponInfo myCouponInfo) throws Exception {
                        couponListView.dismissLoading();
                        couponListView.renderMoreCouponInfos(myCouponInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        couponListView.dismissLoading();
                        couponListView.showError(throwable);
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
