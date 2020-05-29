package com.mall.sls.homepage.presenter;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.ConfirmOrderDetail;
import com.mall.sls.data.entity.GoodsDetailsInfo;
import com.mall.sls.data.entity.Ignore;
import com.mall.sls.data.entity.InvitationCodeInfo;
import com.mall.sls.data.entity.ShareInfo;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.data.request.CartFastaddRequest;
import com.mall.sls.data.request.GroupRemindRequest;
import com.mall.sls.homepage.HomepageContract;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/5/13.
 * 描述：
 */
public class GoodsDetailsPresenter implements HomepageContract.GoodsDetailsPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private HomepageContract.GoodsDetailsView goodsDetailsView;
    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    @Inject
    public GoodsDetailsPresenter(RestApiService restApiService, HomepageContract.GoodsDetailsView goodsDetailsView) {
        this.restApiService = restApiService;
        this.goodsDetailsView = goodsDetailsView;
    }

    @Inject
    public void setupListener() {
        goodsDetailsView.setPresenter(this);
    }

    @Override
    public void getGoodsDetails(String goodsId) {
        goodsDetailsView.showLoading(StaticData.LOADING);
        String queryString = "goodsId=" +goodsId;
        String sign = SignUnit.signGet(RequestUrl.GOODS_DETAILS_URL, queryString);
        Disposable disposable = restApiService.getGoodsDetailsInfo(sign,goodsId)
                .flatMap(new RxRemoteDataParse<GoodsDetailsInfo>())
                .compose(new RxSchedulerTransformer<GoodsDetailsInfo>())
                .subscribe(new Consumer<GoodsDetailsInfo>() {
                    @Override
                    public void accept(GoodsDetailsInfo goodsDetailsInfo) throws Exception {
                        goodsDetailsView.dismissLoading();
                        goodsDetailsView.renderGoodsDetails(goodsDetailsInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        goodsDetailsView.dismissLoading();
                        goodsDetailsView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getConsumerPhone() {
        String queryString = "null";
        String sign = SignUnit.signGet(RequestUrl.CUSTOMER_PHONE_URL, queryString);
        Disposable disposable = restApiService.getConsumerPhone(sign)
                .flatMap(new RxRemoteDataParse<String>())
                .compose(new RxSchedulerTransformer<String>())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String consumerPhone) throws Exception {
                        goodsDetailsView.renderConsumerPhone(consumerPhone);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        goodsDetailsView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void cartFastAdd(String goodsId, String productId, boolean isGroup, String number, String groupId, String groupRulesId) {
        goodsDetailsView.showLoading(StaticData.PROCESSING);
        CartFastaddRequest request=new CartFastaddRequest(goodsId,productId,isGroup,number,groupId,groupRulesId);
        String sign= SignUnit.signPost(RequestUrl.CART_FAST_ADD_URL,gson.toJson(request));
        Disposable disposable = restApiService.cartFastAdd(sign,request)
                .flatMap(new RxRemoteDataParse<ConfirmOrderDetail>())
                .compose(new RxSchedulerTransformer<ConfirmOrderDetail>())
                .subscribe(new Consumer<ConfirmOrderDetail>() {
                    @Override
                    public void accept(ConfirmOrderDetail confirmOrderDetail) throws Exception {
                        goodsDetailsView.dismissLoading();
                        goodsDetailsView.renderCartFastAdd(confirmOrderDetail);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        goodsDetailsView.dismissLoading();
                        goodsDetailsView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void groupRemind(String ruleId) {
        goodsDetailsView.showLoading(StaticData.PROCESSING);
        GroupRemindRequest request=new GroupRemindRequest(ruleId,StaticData.REFLASH_ONE);
        String sign= SignUnit.signPost(RequestUrl.GROUP_REMIND_URL,gson.toJson(request));
        Disposable disposable = restApiService.groupRemind(sign,request)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        goodsDetailsView.dismissLoading();
                        goodsDetailsView.renderGroupRemind();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        goodsDetailsView.dismissLoading();
                        goodsDetailsView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getInvitationCodeInfo() {
        String queryString = "null";
        String sign = SignUnit.signGet(RequestUrl.WX_INVITATION_CODE, queryString);
        Disposable disposable = restApiService.getInvitationCodeInfo(sign)
                .flatMap(new RxRemoteDataParse<InvitationCodeInfo>())
                .compose(new RxSchedulerTransformer<InvitationCodeInfo>())
                .subscribe(new Consumer<InvitationCodeInfo>() {
                    @Override
                    public void accept(InvitationCodeInfo invitationCodeInfo) throws Exception {
                        goodsDetailsView.renderInvitationCodeInfo(invitationCodeInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        goodsDetailsView.showError(throwable);
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
