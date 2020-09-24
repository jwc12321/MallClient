package com.mall.sls.lottery.presenter;

import android.text.TextUtils;

import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.LotteryItemInfo;
import com.mall.sls.data.entity.LotteryRecord;
import com.mall.sls.data.entity.PrizeVo;
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
public class LotteryRecordPresenter implements LotteryContract.LotteryRecordPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private LotteryContract.LotteryRecordView lotteryRecordView;
    private int currentIndex = 1;  //当前index

    @Inject
    public LotteryRecordPresenter(RestApiService restApiService, LotteryContract.LotteryRecordView lotteryRecordView) {
        this.restApiService = restApiService;
        this.lotteryRecordView = lotteryRecordView;
    }

    @Inject
    public void setupListener() {
        lotteryRecordView.setPresenter(this);
    }


    @Override
    public void getLotteryRecord(String refreshType) {
        if (TextUtils.equals(StaticData.REFRESH_ONE, refreshType)) {
            lotteryRecordView.showLoading(StaticData.LOADING);
        }
        currentIndex=1;
        String queryString="page="+currentIndex+"&limit="+StaticData.TEN_LIST_SIZE;
        String sign= SignUnit.signGet(RequestUrl.PRIZE_HISTORY,queryString);
        Disposable disposable = restApiService.getLotteryRecord(sign,String.valueOf(currentIndex),StaticData.TEN_LIST_SIZE)
                .flatMap(new RxRemoteDataParse<LotteryRecord>())
                .compose(new RxSchedulerTransformer<LotteryRecord>())
                .subscribe(new Consumer<LotteryRecord>() {
                    @Override
                    public void accept(LotteryRecord lotteryRecord) throws Exception {
                        lotteryRecordView.dismissLoading();
                        lotteryRecordView.renderLotteryRecord(lotteryRecord);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        lotteryRecordView.dismissLoading();
                        lotteryRecordView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreLotteryRecord() {
        currentIndex=currentIndex+1;
        String queryString="page="+currentIndex+"&limit="+StaticData.TEN_LIST_SIZE;
        String sign= SignUnit.signGet(RequestUrl.PRIZE_HISTORY,queryString);
        Disposable disposable = restApiService.getLotteryRecord(sign,String.valueOf(currentIndex),StaticData.TEN_LIST_SIZE)
                .flatMap(new RxRemoteDataParse<LotteryRecord>())
                .compose(new RxSchedulerTransformer<LotteryRecord>())
                .subscribe(new Consumer<LotteryRecord>() {
                    @Override
                    public void accept(LotteryRecord lotteryRecord) throws Exception {
                        lotteryRecordView.dismissLoading();
                        lotteryRecordView.renderMoreLotteryRecord(lotteryRecord);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        lotteryRecordView.dismissLoading();
                        lotteryRecordView.showError(throwable);
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
