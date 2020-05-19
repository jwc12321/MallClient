package com.mall.sls.message.presenter;

import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.MessageTypeInfo;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.message.MessageContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author jwc on 2020/5/19.
 * 描述：
 */
public class MsgTypePresenter implements MessageContract.MsgTypePresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private MessageContract.MsgTypeView msgTypeView;

    @Inject
    public MsgTypePresenter(RestApiService restApiService, MessageContract.MsgTypeView msgTypeView) {
        this.restApiService = restApiService;
        this.msgTypeView = msgTypeView;
    }

    @Inject
    public void setupListener() {
        msgTypeView.setPresenter(this);
    }

    @Override
    public void getMsgType() {
        msgTypeView.showLoading(StaticData.LOADING);
        String queryString = "null";
        String sign = SignUnit.signGet(RequestUrl.MES_TYPE_URL, queryString);
        Disposable disposable = restApiService.getMessageTypeInfos(sign)
                .flatMap(new RxRemoteDataParse<List<MessageTypeInfo>>())
                .compose(new RxSchedulerTransformer<List<MessageTypeInfo>>())
                .subscribe(new Consumer<List<MessageTypeInfo>>() {
                    @Override
                    public void accept(List<MessageTypeInfo> messageTypeInfos) throws Exception {
                        msgTypeView.dismissLoading();
                        msgTypeView.renderMsgType(messageTypeInfos);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        msgTypeView.dismissLoading();
                        msgTypeView.showError(throwable);
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
