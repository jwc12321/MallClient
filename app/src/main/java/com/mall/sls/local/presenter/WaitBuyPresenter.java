package com.mall.sls.local.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.LocalTeam;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.local.LocalContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/7/1.
 * 描述：
 */
public class WaitBuyPresenter implements LocalContract.WaitBuyPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private LocalContract.WaitBuyView waitBuyView;
    private int currentIndex = 1;  //当前index

    @Inject
    public WaitBuyPresenter(RestApiService restApiService, LocalContract.WaitBuyView waitBuyView) {
        this.restApiService = restApiService;
        this.waitBuyView = waitBuyView;
    }

    @Inject
    public void setupListener() {
        waitBuyView.setPresenter(this);
    }

    @Override
    public void getWaitBuy(String refreshType) {
        if (TextUtils.equals("1", refreshType)) {
            waitBuyView.showLoading(StaticData.LOADING);
        }
        currentIndex=1;
        String queryString="page="+currentIndex+"&limit="+StaticData.TEN_LIST_SIZE;
        String sign= SignUnit.signGet(RequestUrl.GOODS_WAIT_BUY,queryString);
        Disposable disposable = restApiService.getWaitBuy(sign,String.valueOf(currentIndex),StaticData.TEN_LIST_SIZE)
                .flatMap(new RxRemoteDataParse<LocalTeam>())
                .compose(new RxSchedulerTransformer<LocalTeam>())
                .subscribe(new Consumer<LocalTeam>() {
                    @Override
                    public void accept(LocalTeam localTeam) throws Exception {
                        waitBuyView.dismissLoading();
                        waitBuyView.renderWaitBuy(localTeam);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        waitBuyView.dismissLoading();
                        waitBuyView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreWaitBuy() {
        currentIndex=1;
        String queryString="page="+currentIndex+"&limit="+StaticData.TEN_LIST_SIZE;
        String sign= SignUnit.signGet(RequestUrl.GOODS_WAIT_BUY,queryString);
        Disposable disposable = restApiService.getWaitBuy(sign,String.valueOf(currentIndex),StaticData.TEN_LIST_SIZE)
                .flatMap(new RxRemoteDataParse<LocalTeam>())
                .compose(new RxSchedulerTransformer<LocalTeam>())
                .subscribe(new Consumer<LocalTeam>() {
                    @Override
                    public void accept(LocalTeam localTeam) throws Exception {
                        waitBuyView.dismissLoading();
                        waitBuyView.renderMoreWaitBuy(localTeam);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        waitBuyView.dismissLoading();
                        waitBuyView.showError(throwable);
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
