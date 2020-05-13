package com.mall.sls.homepage.presenter;

import android.text.TextUtils;

import com.mall.sls.BuildConfig;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.GoodsDetailsInfo;
import com.mall.sls.data.entity.HomePageInfo;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.homepage.HomepageContract;
import com.stx.xhb.androidx.XBanner;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/5/13.
 * 描述：
 */
public class GoodsDetailsPresenter implements HomepageContract.GoodsDetailsPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private HomepageContract.GoodsDetailsView goodsDetailsView;

    @Inject
    public GoodsDetailsPresenter(RestApiService restApiService, HomepageContract.GoodsDetailsView goodsDetailsView) {
        this.restApiService = restApiService;
        this.goodsDetailsView = goodsDetailsView;
    }

    @Inject
    public void setupListener() {
        goodsDetailsView.setPresenter(this);
    }

    @Override
    public void getGoodsDetails(String goodsId) {
        goodsDetailsView.showLoading(StaticData.LOADING);
        String queryString = "goodsId=" +goodsId;
        String sign = SignUnit.signGet(RequestUrl.HOME_INDEX_URL, queryString);
        Disposable disposable = restApiService.getGoodsDetailsInfo(sign,goodsId)
                .flatMap(new RxRemoteDataParse<GoodsDetailsInfo>())
                .compose(new RxSchedulerTransformer<GoodsDetailsInfo>())
                .subscribe(new Consumer<GoodsDetailsInfo>() {
                    @Override
                    public void accept(GoodsDetailsInfo goodsDetailsInfo) throws Exception {
                        goodsDetailsView.dismissLoading();
                        goodsDetailsView.renderGoodsDetails(goodsDetailsInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        goodsDetailsView.dismissLoading();
                        goodsDetailsView.showError(throwable);
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
