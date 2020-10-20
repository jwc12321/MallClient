package com.mall.sls.homepage.presenter;

import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.homepage.HomepageContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/10/20.
 * 描述：
 */
public class ShipInfoPresenter implements HomepageContract.ShipInfoPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private HomepageContract.ShipInfoView shipInfoView;

    @Inject
    public ShipInfoPresenter(RestApiService restApiService, HomepageContract.ShipInfoView shipInfoView) {
        this.restApiService = restApiService;
        this.shipInfoView = shipInfoView;
    }


    @Inject
    public void setupListener() {
        shipInfoView.setPresenter(this);
    }

    @Override
    public void getShipInfo() {
        shipInfoView.showLoading(StaticData.LOADING);
        String queryString = "null";
        String sign = SignUnit.signGet(RequestUrl.SHIP_INFO, queryString);
        Disposable disposable = restApiService.getShipInfo(sign)
                .flatMap(new RxRemoteDataParse<String>())
                .compose(new RxSchedulerTransformer<String>())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String shipInfo) throws Exception {
                        shipInfoView.dismissLoading();
                        shipInfoView.renderShipInfo(shipInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        shipInfoView.dismissLoading();
                        shipInfoView.showError(throwable);
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
