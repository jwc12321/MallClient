package com.mall.sls.mine.presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.Ignore;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.data.request.DescriptionRequest;
import com.mall.sls.mine.MineContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/5/29.
 * 描述：
 */
public class FeedBackPresenter implements MineContract.FeedBackPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private MineContract.FeedBackView feedBackView;
    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    @Inject
    public FeedBackPresenter(RestApiService restApiService, MineContract.FeedBackView feedBackView) {
        this.restApiService = restApiService;
        this.feedBackView = feedBackView;
    }

    @Inject
    public void setupListener() {
        feedBackView.setPresenter(this);
    }

    @Override
    public void addFeedBack(String description) {
        feedBackView.showLoading(StaticData.PROCESSING);
        DescriptionRequest request=new DescriptionRequest(description);
        String sign= SignUnit.signPost(RequestUrl.ADD_FEED_BACK_URL,gson.toJson(request));
        Disposable disposable = restApiService.addFeedBack(sign,request)
                .flatMap(new RxRemoteDataParse<Boolean>())
                .compose(new RxSchedulerTransformer<Boolean>())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isBoolean) throws Exception {
                        feedBackView.dismissLoading();
                        feedBackView.renderAddFeedBack(isBoolean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        feedBackView.dismissLoading();
                        feedBackView.showError(throwable);
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
