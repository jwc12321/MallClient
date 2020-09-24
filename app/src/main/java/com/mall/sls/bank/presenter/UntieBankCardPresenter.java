package com.mall.sls.bank.presenter;

import com.mall.sls.bank.BankContract;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
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
public class UntieBankCardPresenter implements BankContract.UntieBankCardPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private BankContract.UntieBankCardView untieBankCardView;

    @Inject
    public UntieBankCardPresenter(RestApiService restApiService, BankContract.UntieBankCardView untieBankCardView) {
        this.restApiService = restApiService;
        this.untieBankCardView = untieBankCardView;
    }


    @Inject
    public void setupListener() {
        untieBankCardView.setPresenter(this);
    }

    @Override
    public void untieBankCard(String id) {
        untieBankCardView.showLoading(StaticData.PROCESSING);
        String sign= SignUnit.signPost(RequestUrl.BANK_BEGIN+id+RequestUrl.BANK_UNTIE,"null");
        Disposable disposable = restApiService.untieBankCard(sign,id)
                .flatMap(new RxRemoteDataParse<Boolean>())
                .compose(new RxSchedulerTransformer<Boolean>())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isBoolean) throws Exception {
                        untieBankCardView.dismissLoading();
                        untieBankCardView.renderUntieBankCard(isBoolean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        untieBankCardView.dismissLoading();
                        untieBankCardView.showError(throwable);
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
