package com.mall.sls.homepage.presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.ConfirmCartOrderDetail;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.data.request.CartGeneralCheckedRequest;
import com.mall.sls.homepage.HomepageContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/6/24.
 * 描述：
 */
public class CartConfirmOrderPresenter implements HomepageContract.CartConfirmOrderPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private HomepageContract.CartConfirmOrderView cartConfirmOrderView;
    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    @Inject
    public CartConfirmOrderPresenter(RestApiService restApiService, HomepageContract.CartConfirmOrderView cartConfirmOrderView) {
        this.restApiService = restApiService;
        this.cartConfirmOrderView = cartConfirmOrderView;
    }
    @Inject
    public void setupListener() {
        cartConfirmOrderView.setPresenter(this);
    }


    @Override
    public void cartGeneralChecked(String addressId, List<String> ids, String userCouponId) {
        cartConfirmOrderView.showLoading(StaticData.PROCESSING);
        CartGeneralCheckedRequest request = new CartGeneralCheckedRequest(addressId,ids, userCouponId);
        String sign = SignUnit.signPost(RequestUrl.CART_GENERAL_CHECKED, gson.toJson(request));
        Disposable disposable = restApiService.cartGeneralChecked(sign, request)
                .flatMap(new RxRemoteDataParse<ConfirmCartOrderDetail>())
                .compose(new RxSchedulerTransformer<ConfirmCartOrderDetail>())
                .subscribe(new Consumer<ConfirmCartOrderDetail>() {
                    @Override
                    public void accept(ConfirmCartOrderDetail confirmCartOrderDetail) throws Exception {
                        cartConfirmOrderView.dismissLoading();
                        cartConfirmOrderView.renderCartGeneralChecked(confirmCartOrderDetail);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        cartConfirmOrderView.dismissLoading();
                        cartConfirmOrderView.showError(throwable);
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
