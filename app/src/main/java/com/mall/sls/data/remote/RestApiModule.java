package com.mall.sls.data.remote;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.mall.sls.MainApplication;
import com.mall.sls.common.unit.BaseUrlManager;
import com.mall.sls.common.unit.TimeManager;
import com.mall.sls.common.unit.TokenManager;
import com.mall.sls.data.EntitySerializer;
import com.mall.sls.data.GsonSerializer;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.net.URLDecoder;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/1/5.
 */

@Module
public class RestApiModule {

    public RestApiModule() {

    }

    private String url = null;

    public RestApiModule(String url) {
        this.url = url;
    }

    @Singleton
    @Provides
    @Named("SupportExpose")
    Gson provideSupportExposeGson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    @Singleton
    @Provides
    Gson provideGson() {
        return new Gson();
    }

    @Singleton
    @Provides
    @Named("GsonSerializer")
    EntitySerializer provideEntitySerializer(Gson gson) {
        return new GsonSerializer(gson);
    }

    @Singleton
    @Provides
    @Named("HttpLogging")
    Interceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    public class HttpLogger implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(String message) {
            try {

                String text = URLDecoder.decode(message, "utf-8");
                Log.e("OKHttp-----", text);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Log.e("OKHttp-----", message);
            }
        }
    }


    @Singleton
    @Provides
    @Named("AddFormData")
    Interceptor provideAddFormDataInterceptor() {

        //add platform param to request body for form-style request
        return new Interceptor() {
            //Android platform special param

            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Authorization", TokenManager.getToken() + "")
                        .header("X-Hc-Timestamp", TimeManager.getTime())
//                        .header("areaCode", AreaCodeManager.getAreaCode()+"")
                        .build();
                return chain.proceed(request);
            }
        };
    }

    @Singleton
    @Provides
    Cache provideCache(Context context) {
        long cacheSize = 10 * 1024 * 1024;
        return new Cache(context.getCacheDir(), cacheSize);
    }

    @Singleton
    @Provides
    @Named("NoCache")
    OkHttpClient provideOkHttpClient(@Named("AddFormData") Interceptor addFormData,
                                     @Named("HttpLogging") Interceptor httpLogging) {
//        SSLSocketFactory sslSocketFactory=null;
//        try {
//            sslSocketFactory= SSLUtil.getSSLSocketFactory(MainApplication.getContext().getAssets()
//                    .open("app.ava.youzh-inc.com_chain.cer"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .addNetworkInterceptor(addFormData)
//                .addNetworkInterceptor(httpLogging)
//                .addInterceptor(httpLogging)
                .sslSocketFactory(createSSLSocketFactory())
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
//                .sslSocketFactory(sslSocketFactory)
                .proxy(Proxy.NO_PROXY)
                .dns(OkHttpDns.getInstance(MainApplication.getContext()))
                .build();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(@Named("NoCache") OkHttpClient okHttpClient, Gson gson) {
            return new Retrofit.Builder()
                    .baseUrl(BaseUrlManager.getBaseUrl())
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
    }


    @Singleton
    @Provides
    RestApiService provideRestApiService(Retrofit retrofit) {
        return retrofit.create(RestApiService.class);
    }


    private SSLSocketFactory createSSLSocketFactory() {

        SSLSocketFactory sSLSocketFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllManager()}, new SecureRandom());
            sSLSocketFactory = sc.getSocketFactory();
        } catch (Exception e) {

        }

        return sSLSocketFactory;
    }

    private class TrustAllManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates,
                                       String s) throws java.security.cert.CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates,
                                       String s) throws java.security.cert.CertificateException {
        }


        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}
