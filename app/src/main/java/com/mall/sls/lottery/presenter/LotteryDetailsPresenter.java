package com.mall.sls.lottery.presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.JoinPrizeInfo;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.data.request.JoinPrizeRequest;
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
public class LotteryDetailsPresenter implements LotteryContract.LotteryDetailsPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private LotteryContract.LotteryDetailsView lotteryDetailsView;
    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    @Inject
    public LotteryDetailsPresenter(RestApiService restApiService, LotteryContract.LotteryDetailsView lotteryDetailsView) {
        this.restApiService = restApiService;
        this.lotteryDetailsView = lotteryDetailsView;
    }

    @Inject
    public void setupListener() {
        lotteryDetailsView.setPresenter(this);
    }


    @Override
    public void getSystemTime() {
        lotteryDetailsView.showLoading(StaticData.LOADING);
        String queryString = "null";
        String sign = SignUnit.signGet(RequestUrl.SYSTEM_TIME, queryString);
        Disposable disposable = restApiService.getSystemTime(sign)
                .flatMap(new RxRemoteDataParse<String>())
                .compose(new RxSchedulerTransformer<String>())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String time) throws Exception {
                        lotteryDetailsView.dismissLoading();
                        lotteryDetailsView.renderSystemTime(time);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        lotteryDetailsView.dismissLoading();
                        lotteryDetailsView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getJoinPrizeInfo(String prizeId, String number, String payType) {
        lotteryDetailsView.showLoading(StaticData.PROCESSING);
        JoinPrizeRequest request=new JoinPrizeRequest(prizeId,number,payType);
        String sign= SignUnit.signPost(RequestUrl.JOIN_PRIZE,gson.toJson(request));
        Disposable disposable = restApiService.getJoinPrizeInfo(sign,request)
                .flatMap(new RxRemoteDataParse<JoinPrizeInfo>())
                .compose(new RxSchedulerTransformer<JoinPrizeInfo>())
                .subscribe(new Consumer<JoinPrizeInfo>() {
                    @Override
                    public void accept(JoinPrizeInfo joinPrizeInfo) throws Exception {
                        lotteryDetailsView.dismissLoading();
                        lotteryDetailsView.renderJoinPrizeInfo(joinPrizeInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        lotteryDetailsView.dismissLoading();
                        lotteryDetailsView.showError(throwable);
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
