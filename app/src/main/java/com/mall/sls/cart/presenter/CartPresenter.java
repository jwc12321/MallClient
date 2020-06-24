package com.mall.sls.cart.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mall.sls.cart.CartContract;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.CartAddInfo;
import com.mall.sls.data.entity.CartInfo;
import com.mall.sls.data.entity.ConfirmCartOrderDetail;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.data.request.CartGeneralCheckedRequest;
import com.mall.sls.data.request.CartUpdateNumberRequest;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/6/24.
 * 描述：
 */
public class CartPresenter implements CartContract.CartPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private CartContract.CartView cartView;
    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    @Inject
    public CartPresenter(RestApiService restApiService, CartContract.CartView cartView) {
        this.restApiService = restApiService;
        this.cartView = cartView;
    }

    @Inject
    public void setupListener() {
        cartView.setPresenter(this);
    }


    @Override
    public void getCartInfo(String refreshType) {
        if (TextUtils.equals("1", refreshType)) {
            cartView.showLoading(StaticData.LOADING);
        }
        String queryString = "null";
        String sign = SignUnit.signGet(RequestUrl.CART_GENERAL_LIST, queryString);
        Disposable disposable = restApiService.getCartInfo(sign)
                .flatMap(new RxRemoteDataParse<CartInfo>())
                .compose(new RxSchedulerTransformer<CartInfo>())
                .subscribe(new Consumer<CartInfo>() {
                    @Override
                    public void accept(CartInfo cartInfo) throws Exception {
                        cartView.dismissLoading();
                        cartView.renderCartInfo(cartInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        cartView.dismissLoading();
                        cartView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void deleteCartItem(String id) {
        cartView.showLoading(StaticData.PROCESSING);
        String sign= SignUnit.signGet(RequestUrl.DELETE_CART_ITEM+id,"null");
        Disposable disposable = restApiService.deleteCartItem(sign,id)
                .flatMap(new RxRemoteDataParse<Boolean>())
                .compose(new RxSchedulerTransformer<Boolean>())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isBoolean) throws Exception {
                        cartView.dismissLoading();
                        cartView.renderDeleteCartItem(isBoolean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        cartView.dismissLoading();
                        cartView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void cartUpdateNumber(String id, String number) {
        cartView.showLoading(StaticData.PROCESSING);
        CartUpdateNumberRequest request = new CartUpdateNumberRequest(id,number);
        String sign = SignUnit.signPost(RequestUrl.CART_UPDATE_NUMBER, gson.toJson(request));
        Disposable disposable = restApiService.cartUpdateNumer(sign, request)
                .flatMap(new RxRemoteDataParse<CartAddInfo>())
                .compose(new RxSchedulerTransformer<CartAddInfo>())
                .subscribe(new Consumer<CartAddInfo>() {
                    @Override
                    public void accept(CartAddInfo cartAddInfo) throws Exception {
                        cartView.dismissLoading();
                        cartView.renderCartUpdateNumber();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        cartView.dismissLoading();
                        cartView.showError(throwable);
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
