package com.mall.sls.certify.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.alipay.sdk.app.PayTask;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.bank.ui.BankCardPayActivity;
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
import com.mall.sls.common.unit.SystemUtil;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.AliPay;
import com.mall.sls.data.entity.BaoFuPay;
import com.mall.sls.data.entity.PayMethodInfo;
import com.mall.sls.data.entity.UserPayInfo;
import com.mall.sls.data.entity.WXPaySignResponse;
import com.mall.sls.data.entity.WxPay;
import com.mall.sls.data.event.PayAbortEvent;
import com.mall.sls.data.event.WXSuccessPayEvent;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
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
    @BindView(R.id.weixin_rl)
    RelativeLayout weixinRl;
    @BindView(R.id.ali_rl)
    RelativeLayout aliRl;
    @BindView(R.id.bank_iv)
    ImageView bankIv;
    @BindView(R.id.select_bank_iv)
    ImageView selectBankIv;
    @BindView(R.id.bank_rl)
    RelativeLayout bankRl;
    @BindView(R.id.wx_tv)
    ConventionalTextView wxTv;
    @BindView(R.id.wx_tip)
    ConventionalTextView wxTip;
    @BindView(R.id.ali_tv)
    ConventionalTextView aliTv;
    @BindView(R.id.ali_tip)
    ConventionalTextView aliTip;
    @BindView(R.id.bank_tv)
    ConventionalTextView bankTv;
    @BindView(R.id.bank_tip)
    ConventionalTextView bankTip;
    private Handler mHandler = new MyHandler(this);
    private String certifyAmount;
    private String paymentMethod;
    private String orderId;
    private String orderType;
    private String devicePlatform;

    private UserPayInfo userPayInfo;
    private String result;


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
        orderType = StaticData.TYPE_CERTIFY;
        devicePlatform = SystemUtil.getChannel(this);
        certifyPayPresenter.getPayMethod(devicePlatform,StaticData.TYPE_CERTIFY);
    }

    @Override
    protected void initializeInjector() {
        DaggerCertifyComponent.builder()
                .applicationComponent(getApplicationComponent())
                .certifyModule(new CertifyModule(this))
                .build()
                .inject(this);

    }


    @OnClick({R.id.confirm_bt, R.id.back, R.id.weixin_rl, R.id.ali_rl, R.id.bank_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_bt:
                confirm();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.weixin_rl:
                paymentMethod = StaticData.WX_PAY;
                selectPayType();
                break;
            case R.id.ali_rl:
                paymentMethod = StaticData.ALI_PAY;
                selectPayType();
                break;
            case R.id.bank_rl:
                paymentMethod = StaticData.BAO_FU_PAY;
                selectPayType();
                break;
            default:
        }
    }

    private void confirm() {
        if (TextUtils.equals(StaticData.WX_PAY, paymentMethod)) {
            //微信
            if (PayTypeInstalledUtils.isWeixinAvilible(CerifyPayActivity.this)) {
                certifyPayPresenter.getWxPay(orderId, orderType, paymentMethod);
            } else {
                showMessage(getString(R.string.install_weixin));
            }
        } else if (TextUtils.equals(StaticData.ALI_PAY, paymentMethod)) {
            if (PayTypeInstalledUtils.isAliPayInstalled(CerifyPayActivity.this)) {
                certifyPayPresenter.getAliPay(orderId, orderType, paymentMethod);
            } else {
                showMessage(getString(R.string.install_alipay));
            }
        }else if(TextUtils.equals(StaticData.BAO_FU_PAY, paymentMethod)){
            certifyPayPresenter.getBaoFuPay(orderId, orderType, paymentMethod);
        }
    }

    private void selectPayType() {
        selectWeixinIv.setSelected(TextUtils.equals(StaticData.WX_PAY, paymentMethod));
        selectAliIv.setSelected(TextUtils.equals(StaticData.ALI_PAY, paymentMethod));
        selectBankIv.setSelected(TextUtils.equals(StaticData.BAO_FU_PAY, paymentMethod));
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }


    @Override
    public void renderWxPay(WxPay wxPay) {
        if (wxPay != null) {
            userPayInfo=wxPay.getUserPayInfo();
            wechatPay(wxPay.getWxPayInfo());
        }
    }

    @Override
    public void renderAliPay(AliPay aliPay) {
        if (aliPay != null) {
            userPayInfo=aliPay.getUserPayInfo();
            if (!TextUtils.isEmpty(aliPay.getAliPayInfo())) {
                startAliPay(aliPay.getAliPayInfo());
            }
        }
    }


    @Override
    public void renderBaoFuPay(BaoFuPay baoFuPay) {
        if(baoFuPay!=null){
            userPayInfo=baoFuPay.getUserPayInfo();
            bankPay();
        }
    }

    @Override
    public void renderPayMethod(List<PayMethodInfo> payMethods) {
        if (payMethods != null) {
            for (PayMethodInfo payMethodInfo : payMethods) {
                if (TextUtils.equals(StaticData.WX_PAY, payMethodInfo.getPaymentMethod())) {
                    weixinRl.setVisibility(View.VISIBLE);
                    wxTip.setVisibility(TextUtils.isEmpty(payMethodInfo.getDescription())?View.GONE:View.VISIBLE);
                    wxTip.setText(payMethodInfo.getDescription());
                } else if (TextUtils.equals(StaticData.ALI_PAY, payMethodInfo.getPaymentMethod())) {
                    aliRl.setVisibility(View.VISIBLE);
                    aliTip.setVisibility(TextUtils.isEmpty(payMethodInfo.getDescription())?View.GONE:View.VISIBLE);
                    aliTip.setText(payMethodInfo.getDescription());
                } else if (TextUtils.equals(StaticData.BAO_FU_PAY, payMethodInfo.getPaymentMethod())) {
                    bankRl.setVisibility(View.VISIBLE);
                    bankTip.setVisibility(TextUtils.isEmpty(payMethodInfo.getDescription())?View.GONE:View.VISIBLE);
                    bankTip.setText(payMethodInfo.getDescription());
                }
            }
            if (weixinRl.getVisibility() == View.VISIBLE) {
                paymentMethod = StaticData.WX_PAY;
                selectPayType();
                return;
            }
            if (aliRl.getVisibility() == View.VISIBLE) {
                paymentMethod = StaticData.ALI_PAY;
                selectPayType();
                return;
            }
            if (bankRl.getVisibility() == View.VISIBLE) {
                paymentMethod = StaticData.BAO_FU_PAY;
                selectPayType();
                return;
            }
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

    public class MyHandler extends StaticHandler<CerifyPayActivity> {

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

    public void wechatPay(WXPaySignResponse wxPaySignResponse) {
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
        if (event != null) {
            if (event.code == -1) {
                showMessage(getString(R.string.pay_failed));
            } else if (event.code == -2) {
                showMessage(getString(R.string.pay_cancel));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void bankPay(){
        Intent intent = new Intent(this, BankCardPayActivity.class);
        intent.putExtra(StaticData.USER_PAY_INFO, userPayInfo);
        startActivityForResult(intent, RequestCodeStatic.BACK_BANE_RESULT);
    }

    private void backResult(String result){
        if(TextUtils.equals(StaticData.BANK_PAY_SUCCESS,result)){
            NameVerifiedActivity.start(this);
            finish();
        }else if(TextUtils.equals(StaticData.BANK_PAY_PROCESSING,result)){
            showMessage(getString(R.string.processing));
        }else if(TextUtils.equals(StaticData.BANK_PAY_FAILED,result)){
            showMessage(getString(R.string.pay_failed));
        }else {
            showMessage(getString(R.string.pay_cancel));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodeStatic.BACK_BANE_RESULT:
                    if(data!=null){
                        result=data.getStringExtra(StaticData.PAY_RESULT);
                        backResult(result);
                    }
                    break;
                default:
            }
        }
    }
}
