package com.mall.sls.address.presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mall.sls.address.AddressContract;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.Ignore;
import com.mall.sls.data.entity.ProvinceBean;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.data.request.AddAddressRequest;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/5/13.
 * 描述：
 */
public class AddAddressPresenter implements AddressContract.AddAddressPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private AddressContract.AddAddressView addAddressView;
    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    @Inject
    public AddAddressPresenter(RestApiService restApiService, AddressContract.AddAddressView addAddressView) {
        this.restApiService = restApiService;
        this.addAddressView = addAddressView;
    }

    @Inject
    public void setupListener() {
        addAddressView.setPresenter(this);
    }

    @Override
    public void addAddress(AddAddressRequest addAddressRequest) {
        addAddressView.showLoading(StaticData.PROCESSING);
        String sign= SignUnit.signPost(RequestUrl.ADDRESS_SAVE_URL,gson.toJson(addAddressRequest));
        Disposable disposable = restApiService.addAddress(sign,addAddressRequest)
                .flatMap(new RxRemoteDataParse<String>())
                .compose(new RxSchedulerTransformer<String>())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String addressId) throws Exception {
                        addAddressView.dismissLoading();
                        addAddressView.renderAddAddress(addressId);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        addAddressView.dismissLoading();
                        addAddressView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getAreas() {
        addAddressView.showLoading(StaticData.LOADING);
        String queryString = "null";
        String sign = SignUnit.signGet(RequestUrl.COMMON_AREA_URL, queryString);
        Disposable disposable = restApiService.getAreas(sign)
                .flatMap(new RxRemoteDataParse<List<ProvinceBean>>())
                .compose(new RxSchedulerTransformer<List<ProvinceBean>>())
                .subscribe(new Consumer<List<ProvinceBean>>() {
                    @Override
                    public void accept(List<ProvinceBean> provinceBeans) throws Exception {
                        addAddressView.renderAresa(provinceBeans);
                        addAddressView.dismissLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        addAddressView.showError(throwable);
                        addAddressView.dismissLoading();
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void deleteAddress(String id) {
        addAddressView.showLoading(StaticData.PROCESSING);
        String sign= SignUnit.signDelete(RequestUrl.DELETE_ADDRESS_URL+id,"null");
        Disposable disposable = restApiService.deleteAddress(sign,id)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        addAddressView.dismissLoading();
                        addAddressView.renderDeleteAddress();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        addAddressView.dismissLoading();
                        addAddressView.showError(throwable);
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
