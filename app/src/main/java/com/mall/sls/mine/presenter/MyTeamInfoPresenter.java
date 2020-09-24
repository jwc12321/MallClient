package com.mall.sls.mine.presenter;

import android.text.TextUtils;

import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.TeamInfo;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.mine.MineContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/5/20.
 * 描述：
 */
public class MyTeamInfoPresenter implements MineContract.MyTeamInfoPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private MineContract.MyTeamInfoView myTeamInfoView;
    private int currentIndex = 1;  //当前index
    @Inject
    public MyTeamInfoPresenter(RestApiService restApiService, MineContract.MyTeamInfoView myTeamInfoView) {
        this.restApiService = restApiService;
        this.myTeamInfoView = myTeamInfoView;
    }

    @Inject
    public void setupListener() {
        myTeamInfoView.setPresenter(this);
    }

    @Override
    public void getMyTeamInfo(String refreshType) {
        if (TextUtils.equals(StaticData.REFRESH_ONE, refreshType)) {
            myTeamInfoView.showLoading(StaticData.LOADING);
        }
        currentIndex=1;
        String queryString="page="+currentIndex+"&limit="+StaticData.TEN_LIST_SIZE;
        String sign= SignUnit.signGet(RequestUrl.GROUPON_URL,queryString);
        Disposable disposable = restApiService.getTeamInfo(sign,String.valueOf(currentIndex),StaticData.TEN_LIST_SIZE)
                .flatMap(new RxRemoteDataParse<TeamInfo>())
                .compose(new RxSchedulerTransformer<TeamInfo>())
                .subscribe(new Consumer<TeamInfo>() {
                    @Override
                    public void accept(TeamInfo teamInfo) throws Exception {
                        myTeamInfoView.dismissLoading();
                        myTeamInfoView.renderMyTeamInfo(teamInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        myTeamInfoView.dismissLoading();
                        myTeamInfoView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreMyTeamInfo() {
        currentIndex=currentIndex+1;
        String queryString="page="+currentIndex+"&limit="+StaticData.TEN_LIST_SIZE;
        String sign= SignUnit.signGet(RequestUrl.GROUPON_URL,queryString);
        Disposable disposable = restApiService.getTeamInfo(sign,String.valueOf(currentIndex),StaticData.TEN_LIST_SIZE)
                .flatMap(new RxRemoteDataParse<TeamInfo>())
                .compose(new RxSchedulerTransformer<TeamInfo>())
                .subscribe(new Consumer<TeamInfo>() {
                    @Override
                    public void accept(TeamInfo teamInfo) throws Exception {
                        myTeamInfoView.dismissLoading();
                        myTeamInfoView.renderMoreMyTeamInfo(teamInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        myTeamInfoView.dismissLoading();
                        myTeamInfoView.showError(throwable);
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
