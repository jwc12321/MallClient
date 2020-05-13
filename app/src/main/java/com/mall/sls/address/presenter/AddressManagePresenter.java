package com.mall.sls.address.presenter;

import com.mall.sls.address.AddressContract;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.AddressInfo;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/5/13.
 * 描述：
 */
public class AddressManagePresenter implements AddressContract.AddressManagePresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private AddressContract.AddressManageView addressManageView;

    @Inject
    public AddressManagePresenter(RestApiService restApiService, AddressContract.AddressManageView addressManageView) {
        this.restApiService = restApiService;
        this.addressManageView = addressManageView;
    }
    @Inject
    public void setupListener() {
        addressManageView.setPresenter(this);
    }

    @Override
    public void getAddressInfo() {
        addressManageView.showLoading(StaticData.LOADING);
        String queryString="null";
        String sign= SignUnit.signGet(RequestUrl.ADDRESS_MANAGE_URL,queryString);
        Disposable disposable = restApiService.getAddressInfo(sign)
                .flatMap(new RxRemoteDataParse<List<AddressInfo>>())
                .compose(new RxSchedulerTransformer<List<AddressInfo>>())
                .subscribe(new Consumer<List<AddressInfo>>() {
                    @Override
                    public void accept(List<AddressInfo> addressInfos) throws Exception {
                        addressManageView.dismissLoading();
                        addressManageView.renderAddressInfo(addressInfos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        addressManageView.dismissLoading();
                        addressManageView.showError(throwable);
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
