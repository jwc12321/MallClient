package com.mall.sls.homepage.presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.CartAddInfo;
import com.mall.sls.data.entity.ConfirmCartOrderDetail;
import com.mall.sls.data.entity.GeneralGoodsDetailsInfo;
import com.mall.sls.data.entity.GoodsDetailsInfo;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.data.request.BuyNowRequest;
import com.mall.sls.data.request.CartAddRequest;
import com.mall.sls.homepage.HomepageContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/6/23.
 * 描述：
 */
public class GeneralGoodsDetailsPresenter implements HomepageContract.GeneralGoodsDetailsPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private HomepageContract.GeneralGoodsDetailsView generalGoodsDetailsView;
    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    @Inject
    public GeneralGoodsDetailsPresenter(RestApiService restApiService, HomepageContract.GeneralGoodsDetailsView generalGoodsDetailsView) {
        this.restApiService = restApiService;
        this.generalGoodsDetailsView = generalGoodsDetailsView;
    }

    @Inject
    public void setupListener() {
        generalGoodsDetailsView.setPresenter(this);
    }

    @Override
    public void getCartCount() {
        String queryString = "null";
        String sign = SignUnit.signGet(RequestUrl.CART_COUNT, queryString);
        Disposable disposable = restApiService.getCartCount(sign)
                .flatMap(new RxRemoteDataParse<String>())
                .compose(new RxSchedulerTransformer<String>())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String number) throws Exception {
                        generalGoodsDetailsView.renderCartCount(number);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        generalGoodsDetailsView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void cartAdd(String productId, String number) {
        generalGoodsDetailsView.showLoading(StaticData.PROCESSING);
        CartAddRequest request = new CartAddRequest(productId, number);
        String sign = SignUnit.signPost(RequestUrl.CART_ADD, gson.toJson(request));
        Disposable disposable = restApiService.cartAdd(sign, request)
                .flatMap(new RxRemoteDataParse<CartAddInfo>())
                .compose(new RxSchedulerTransformer<CartAddInfo>())
                .subscribe(new Consumer<CartAddInfo>() {
                    @Override
                    public void accept(CartAddInfo cartAddInfo) throws Exception {
                        generalGoodsDetailsView.dismissLoading();
                        generalGoodsDetailsView.renderCartAdd();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        generalGoodsDetailsView.dismissLoading();
                        generalGoodsDetailsView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getGeneralGoodsDetailsInfo(String goodsId) {
        generalGoodsDetailsView.showLoading(StaticData.LOADING);
        String queryString = "goodsId=" +goodsId;
        String sign = SignUnit.signGet(RequestUrl.GOODS_BASE_GOOD, queryString);
        Disposable disposable = restApiService.getGeneralGoodsDetailsInfo(sign,goodsId)
                .flatMap(new RxRemoteDataParse<GeneralGoodsDetailsInfo>())
                .compose(new RxSchedulerTransformer<GeneralGoodsDetailsInfo>())
                .subscribe(new Consumer<GeneralGoodsDetailsInfo>() {
                    @Override
                    public void accept(GeneralGoodsDetailsInfo generalGoodsDetailsInfo) throws Exception {
                        generalGoodsDetailsView.dismissLoading();
                        generalGoodsDetailsView.renderGeneralGoodsDetailsInfo(generalGoodsDetailsInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        generalGoodsDetailsView.dismissLoading();
                        generalGoodsDetailsView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void buyNow(String productId, String number) {
        generalGoodsDetailsView.showLoading(StaticData.PROCESSING);
        BuyNowRequest request = new BuyNowRequest(productId, number);
        String sign = SignUnit.signPost(RequestUrl.CART_FAST_ADD, gson.toJson(request));
        Disposable disposable = restApiService.buyNow(sign, request)
                .flatMap(new RxRemoteDataParse<ConfirmCartOrderDetail>())
                .compose(new RxSchedulerTransformer<ConfirmCartOrderDetail>())
                .subscribe(new Consumer<ConfirmCartOrderDetail>() {
                    @Override
                    public void accept(ConfirmCartOrderDetail confirmCartOrderDetail) throws Exception {
                        generalGoodsDetailsView.dismissLoading();
                        generalGoodsDetailsView.renderBuyNow(confirmCartOrderDetail);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        generalGoodsDetailsView.dismissLoading();
                        generalGoodsDetailsView.showError(throwable);
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
