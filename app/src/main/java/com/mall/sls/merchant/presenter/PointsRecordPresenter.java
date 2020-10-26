package com.mall.sls.merchant.presenter;

import android.text.TextUtils;

import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.PointsRecord;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.merchant.MerchantContract;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/10/26.
 * 描述：
 */
public class PointsRecordPresenter implements MerchantContract.PointsRecordPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private MerchantContract.PointsRecordView pointsRecordView;
    private int currentIndex = 1;  //当前index

    @Inject
    public PointsRecordPresenter(RestApiService restApiService, MerchantContract.PointsRecordView pointsRecordView) {
        this.restApiService = restApiService;
        this.pointsRecordView = pointsRecordView;
    }

    @Inject
    public void setupListener() {
        pointsRecordView.setPresenter(this);
    }

    @Override
    public void getPointsRecord(String refreshType) {
        if (TextUtils.equals(StaticData.REFRESH_ONE, refreshType)) {
            pointsRecordView.showLoading(StaticData.LOADING);
        }
        currentIndex=1;
        String queryString="page="+currentIndex+"&limit="+StaticData.TEN_LIST_SIZE;
        String sign= SignUnit.signGet(RequestUrl.POINT_RECORD,queryString);
        Disposable disposable = restApiService.getPointsRecord(sign,String.valueOf(currentIndex),StaticData.TEN_LIST_SIZE)
                .flatMap(new RxRemoteDataParse<PointsRecord>())
                .compose(new RxSchedulerTransformer<PointsRecord>())
                .subscribe(new Consumer<PointsRecord>() {
                    @Override
                    public void accept(PointsRecord pointsRecord) throws Exception {
                        pointsRecordView.dismissLoading();
                        pointsRecordView.renderPointsRecord(pointsRecord);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        pointsRecordView.dismissLoading();
                        pointsRecordView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMorePointsRecord() {
        currentIndex=currentIndex+1;
        String queryString="page="+currentIndex+"&limit="+StaticData.TEN_LIST_SIZE;
        String sign= SignUnit.signGet(RequestUrl.POINT_RECORD,queryString);
        Disposable disposable = restApiService.getPointsRecord(sign,String.valueOf(currentIndex),StaticData.TEN_LIST_SIZE)
                .flatMap(new RxRemoteDataParse<PointsRecord>())
                .compose(new RxSchedulerTransformer<PointsRecord>())
                .subscribe(new Consumer<PointsRecord>() {
                    @Override
                    public void accept(PointsRecord pointsRecord) throws Exception {
                        pointsRecordView.dismissLoading();
                        pointsRecordView.renderMorePointsRecord(pointsRecord);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        pointsRecordView.dismissLoading();
                        pointsRecordView.showError(throwable);
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
