package com.mall.sls.homepage.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.alipay.sdk.app.PayTask;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.address.ui.AddressManageActivity;
import com.mall.sls.bank.ui.AddChinaGCardActivity;
import com.mall.sls.bank.ui.BankCardPayActivity;
import com.mall.sls.bank.ui.BankPayResultActivity;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.BriefUnit;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.unit.PayResult;
import com.mall.sls.common.unit.PayTypeInstalledUtils;
import com.mall.sls.common.unit.StaticHandler;
import com.mall.sls.common.unit.TCAgentUnit;
import com.mall.sls.common.widget.scrollview.ReboundScrollView;
import com.mall.sls.common.widget.textview.ConventionalEditTextView;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.coupon.ui.SelectCouponActivity;
import com.mall.sls.data.entity.AddressInfo;
import com.mall.sls.data.entity.AiNongPay;
import com.mall.sls.data.entity.AliPay;
import com.mall.sls.data.entity.BaoFuPay;
import com.mall.sls.data.entity.CheckedGoods;
import com.mall.sls.data.entity.ConfirmOrderDetail;
import com.mall.sls.data.entity.OrderSubmitInfo;
import com.mall.sls.data.entity.UserPayInfo;
import com.mall.sls.data.entity.WXPaySignResponse;
import com.mall.sls.data.entity.WxPay;
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
import butterknife.OnTextChanged;

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
    @BindView(R.id.address_all)
    RelativeLayout addressAll;
    @BindView(R.id.goods_iv)
    ImageView goodsIv;
    @BindView(R.id.goods_name)
    MediumThickTextView goodsName;
    @BindView(R.id.goods_number)
    ConventionalTextView goodsNumber;
    @BindView(R.id.goods_price)
    MediumThickTextView goodsPrice;
    @BindView(R.id.goods_total_price)
    ConventionalTextView goodsTotalPrice;
    @BindView(R.id.coupon_iv)
    ImageView couponIv;
    @BindView(R.id.coupon)
    ConventionalTextView coupon;
    @BindView(R.id.coupon_rl)
    RelativeLayout couponRl;
    @BindView(R.id.delivery_method)
    ConventionalTextView deliveryMethod;
    @BindView(R.id.delivery_fee)
    ConventionalTextView deliveryFee;
    @BindView(R.id.order_notes_tv)
    ConventionalTextView orderNotesTv;
    @BindView(R.id.notes_et)
    ConventionalEditTextView notesEt;
    @BindView(R.id.confirm_bt)
    ConventionalTextView confirmBt;
    @BindView(R.id.total_amount_tv)
    ConventionalTextView totalAmountTv;
    @BindView(R.id.total_amount)
    MediumThickTextView totalAmount;
    @BindView(R.id.sku)
    ConventionalTextView sku;
    @BindView(R.id.goods_price_rl)
    RelativeLayout goodsPriceRl;
    @BindView(R.id.delivery_method_rl)
    RelativeLayout deliveryMethodRl;
    @BindView(R.id.delivery_fee_iv)
    ImageView deliveryFeeIv;
    @BindView(R.id.delivery_fee_tip)
    ConventionalTextView deliveryFeeTip;
    @BindView(R.id.delivery_fee_rl)
    RelativeLayout deliveryFeeRl;
    @BindView(R.id.same_city_bt)
    ConventionalTextView sameCityBt;
    @BindView(R.id.express_delivery_bt)
    ConventionalTextView expressDeliveryBt;
    @BindView(R.id.select_delivery_method_rl)
    LinearLayout selectDeliveryMethodRl;
    @BindView(R.id.goods_price_ll)
    LinearLayout goodsPriceLl;
    @BindView(R.id.item_rl)
    RelativeLayout itemRl;
    @BindView(R.id.scrollView)
    ReboundScrollView scrollView;
    private ConfirmOrderDetail confirmOrderDetail;
    private AddressInfo addressInfo;
    private String addressId;
    private List<CheckedGoods> checkedGoodsList;
    private CheckedGoods checkedGoods;
    private String couponId;
    private String userCouponId;
    private String message = "";
    //1:单独购买 2：发起拼单 3：拼团 4：百人团
    private String purchaseType;
    private Handler mHandler = new MyHandler(this);
    private String orderId;
    private String orderTotalPrice;
    private String goodsProductId;
    private String grouponId;
    private String goodsId;
    private String tipBack;
    private String nameText;
    private String briefText;
    private String wxUrl;
    private String inviteCode;
    private String picUrl;
    private String paymentMethod;
    private String cartIds;
    private String orderType;
    private UserPayInfo userPayInfo;
    private String choiceType;
    private String result;
    private String shipChannel = "";
    private Boolean outShip = false;

    @Inject
    ConfirmOrderPresenter confirmOrderPresenter;

    public static void start(Context context, ConfirmOrderDetail confirmOrderDetail, String purchaseType, String wxUrl, String inviteCode) {
        Intent intent = new Intent(context, ConfirmOrderActivity.class);
        intent.putExtra(StaticData.CONFIRM_ORDER_DETAIL, (Serializable) confirmOrderDetail);
        intent.putExtra(StaticData.PURCHASE_TYPE, purchaseType);
        intent.putExtra(StaticData.WX_URL, wxUrl);
        intent.putExtra(StaticData.INVITE_CODE, inviteCode);
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
        choiceType = StaticData.REFRESH_ONE;
        confirmOrderDetail = (ConfirmOrderDetail) getIntent().getSerializableExtra(StaticData.CONFIRM_ORDER_DETAIL);
        purchaseType = getIntent().getStringExtra(StaticData.PURCHASE_TYPE);
        wxUrl = getIntent().getStringExtra(StaticData.WX_URL);
        inviteCode = getIntent().getStringExtra(StaticData.INVITE_CODE);
        orderType = StaticData.TYPE_ORDER;
        confirmDetail();
        confirmOrderPresenter.getDeliveryMethod();
    }

    private void confirmDetail() {
        if (confirmOrderDetail != null) {
            outShip = confirmOrderDetail.getOutShip();
            addressInfo = confirmOrderDetail.getAddressInfo();
            address();
            cartIds = confirmOrderDetail.getCartId();
            checkedGoodsList = confirmOrderDetail.getCheckedGoods();
            if (checkedGoodsList != null && checkedGoodsList.size() > 0) {
                //现在没有购物车，所以单独一个
                checkedGoods = checkedGoodsList.get(0);
                goodsId = checkedGoods.getGoodsId();
                goodsProductId = checkedGoods.getProductId();
                goodsNumber.setText("x" + checkedGoods.getNumber());
                nameText = BriefUnit.returnName(checkedGoods.getPrice(), checkedGoods.getGoodsName());
                goodsName.setText(checkedGoods.getGoodsName());
                goodsPrice.setText(NumberFormatUnit.numberFormat(checkedGoods.getPrice()));
                GlideHelper.load(this, checkedGoods.getPicUrl(), R.mipmap.icon_default_goods, goodsIv);
                sku.setText(checkedGoods.getSpecifications());
                picUrl = checkedGoods.getPicUrl();
            }
            briefText = BriefUnit.returnBrief(confirmOrderDetail.getBrief());
            orderTotalPrice = confirmOrderDetail.getOrderTotalPrice();
            goodsTotalPrice.setText(NumberFormatUnit.goodsFormat(confirmOrderDetail.getGoodsTotalPrice()));
            totalAmount.setText(NumberFormatUnit.numberFormat(confirmOrderDetail.getOrderTotalPrice()));
            couponId = confirmOrderDetail.getCouponId();
            userCouponId = confirmOrderDetail.getUserCouponId();
            if (TextUtils.equals(StaticData.REFRESH_ZERO, confirmOrderDetail.getAvailableCouponLength())) {
                coupon.setText(getString(R.string.no_available));
            } else {
                if (TextUtils.equals(StaticData.REFRESH_ZERO, userCouponId) || TextUtils.equals(StaticData.REFRESH_MINUS_ONE, userCouponId)) {
                    coupon.setText(confirmOrderDetail.getAvailableCouponLength() + "张优惠券可用");
                } else {
                    coupon.setText("-" + NumberFormatUnit.goodsFormat(confirmOrderDetail.getCouponPrice()));
                }
            }
            if (TextUtils.isEmpty(shipChannel)) {
                deliveryFee.setText("¥ 0");
            } else {
                if (NumberFormatUnit.isZero(confirmOrderDetail.getFreightPrice())) {
                    deliveryFee.setText(getString(R.string.free_shipping));
                } else {
                    deliveryFee.setText(NumberFormatUnit.goodsFormat(confirmOrderDetail.getFreightPrice()));
                }
            }
            deliveryFeeTip.setVisibility(TextUtils.isEmpty(confirmOrderDetail.getFreeShipDes()) ? View.GONE : View.VISIBLE);
            deliveryFeeTip.setText("(" + confirmOrderDetail.getFreeShipDes() + ")");
            if (outShip != null && outShip) {
                commonTip();
            }
        }
    }

    private void commonTip() {
        Intent intent = new Intent(this, CommonTipActivity.class);
        intent.putExtra(StaticData.COMMON_TITLE, getString(R.string.change_shipping_method));
        intent.putExtra(StaticData.CANCEL_TEXT, getString(R.string.cancel));
        intent.putExtra(StaticData.CONFIRM_TEXT, getString(R.string.express_delivery));
        startActivityForResult(intent, RequestCodeStatic.COMMON_TIP_PAGE);
    }

    private void address() {
        if (addressInfo != null && !TextUtils.equals(StaticData.REFRESH_ZERO, addressInfo.getId())) {
            addressId = addressInfo.getId();
            noAddressTv.setVisibility(View.GONE);
            addressRl.setVisibility(View.VISIBLE);
            name.setText(addressInfo.getName());
            phone.setText(addressInfo.getTel());
            address.setText(addressInfo.getProvince() + addressInfo.getCity() + addressInfo.getCounty() + addressInfo.getAddressDetail());
        } else {
            addressId = "";
            noAddressTv.setVisibility(View.VISIBLE);
            addressRl.setVisibility(View.GONE);
        }
    }


    @OnTextChanged({R.id.notes_et})
    public void checkPhoneEnable() {
        message = notesEt.getText().toString().trim();
    }

    @Override
    protected void initializeInjector() {
        DaggerHomepageComponent.builder()
                .applicationComponent(getApplicationComponent())
                .homepageModule(new HomepageModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.back, R.id.confirm_bt, R.id.coupon_rl, R.id.address_all, R.id.delivery_method_rl, R.id.same_city_bt, R.id.express_delivery_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                goneDelivery();
                back();
                break;
            case R.id.confirm_bt://去支付
                goneDelivery();
                confirm();
                break;
            case R.id.coupon_rl:
                goneDelivery();
                Intent couponIntent = new Intent(this, SelectCouponActivity.class);
                couponIntent.putExtra(StaticData.CART_IDS, (Serializable) cartIds);
                couponIntent.putExtra(StaticData.USER_COUPON_ID, userCouponId);
                startActivityForResult(couponIntent, RequestCodeStatic.SELECT_COUPON);
                break;
            case R.id.address_all://选择地址
                goneDelivery();
                Intent intent = new Intent(this, AddressManageActivity.class);
                intent.putExtra(StaticData.CHOICE_TYPE, StaticData.REFRESH_ZERO);
                intent.putExtra(StaticData.SELECT_ID, addressId);
                startActivityForResult(intent, RequestCodeStatic.REQUEST_ADDRESS);
                break;
            case R.id.delivery_method_rl://配送方式
                clickDelivery();
                break;
            case R.id.same_city_bt://同城
                deliveryFeeIv.setSelected(false);
                shipChannel = StaticData.SF_SAME_CITY;
                deliveryMethodSelect();
                deliveryMethod.setText(getString(R.string.same_city));
                confirmOrderPresenter.cartCheckout(addressId, cartIds, couponId, userCouponId, shipChannel);
                break;
            case R.id.express_delivery_bt://快递
                deliveryFeeIv.setSelected(false);
                shipChannel = StaticData.SF_EXPRESS;
                deliveryMethodSelect();
                deliveryMethod.setText(getString(R.string.express_delivery));
                confirmOrderPresenter.cartCheckout(addressId, cartIds, couponId, userCouponId, shipChannel);
                break;
            default:
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodeStatic.REQUEST_ADDRESS://地址
                    if (data != null) {
                        addressId = data.getStringExtra(StaticData.ADDRESS_ID);
                        confirmOrderPresenter.cartCheckout(addressId, cartIds, couponId, userCouponId, shipChannel);
                    }
                    break;
                case RequestCodeStatic.SELECT_COUPON://优惠卷
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        couponId = bundle.getString(StaticData.COUPON_ID);
                        userCouponId = bundle.getString(StaticData.USER_COUPON_ID);
                        confirmOrderPresenter.cartCheckout(addressId, cartIds, couponId, userCouponId, shipChannel);
                    }
                    break;
                case RequestCodeStatic.PAY_TYPE://选择支付方式
                    if (data != null) {
                        paymentMethod = data.getStringExtra(StaticData.PAYMENT_METHOD);
                        if (TextUtils.equals(StaticData.WX_PAY, paymentMethod)) {
                            //微信
                            if (PayTypeInstalledUtils.isWeixinAvilible(ConfirmOrderActivity.this)) {
                                confirmOrderPresenter.orderSubmit(addressId, cartIds, couponId, userCouponId, message, shipChannel);
                            } else {
                                showMessage(getString(R.string.install_weixin));
                            }
                        } else if (TextUtils.equals(StaticData.ALI_PAY, paymentMethod)) {
                            if (PayTypeInstalledUtils.isAliPayInstalled(ConfirmOrderActivity.this)) {
                                confirmOrderPresenter.orderSubmit(addressId, cartIds, couponId, userCouponId, message, shipChannel);
                            } else {
                                showMessage(getString(R.string.install_alipay));
                            }
                        } else if (TextUtils.equals(StaticData.BAO_FU_PAY, paymentMethod)) {
                            confirmOrderPresenter.orderSubmit(addressId, cartIds, couponId, userCouponId, message, shipChannel);
                        } else if (TextUtils.equals(StaticData.AI_NONG_PAY, paymentMethod)) {
                            confirmOrderPresenter.orderSubmit(addressId, cartIds, couponId, userCouponId, message, shipChannel);
                        }
                    }
                    break;
                case RequestCodeStatic.TIP_PAGE://点击返回
                    if (data != null) {
                        tipBack = data.getStringExtra(StaticData.TIP_BACK);
                        if (TextUtils.equals(StaticData.REFRESH_ONE, tipBack)) {
                            if (TextUtils.isEmpty(addressId)) {
                                showMessage(getString(R.string.select_address));
                                return;
                            }
                            if (TextUtils.equals(StaticData.REFRESH_ZERO, orderTotalPrice) || TextUtils.equals("0.00", orderTotalPrice)) {
                                confirmOrderPresenter.orderSubmit(addressId, cartIds, couponId, userCouponId, message, shipChannel);
                            } else {
                                Intent intent = new Intent(this, SelectPayTypeActivity.class);
                                intent.putExtra(StaticData.CHOICE_TYPE, StaticData.REFRESH_TWO);
                                intent.putExtra(StaticData.PAYMENT_AMOUNT, orderTotalPrice);
                                intent.putExtra(StaticData.ORDER_TYPE, StaticData.TYPE_ORDER);
                                startActivityForResult(intent, RequestCodeStatic.PAY_TYPE);
                            }
                        } else {
                            finish();
                        }
                    }
                    break;
                case RequestCodeStatic.BACK_BANE_RESULT:
                case RequestCodeStatic.CHINA_PAY:
                    if (data != null) {
                        result = data.getStringExtra(StaticData.PAY_RESULT);
                        backResult(result);
                    }
                    break;
                case RequestCodeStatic.COMMON_TIP_PAGE://点击返回
                    if (data != null) {
                        tipBack = data.getStringExtra(StaticData.TIP_BACK);
                        if (TextUtils.equals(StaticData.REFRESH_ONE, tipBack)) {
                            shipChannel = StaticData.SF_EXPRESS;
                            deliveryMethodSelect();
                            deliveryMethod.setText(getString(R.string.express_delivery));
                            confirmOrderPresenter.cartCheckout(addressId, cartIds, couponId, userCouponId, shipChannel);
                        }
                    }
                    break;
                default:
            }
        }
    }

    private void clickDelivery(){
        if(selectDeliveryMethodRl.getVisibility()==View.VISIBLE){
            deliveryFeeIv.setSelected(false);
            selectDeliveryMethodRl.setVisibility(View.GONE);
        }else {
            deliveryFeeIv.setSelected(true);
            selectDeliveryMethodRl.setVisibility(View.VISIBLE);
        }
    }

    private void goneDelivery() {
        deliveryFeeIv.setSelected(false);
        selectDeliveryMethodRl.setVisibility(View.GONE);
    }

    private void back() {
        Intent intent = new Intent(this, CommonTipActivity.class);
        intent.putExtra(StaticData.COMMON_TITLE, getString(R.string.cancel_pay_tip));
        intent.putExtra(StaticData.CANCEL_TEXT, getString(R.string.cancel_pay_cancel_text));
        intent.putExtra(StaticData.CONFIRM_TEXT, getString(R.string.cancel_pay_confirm_text));
        startActivityForResult(intent, RequestCodeStatic.TIP_PAGE);
    }

    private void confirm() {
        TCAgentUnit.setEventId(this, getString(R.string.payment));
        if (TextUtils.isEmpty(addressId)) {
            showMessage(getString(R.string.select_address));
            return;
        }
        if (TextUtils.isEmpty(shipChannel)) {
            showMessage(getString(R.string.select_delivery_method));
            return;
        }
        if (outShip != null && outShip) {
            commonTip();
            return;
        }
        if (TextUtils.equals(StaticData.REFRESH_ZERO, orderTotalPrice) || TextUtils.equals("0.00", orderTotalPrice)) {
            confirmOrderPresenter.orderSubmit(addressId, cartIds, couponId, userCouponId, message, shipChannel);
        } else {
            Intent intent = new Intent(this, SelectPayTypeActivity.class);
            intent.putExtra(StaticData.CHOICE_TYPE, StaticData.REFRESH_TWO);
            intent.putExtra(StaticData.PAYMENT_AMOUNT, orderTotalPrice);
            intent.putExtra(StaticData.ORDER_TYPE, StaticData.TYPE_ORDER);
            startActivityForResult(intent, RequestCodeStatic.PAY_TYPE);
        }
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
            if (orderSubmitInfo.getPay()) {
                if (TextUtils.equals(StaticData.WX_PAY, paymentMethod)) {
                    confirmOrderPresenter.getWxPay(orderId, orderType, paymentMethod);
                } else if (TextUtils.equals(StaticData.ALI_PAY, paymentMethod)) {
                    confirmOrderPresenter.getAliPay(orderId, orderType, paymentMethod);
                } else if (TextUtils.equals(StaticData.BAO_FU_PAY, paymentMethod)) {
                    confirmOrderPresenter.getBaoFuPay(orderId, orderType, paymentMethod);
                } else if (TextUtils.equals(StaticData.AI_NONG_PAY, paymentMethod)) {
                    confirmOrderPresenter.getAiNongPay(orderId, orderType, paymentMethod);
                }
            } else {
                paySuccess();
            }
        }
    }

    @Override
    public void renderWxPay(WxPay wxPay) {
        if (wxPay != null) {
            userPayInfo = wxPay.getUserPayInfo();
            wechatPay(wxPay.getWxPayInfo());
        }
    }

    @Override
    public void renderAliPay(AliPay aliPay) {
        if (aliPay != null) {
            userPayInfo = aliPay.getUserPayInfo();
            if (!TextUtils.isEmpty(aliPay.getAliPayInfo())) {
                startAliPay(aliPay.getAliPayInfo());
            }
        }
    }


    @Override
    public void renderBaoFuPay(BaoFuPay baoFuPay) {
        if (baoFuPay != null) {
            userPayInfo = baoFuPay.getUserPayInfo();
            bankPay();
        }
    }

    @Override
    public void renderAiNongPay(AiNongPay aiNongPay) {
        if (aiNongPay != null) {
            userPayInfo = aiNongPay.getUserPayInfo();
            aiNongPay();
        }
    }

    @Override
    public void renderDeliveryMethod(List<String> methods) {
        if(methods!=null){
            sameCityBt.setVisibility(methods.contains(StaticData.SF_SAME_CITY) ? View.VISIBLE : View.GONE);
            expressDeliveryBt.setVisibility(methods.contains(StaticData.SF_EXPRESS) ? View.VISIBLE : View.GONE);
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

    public class MyHandler extends StaticHandler<ConfirmOrderActivity> {

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
        paySuccess();
    }

    //支付失败
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayCancel(PayAbortEvent event) {
        if (event != null) {
            if (event.code == -1) {
                showMessage(getString(R.string.pay_failed));
                GoodsOrderDetailsActivity.start(this, orderId);
                finish();
            } else if (event.code == -2) {
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
        WXShareBackActivity.start(this, purchaseType, nameText, briefText, goodsId, wxUrl, inviteCode, grouponId, goodsProductId, orderId, picUrl, userPayInfo);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        TCAgentUnit.pageStart(this, getString(R.string.submit_order_page));
    }

    @Override
    protected void onPause() {
        super.onPause();
        TCAgentUnit.pageEnd(this, getString(R.string.submit_order_page));
    }

    private void bankPay() {
        Intent intent = new Intent(this, BankCardPayActivity.class);
        intent.putExtra(StaticData.USER_PAY_INFO, userPayInfo);
        startActivityForResult(intent, RequestCodeStatic.BACK_BANE_RESULT);
    }

    private void backResult(String result) {
        if (TextUtils.equals(StaticData.BANK_PAY_SUCCESS, result)) {
            paySuccess();
        } else if (TextUtils.equals(StaticData.BANK_PAY_PROCESSING, result)) {
            BankPayResultActivity.start(this, orderId, result, choiceType);
            finish();
        } else if (TextUtils.equals(StaticData.BANK_PAY_FAILED, result)) {
            GoodsOrderDetailsActivity.start(this, orderId);
            finish();
        } else {
            showMessage(getString(R.string.pay_cancel));
            GoodsOrderDetailsActivity.start(this, orderId);
            finish();
        }
    }

    private void deliveryMethodSelect() {
        sameCityBt.setSelected(TextUtils.equals(StaticData.SF_SAME_CITY, shipChannel));
        expressDeliveryBt.setSelected(TextUtils.equals(StaticData.SF_EXPRESS, shipChannel));
        selectDeliveryMethodRl.setVisibility(View.GONE);
    }

    private void aiNongPay() {
        Intent intent = new Intent(this, AddChinaGCardActivity.class);
        intent.putExtra(StaticData.PAY_ID, userPayInfo.getId());
        startActivityForResult(intent, RequestCodeStatic.CHINA_PAY);
    }
}
