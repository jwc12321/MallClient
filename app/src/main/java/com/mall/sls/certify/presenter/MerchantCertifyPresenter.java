package com.mall.sls.certify.presenter;

import android.util.ArrayMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mall.sls.certify.CertifyContract;
import com.mall.sls.common.RequestUrl;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SignUnit;
import com.mall.sls.data.RxSchedulerTransformer;
import com.mall.sls.data.entity.MerchantCertifyInfo;
import com.mall.sls.data.entity.UploadUrlInfo;
import com.mall.sls.data.remote.RestApiService;
import com.mall.sls.data.remote.RxRemoteDataParse;
import com.mall.sls.data.request.MerchantCertifyRequest;
import com.mall.sls.data.request.UploadFileRequest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author jwc on 2020/10/23.
 * 描述：
 */
public class MerchantCertifyPresenter implements CertifyContract.MerchantCertifyPresenter {
    private RestApiService restApiService;
    private List<Disposable> mDisposableList = new ArrayList<>();
    private CertifyContract.MerchantCertifyView merchantCertifyView;
    private Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    @Inject
    public MerchantCertifyPresenter(RestApiService restApiService, CertifyContract.MerchantCertifyView merchantCertifyView) {
        this.restApiService = restApiService;
        this.merchantCertifyView = merchantCertifyView;
    }

    @Inject
    public void setupListener() {
        merchantCertifyView.setPresenter(this);
    }

    @Override
    public void getMerchantCertifyInfo() {
        merchantCertifyView.showLoading(StaticData.LOADING);
        String queryString = "null";
        String sign = SignUnit.signGet(RequestUrl.CERTIFY_MERCHANT, queryString);
        Disposable disposable = restApiService.getMerchantCertifyInfo(sign)
                .flatMap(new RxRemoteDataParse<MerchantCertifyInfo>())
                .compose(new RxSchedulerTransformer<MerchantCertifyInfo>())
                .subscribe(new Consumer<MerchantCertifyInfo>() {
                    @Override
                    public void accept(MerchantCertifyInfo merchantCertifyInfo) throws Exception {
                        merchantCertifyView.dismissLoading();
                        merchantCertifyView.renderMerchantCertifyInfo(merchantCertifyInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        merchantCertifyView.dismissLoading();
                        merchantCertifyView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void merchantCertify(String businessLicense, String doorHeader, String address, String detail) {
        merchantCertifyView.showLoading(StaticData.PROCESSING);
        MerchantCertifyRequest request=new MerchantCertifyRequest(businessLicense,doorHeader,address,detail);
        String sign= SignUnit.signPost(RequestUrl.CERTIFY_MERCHANT,gson.toJson(request));
        Disposable disposable = restApiService.merchantCertify(sign,request)
                .flatMap(new RxRemoteDataParse<Boolean>())
                .compose(new RxSchedulerTransformer<Boolean>())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isBoolean) throws Exception {
                        merchantCertifyView.dismissLoading();
                        merchantCertifyView.renderMerchantCertify(isBoolean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        merchantCertifyView.dismissLoading();
                        merchantCertifyView.showError(throwable);
                    }
                });
        mDisposableList.add(disposable);
    }

    @Override
    public void uploadFile(String photoUrl,String type) {
        merchantCertifyView.showLoading(StaticData.PROCESSING);
        UploadFileRequest request = new UploadFileRequest(photoUrl,type);
        String sign= SignUnit.signPost(RequestUrl.UPLOAD_FILE,gson.toJson(request));
        Map<String, RequestBody> requestBodyMap = new ArrayMap<>();
        File file = new File(photoUrl);
        String fileName = file.getName();
        RequestBody photo = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        requestBodyMap.put("file\"; filename=\"" + fileName, photo);
        RequestBody text = RequestBody.create(MediaType.parse("text/plain"), type);
        requestBodyMap.put("type", text);
        Disposable disposable = restApiService.uploadFile(sign,requestBodyMap)
                .flatMap(new RxRemoteDataParse<UploadUrlInfo>())
                .compose(new RxSchedulerTransformer<UploadUrlInfo>())
                .subscribe(new Consumer<UploadUrlInfo>() {
                    @Override
                    public void accept(UploadUrlInfo uploadUrlInfo) throws Exception {
                        merchantCertifyView.dismissLoading();
                        merchantCertifyView.renderUploadFile(uploadUrlInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        merchantCertifyView.dismissLoading();
                        merchantCertifyView.showError(throwable);
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
