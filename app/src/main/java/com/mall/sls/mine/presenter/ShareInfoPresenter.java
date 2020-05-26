package com.mall.sls.mine.presenter;

import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.ShareInfo;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.mine.MineContract;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/5/26.
 * 描述：
 */
public class ShareInfoPresenter implements MineContract.ShareInfoPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private MineContract.ShareInfoView shareInfoView;

    @Inject
    public ShareInfoPresenter(RestApiService restApiService, MineContract.ShareInfoView shareInfoView) {
        this.restApiService = restApiService;
        this.shareInfoView = shareInfoView;
    }

    @Inject
    public void setupListener() {
        shareInfoView.setPresenter(this);
    }

    @Override
    public void getShareInfo() {
        shareInfoView.showLoading(StaticData.LOADING);
        String queryString = "null";
        String sign = SignUnit.signGet(RequestUrl.AUTH_SHARE_URL, queryString);
        Disposable disposable = restApiService.getShareInfo(sign)
                .flatMap(new RxRemoteDataParse<ShareInfo>())
                .compose(new RxSchedulerTransformer<ShareInfo>())
                .subscribe(new Consumer<ShareInfo>() {
                    @Override
                    public void accept(ShareInfo shareInfo) throws Exception {
                        shareInfoView.dismissLoading();
                        shareInfoView.renderShareInfo(shareInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        shareInfoView.dismissLoading();
                        shareInfoView.showError(throwable);
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
