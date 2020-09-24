package com.mall.sls.lottery.presenter;

import android.text.TextUtils;

import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.LotteryItemInfo;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.lottery.LotteryContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/6/9.
 * 描述：
 */
public class LotteryItemPresenter implements LotteryContract.LotteryItemPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private LotteryContract.LotteryItemView lotteryItemView;
    private int currentIndex = 1;  //当前index

    @Inject
    public LotteryItemPresenter(RestApiService restApiService, LotteryContract.LotteryItemView lotteryItemView) {
        this.restApiService = restApiService;
        this.lotteryItemView = lotteryItemView;
    }

    @Inject
    public void setupListener() {
        lotteryItemView.setPresenter(this);
    }


    @Override
    public void getLotteryItemInfo(String refreshType) {
        if (TextUtils.equals(StaticData.REFRESH_ONE, refreshType)) {
            lotteryItemView.showLoading(StaticData.LOADING);
        }
        currentIndex=1;
        String queryString="page="+currentIndex+"&limit="+StaticData.TEN_LIST_SIZE;
        String sign= SignUnit.signGet(RequestUrl.PRIZE_LIST,queryString);
        Disposable disposable = restApiService.getLotteryItemInfo(sign,String.valueOf(currentIndex),StaticData.TEN_LIST_SIZE)
                .flatMap(new RxRemoteDataParse<LotteryItemInfo>())
                .compose(new RxSchedulerTransformer<LotteryItemInfo>())
                .subscribe(new Consumer<LotteryItemInfo>() {
                    @Override
                    public void accept(LotteryItemInfo lotteryItemInfo) throws Exception {
                        lotteryItemView.dismissLoading();
                        lotteryItemView.renderLotteryItemInfo(lotteryItemInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        lotteryItemView.dismissLoading();
                        lotteryItemView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreLotteryItemInfo() {
        currentIndex=currentIndex+1;
        String queryString="page="+currentIndex+"&limit="+StaticData.TEN_LIST_SIZE;
        String sign= SignUnit.signGet(RequestUrl.PRIZE_LIST,queryString);
        Disposable disposable = restApiService.getLotteryItemInfo(sign,String.valueOf(currentIndex),StaticData.TEN_LIST_SIZE)
                .flatMap(new RxRemoteDataParse<LotteryItemInfo>())
                .compose(new RxSchedulerTransformer<LotteryItemInfo>())
                .subscribe(new Consumer<LotteryItemInfo>() {
                    @Override
                    public void accept(LotteryItemInfo lotteryItemInfo) throws Exception {
                        lotteryItemView.dismissLoading();
                        lotteryItemView.renderMoreLotteryItemInfo(lotteryItemInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        lotteryItemView.dismissLoading();
                        lotteryItemView.showError(throwable);
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
