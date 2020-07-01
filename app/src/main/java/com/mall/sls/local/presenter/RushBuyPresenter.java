package com.mall.sls.local.presenter;

import android.text.TextUtils;

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
public class RushBuyPresenter implements LocalContract.RushBuyPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private LocalContract.RushBuyView rushBuyView;
    private int currentIndex = 1;  //当前index

    @Inject
    public RushBuyPresenter(RestApiService restApiService, LocalContract.RushBuyView rushBuyView) {
        this.restApiService = restApiService;
        this.rushBuyView = rushBuyView;
    }

    @Inject
    public void setupListener() {
        rushBuyView.setPresenter(this);
    }


    @Override
    public void getRushBuy(String refreshType) {
        if (TextUtils.equals("1", refreshType)) {
            rushBuyView.showLoading(StaticData.LOADING);
        }
        currentIndex=1;
        String queryString="page="+currentIndex+"&limit="+StaticData.TEN_LIST_SIZE;
        String sign= SignUnit.signGet(RequestUrl.GOODS_RUSH_BUY,queryString);
        Disposable disposable = restApiService.getRushBuy(sign,String.valueOf(currentIndex),StaticData.TEN_LIST_SIZE)
                .flatMap(new RxRemoteDataParse<LocalTeam>())
                .compose(new RxSchedulerTransformer<LocalTeam>())
                .subscribe(new Consumer<LocalTeam>() {
                    @Override
                    public void accept(LocalTeam localTeam) throws Exception {
                        rushBuyView.dismissLoading();
                        rushBuyView.renderRushBuy(localTeam);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        rushBuyView.dismissLoading();
                        rushBuyView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreRushBuy() {
        currentIndex=currentIndex+1;
        String queryString="page="+currentIndex+"&limit="+StaticData.TEN_LIST_SIZE;
        String sign= SignUnit.signGet(RequestUrl.GOODS_RUSH_BUY,queryString);
        Disposable disposable = restApiService.getRushBuy(sign,String.valueOf(currentIndex),StaticData.TEN_LIST_SIZE)
                .flatMap(new RxRemoteDataParse<LocalTeam>())
                .compose(new RxSchedulerTransformer<LocalTeam>())
                .subscribe(new Consumer<LocalTeam>() {
                    @Override
                    public void accept(LocalTeam localTeam) throws Exception {
                        rushBuyView.dismissLoading();
                        rushBuyView.renderMoreRushBuy(localTeam);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        rushBuyView.dismissLoading();
                        rushBuyView.showError(throwable);
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
