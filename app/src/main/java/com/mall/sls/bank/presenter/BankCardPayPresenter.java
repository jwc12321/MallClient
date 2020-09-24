package com.mall.sls.bank.presenter;

import com.mall.sls.bank.BankContract;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.BankCardInfo;
import com.mall.sls.data.entity.BankPayInfo;
import com.mall.sls.data.entity.Ignore;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.data.request.BankPayRequest;
import com.mall.sls.data.request.MobileRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/9/11.
 * 描述：
 */
public class BankCardPayPresenter implements BankContract.BankCardPayPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private BankContract.BankCardPayView bankCardPayView;
    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    @Inject
    public BankCardPayPresenter(RestApiService restApiService, BankContract.BankCardPayView bankCardPayView) {
        this.restApiService = restApiService;
        this.bankCardPayView = bankCardPayView;
    }

    @Inject
    public void setupListener() {
        bankCardPayView.setPresenter(this);
    }

    @Override
    public void getBankCardInfos() {
        bankCardPayView.showLoading(StaticData.LOADING);
        String queryString="null";
        String sign= SignUnit.signGet(RequestUrl.BANK_CARD_LIST,queryString);
        Disposable disposable = restApiService.getBankCardInfos(sign)
                .flatMap(new RxRemoteDataParse<List<BankCardInfo>>())
                .compose(new RxSchedulerTransformer<List<BankCardInfo>>())
                .subscribe(new Consumer<List<BankCardInfo>>() {
                    @Override
                    public void accept(List<BankCardInfo> bankCardInfos) throws Exception {
                        bankCardPayView.dismissLoading();
                        bankCardPayView.renderBankCardS(bankCardInfos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        bankCardPayView.dismissLoading();
                        bankCardPayView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void baoFooSinglePay(BankPayRequest request) {
        bankCardPayView.showLoading(StaticData.PROCESSING);
        String sign= SignUnit.signPost(RequestUrl.BAO_FOO_SINGLE_PAY,gson.toJson(request));
        Disposable disposable = restApiService.baoFooSinglePay(sign,request)
                .flatMap(new RxRemoteDataParse<BankPayInfo>())
                .compose(new RxSchedulerTransformer<BankPayInfo>())
                .subscribe(new Consumer<BankPayInfo>() {
                    @Override
                    public void accept(BankPayInfo bankPayInfo) throws Exception {
                        bankCardPayView.dismissLoading();
                        bankCardPayView.renderBankPayInfo(bankPayInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        bankCardPayView.dismissLoading();
                        bankCardPayView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void sendBaoFuCode(String mobile) {
        bankCardPayView.showLoading(StaticData.PROCESSING);
        MobileRequest request=new MobileRequest(mobile);
        String sign= SignUnit.signPost(RequestUrl.SEND_BAO_FU_CODE,gson.toJson(request));
        Disposable disposable = restApiService.sendBaoFuCode(sign,request)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        bankCardPayView.dismissLoading();
                        bankCardPayView.renderBaoFuCode();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        bankCardPayView.dismissLoading();
                        bankCardPayView.showError(throwable);
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
