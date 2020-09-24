package com.mall.sls.message.presenter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.Ignore;
import com.mall.sls.data.entity.MessageInfo;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.data.request.MsgIdRequest;
import com.mall.sls.data.request.MsgReadRequest;
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
public class MsgInfoPresenter implements MessageContract.MsgInfoPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private MessageContract.MsgInfoView msgInfoView;
    private int currentIndex = 1;  //当前index
    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    @Inject
    public MsgInfoPresenter(RestApiService restApiService, MessageContract.MsgInfoView msgInfoView) {
        this.restApiService = restApiService;
        this.msgInfoView = msgInfoView;
    }

    @Inject
    public void setupListener() {
        msgInfoView.setPresenter(this);
    }

    @Override
    public void getMsgInfo(String refreshType, String typeId) {
        if (TextUtils.equals(StaticData.REFRESH_ONE, refreshType)) {
            msgInfoView.showLoading(StaticData.LOADING);
        }
        currentIndex=1;
        String queryString="typeId="+typeId+"&page="+currentIndex+"&limit="+StaticData.TEN_LIST_SIZE;
        String sign= SignUnit.signGet(RequestUrl.MSG_LIST_URL,queryString);
        Disposable disposable = restApiService.getMessageInfo(sign,typeId,String.valueOf(currentIndex),StaticData.TEN_LIST_SIZE)
                .flatMap(new RxRemoteDataParse<MessageInfo>())
                .compose(new RxSchedulerTransformer<MessageInfo>())
                .subscribe(new Consumer<MessageInfo>() {
                    @Override
                    public void accept(MessageInfo messageInfo) throws Exception {
                        msgInfoView.dismissLoading();
                        msgInfoView.renderMsgInfo(messageInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        msgInfoView.dismissLoading();
                        msgInfoView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void getMoreMsgInfo(String typeId) {
        currentIndex=currentIndex+1;
        String queryString="typeId="+typeId+"&page="+currentIndex+"&limit="+StaticData.TEN_LIST_SIZE;
        String sign= SignUnit.signGet(RequestUrl.MSG_LIST_URL,queryString);
        Disposable disposable = restApiService.getMessageInfo(sign,typeId,String.valueOf(currentIndex),StaticData.TEN_LIST_SIZE)
                .flatMap(new RxRemoteDataParse<MessageInfo>())
                .compose(new RxSchedulerTransformer<MessageInfo>())
                .subscribe(new Consumer<MessageInfo>() {
                    @Override
                    public void accept(MessageInfo messageInfo) throws Exception {
                        msgInfoView.dismissLoading();
                        msgInfoView.renderMoreMsgInfo(messageInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        msgInfoView.dismissLoading();
                        msgInfoView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void msgEmpty(String msgId) {
        msgInfoView.showLoading(StaticData.PROCESSING);
        MsgIdRequest request=new MsgIdRequest(msgId);
        String sign= SignUnit.signPost(RequestUrl.MSG_EMPTY_URL,gson.toJson(request));
        Disposable disposable = restApiService.msgEmpty(sign,request)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        msgInfoView.dismissLoading();
                        msgInfoView.renderMsgEmpty();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        msgInfoView.dismissLoading();
                        msgInfoView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void msgChangeStatus(String msgId) {
        msgInfoView.showLoading(StaticData.PROCESSING);
        MsgReadRequest request=new MsgReadRequest(msgId,StaticData.REFRESH_ONE);
        String sign= SignUnit.signPost(RequestUrl.MSG_CHANGE_STATUS,gson.toJson(request));
        Disposable disposable = restApiService.msgChangeStatus(sign,request)
                .flatMap(new RxRemoteDataParse<Ignore>())
                .compose(new RxSchedulerTransformer<Ignore>())
                .subscribe(new Consumer<Ignore>() {
                    @Override
                    public void accept(Ignore ignore) throws Exception {
                        msgInfoView.dismissLoading();
                        msgInfoView.renderMsgChangeStatus();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        msgInfoView.dismissLoading();
                        msgInfoView.showError(throwable);
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
