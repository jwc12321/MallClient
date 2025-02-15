package com.mall.sls.local.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.Ignore;
import com.mall.sls.data.entity.LocalTeam;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.data.request.GroupRemindRequest;
import com.mall.sls.local.LocalContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/5/18.
 * 描述：
 */
public class LocalTeamPresenter implements LocalContract.LocalTeamPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private LocalContract.LocalTeamView localTeamView;
    private int currentIndex = 1;  //当前index
    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    @Inject
    public LocalTeamPresenter(RestApiService restApiService, LocalContract.LocalTeamView localTeamView) {
        this.restApiService = restApiService;
        this.localTeamView = localTeamView;
    }

    @Inject
    public void setupListener() {
        localTeamView.setPresenter(this);
    }


    @Override
    public void getLocalTeam(String refreshType, String type) {
        if (TextUtils.equals(StaticData.REFRESH_ONE, refreshType)) {
            localTeamView.showLoading(StaticData.LOADING);
        }
        currentIndex=1;
        String queryString="type="+type+"&page="+currentIndex+"&limit="+StaticData.TEN_LIST_SIZE;
        String sign= SignUnit.signGet(RequestUrl.LOCAL_GOODS_URL,queryString);
        Disposable disposable = restApiService.getLocalTeam(sign,type,String.valueOf(currentIndex),StaticData.TEN_LIST_SIZE)
                .flatMap(new RxRemoteDataParse<LocalTeam>())
                .compose(new RxSchedulerTransformer<LocalTeam>())
                .subscribe(new Consumer<LocalTeam>() {
                    @Override
                    public void accept(LocalTeam localTeam) throws Exception {
                        localTeamView.dismissLoading();
                        localTeamView.renderLocalTeam(localTeam);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        localTeamView.dismissLoading();
                        localTeamView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreLocalTeam(String type) {
        currentIndex=currentIndex+1;
        String queryString="type="+type+"&page="+currentIndex+"&limit="+StaticData.TEN_LIST_SIZE;
        String sign= SignUnit.signGet(RequestUrl.LOCAL_GOODS_URL,queryString);
        Disposable disposable = restApiService.getLocalTeam(sign,type,String.valueOf(currentIndex),StaticData.TEN_LIST_SIZE)
                .flatMap(new RxRemoteDataParse<LocalTeam>())
                .compose(new RxSchedulerTransformer<LocalTeam>())
                .subscribe(new Consumer<LocalTeam>() {
                    @Override
                    public void accept(LocalTeam localTeam) throws Exception {
                        localTeamView.dismissLoading();
                        localTeamView.renderMoreLocalTeam(localTeam);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        localTeamView.dismissLoading();
                        localTeamView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void groupRemind(String ruleId) {
        localTeamView.showLoading(StaticData.PROCESSING);
        GroupRemindRequest request=new GroupRemindRequest(ruleId,StaticData.REFRESH_ONE);
        String sign= SignUnit.signPost(RequestUrl.GROUP_REMIND_URL,gson.toJson(request));
        Disposable disposable = restApiService.groupRemind(sign,request)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        localTeamView.dismissLoading();
                        localTeamView.renderGroupRemind();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        localTeamView.dismissLoading();
                        localTeamView.showError(throwable);
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
