package com.mall.sls.bank.presenter;

import com.mall.sls.bank.BankContract;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.BankCardInfo;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/9/10.
 * 描述：
 */
public class BankCardSPresenter implements BankContract.BankCardSPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private BankContract.BankCardSView bankCardSView;

    @Inject
    public BankCardSPresenter(RestApiService restApiService, BankContract.BankCardSView bankCardSView) {
        this.restApiService = restApiService;
        this.bankCardSView = bankCardSView;
    }

    @Inject
    public void setupListener() {
        bankCardSView.setPresenter(this);
    }

    @Override
    public void getBankCardInfos() {
        bankCardSView.showLoading(StaticData.LOADING);
        String queryString="null";
        String sign= SignUnit.signGet(RequestUrl.BANK_CARD_LIST,queryString);
        Disposable disposable = restApiService.getBankCardInfos(sign)
                .flatMap(new RxRemoteDataParse<List<BankCardInfo>>())
                .compose(new RxSchedulerTransformer<List<BankCardInfo>>())
                .subscribe(new Consumer<List<BankCardInfo>>() {
                    @Override
                    public void accept(List<BankCardInfo> bankCardInfos) throws Exception {
                        bankCardSView.dismissLoading();
                        bankCardSView.renderBankCardS(bankCardInfos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        bankCardSView.dismissLoading();
                        bankCardSView.showError(throwable);
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
