package com.mall.sls.member.ui;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.alipay.sdk.app.PayTask;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.certify.ui.CerifyTipActivity;
import com.mall.sls.certify.ui.NameVerifiedActivity;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.PayResult;
import com.mall.sls.common.unit.PayTypeInstalledUtils;
import com.mall.sls.common.unit.StaticHandler;
import com.mall.sls.common.unit.VerifyManager;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.AliPay;
import com.mall.sls.data.entity.BaoFuPay;
import com.mall.sls.data.entity.LocalTeam;
import com.mall.sls.data.entity.WXPaySignResponse;
import com.mall.sls.data.entity.WxPay;
import com.mall.sls.data.event.PayAbortEvent;
import com.mall.sls.data.event.WXSuccessPayEvent;
import com.mall.sls.homepage.ui.ActivityGroupGoodsActivity;
import com.mall.sls.homepage.ui.SelectPayTypeActivity;
import com.mall.sls.member.DaggerMemberComponent;
import com.mall.sls.member.MemberContract;
import com.mall.sls.member.MemberModule;
import com.mall.sls.member.adapter.MemberGoodsItemAdapter;
import com.mall.sls.member.presenter.SuperMemberPresenter;
import com.mall.sls.mine.ui.InviteFriendsActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
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

/**
 * @author jwc on 2020/5/12.
 * 描述：会员
 */
