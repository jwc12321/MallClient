package com.mall.sls.certify.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.alipay.sdk.app.PayTask;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.unit.PayResult;
import com.mall.sls.common.unit.StaticHandler;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.mainframe.ui.MainFrameActivity;
import com.mall.sls.splash.SplashActivity;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CerifyPayActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.weixin_iv)
    ImageView weixinIv;
    @BindView(R.id.select_weixin_iv)
    ImageView selectWeixinIv;
    @BindView(R.id.ali_iv)
    ImageView aliIv;
    @BindView(R.id.select_ali_iv)
    ImageView selectAliIv;
    @BindView(R.id.confirm_bt)
    MediumThickTextView confirmBt;
    private String selectType="1";
    private Handler mHandler = new MyHandler(this);

    public static void start(Context context) {
        Intent intent = new Intent(context, CerifyPayActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerify_pay);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();
    }

    private void initView(){
        selectPayType();
    }


    @OnClick({R.id.confirm_bt, R.id.back,R.id.select_weixin_iv,R.id.select_ali_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_bt:
//                NameVerifiedActivity.start(this);
                String s="alipay_sdk=alipay-sdk-java-3.4.27.ALL&app_id=2016102100732399&biz_content=%7B%22body%22%3A%22%E8%B6%85%E7%BA%A7%E4%BC%9A%E5%91%98%E6%94%AF%E4%BB%98%22%2C%22out_trade_no%22%3A%22SP202005201045360000174%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E8%B6%85%E7%BA%A7%E4%BC%9A%E5%91%98%E6%94%AF%E4%BB%98%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%22299.0%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F47.114.109.50%3A8080%2Fapp%2Fuser%2Fpay%2Fali%2FcallBack&sign=iUzORu7BUKMaJL2WQaXEKs%2FsHeN3KIh5wssPHoOKuxGQ%2BLFqCikCGWdznLds24%2FUWs%2Ftik%2BmQovibDVpkYeG7JC6K92InX2pZYHiJ%2BNc6J1L3JupKzi6OJ0%2FGG8gMVP4KS%2FalnoID4Fzr7jxny%2B6ezdg4LdR%2FD7YoRM3uuqDbehK8nJrhVzM296Da0BuiP6UjZmlN60riy7Hlgxnccv86UNEvh7Uq8QUAZKnHRyyx89i%2FAnV1YUHCcQITmZDonR61zssH0Yn2kj5Z9RACDiEPQQ1JVUh0mJKdxxwUFpD9wKYY5aDsnMZBZ2M%2FD4co1a8oH0w3JrD9NyfCNyLfpBXuw%3D%3D&sign_type=RSA2&timestamp=2020-05-20+10%3A45%3A36&version=1.0";
                startAliPay(s);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.select_weixin_iv:
                selectType="1";
                selectPayType();
                break;
            case R.id.select_ali_iv:
                selectType="2";
                selectPayType();
                break;
            default:
        }
    }

    private void selectPayType(){
        selectWeixinIv.setSelected(TextUtils.equals("1",selectType));
        selectAliIv.setSelected(TextUtils.equals("2",selectType));
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    private void startAliPay(String sign) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                PayTask payTask = new PayTask(CerifyPayActivity.this);
                Map<String,String> result = payTask.payV2(sign,true);
                Message message = Message.obtain();
                message.what = RequestCodeStatic.SDK_PAY_FLAG;
//                message.obj = result;
                mHandler.sendMessage(message);
            }
        };

        new Thread(runnable).start();
    }
    public static class MyHandler extends StaticHandler<CerifyPayActivity> {

        public MyHandler(CerifyPayActivity target) {
            super(target);
        }

        @Override
        public void handle(CerifyPayActivity target, Message msg) {
            switch (msg.what) {
                case RequestCodeStatic.SDK_PAY_FLAG:
                    target.alpay(msg);
                    break;
            }
        }
    }

    //跳转到主页
    private void alpay(Message msg) {
        PayResult payResult = new PayResult((Map<String, String>) msg.obj);
        String resultStatus = payResult.getResultStatus();
        Log.d("111","数据"+payResult.getResult()+"=="+payResult.getResultStatus());
        if (TextUtils.equals(resultStatus, "9000")) {
            showMessage("成功");
        } else {
            if (TextUtils.equals(resultStatus, "8000")) {
                showMessage("失败");
            } else if (TextUtils.equals(resultStatus, "6001")) {
                showMessage("取消");
            } else {
                showMessage("失败");
            }
        }
    }
}
