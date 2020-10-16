package com.mall.sls.bank.presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mall.sls.bank.BankContract;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.CertifyInfo;
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
public class AddChinaGCardPresenter implements BankContract.AddChinaGCardPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private BankContract.AddChinaGCardView addChinaGCardView;

    @Inject
    public AddChinaGCardPresenter(RestApiService restApiService, BankContract.AddChinaGCardView addChinaGCardView) {
        this.restApiService = restApiService;
        this.addChinaGCardView = addChinaGCardView;
    }

    @Inject
    public void setupListener() {
        addChinaGCardView.setPresenter(this);
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
                        addChinaGCardView.renderCertifyInfo(certifyInfo);
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
