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
import com.mall.sls.certify.CertifyContract;
import com.mall.sls.certify.CertifyModule;
import com.mall.sls.certify.DaggerCertifyComponent;
import com.mall.sls.certify.presenter.CertifyPayPresenter;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.unit.PayResult;
import com.mall.sls.common.unit.PayTypeInstalledUtils;
import com.mall.sls.common.unit.StaticHandler;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.WXPaySignResponse;
import com.mall.sls.data.event.PayAbortEvent;
import com.mall.sls.data.event.WXSuccessPayEvent;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CerifyPayActivity extends BaseActivity implements CertifyContract.CertifyPayView {
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
    @BindView(R.id.amount)
    ConventionalTextView amount;
    private String selectType = StaticData.REFRESH_ZERO;
    private Handler mHandler = new MyHandler(this);
    private String certifyAmount;

    @Inject
    CertifyPayPresenter certifyPayPresenter;

    public static void start(Context context, String certifyAmount) {
        Intent intent = new Intent(context, CerifyPayActivity.class);
        intent.putExtra(StaticData.CERTIFY_AMOUNT, certifyAmount);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerify_pay);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        EventBus.getDefault().register(this);
        certifyAmount = getIntent().getStringExtra(StaticData.CERTIFY_AMOUNT);
        amount.setText(NumberFormatUnit.goodsFormat(certifyAmount));
        selectPayType();
    }

    @Override
    protected void initializeInjector() {
        DaggerCertifyComponent.builder()
                .applicationComponent(getApplicationComponent())
                .certifyModule(new CertifyModule(this))
                .build()
                .inject(this);

    }


    @OnClick({R.id.confirm_bt, R.id.back, R.id.select_weixin_iv, R.id.select_ali_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_bt:
                confirm();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.select_weixin_iv:
                selectType = StaticData.REFRESH_ZERO;
                selectPayType();
                break;
            case R.id.select_ali_iv:
                selectType = StaticData.REFRESH_ONE;
                selectPayType();
                break;
            default:
        }
    }

    private void confirm() {
        if (TextUtils.equals(StaticData.REFRESH_ZERO, selectType)) {
            //微信
            if (PayTypeInstalledUtils.isWeixinAvilible(CerifyPayActivity.this)) {
                certifyPayPresenter.wxPay(StaticData.REFRESH_ZERO, selectType);

            } else {
                showMessage(getString(R.string.install_weixin));
            }
        } else {
            if (PayTypeInstalledUtils.isAliPayInstalled(CerifyPayActivity.this)) {
                certifyPayPresenter.aliPay(StaticData.REFRESH_ZERO, selectType);
            } else {
                showMessage(getString(R.string.install_alipay));
            }
        }
    }

    private void selectPayType() {
        selectWeixinIv.setSelected(TextUtils.equals(StaticData.REFRESH_ZERO, selectType));
        selectAliIv.setSelected(TextUtils.equals(StaticData.REFRESH_ONE, selectType));
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }


    @Override
    public void renderAliPay(String alipayStr) {
        if (!TextUtils.isEmpty(alipayStr)) {
            startAliPay(alipayStr);
        }
    }

    @Override
    public void renderWxPay(WXPaySignResponse wxPaySignResponse) {
        if(wxPaySignResponse!=null){
            wechatPay(wxPaySignResponse);
        }
    }


    private void startAliPay(String sign) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                PayTask payTask = new PayTask(CerifyPayActivity.this);
                Map<String, String> result = payTask.payV2(sign, true);
                Message message = Message.obtain();
                message.what = RequestCodeStatic.SDK_PAY_FLAG;
                message.obj = result;
                mHandler.sendMessage(message);
            }
        };

        new Thread(runnable).start();
    }

    @Override
    public void setPresenter(CertifyContract.CertifyPayPresenter presenter) {

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
        if (TextUtils.equals(resultStatus, "9000")) {
            NameVerifiedActivity.start(this);
            finish();
        } else if (TextUtils.equals(resultStatus, "6001")) {
            showMessage(getString(R.string.pay_cancel));
        } else {
            showMessage(getString(R.string.pay_failed));
        }
    }

    public  void wechatPay(WXPaySignResponse wxPaySignResponse) {
        // 将该app注册到微信
        IWXAPI wxapi = WXAPIFactory.createWXAPI(this, StaticData.WX_APP_ID);
        PayReq request = new PayReq();
        request.appId = wxPaySignResponse.getAppid();
        request.partnerId = wxPaySignResponse.getPartnerId();
        request.prepayId = wxPaySignResponse.getPrepayId();
        request.packageValue = wxPaySignResponse.getPackageValue();
        request.nonceStr = wxPaySignResponse.getNonceStr();
        request.timeStamp = wxPaySignResponse.getTimestamp();
        request.sign = wxPaySignResponse.getSign();
        wxapi.sendReq(request);
    }

    //支付成功
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPaySuccess(WXSuccessPayEvent event) {
        NameVerifiedActivity.start(this);
        finish();
    }

    //支付失败
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayCancel(PayAbortEvent event) {
        if(event!=null){
            if(event.code==-1){
                showMessage(getString(R.string.pay_failed));
            }else if(event.code==-2){
                showMessage(getString(R.string.pay_cancel));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
