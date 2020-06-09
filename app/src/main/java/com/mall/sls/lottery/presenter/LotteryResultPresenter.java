package com.mall.sls.lottery.presenter;

import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
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
public class LotteryResultPresenter implements LotteryContract.LotteryResultPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private LotteryContract.LotteryResultView lotteryResultView;

    @Inject
    public LotteryResultPresenter(RestApiService restApiService, LotteryContract.LotteryResultView lotteryResultView) {
        this.restApiService = restApiService;
        this.lotteryResultView = lotteryResultView;
    }

    @Inject
    public void setupListener() {
        lotteryResultView.setPresenter(this);
    }

    @Override
    public void getPrizeResult(String prizeId) {
        lotteryResultView.showLoading(StaticData.LOADING);
        String queryString="prizeId="+prizeId;
        String sign= SignUnit.signGet(RequestUrl.PRIZE_RESULT,queryString);
        Disposable disposable = restApiService.getPrizeResult(sign,prizeId)
                .flatMap(new RxRemoteDataParse<List<String>>())
                .compose(new RxSchedulerTransformer<List<String>>())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> results) throws Exception {
                        lotteryResultView.dismissLoading();
                        lotteryResultView.renderPrizeResult(results);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        lotteryResultView.dismissLoading();
                        lotteryResultView.showError(throwable);
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
