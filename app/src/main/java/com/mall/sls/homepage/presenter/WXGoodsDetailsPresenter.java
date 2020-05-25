package com.mall.sls.homepage.presenter;

import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.WXGoodsDetailsInfo;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.homepage.HomepageContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/5/25.
 * 描述：
 */
public class WXGoodsDetailsPresenter implements HomepageContract.WXGoodsDetailsPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private HomepageContract.WXGoodsDetailsView wxGoodsDetailsView;

    @Inject
    public WXGoodsDetailsPresenter(RestApiService restApiService, HomepageContract.WXGoodsDetailsView wxGoodsDetailsView) {
        this.restApiService = restApiService;
        this.wxGoodsDetailsView = wxGoodsDetailsView;
    }

    @Inject
    public void setupListener() {
        wxGoodsDetailsView.setPresenter(this);
    }

    @Override
    public void getWXGoodsDetailsInfo(String goodsProductId, String grouponId) {
        wxGoodsDetailsView.showLoading(StaticData.LOADING);
        String queryString="goodsProductId="+goodsProductId+"&grouponId="+ grouponId;
        String sign= SignUnit.signGet(RequestUrl.WX_GOODS_DETAILS,queryString);
        Disposable disposable = restApiService.getWXGoodsDetailsInfo(sign,goodsProductId,grouponId)
                .flatMap(new RxRemoteDataParse<WXGoodsDetailsInfo>())
                .compose(new RxSchedulerTransformer<WXGoodsDetailsInfo>())
                .subscribe(new Consumer<WXGoodsDetailsInfo>() {
                    @Override
                    public void accept(WXGoodsDetailsInfo wxGoodsDetailsInfo) throws Exception {
                        wxGoodsDetailsView.dismissLoading();
                        wxGoodsDetailsView.renderWXGoodsDetailsInfo(wxGoodsDetailsInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        wxGoodsDetailsView.dismissLoading();
                        wxGoodsDetailsView.showError(throwable);
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
