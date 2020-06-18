package com.mall.sls.mine.presenter;

import com.mall.sls.BuildConfig;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.AppUrlInfo;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.mine.MineContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/6/18.
 * 描述：
 */
public class AboutAppPresenter implements MineContract.AboutAppPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private MineContract.AboutAppView aboutAppView;

    @Inject
    public AboutAppPresenter(RestApiService restApiService, MineContract.AboutAppView aboutAppView) {
        this.restApiService = restApiService;
        this.aboutAppView = aboutAppView;
    }

    @Inject
    public void setupListener() {
        aboutAppView.setPresenter(this);
    }

    @Override
    public void getAppUrlInfo() {
        aboutAppView.showLoading(StaticData.LOADING);
        String queryString="version="+ BuildConfig.VERSION_NAME;
        String sign= SignUnit.signGet(RequestUrl.APP_URL_INFO_URL,queryString);
        Disposable disposable = restApiService.getAppUrlInfo(sign,BuildConfig.VERSION_NAME)
                .flatMap(new RxRemoteDataParse<AppUrlInfo>())
                .compose(new RxSchedulerTransformer<AppUrlInfo>())
                .subscribe(new Consumer<AppUrlInfo>() {
                    @Override
                    public void accept(AppUrlInfo appUrlInfo) throws Exception {
                        aboutAppView.dismissLoading();
                        aboutAppView.renderAppUrlInfo(appUrlInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        aboutAppView.dismissLoading();
                        aboutAppView.showError(throwable);
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
