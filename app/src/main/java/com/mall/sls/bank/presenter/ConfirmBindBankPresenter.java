package com.mall.sls.bank.presenter;

import com.mall.sls.bank.BankContract;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.BindResultInfo;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.data.request.ConfirmBindBankRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/9/10.
 * 描述：
 */
public class ConfirmBindBankPresenter implements BankContract.ConfirmBindBankPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private BankContract.ConfirmBindBankView confirmBindBankView;
    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    @Inject
    public ConfirmBindBankPresenter(RestApiService restApiService, BankContract.ConfirmBindBankView confirmBindBankView) {
        this.restApiService = restApiService;
        this.confirmBindBankView = confirmBindBankView;
    }


    @Inject
    public void setupListener() {
        confirmBindBankView.setPresenter(this);
    }

    @Override
    public void confirmBindBank(String id, String smsCode) {
        confirmBindBankView.showLoading(StaticData.PROCESSING);
        ConfirmBindBankRequest request=new ConfirmBindBankRequest(id,smsCode);
        String sign= SignUnit.signPost(RequestUrl.CONFIRM_BANK_BIND,gson.toJson(request));
        Disposable disposable = restApiService.confirmBindBank(sign,request)
                .flatMap(new RxRemoteDataParse<BindResultInfo>())
                .compose(new RxSchedulerTransformer<BindResultInfo>())
                .subscribe(new Consumer<BindResultInfo>() {
                    @Override
                    public void accept(BindResultInfo bindResultInfo) throws Exception {
                        confirmBindBankView.dismissLoading();
                        confirmBindBankView.renderConfirmBindBank(bindResultInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        confirmBindBankView.dismissLoading();
                        confirmBindBankView.showError(throwable);
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
