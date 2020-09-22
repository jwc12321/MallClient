package com.mall.sls.mine.presenter;

import android.text.TextUtils;

import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.InviteInfo;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.mine.MineContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/5/19.
 * 描述：
 */
public class MyInvitePresenter implements MineContract.MyInvitePresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private MineContract.MyInviteView myInviteView;

    @Inject
    public MyInvitePresenter(RestApiService restApiService, MineContract.MyInviteView myInviteView) {
        this.restApiService = restApiService;
        this.myInviteView = myInviteView;
    }

    @Inject
    public void setupListener() {
        myInviteView.setPresenter(this);
    }

    @Override
    public void getMyInvite(String refreshType) {
        if (TextUtils.equals(StaticData.REFLASH_ONE, refreshType)) {
            myInviteView.showLoading(StaticData.LOADING);
        }
        String queryString = "null";
        String sign = SignUnit.signGet(RequestUrl.AUTH_INVITER, queryString);
        Disposable disposable = restApiService.getInviteInfos(sign)
                .flatMap(new RxRemoteDataParse<List<InviteInfo>>())
                .compose(new RxSchedulerTransformer<List<InviteInfo>>())
                .subscribe(new Consumer<List<InviteInfo>>() {
                    @Override
                    public void accept(List<InviteInfo> inviteInfos) throws Exception {
                        myInviteView.dismissLoading();
                        myInviteView.renderMyInvite(inviteInfos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        myInviteView.dismissLoading();
                        myInviteView.showError(throwable);
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
