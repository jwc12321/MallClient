package com.mall.sls.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.FormatUtil;
import com.mall.sls.common.unit.MainStartManager;
import com.mall.sls.common.unit.PrivacyManager;
import com.mall.sls.common.unit.SpikeManager;
import com.mall.sls.common.unit.StaticHandler;
import com.mall.sls.common.unit.TimeManager;
import com.mall.sls.common.unit.TokenManager;
import com.mall.sls.login.ui.WeixinLoginActivity;
import com.mall.sls.mainframe.ui.MainFrameActivity;
import com.mall.sls.mine.ui.PrivacyPolicyTipActivity;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import java.sql.Time;

import butterknife.ButterKnife;


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
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            // 在这里发送广播，唤醒之前启动的Activity
            finish();
            return;
        }
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        MainStartManager.saveMainStart(StaticData.REFLASH_ZERO);
        SpikeManager.saveSpike(StaticData.REFLASH_ZERO);
//        initData();
        if(TextUtils.isEmpty(PrivacyManager.getPrivacy())){//隐私政策弹框
            Intent intent = new Intent(this, PrivacyPolicyTipActivity.class);
            startActivityForResult(intent, RequestCodeStatic.PRIVACY_POLICY);
        }else {
            if (!TextUtils.isEmpty(TokenManager.getToken())) {
                mHandler.sendEmptyMessageDelayed(GO_MAIN, 300);
            } else {
                mHandler.sendEmptyMessageDelayed(GO_LOGIN, 300);
            }
        }
    }

    private void initData() {
//        Intent intent = getIntent();
//        String action = intent.getAction();
//        if(Intent.ACTION_VIEW.equals(action)){
//            Uri uri = intent.getData();
//            if(uri != null){
//                String name = uri.getQueryParameter("name");
//                String age= uri.getQueryParameter("age");
//            }
//        }
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
//        api = WXAPIFactory.createWXAPI(this, StaticData.WX_APP_ID, true);
//
//        // 将应用的appId注册到微信
//        api.registerApp( StaticData.WX_APP_ID);
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


    public static class MyHandler extends StaticHandler<SplashActivity> {

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
                        if (TextUtils.equals(StaticData.REFLASH_ONE, backType)) {//同意
                            PrivacyManager.savePrivacy("1");
                            if(!TextUtils.isEmpty(TokenManager.getToken())) {
                                mHandler.sendEmptyMessageDelayed(GO_MAIN, 300);
                            }else {
                                mHandler.sendEmptyMessageDelayed(GO_LOGIN, 300);
                            }
                        } else {//不同意
                            finish();
                        }
                    }
                    break;
                default:
            }
        }
    }
}