public class SuperMemberActivity extends BaseActivity implements MemberContract.SuperMemberView, MemberGoodsItemAdapter.OnItemClickListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.head_photo)
    RoundedImageView headPhoto;
    @BindView(R.id.phone)
    ConventionalTextView phone;
    @BindView(R.id.status)
    ConventionalTextView status;
    @BindView(R.id.description)
    ConventionalTextView description;
    @BindView(R.id.confirm_bt)
    ImageView confirmBt;
    @BindView(R.id.record_rv)
    RecyclerView recordRv;
    @BindView(R.id.invite_friends)
    ImageView inviteFriends;
    @BindView(R.id.member_title_iv)
    ImageView memberTitleIv;
    @BindView(R.id.endTime)
    ConventionalTextView endTime;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private Handler mHandler = new MyHandler(this);

    private MemberGoodsItemAdapter memberGoodsItemAdapter;
    private String avatarUrl;
    private String mobile;
    private String vipAmount;
    private String vipDescription;
    private boolean certifyPay;
    private String certifyAmount;
    private String vipExpireDate;
    private String orderType;
    private String paymentMethod;
    private String orderId;
    @Inject
    SuperMemberPresenter superMemberPresenter;

    public static void start(Context context, String avatarUrl, String mobile, String vipAmount, String vipDescription, boolean certifyPay, String certifyAmount, String vipExpireDate) {
        Intent intent = new Intent(context, SuperMemberActivity.class);
        intent.putExtra(StaticData.AVATAR_URL, avatarUrl);
        intent.putExtra(StaticData.MOBILE, mobile);
        intent.putExtra(StaticData.VIP_AMOUNT, vipAmount);
        intent.putExtra(StaticData.VIP_DESCRIPTION, vipDescription);
        intent.putExtra(StaticData.CERTIFY_PAY, certifyPay);
        intent.putExtra(StaticData.CERTIFY_AMOUNT, certifyAmount);
        intent.putExtra(StaticData.VIP_EXPIRE_DATE, vipExpireDate);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_menber);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        refreshLayout.setOnMultiPurposeListener(simpleMultiPurposeListener);
        orderType=StaticData.TYPE_SUPER;
        EventBus.getDefault().register(this);
        avatarUrl = getIntent().getStringExtra(StaticData.AVATAR_URL);
        mobile = getIntent().getStringExtra(StaticData.MOBILE);
        vipAmount = getIntent().getStringExtra(StaticData.VIP_AMOUNT);
        vipDescription = getIntent().getStringExtra(StaticData.VIP_DESCRIPTION);
        certifyPay = getIntent().getBooleanExtra(StaticData.CERTIFY_PAY, false);
        certifyAmount = getIntent().getStringExtra(StaticData.CERTIFY_AMOUNT);
        vipExpireDate = getIntent().getStringExtra(StaticData.VIP_EXPIRE_DATE);
        GlideHelper.load(this, avatarUrl, R.mipmap.icon_defalut_head, headPhoto);
        phone.setText(mobile);
        memberGoodsItemAdapter = new MemberGoodsItemAdapter(this);
        memberGoodsItemAdapter.setOnItemClickListener(this);
        recordRv.setAdapter(memberGoodsItemAdapter);
        confirmBt.setEnabled(TextUtils.equals(StaticData.REFRESH_THREE, VerifyManager.getVerify()) ? false : true);

        if (TextUtils.equals(StaticData.REFRESH_TWO, VerifyManager.getVerify())) {
            confirmBt.setEnabled(false);
            status.setText(getString(R.string.is_open));
            endTime.setText(vipExpireDate + "到期");
        } else {
            confirmBt.setEnabled(true);
            status.setText(getString(R.string.nonactivated));
        }
        superMemberPresenter.getVipGroupons(StaticData.REFRESH_ONE);
    }


    SimpleMultiPurposeListener simpleMultiPurposeListener = new SimpleMultiPurposeListener() {
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            refreshLayout.finishRefresh(6000);
            superMemberPresenter.getVipGroupons(StaticData.REFRESH_ZERO);
        }

        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            superMemberPresenter.getMoreVipGroupons();
        }
    };


    @OnClick({R.id.confirm_bt, R.id.back, R.id.description, R.id.invite_friends})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_bt:
                if (TextUtils.equals(StaticData.REFRESH_ZERO, VerifyManager.getVerify())) {
                    showMessage(getString(R.string.to_open_person_authentication));
                    if (certifyPay) {
                        NameVerifiedActivity.start(this,StaticData.REFRESH_ZERO);
                    } else {
                        CerifyTipActivity.start(this, certifyAmount);
                    }
                } else {
                    Intent intent = new Intent(this, SelectPayTypeActivity.class);
                    intent.putExtra(StaticData.CHOICE_TYPE, StaticData.REFRESH_ONE);
                    intent.putExtra(StaticData.PAYMENT_AMOUNT, vipAmount);
                    intent.putExtra(StaticData.ORDER_TYPE,StaticData.TYPE_SUPER);
                    startActivityForResult(intent, RequestCodeStatic.PAY_TYPE);
                }
                break;
            case R.id.back:
                finish();
                break;
            case R.id.description:
                MemberDescriptionActivity.start(this, vipDescription);
                break;
            case R.id.invite_friends:
                InviteFriendsActivity.start(this);
                break;
            default:
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodeStatic.PAY_TYPE:
                    if (data != null) {
                        paymentMethod = data.getStringExtra(StaticData.PAYMENT_METHOD);
                        if (TextUtils.equals(StaticData.WX_PAY, paymentMethod)) {
                            //微信
                            if (PayTypeInstalledUtils.isWeixinAvilible(SuperMemberActivity.this)) {
                                superMemberPresenter.getWxPay(orderId,orderType,paymentMethod);
                            } else {
                                showMessage(getString(R.string.install_weixin));
                            }
                        } else if (TextUtils.equals(StaticData.ALI_PAY, paymentMethod)) {
                            if (PayTypeInstalledUtils.isAliPayInstalled(SuperMemberActivity.this)) {
                                superMemberPresenter.getAliPay(orderId,orderType,paymentMethod);
                            } else {
                                showMessage(getString(R.string.install_alipay));
                            }
                        }
                    }
                    break;
                default:
            }
        }
    }

    @Override
    protected void initializeInjector() {
        DaggerMemberComponent.builder()
                .applicationComponent(getApplicationComponent())
                .memberModule(new MemberModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void renderVipGroupons(LocalTeam localTeam) {
        refreshLayout.finishRefresh();
        if (localTeam != null) {
            if (localTeam.getGoodsItemInfos()!= null && localTeam.getGoodsItemInfos().size() > 0) {
                recordRv.setVisibility(View.VISIBLE);
                if (localTeam.getGoodsItemInfos().size() == Integer.parseInt(StaticData.TEN_LIST_SIZE)) {
                    refreshLayout.resetNoMoreData();
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
                memberGoodsItemAdapter.setData(localTeam.getGoodsItemInfos());
                memberTitleIv.setVisibility(View.VISIBLE);
            } else {
                recordRv.setVisibility(View.GONE);
                refreshLayout.finishLoadMoreWithNoMoreData();
                memberTitleIv.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void renderMoreVipGroupons(LocalTeam localTeam) {
        refreshLayout.finishLoadMore();
        if (localTeam != null && localTeam.getGoodsItemInfos() != null) {
            if (localTeam.getGoodsItemInfos().size() != Integer.parseInt(StaticData.TEN_LIST_SIZE)) {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            memberGoodsItemAdapter.addMore(localTeam.getGoodsItemInfos());
        }
    }

    @Override
    public void renderVipOpen(Boolean isBoolean) {
        if (isBoolean) {
            showMessage(getString(R.string.open_vip));
            VerifyManager.saveVerify(StaticData.REFRESH_THREE);
            confirmBt.setEnabled(false);
            status.setText(getString(R.string.is_open));
        }
    }

    @Override
    public void renderWxPay(WxPay wxPay) {
        if (wxPay != null) {
            wechatPay(wxPay.getWxPayInfo());
        }
    }

    @Override
    public void renderAliPay(AliPay aliPay) {
        if (aliPay != null && !TextUtils.isEmpty(aliPay.getAliPayInfo())) {
            startAliPay(aliPay.getAliPayInfo());
        }
    }


    @Override
    public void renderBaoFuPay(BaoFuPay baoFuPay) {

    }

    @Override
    public void setPresenter(MemberContract.SuperMemberPresente presenter) {

    }

    @Override
    public void goActivityGroupGoods(String goodsId) {
        ActivityGroupGoodsActivity.start(this, goodsId);
    }

    private void startAliPay(String sign) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                PayTask payTask = new PayTask(SuperMemberActivity.this);
                Map<String, String> result = payTask.payV2(sign, true);
                Message message = Message.obtain();
                message.what = RequestCodeStatic.SDK_PAY_FLAG;
                message.obj = result;
                mHandler.sendMessage(message);
            }
        };

        new Thread(runnable).start();
    }

    public class MyHandler extends StaticHandler<SuperMemberActivity> {

        public MyHandler(SuperMemberActivity target) {
            super(target);
        }

        @Override
        public void handle(SuperMemberActivity target, Message msg) {
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
            superMemberPresenter.vipOpen();
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
        superMemberPresenter.vipOpen();
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
}
