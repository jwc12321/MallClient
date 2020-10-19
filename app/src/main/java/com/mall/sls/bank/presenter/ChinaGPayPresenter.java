package com.mall.sls.bank.presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mall.sls.bank.BankContract;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.BankPayInfo;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.data.request.ChinaGPayRequest;
import com.mall.sls.data.request.ChinaGSendCodeRequest;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/10/19.
 * 描述：
 */
public class ChinaGPayPresenter implements BankContract.ChinaGPayPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private BankContract.ChinaGPayView chinaGPayView;
    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    @Inject
    public ChinaGPayPresenter(RestApiService restApiService, BankContract.ChinaGPayView chinaGPayView) {
        this.restApiService = restApiService;
        this.chinaGPayView = chinaGPayView;
    }

    @Inject
    public void setupListener() {
        chinaGPayView.setPresenter(this);
    }

    @Override
    public void chinaGSendCode(String tn) {
        chinaGPayView.showLoading(StaticData.PROCESSING);
        ChinaGSendCodeRequest request=new ChinaGSendCodeRequest(tn);
        String sign= SignUnit.signPost(RequestUrl.CHINA_G_SEND_CODE,gson.toJson(request));
        Disposable disposable = restApiService.chinaGSendCode(sign,request)
                .flatMap(new RxRemoteDataParse<Boolean>())
                .compose(new RxSchedulerTransformer<Boolean>())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isBoolean) throws Exception {
                        chinaGPayView.dismissLoading();
                        chinaGPayView.renderChinaGSendCode(isBoolean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        chinaGPayView.dismissLoading();
                        chinaGPayView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void chinaGPay(String payId, String smsCode, String tn) {
        chinaGPayView.showLoading(StaticData.PROCESSING);
        ChinaGPayRequest request=new ChinaGPayRequest(payId,smsCode,tn);
        String sign= SignUnit.signPost(RequestUrl.CHINA_G_PAY,gson.toJson(request));
        Disposable disposable = restApiService.chinaGPay(sign,request)
                .flatMap(new RxRemoteDataParse<BankPayInfo>())
                .compose(new RxSchedulerTransformer<BankPayInfo>())
                .subscribe(new Consumer<BankPayInfo>() {
                    @Override
                    public void accept(BankPayInfo bankPayInfo) throws Exception {
                        chinaGPayView.dismissLoading();
                        chinaGPayView.renderChinaGPay(bankPayInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        chinaGPayView.dismissLoading();
                        chinaGPayView.showError(throwable);
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
