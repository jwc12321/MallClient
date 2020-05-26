package com.mall.sls.splash;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.unit.StaticHandler;
import com.mall.sls.common.unit.TokenManager;
import com.mall.sls.login.ui.LoginActivity;
import com.mall.sls.login.ui.WeixinLoginActivity;
import com.mall.sls.mainframe.ui.MainFrameActivity;

import butterknife.ButterKnife;


public class  SplashActivity extends BaseActivity {
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
//        initData();
        if(!TextUtils.isEmpty(TokenManager.getToken())) {
            mHandler.sendEmptyMessageDelayed(GO_MAIN, 300);
        }else {
            mHandler.sendEmptyMessageDelayed(GO_LOGIN, 300);
        }
    }

    private void initData() {
        Intent intent = getIntent();
        String action = intent.getAction();
        Log.d("111","数据");
        if(Intent.ACTION_VIEW.equals(action)){
            Uri uri = intent.getData();
            Log.d("111","数据=="+uri);
            if(uri != null){
                String name = uri.getQueryParameter("name");
                String age= uri.getQueryParameter("age");
                Log.d("111","数据"+name+age);
            }
        }
    }

    //跳转到主页
    private void goMain() {
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
}
