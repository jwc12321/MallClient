package com.mall.sls.homepage.presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.ConfirmOrderDetail;
import com.mall.sls.data.entity.GoodsDetailsInfo;
import com.mall.sls.data.entity.InvitationCodeInfo;
import com.mall.sls.data.entity.WXGoodsDetailsInfo;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.data.request.CartFastaddRequest;
import com.mall.sls.homepage.HomepageContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/5/25.
 * 描述：
 */
public class WXGoodsDetailsPresenter implements HomepageContract.WXGoodsDetailsPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private HomepageContract.WXGoodsDetailsView wxGoodsDetailsView;
    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    @Inject
    public WXGoodsDetailsPresenter(RestApiService restApiService, HomepageContract.WXGoodsDetailsView wxGoodsDetailsView) {
        this.restApiService = restApiService;
        this.wxGoodsDetailsView = wxGoodsDetailsView;
    }

    @Inject
    public void setupListener() {
        wxGoodsDetailsView.setPresenter(this);
    }

    @Override
    public void getWXGoodsDetailsInfo(String goodsProductId, String grouponId) {
        wxGoodsDetailsView.showLoading(StaticData.LOADING);
        String queryString="goodsProductId="+goodsProductId+"&grouponId="+ grouponId;
        String sign= SignUnit.signGet(RequestUrl.WX_GOODS_DETAILS,queryString);
        Disposable disposable = restApiService.getWXGoodsDetailsInfo(sign,goodsProductId,grouponId)
                .flatMap(new RxRemoteDataParse<GoodsDetailsInfo>())
                .compose(new RxSchedulerTransformer<GoodsDetailsInfo>())
                .subscribe(new Consumer<GoodsDetailsInfo>() {
                    @Override
                    public void accept(GoodsDetailsInfo goodsDetailsInfo) throws Exception {
                        wxGoodsDetailsView.dismissLoading();
                        wxGoodsDetailsView.renderWXGoodsDetailsInfo(goodsDetailsInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        wxGoodsDetailsView.dismissLoading();
                        wxGoodsDetailsView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void cartFastAdd(String goodsId, String productId, boolean isGroup, String number, String groupId, String groupRulesId) {
        wxGoodsDetailsView.showLoading(StaticData.PROCESSING);
        CartFastaddRequest request=new CartFastaddRequest(goodsId,productId,isGroup,number,groupId,groupRulesId);
        String sign= SignUnit.signPost(RequestUrl.CART_FAST_ADD_URL,gson.toJson(request));
        Disposable disposable = restApiService.cartFastAdd(sign,request)
                .flatMap(new RxRemoteDataParse<ConfirmOrderDetail>())
                .compose(new RxSchedulerTransformer<ConfirmOrderDetail>())
                .subscribe(new Consumer<ConfirmOrderDetail>() {
                    @Override
                    public void accept(ConfirmOrderDetail confirmOrderDetail) throws Exception {
                        wxGoodsDetailsView.dismissLoading();
                        wxGoodsDetailsView.renderCartFastAdd(confirmOrderDetail);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        wxGoodsDetailsView.dismissLoading();
                        wxGoodsDetailsView.showError(throwable);
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
                        wxGoodsDetailsView.renderInvitationCodeInfo(invitationCodeInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        wxGoodsDetailsView.showError(throwable);
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
