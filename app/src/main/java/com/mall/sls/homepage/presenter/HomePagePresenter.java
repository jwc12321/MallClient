package com.mall.sls.homepage.presenter;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mall.sls.BuildConfig;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.AppUrlInfo;
import com.mall.sls.data.entity.CouponInfo;
import com.mall.sls.data.entity.HomePageInfo;
import com.mall.sls.data.entity.HomeSnapUp;
import com.mall.sls.data.entity.Ignore;
import com.mall.sls.data.entity.TokenRefreshInfo;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.data.request.CodeRequest;
import com.mall.sls.data.request.TypeRequest;
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
public class HomePagePresenter implements HomepageContract.HomePagePresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private HomepageContract.HomePageView homePageView;
    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    @Inject
    public HomePagePresenter(RestApiService restApiService, HomepageContract.HomePageView homePageView) {
        this.restApiService = restApiService;
        this.homePageView = homePageView;
    }

    @Inject
    public void setupListener() {
        homePageView.setPresenter(this);
    }

    @Override
    public void getHomePageInfo(String refreshType) {
        homePageView.dismissLoading();
        if (TextUtils.equals(StaticData.REFRESH_ONE, refreshType)) {
            homePageView.showLoading(StaticData.LOADING);
        }
        String queryString="null";
        String sign= SignUnit.signGet(RequestUrl.HOME_INDEX_URL,queryString);
        Disposable disposable = restApiService.getHomePageInfo(sign)
                .flatMap(new RxRemoteDataParse<HomePageInfo>())
                .compose(new RxSchedulerTransformer<HomePageInfo>())
                .subscribe(new Consumer<HomePageInfo>() {
                    @Override
                    public void accept(HomePageInfo homePageInfo) throws Exception {
                        homePageView.dismissLoading();
                        homePageView.renderHomePageInfo(homePageInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        homePageView.dismissLoading();
                        homePageView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void bindWx(String code) {
        homePageView.showLoading(StaticData.PROCESSING);
        CodeRequest request=new CodeRequest(code);
        String sign= SignUnit.signPost(RequestUrl.BIND_WX,gson.toJson(request));
        Disposable disposable = restApiService.bindWx(sign,request)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        homePageView.dismissLoading();
                        homePageView.renderBindWx();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        homePageView.dismissLoading();
                        homePageView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getAppUrlInfo() {
        String queryString="version="+BuildConfig.VERSION_NAME;
        String sign=SignUnit.signGet(RequestUrl.APP_URL_INFO_URL,queryString);
        Disposable disposable = restApiService.getAppUrlInfo(sign,BuildConfig.VERSION_NAME)
                .flatMap(new RxRemoteDataParse<AppUrlInfo>())
                .compose(new RxSchedulerTransformer<AppUrlInfo>())
                .subscribe(new Consumer<AppUrlInfo>() {
                    @Override
                    public void accept(AppUrlInfo appUrlInfo) throws Exception {
                        homePageView.renderAppUrlInfo(appUrlInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        homePageView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void couponReceive(String type) {
        homePageView.showLoading(StaticData.PROCESSING);
        TypeRequest request=new TypeRequest(type);
        String sign= SignUnit.signPost(RequestUrl.COUPON_RECEIVE,gson.toJson(request));
        Disposable disposable = restApiService.couponReceive(sign,request)
                .flatMap(new RxRemoteDataParse<List<CouponInfo>>())
                .compose(new RxSchedulerTransformer<List<CouponInfo>>())
                .subscribe(new Consumer<List<CouponInfo>>() {
                    @Override
                    public void accept(List<CouponInfo> couponInfos) throws Exception {
                        homePageView.dismissLoading();
                        homePageView.renderCouponReceive(couponInfos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        homePageView.dismissLoading();
                        homePageView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getHomeSnapUp() {
        String queryString="null";
        String sign= SignUnit.signGet(RequestUrl.SNAP_UP,queryString);
        Disposable disposable = restApiService.getHomeSnapUp(sign)
                .flatMap(new RxRemoteDataParse<HomeSnapUp>())
                .compose(new RxSchedulerTransformer<HomeSnapUp>())
                .subscribe(new Consumer<HomeSnapUp>() {
                    @Override
                    public void accept(HomeSnapUp homeSnapUp) throws Exception {
                        homePageView.renderHomeSnapUp(homeSnapUp);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        homePageView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getTokenRefreshInfo() {
        String queryString = "null";
        String sign = SignUnit.signGet(RequestUrl.TOKEN_REFRESH, queryString);
        Disposable disposable = restApiService.getTokenRefreshInfo(sign)
                .flatMap(new RxRemoteDataParse<TokenRefreshInfo>())
                .compose(new RxSchedulerTransformer<TokenRefreshInfo>())
                .subscribe(new Consumer<TokenRefreshInfo>() {
                    @Override
                    public void accept(TokenRefreshInfo tokenRefreshInfo) throws Exception {
                        homePageView.renderTokenRefreshInfo(tokenRefreshInfo);
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
