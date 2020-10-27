package com.mall.sls.certify.presenter;

import com.mall.sls.certify.CertifyContract;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.MineInfo;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/10/27.
 * 描述：
 */
public class MerchantCertifyTipPresenter implements CertifyContract.MerchantCertifyTipPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private CertifyContract.MerchantCertifyTipView merchantCertifyTipView;

    @Inject
    public MerchantCertifyTipPresenter(RestApiService restApiService, CertifyContract.MerchantCertifyTipView merchantCertifyTipView) {
        this.restApiService = restApiService;
        this.merchantCertifyTipView = merchantCertifyTipView;
    }

    @Inject
    public void setupListener() {
        merchantCertifyTipView.setPresenter(this);
    }

    @Override
    public void getMineInfo() {
        merchantCertifyTipView.showLoading(StaticData.LOADING);
        String queryString = "null";
        String sign = SignUnit.signGet(RequestUrl.MINE_INFO_URL, queryString);
        Disposable disposable = restApiService.getMineInfo(sign)
                .flatMap(new RxRemoteDataParse<MineInfo>())
                .compose(new RxSchedulerTransformer<MineInfo>())
                .subscribe(new Consumer<MineInfo>() {
                    @Override
                    public void accept(MineInfo mineInfo) throws Exception {
                        merchantCertifyTipView.dismissLoading();
                        merchantCertifyTipView.renderMineInfo(mineInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        merchantCertifyTipView.dismissLoading();
                        merchantCertifyTipView.showError(throwable);
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
