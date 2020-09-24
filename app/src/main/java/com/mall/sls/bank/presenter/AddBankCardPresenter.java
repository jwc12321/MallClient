package com.mall.sls.bank.presenter;

import com.mall.sls.bank.BankContract;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.BankCardInfo;
import com.mall.sls.data.entity.CardInfo;
import com.mall.sls.data.entity.CertifyInfo;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.data.request.StartBindBankRequest;
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
public class AddBankCardPresenter implements BankContract.AddBankCardPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private BankContract.AddBankCardView addBankCardView;
    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    @Inject
    public AddBankCardPresenter(RestApiService restApiService, BankContract.AddBankCardView addBankCardView) {
        this.restApiService = restApiService;
        this.addBankCardView = addBankCardView;
    }


    @Inject
    public void setupListener() {
        addBankCardView.setPresenter(this);
    }


    @Override
    public void getCardInfo(String cardNo) {
        addBankCardView.showLoading(StaticData.PROCESSING);
        String queryString="cardNo="+cardNo;
        String sign= SignUnit.signGet(RequestUrl.CARD_INFO,queryString);
        Disposable disposable = restApiService.getCardInfo(sign,cardNo)
                .flatMap(new RxRemoteDataParse<CardInfo>())
                .compose(new RxSchedulerTransformer<CardInfo>())
                .subscribe(new Consumer<CardInfo>() {
                    @Override
                    public void accept(CardInfo cardInfo) throws Exception {
                        addBankCardView.dismissLoading();
                        addBankCardView.renderCardInfo(cardInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        addBankCardView.dismissLoading();
                        addBankCardView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getStartBindBankInfo(StartBindBankRequest request) {
        addBankCardView.showLoading(StaticData.PROCESSING);
        String sign= SignUnit.signPost(RequestUrl.START_BANK_BIND,gson.toJson(request));
        Disposable disposable = restApiService.getStartBindBankInfo(sign,request)
                .flatMap(new RxRemoteDataParse<BankCardInfo>())
                .compose(new RxSchedulerTransformer<BankCardInfo>())
                .subscribe(new Consumer<BankCardInfo>() {
                    @Override
                    public void accept(BankCardInfo bankCardInfo) throws Exception {
                        addBankCardView.dismissLoading();
                        addBankCardView.renderStartBindBankInfo(bankCardInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        addBankCardView.dismissLoading();
                        addBankCardView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getCertifyInfo() {
        String queryString = "null";
        String sign = SignUnit.signGet(RequestUrl.CERTIFY_INFO, queryString);
        Disposable disposable = restApiService.getCertifyInfo(sign)
                .flatMap(new RxRemoteDataParse<CertifyInfo>())
                .compose(new RxSchedulerTransformer<CertifyInfo>())
                .subscribe(new Consumer<CertifyInfo>() {
                    @Override
                    public void accept(CertifyInfo certifyInfo) throws Exception {
                        addBankCardView.renderCertifyInfo(certifyInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
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
