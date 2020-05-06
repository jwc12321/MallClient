package com.mall.sls.data.remote;


import com.mall.sls.data.RemoteDataWrapper;
import com.mall.sls.data.entity.AppUrlInfo;
import com.mall.sls.data.entity.OneClickInfo;
import com.mall.sls.data.entity.TokenInfo;
import com.mall.sls.data.request.LoginRequest;
import com.mall.sls.data.request.MobileRequest;
import com.mall.sls.data.request.OneClickLoginRequest;
import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/12/27.
 */

public interface RestApiService {

    //获取下载地址
    @GET("api/public/f/version/android")
    Flowable<RemoteDataWrapper<AppUrlInfo>> getAppUrlInfo(@Header("X-Hc-Sign") String sign, @Query("version") String version);

    //登录
    @POST("wx/auth/login/code")
    Flowable<RemoteDataWrapper<TokenInfo>> loginIn(@Header("X-Hc-Sign") String sign, @Body LoginRequest loginRequest);

    //一键登录
    @POST("wx/auth/login/ali")
    Flowable<RemoteDataWrapper<TokenInfo>> oneClickLogin(@Header("X-Hc-Sign") String sign, @Body OneClickLoginRequest request);

    //发送验证码
    @POST("wx/auth/regCaptcha")
    Flowable<RemoteDataWrapper<String>> sendCodeV(@Header("X-Hc-Sign") String sign, @Body MobileRequest request);

    //获取用户认证状态
    @GET("api/f/uses/rp/status")
    Flowable<RemoteDataWrapper<Boolean>> getUsersRpStatus(@Header("X-Hc-Sign") String sign);
}
