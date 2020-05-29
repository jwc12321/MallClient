package com.mall.sls.homepage.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.alipay.sdk.app.PayTask;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.address.ui.AddressManageActivity;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.unit.PayResult;
import com.mall.sls.common.unit.PayTypeInstalledUtils;
import com.mall.sls.common.unit.StaticHandler;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.coupon.ui.SelectCouponActivity;
import com.mall.sls.data.entity.AddressInfo;
import com.mall.sls.data.entity.CheckedGoods;
import com.mall.sls.data.entity.ConfirmOrderDetail;
import com.mall.sls.data.entity.OrderSubmitInfo;
import com.mall.sls.data.entity.WXPaySignResponse;
import com.mall.sls.data.event.PayAbortEvent;
import com.mall.sls.data.event.WXSuccessPayEvent;
import com.mall.sls.homepage.DaggerHomepageComponent;
import com.mall.sls.homepage.HomepageContract;
import com.mall.sls.homepage.HomepageModule;
import com.mall.sls.homepage.presenter.ConfirmOrderPresenter;
import com.mall.sls.order.ui.GoodsOrderDetailsActivity;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/11.
 * 描述：确认下单
 */
public class ConfirmOrderActivity extends BaseActivity implements HomepageContract.ConfirmOrderView {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.right_arrow_iv)
    ImageView rightArrowIv;
    @BindView(R.id.no_address_tv)
    ConventionalTextView noAddressTv;
    @BindView(R.id.name)
    MediumThickTextView name;
    @BindView(R.id.phone)
    ConventionalTextView phone;
    @BindView(R.id.address)
    ConventionalTextView address;
    @BindView(R.id.address_rl)
    LinearLayout addressRl;
    @BindView(R.id.goods_iv)
    ImageView goodsIv;
    @BindView(R.id.goods_name)
    MediumThickTextView goodsName;
    @BindView(R.id.goods_number)
    ConventionalTextView goodsNumber;
    @BindView(R.id.goods_price)
    ConventionalTextView goodsPrice;
    @BindView(R.id.buyer_message_tv)
    ConventionalTextView buyerMessageTv;
    @BindView(R.id.buyer_message)
    TextView buyerMessage;
    @BindView(R.id.buyer_message_iv)
    ImageView buyerMessageIv;
    @BindView(R.id.goods_total_price)
    ConventionalTextView goodsTotalPrice;
    @BindView(R.id.coupon_iv)
    ImageView couponIv;
    @BindView(R.id.coupon)
    ConventionalTextView coupon;
    @BindView(R.id.coupon_rl)
    RelativeLayout couponRl;
    @BindView(R.id.order_price)
    ConventionalTextView orderPrice;
    @BindView(R.id.confirm_bt)
    ConventionalTextView confirmBt;
    @BindView(R.id.total_amount_tv)
    ConventionalTextView totalAmountTv;
    @BindView(R.id.total_amount)
    ConventionalTextView totalAmount;
    @BindView(R.id.address_all)
    RelativeLayout addressAll;
    @BindView(R.id.remark_rl)
    RelativeLayout remarkRl;
    private ConfirmOrderDetail confirmOrderDetail;
    private AddressInfo addressInfo;
    private String addressId;
    private List<CheckedGoods> checkedGoodsList;
    private CheckedGoods checkedGoods;
    private String couponId;
    private String userCouponId;
    private String cartId;
    private String message = "";
    //1:单独购买 2：发起拼单 3：拼团 4：百人团
    private String purchaseType;
    private Handler mHandler = new MyHandler(this);
    private String orderId;
    private String orderTotalPrice;
    private String goodsProductId;
    private String grouponId;
    private String backType;
    private String goodsId;
    private String tipBack;
    private String nameText;
    private String briefText;
    private String groupExpireTime;
    private String wxUrl;
    private String inviteCode;

    @Inject
    ConfirmOrderPresenter confirmOrderPresenter;

    public static void start(Context context, ConfirmOrderDetail confirmOrderDetail, String purchaseType,String wxUrl,String inviteCode) {
        Intent intent = new Intent(context, ConfirmOrderActivity.class);
        intent.putExtra(StaticData.CONFIRM_ORDER_DETAIL, (Serializable) confirmOrderDetail);
        intent.putExtra(StaticData.PURCHASE_TYPE, purchaseType);
        intent.putExtra(StaticData.WX_URL,wxUrl);
        intent.putExtra(StaticData.INVITE_CODE,inviteCode);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        EventBus.getDefault().register(this);
        confirmOrderDetail = (ConfirmOrderDetail) getIntent().getSerializableExtra(StaticData.CONFIRM_ORDER_DETAIL);
        purchaseType = getIntent().getStringExtra(StaticData.PURCHASE_TYPE);
        wxUrl=getIntent().getStringExtra(StaticData.WX_URL);
        inviteCode=getIntent().getStringExtra(StaticData.INVITE_CODE);
        confirmDetail();
    }

    private void confirmDetail() {
        if (confirmOrderDetail != null) {
            addressInfo = confirmOrderDetail.getAddressInfo();
            address();
            cartId = confirmOrderDetail.getCartId();
            checkedGoodsList = confirmOrderDetail.getCheckedGoods();
            if (checkedGoodsList != null && checkedGoodsList.size() > 0) {
                //现在没有购物车，所以单独一个
                checkedGoods = checkedGoodsList.get(0);
                goodsId=checkedGoods.getGoodsId();
                goodsProductId = checkedGoods.getProductId();
                goodsNumber.setText("x" + checkedGoods.getNumber());
                nameText=checkedGoods.getGoodsName();
                briefText=checkedGoods.getBrief();
                goodsName.setText(checkedGoods.getGoodsName());
                goodsPrice.setText("¥" + NumberFormatUnit.twoDecimalFormat(checkedGoods.getPrice()));
                GlideHelper.load(this, checkedGoods.getPicUrl(), R.mipmap.icon_default_goods, goodsIv);
                groupExpireTime=checkedGoods.getGroupExpireTime();
            }
            orderTotalPrice = confirmOrderDetail.getOrderTotalPrice();
            goodsTotalPrice.setText("¥" + NumberFormatUnit.twoDecimalFormat(confirmOrderDetail.getGoodsTotalPrice()));
            orderPrice.setText("¥" + NumberFormatUnit.twoDecimalFormat(confirmOrderDetail.getOrderTotalPrice()));
            totalAmount.setText("¥" + NumberFormatUnit.twoDecimalFormat(confirmOrderDetail.getOrderTotalPrice()));
            couponId = confirmOrderDetail.getCouponId();
            userCouponId = confirmOrderDetail.getUserCouponId();
            if (TextUtils.equals(StaticData.REFLASH_ZERO, confirmOrderDetail.getAvailableCouponLength())) {
                couponIv.setVisibility(View.GONE);
                coupon.setText("0张优惠卷可用");
            } else {
                couponIv.setVisibility(View.VISIBLE);
                if (TextUtils.equals("-1", couponId)) {
                    coupon.setText(confirmOrderDetail.getAvailableCouponLength() + "张优惠卷可用");
                } else {
                    coupon.setText("-¥" + confirmOrderDetail.getCouponPrice());
                }
            }
        }
    }

    private void address() {
        if (addressInfo != null && !TextUtils.equals(StaticData.REFLASH_ZERO, addressInfo.getId())) {
            addressId = addressInfo.getId();
            noAddressTv.setVisibility(View.GONE);
            addressRl.setVisibility(View.VISIBLE);
            name.setText(addressInfo.getName());
            phone.setText(addressInfo.getTel());
            address.setText(addressInfo.getProvince() + addressInfo.getCity() + addressInfo.getCounty() + addressInfo.getAddressDetail());
        } else {
            noAddressTv.setVisibility(View.VISIBLE);
            addressRl.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initializeInjector() {
        DaggerHomepageComponent.builder()
                .applicationComponent(getApplicationComponent())
                .homepageModule(new HomepageModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.back, R.id.confirm_bt, R.id.coupon_rl, R.id.address_all, R.id.remark_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                back();
                break;
            case R.id.confirm_bt://去支付
                confirmOrderPresenter.orderSubmit(addressId, cartId, couponId, userCouponId, message);
                break;
            case R.id.coupon_rl:
                Intent couponIntent = new Intent(this, SelectCouponActivity.class);
                couponIntent.putExtra(StaticData.CART_IDS, cartId);
                couponIntent.putExtra(StaticData.USER_COUPON_ID, userCouponId);
                startActivityForResult(couponIntent, RequestCodeStatic.SELECT_COUPON);
                break;
            case R.id.address_all://选择地址
                Intent intent = new Intent(this, AddressManageActivity.class);
                intent.putExtra(StaticData.CHOICE_TYPE, StaticData.REFLASH_ZERO);
                startActivityForResult(intent, RequestCodeStatic.REQUEST_ADDRESS);
                break;
            case R.id.remark_rl:
                Intent remarkIntent = new Intent(this, FillRemarksActivity.class);
                remarkIntent.putExtra(StaticData.REMARK, message);
                startActivityForResult(remarkIntent, RequestCodeStatic.REQUEST_REMARK);
                break;
            default:
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodeStatic.REQUEST_ADDRESS:
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        addressInfo = (AddressInfo) bundle.getSerializable(StaticData.ADDRESS_INFO);
                        addressId = addressInfo.getId();
                        confirmOrderPresenter.cartCheckout(addressId, cartId, couponId, userCouponId);
                    }
                    break;
                case RequestCodeStatic.REQUEST_REMARK:
                    if (data != null) {
                        message = data.getStringExtra(StaticData.REMARK);
                        buyerMessage.setText(message);
                    }
                    break;
                case RequestCodeStatic.SELECT_COUPON:
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        couponId = bundle.getString(StaticData.COUPON_ID);
                        userCouponId = bundle.getString(StaticData.USER_COUPON_ID);
                        confirmOrderPresenter.cartCheckout(addressId, cartId, couponId, userCouponId);
                    }
                    break;
                case RequestCodeStatic.PAY_TYPE:
                    if (data != null) {
                        String selectType = data.getStringExtra(StaticData.SELECT_TYPE);
                        if (TextUtils.equals(StaticData.REFLASH_ZERO, selectType)) {
                            //微信
                            if (PayTypeInstalledUtils.isWeixinAvilible(ConfirmOrderActivity.this)) {

                            } else {
                                showMessage(getString(R.string.install_weixin));
                            }
                        } else {
                            if (PayTypeInstalledUtils.isAliPayInstalled(ConfirmOrderActivity.this)) {
                                confirmOrderPresenter.orderAliPay(orderId, StaticData.REFLASH_ONE);
                            } else {
                                showMessage(getString(R.string.install_alipay));
                            }
                        }
                    }
                    break;
                case RequestCodeStatic.ALONE_GROUP:
                    if (data != null) {
                        backType = data.getStringExtra(StaticData.BACK_TYPE);
                        if(TextUtils.equals(StaticData.REFLASH_ONE,backType)){
                            WXShareBackActivity.start(this,StaticData.REFLASH_ZERO,nameText,briefText,goodsId,wxUrl,inviteCode,"",grouponId,goodsProductId);
                            finish();
                        }else {
                            GoodsOrderDetailsActivity.start(this, orderId);
                            finish();
                        }
                    }
                    break;
                case RequestCodeStatic.MANY_GROUP:
                    if (data != null) {
                        backType = data.getStringExtra(StaticData.BACK_TYPE);
                        if(TextUtils.equals(StaticData.REFLASH_ONE,backType)){
                            WXShareBackActivity.start(this,StaticData.REFLASH_ONE,nameText,briefText,goodsId,wxUrl,inviteCode,groupExpireTime,grouponId,goodsProductId);
                            finish();
                        }else {
                            GoodsOrderDetailsActivity.start(this, orderId);
                            finish();
                        }
                    }
                    break;
                case RequestCodeStatic.TIP_PAGE:
                    if (data != null) {
                        tipBack = data.getStringExtra(StaticData.TIP_BACK);
                        if(TextUtils.equals(StaticData.REFLASH_ONE,tipBack)){
                            confirmOrderPresenter.orderSubmit(addressId, cartId, couponId, userCouponId, message);
                        }else {
                            finish();
                        }
                    }
                    break;
                    case RequestCodeStatic.PINYIN_SUCCESS:
                        if (data != null) {
                            backType = data.getStringExtra(StaticData.BACK_TYPE);
                            if(TextUtils.equals(StaticData.REFLASH_ONE,backType)){
                                finish();
                            }else {
                                GoodsOrderDetailsActivity.start(this, orderId);
                                finish();
                            }
                        }
                        break;
                default:
            }
        }
    }

    private void back(){
        Intent intent = new Intent(this, CommonTipActivity.class);
        intent.putExtra(StaticData.COMMON_TITLE, getString(R.string.cancel_pay_tip));
        intent.putExtra(StaticData.CANCEL_TEXT, getString(R.string.cancel_pay_cancel_text));
        intent.putExtra(StaticData.CONFIRM_TEXT, getString(R.string.cancel_pay_confirm_text));
        startActivityForResult(intent, RequestCodeStatic.TIP_PAGE);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void renderCartCheckout(ConfirmOrderDetail confirmOrderDetail) {
        this.confirmOrderDetail = confirmOrderDetail;
        confirmDetail();
    }

    @Override
    public void renderOrderSubmit(OrderSubmitInfo orderSubmitInfo) {
        if (orderSubmitInfo != null) {
            orderId = orderSubmitInfo.getOrderId();
            grouponId = orderSubmitInfo.getGrouponLinkId();
            Intent intent = new Intent(this, SelectPayTypeActivity.class);
            intent.putExtra(StaticData.CHOICE_TYPE, StaticData.REFLASH_TWO);
            intent.putExtra(StaticData.PAYMENT_AMOUNT,orderTotalPrice);
            startActivityForResult(intent, RequestCodeStatic.PAY_TYPE);
        }
    }

    @Override
    public void renderOrderAliPay(String alipayStr) {
        if (!TextUtils.isEmpty(alipayStr)) {
            startAliPay(alipayStr);
        }
    }

    @Override
    public void setPresenter(HomepageContract.ConfirmOrderPresenter presenter) {

    }


    private void startAliPay(String sign) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                PayTask payTask = new PayTask(ConfirmOrderActivity.this);
                Map<String, String> result = payTask.payV2(sign, true);
                Message message = Message.obtain();
                message.what = RequestCodeStatic.SDK_PAY_FLAG;
                message.obj = result;
                mHandler.sendMessage(message);
            }
        };

        new Thread(runnable).start();
    }

    public static class MyHandler extends StaticHandler<ConfirmOrderActivity> {

        public MyHandler(ConfirmOrderActivity target) {
            super(target);
        }

        @Override
        public void handle(ConfirmOrderActivity target, Message msg) {
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
        Log.d("111", "数据" + payResult.getResult() + "==" + payResult.getResultStatus());
        if (TextUtils.equals(resultStatus, "9000")) {
            paySuccess();
        } else if (TextUtils.equals(resultStatus, "6001")) {
            showMessage(getString(R.string.pay_cancel));
            GoodsOrderDetailsActivity.start(this, orderId);
            finish();
        } else {
            showMessage(getString(R.string.pay_failed));
            GoodsOrderDetailsActivity.start(this, orderId);
            finish();
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
        paySuccess();
    }

    //支付失败
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayCancel(PayAbortEvent event) {
        if(event!=null){
            if(event.code==-1){
                showMessage(getString(R.string.pay_failed));
                GoodsOrderDetailsActivity.start(this, orderId);
                finish();
            }else if(event.code==-2){
                showMessage(getString(R.string.pay_cancel));
                GoodsOrderDetailsActivity.start(this, orderId);
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void paySuccess() {
        if (TextUtils.equals(StaticData.REFLASH_ONE, purchaseType)) {
            GoodsOrderDetailsActivity.start(this, orderId);
            finish();
        } else if (TextUtils.equals(StaticData.REFLASH_TWO, purchaseType)) {
            Intent intent = new Intent(this, AloneGroupActivity.class);
            intent.putExtra(StaticData.GOODS_PRODUCT_ID, goodsProductId);
            intent.putExtra(StaticData.GROUPON_ID, grouponId);
            intent.putExtra(StaticData.WX_URL, wxUrl);
            intent.putExtra(StaticData.INVITE_CODE,inviteCode);
            intent.putExtra(StaticData.GOODS_NAME,nameText);
            intent.putExtra(StaticData.GOODS_BRIEF,briefText);

            startActivityForResult(intent, RequestCodeStatic.ALONE_GROUP);
        } else if (TextUtils.equals(StaticData.REFLASH_THREE, purchaseType)) {
            Intent intent = new Intent(this, SuccessfulOrderActivity.class);
            startActivityForResult(intent, RequestCodeStatic.PINYIN_SUCCESS);

        } else if (TextUtils.equals(StaticData.REFLASH_FOUR, purchaseType)) {
            Intent intent = new Intent(this, ManyGroupActivity.class);
            intent.putExtra(StaticData.GOODS_ID, goodsId);
            intent.putExtra(StaticData.WX_URL, wxUrl);
            intent.putExtra(StaticData.INVITE_CODE,inviteCode);
            intent.putExtra(StaticData.GOODS_NAME,nameText);
            intent.putExtra(StaticData.GOODS_BRIEF,briefText);
            startActivityForResult(intent, RequestCodeStatic.MANY_GROUP);
        }
    }
}
