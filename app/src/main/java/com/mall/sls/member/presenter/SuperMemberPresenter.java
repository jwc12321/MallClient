package com.mall.sls.member.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.BaoFuPayInfo;
import com.mall.sls.data.entity.LocalTeam;
import com.mall.sls.data.entity.WXPaySignResponse;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.data.request.PayRequest;
import com.mall.sls.data.request.UserPayDtoRequest;
import com.mall.sls.member.MemberContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/5/20.
 * 描述：
 */
public class SuperMemberPresenter implements MemberContract.SuperMemberPresente {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private MemberContract.SuperMemberView superMemberView;
    private int currentIndex = 1;  //当前index
    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    @Inject
    public SuperMemberPresenter(RestApiService restApiService, MemberContract.SuperMemberView superMemberView) {
        this.restApiService = restApiService;
        this.superMemberView = superMemberView;
    }

    @Inject
    public void setupListener() {
        superMemberView.setPresenter(this);
    }

    @Override
    public void getVipGroupons(String refreshType) {
        if (TextUtils.equals(StaticData.REFRESH_ONE, refreshType)) {
            superMemberView.showLoading(StaticData.LOADING);
        }
        currentIndex=1;
        String queryString="page="+currentIndex+"&limit="+StaticData.TEN_LIST_SIZE;
        String sign= SignUnit.signGet(RequestUrl.VIP_GROUPONS_URL,queryString);
        Disposable disposable = restApiService.getVipGroupons(sign,String.valueOf(currentIndex),StaticData.TEN_LIST_SIZE)
                .flatMap(new RxRemoteDataParse<LocalTeam>())
                .compose(new RxSchedulerTransformer<LocalTeam>())
                .subscribe(new Consumer<LocalTeam>() {
                    @Override
                    public void accept(LocalTeam localTeam) throws Exception {
                        superMemberView.dismissLoading();
                        superMemberView.renderVipGroupons(localTeam);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        superMemberView.dismissLoading();
                        superMemberView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreVipGroupons() {
        currentIndex=currentIndex+1;
        String queryString="page="+currentIndex+"&limit="+StaticData.TEN_LIST_SIZE;
        String sign= SignUnit.signGet(RequestUrl.VIP_GROUPONS_URL,queryString);
        Disposable disposable = restApiService.getVipGroupons(sign,String.valueOf(currentIndex),StaticData.TEN_LIST_SIZE)
                .flatMap(new RxRemoteDataParse<LocalTeam>())
                .compose(new RxSchedulerTransformer<LocalTeam>())
                .subscribe(new Consumer<LocalTeam>() {
                    @Override
                    public void accept(LocalTeam localTeam) throws Exception {
                        superMemberView.dismissLoading();
                        superMemberView.renderMoreVipGroupons(localTeam);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        superMemberView.dismissLoading();
                        superMemberView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void vipOpen() {
        superMemberView.showLoading(StaticData.PROCESSING);
        String sign= SignUnit.signPost(RequestUrl.VIP_OPEN_URL,"null");
        Disposable disposable = restApiService.vipOpen(sign)
                .flatMap(new RxRemoteDataParse<Boolean>())
                .compose(new RxSchedulerTransformer<Boolean>())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isBoolean) throws Exception {
                        superMemberView.dismissLoading();
                        superMemberView.renderVipOpen(isBoolean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        superMemberView.dismissLoading();
                        superMemberView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }
    @Override
    public void getWxPay(String orderId, String orderType, String paymentMethod) {
        superMemberView.showLoading(StaticData.PROCESSING);
        PayRequest request=new PayRequest(orderId,orderType,paymentMethod);
        String sign= SignUnit.signPost(RequestUrl.BEGIN_PAY,gson.toJson(request));
        Disposable disposable = restApiService.getWxPay(sign,request)
                .flatMap(new RxRemoteDataParse<WXPaySignResponse>())
                .compose(new RxSchedulerTransformer<WXPaySignResponse>())
                .subscribe(new Consumer<WXPaySignResponse>() {
                    @Override
                    public void accept(WXPaySignResponse wxPaySignResponse) throws Exception {
                        superMemberView.dismissLoading();
                        superMemberView.renderWxPay(wxPaySignResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        superMemberView.dismissLoading();
                        superMemberView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getAliPay(String orderId, String orderType, String paymentMethod) {
        superMemberView.showLoading(StaticData.PROCESSING);
        PayRequest request=new PayRequest(orderId,orderType,paymentMethod);
        String sign= SignUnit.signPost(RequestUrl.BEGIN_PAY,gson.toJson(request));
        Disposable disposable = restApiService.getAliPay(sign,request)
                .flatMap(new RxRemoteDataParse<String>())
                .compose(new RxSchedulerTransformer<String>())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String aliPayStr) throws Exception {
                        superMemberView.dismissLoading();
                        superMemberView.renderAliPay(aliPayStr);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        superMemberView.dismissLoading();
                        superMemberView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getBaoFuPay(String orderId, String orderType, String paymentMethod) {
        superMemberView.showLoading(StaticData.PROCESSING);
        PayRequest request=new PayRequest(orderId,orderType,paymentMethod);
        String sign= SignUnit.signPost(RequestUrl.BEGIN_PAY,gson.toJson(request));
        Disposable disposable = restApiService.getBaoFuPay(sign,request)
                .flatMap(new RxRemoteDataParse<BaoFuPayInfo>())
                .compose(new RxSchedulerTransformer<BaoFuPayInfo>())
                .subscribe(new Consumer<BaoFuPayInfo>() {
                    @Override
                    public void accept(BaoFuPayInfo baoFuPayInfo) throws Exception {
                        superMemberView.dismissLoading();
                        superMemberView.renderBaoFuPay(baoFuPayInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        superMemberView.dismissLoading();
                        superMemberView.showError(throwable);
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
