package com.mall.sls.mine.presenter;

import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.MineInfo;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.mine.MineContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/5/18.
 * 描述：
 */
public class MineInfoPresenter implements MineContract.MineInfoPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private MineContract.MineInfoView mineInfoView;

    @Inject
    public MineInfoPresenter(RestApiService restApiService, MineContract.MineInfoView mineInfoView) {
        this.restApiService = restApiService;
        this.mineInfoView = mineInfoView;
    }


    @Inject
    public void setupListener() {
        mineInfoView.setPresenter(this);
    }

    @Override
    public void getMineInfo() {
        mineInfoView.showLoading(StaticData.LOADING);
        String queryString = "null";
        String sign = SignUnit.signGet(RequestUrl.MINE_INFO_URL, queryString);
        Disposable disposable = restApiService.getMineInfo(sign)
                .flatMap(new RxRemoteDataParse<MineInfo>())
                .compose(new RxSchedulerTransformer<MineInfo>())
                .subscribe(new Consumer<MineInfo>() {
                    @Override
                    public void accept(MineInfo mineInfo) throws Exception {
                        mineInfoView.dismissLoading();
                        mineInfoView.renderMineInfo(mineInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mineInfoView.dismissLoading();
                        mineInfoView.showError(throwable);
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
