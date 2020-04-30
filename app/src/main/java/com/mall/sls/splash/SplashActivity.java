package com.mall.sls.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.unit.StaticHandler;
import com.mall.sls.common.unit.TokenManager;
import com.mall.sls.login.ui.LoginActivity;

import butterknife.ButterKnife;


public class SplashActivity extends BaseActivity {
    private static final int GO_MAIN = 1;
    private static final int GO_LOGIN = 2;

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
        if(!TextUtils.isEmpty(TokenManager.getToken())) {
            mHandler.sendEmptyMessageDelayed(GO_MAIN, 300);
        }else {
            mHandler.sendEmptyMessageDelayed(GO_LOGIN, 300);
        }
    }

    //跳转到主页
    private void goMain() {
//        MainFrameActivity.start(this);
        finish();
    }

    //跳转到主页
    private void goLogin() {
        LoginActivity.start(this);
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
}
