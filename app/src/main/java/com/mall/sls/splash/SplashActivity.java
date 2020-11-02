package com.mall.sls.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import com.mall.sls.BaseActivity;
import com.mall.sls.BuildConfig;
import com.mall.sls.R;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.BaseUrlManager;
import com.mall.sls.common.unit.FormatUtil;
import com.mall.sls.common.unit.MainStartManager;
import com.mall.sls.common.unit.PrivacyManager;
import com.mall.sls.common.unit.RetrofitUtil;
import com.mall.sls.common.unit.SpikeManager;
import com.mall.sls.common.unit.StaticHandler;
import com.mall.sls.common.unit.TimeManager;
import com.mall.sls.common.unit.TokenManager;
import com.mall.sls.data.entity.HealthInfo;
import com.mall.sls.login.ui.WeixinLoginActivity;
import com.mall.sls.mainframe.ui.MainFrameActivity;
import com.mall.sls.mine.ui.PrivacyPolicyTipActivity;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class  SplashActivity extends BaseActivity {
    private static final int GO_MAIN = 1;
    private static final int GO_LOGIN = 2;
    private IWXAPI api;
    private String backType;

    private Handler mHandler = new MyHandler(this);


    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
//            // 在这里发送广播，唤醒之前启动的Activity
//            finish();
//            return;
//        }
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        MainStartManager.saveMainStart(StaticData.REFRESH_MINUS_ONE);
        SpikeManager.saveSpike(StaticData.REFRESH_ZERO);
        BaseUrlManager.saveBaseUrl(BuildConfig.API_BASE_URL);
        if(TextUtils.isEmpty(PrivacyManager.getPrivacy())){//隐私政策弹框
            Intent intent = new Intent(this, PrivacyPolicyTipActivity.class);
            startActivityForResult(intent, RequestCodeStatic.PRIVACY_POLICY);
        }else {
            getHealth();
        }
    }

    private void goActivity(){
        if (!TextUtils.isEmpty(TokenManager.getToken())) {
            mHandler.sendEmptyMessageDelayed(GO_MAIN, 300);
        } else {
            mHandler.sendEmptyMessageDelayed(GO_LOGIN, 300);
        }
    }



    //跳转到主页
    private void goMain() {
        TimeManager.saveTime(FormatUtil.timeSecond());
        MainFrameActivity.start(this);
        finish();
    }

    //跳转到主页
    private void goLogin() {
        WeixinLoginActivity.start(this);
        finish();
    }


    public class MyHandler extends StaticHandler<SplashActivity> {

        public MyHandler(SplashActivity target) {
            super(target);
        }

        @Override
        public void handle(SplashActivity target, Message msg) {
            switch (msg.what) {
                case GO_MAIN:
                    target.goMain();
                    break;
                case GO_LOGIN:
                    target.goLogin();
                    break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodeStatic.PRIVACY_POLICY:
                    if (data != null) {
                        backType = data.getStringExtra(StaticData.BACK_TYPE);
                        if (TextUtils.equals(StaticData.REFRESH_ONE, backType)) {//同意
                            PrivacyManager.savePrivacy(StaticData.REFRESH_ONE);
                            getHealth();
                        } else {//不同意
                            finish();
                        }
                    }
                    break;
                default:
            }
        }
    }

    private void getHealth() {
        RetrofitUtil.getInstance().getTestService()
                .getHealth()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HealthInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HealthInfo healthInfo) {
                        if(healthInfo==null||!TextUtils.equals(StaticData.HEALTH_UP,healthInfo.getStatus())){
                            BaseUrlManager.saveBaseUrl(StaticData.SPARE_BASE_URL);
                        }
                        goActivity();
                    }

                    @Override
                    public void onError(Throwable e) {
                        BaseUrlManager.saveBaseUrl(StaticData.SPARE_BASE_URL);
                        goActivity();
                    }

                    @Override
                    public void onComplete() {

                    }

                    private Disposable mDisposable;

                });
    }
}
