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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alipay.sdk.app.PayTask;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.address.ui.AddressManageActivity;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.FlashCartManager;
import com.mall.sls.common.unit.MainStartManager;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.unit.PayResult;
import com.mall.sls.common.unit.PayTypeInstalledUtils;
import com.mall.sls.common.unit.StaticHandler;
import com.mall.sls.common.unit.TCAgentUnit;
import com.mall.sls.common.widget.edittextview.SoftKeyBoardListener;
import com.mall.sls.common.widget.textview.ConventionalEditTextView;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.coupon.ui.SelectCouponActivity;
import com.mall.sls.data.entity.AddressInfo;
import com.mall.sls.data.entity.CartItemInfo;
import com.mall.sls.data.entity.ConfirmCartOrderDetail;
import com.mall.sls.data.entity.HiddenItemCartInfo;
import com.mall.sls.data.entity.OrderSubmitInfo;
import com.mall.sls.data.entity.WXPaySignResponse;
import com.mall.sls.data.event.PayAbortEvent;
import com.mall.sls.data.event.WXSuccessPayEvent;
import com.mall.sls.homepage.DaggerHomepageComponent;
import com.mall.sls.homepage.HomepageContract;
import com.mall.sls.homepage.HomepageModule;
import com.mall.sls.homepage.adapter.ConfirmGoodsItemAdapter;
import com.mall.sls.homepage.adapter.HiddenCartGoodsAdapter;
import com.mall.sls.homepage.presenter.CartConfirmOrderPresenter;
import com.mall.sls.mainframe.ui.MainFrameActivity;
import com.mall.sls.order.ui.GoodsOrderDetailsActivity;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author jwc on 2020/6/29.
 * 描述：普通商品下单页
 */
public class CartConfirmOrderActivity extends BaseActivity implements HomepageContract.CartConfirmOrderView {


    @Inject
    CartConfirmOrderPresenter cartConfirmOrderPresenter;
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
    @BindView(R.id.goods_rv)
    RecyclerView goodsRv;
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
    ConventionalTextView totalAmount;
    @BindView(R.id.hidden_cart_rv)
    RecyclerView hiddenCartRv;
    @BindView(R.id.cart_box_rl)
    RelativeLayout cartBoxRl;
    @BindView(R.id.i_know)
    ConventionalTextView iKnow;
    @BindView(R.id.go_homepage)
    ConventionalTextView goHomepage;
    @BindView(R.id.direct_box_rl)
    RelativeLayout directBoxRl;
    @BindView(R.id.continue_order)
    ConventionalTextView continueOrder;
    @BindView(R.id.back_cart)
    ConventionalTextView backCart;
    @BindView(R.id.hidden_number)
    ConventionalTextView hiddenNumber;

    private ConfirmGoodsItemAdapter confirmGoodsItemAdapter;
    private HiddenCartGoodsAdapter hiddenCartGoodsAdapter;
    private List<String> ids;
    private String addressId = "0";
    private String userCouponId = "0";
    private AddressInfo addressInfo;
    private String purchaseType;//1:直接购买 2：购物车购买
    private List<HiddenItemCartInfo> hiddenItemCartInfos;
    private String cartIds;
    private String selectType;
    private String tipBack;
    private String orderTotalPrice;
    private Boolean isBulletBox = true;//是否需要弹框 选择地址或者第一次需要谈 选择优惠卷不弹框
    private String message;
    private List<String> normalIds;//有效的商品
    private List<CartItemInfo> cartItemInfos;
    private String orderId;
    private Handler mHandler = new MyHandler(this);


    public static void start(Context context, List<String> ids, String purchaseType) {
        Intent intent = new Intent(context, CartConfirmOrderActivity.class);
        intent.putExtra(StaticData.CART_ITEM_IDS, (Serializable) ids);
        intent.putExtra(StaticData.PURCHASE_TYPE, purchaseType);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_confirm_order);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        EventBus.getDefault().register(this);
        ids = (List<String>) getIntent().getSerializableExtra(StaticData.CART_ITEM_IDS);
        normalIds = new ArrayList<>();
        purchaseType = getIntent().getStringExtra(StaticData.PURCHASE_TYPE);
        confirmGoodsItemAdapter = new ConfirmGoodsItemAdapter(this);
        goodsRv.setAdapter(confirmGoodsItemAdapter);
        hiddenCartGoodsAdapter = new HiddenCartGoodsAdapter(this);
        hiddenCartRv.setLayoutManager(new GridLayoutManager(this, 3));
        hiddenCartRv.setAdapter(hiddenCartGoodsAdapter);
        cartConfirmOrderPresenter.cartGeneralChecked(addressId, ids, userCouponId);
        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {

            }

            @Override
            public void keyBoardHide(int height) {
                notesEt.clearFocus();
            }
        });
    }

    @Override
    protected void initializeInjector() {
        DaggerHomepageComponent.builder()
                .applicationComponent(getApplicationComponent())
                .homepageModule(new HomepageModule(this))
                .build()
                .inject(this);
    }

    @OnTextChanged({R.id.notes_et})
    public void checkPhoneEnable() {
        message = notesEt.getText().toString().trim();
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void renderCartGeneralChecked(ConfirmCartOrderDetail confirmCartOrderDetail) {
        if (confirmCartOrderDetail != null) {
            addressInfo = confirmCartOrderDetail.getAddressInfo();
            address();
            cartItemInfos = confirmCartOrderDetail.getCartItemInfos();
            confirmGoodsItemAdapter.setData(cartItemInfos);
            goodsTotalPrice.setText(NumberFormatUnit.goodsFormat(confirmCartOrderDetail.getGoodsTotalPrice()));
            totalAmount.setText(NumberFormatUnit.goodsFormat(confirmCartOrderDetail.getOrderTotalPrice()));
            userCouponId = confirmCartOrderDetail.getCouponUserId();
            cartIds = confirmCartOrderDetail.getCartIds();
            orderTotalPrice = confirmCartOrderDetail.getOrderTotalPrice();
            if (TextUtils.equals(StaticData.REFLASH_ZERO, confirmCartOrderDetail.getCouponCount())) {
                coupon.setText(getString(R.string.no_available));
            } else {
                if (TextUtils.equals(StaticData.REFLASH_ZERO, userCouponId)) {
                    coupon.setText(confirmCartOrderDetail.getCouponCount() + "张优惠券可用");
                } else {
                    coupon.setText("-" + NumberFormatUnit.goodsFormat(confirmCartOrderDetail.getCouponPrice()));
                }
            }
            deliveryFee.setText(NumberFormatUnit.goodsFormat(confirmCartOrderDetail.getFreightPrice()));
            deliveryMethod.setText(confirmCartOrderDetail.getPeiSongType());
            hiddenItemCartInfos = confirmCartOrderDetail.getHiddenItemCartInfos();
            if (hiddenItemCartInfos != null && hiddenItemCartInfos.size() > 0 && isBulletBox) {
                if (TextUtils.equals(StaticData.REFLASH_ONE, purchaseType)) {
                    directBoxRl.setVisibility(View.VISIBLE);
                } else {
                    cartBoxRl.setVisibility(View.VISIBLE);
                    hiddenCartGoodsAdapter.setData(hiddenItemCartInfos);
                    hiddenNumber.setText(hiddenItemCartInfos.size()+"件  (共"+cartItemInfos.size()+"件)");
                }
            }
            confirmBt.setEnabled(!(cartItemInfos.size() == hiddenItemCartInfos.size()));
            normalIds.clear();
            for (CartItemInfo cartItemInfo : cartItemInfos) {
                if (cartItemInfo.getCanBuy()) {
                    normalIds.add(cartItemInfo.getId());
                }
            }
        }
    }

    @Override
    public void renderCartOrderSubmit(OrderSubmitInfo orderSubmitInfo) {
        FlashCartManager.saveFlashCart(StaticData.REFLASH_ONE);
        if (orderSubmitInfo != null) {
            orderId = orderSubmitInfo.getOrderId();
            if (orderSubmitInfo.getPay()) {
                if (TextUtils.equals(StaticData.REFLASH_ZERO, selectType)) {
                    cartConfirmOrderPresenter.orderWxPay(orderId, StaticData.REFLASH_ZERO);
                } else {
                    cartConfirmOrderPresenter.orderAliPay(orderId, StaticData.REFLASH_ONE);
                }
            } else {
                paySuccess();
            }
        }
    }

    @Override
    public void renderOrderAliPay(String alipayStr) {
        if (!TextUtils.isEmpty(alipayStr)) {
            startAliPay(alipayStr);
        }
    }

    @Override
    public void renderOrderWxPay(WXPaySignResponse wxPaySignResponse) {
        if (wxPaySignResponse != null) {
            wechatPay(wxPaySignResponse);
        }
    }

    @Override
    public void setPresenter(HomepageContract.CartConfirmOrderPresenter presenter) {

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
            addressId = "";
            noAddressTv.setVisibility(View.VISIBLE);
            addressRl.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.back, R.id.confirm_bt, R.id.coupon_rl, R.id.address_all, R.id.i_know, R.id.go_homepage, R.id.continue_order, R.id.back_cart})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                back();
                break;
            case R.id.confirm_bt://去支付
                confirm();
                break;
            case R.id.coupon_rl:
                isBulletBox = false;
                Intent couponIntent = new Intent(this, SelectCouponActivity.class);
                couponIntent.putExtra(StaticData.CART_IDS, cartIds);
                couponIntent.putExtra(StaticData.USER_COUPON_ID, userCouponId);
                startActivityForResult(couponIntent, RequestCodeStatic.SELECT_COUPON);
                break;
            case R.id.address_all://选择地址
                isBulletBox = true;
                Intent intent = new Intent(this, AddressManageActivity.class);
                intent.putExtra(StaticData.CHOICE_TYPE, StaticData.REFLASH_ZERO);
                intent.putExtra(StaticData.SELECT_ID, addressId);
                startActivityForResult(intent, RequestCodeStatic.REQUEST_ADDRESS);
                break;
            case R.id.i_know:
                directBoxRl.setVisibility(View.GONE);
                break;
            case R.id.go_homepage:
                MainStartManager.saveMainStart(StaticData.REFLASH_ONE);
                MainFrameActivity.start(this);
                finish();
                break;
            case R.id.continue_order:
                cartBoxRl.setVisibility(View.GONE);
                break;
            case R.id.back_cart:
                cartBoxRl.setVisibility(View.GONE);
                finish();
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
                        cartConfirmOrderPresenter.cartGeneralChecked(addressId, ids, userCouponId);
                    }
                    break;
                case RequestCodeStatic.SELECT_COUPON://优惠卷
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        userCouponId = bundle.getString(StaticData.USER_COUPON_ID);
                        cartConfirmOrderPresenter.cartGeneralChecked(addressId, ids, userCouponId);
                    }
                    break;
                case RequestCodeStatic.PAY_TYPE://选择支付方式
                    if (data != null) {
                        selectType = data.getStringExtra(StaticData.SELECT_TYPE);
                        if (TextUtils.equals(StaticData.REFLASH_ZERO, selectType)) {
                            //微信
                            if (PayTypeInstalledUtils.isWeixinAvilible(CartConfirmOrderActivity.this)) {
                                cartConfirmOrderPresenter.cartOrderSubmit(addressId, normalIds, userCouponId, message);
                            } else {
                                showMessage(getString(R.string.install_weixin));
                            }
                        } else if (TextUtils.equals(StaticData.REFLASH_ONE, selectType)) {
                            if (PayTypeInstalledUtils.isAliPayInstalled(CartConfirmOrderActivity.this)) {
                                cartConfirmOrderPresenter.cartOrderSubmit(addressId, normalIds, userCouponId, message);
                            } else {
                                showMessage(getString(R.string.install_alipay));
                            }
                        }
                    }
                    break;
                case RequestCodeStatic.TIP_PAGE://点击返回
                    if (data != null) {
                        tipBack = data.getStringExtra(StaticData.TIP_BACK);
                        if (TextUtils.equals(StaticData.REFLASH_ONE, tipBack)) {
                            confirm();
                        } else {
                            finish();
                        }
                    }
                    break;
                default:
            }
        }
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
        if (TextUtils.equals(StaticData.REFLASH_ZERO, orderTotalPrice) || TextUtils.equals("0.00", orderTotalPrice)) {
            cartConfirmOrderPresenter.cartOrderSubmit(addressId, normalIds, userCouponId, message);
        } else {
            Intent intent = new Intent(this, SelectPayTypeActivity.class);
            intent.putExtra(StaticData.CHOICE_TYPE, StaticData.REFLASH_TWO);
            intent.putExtra(StaticData.PAYMENT_AMOUNT, orderTotalPrice);
            startActivityForResult(intent, RequestCodeStatic.PAY_TYPE);
        }
    }


    private void startAliPay(String sign) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                PayTask payTask = new PayTask(CartConfirmOrderActivity.this);
                Map<String, String> result = payTask.payV2(sign, true);
                Message message = Message.obtain();
                message.what = RequestCodeStatic.SDK_PAY_FLAG;
                message.obj = result;
                mHandler.sendMessage(message);
            }
        };

        new Thread(runnable).start();
    }

    public static class MyHandler extends StaticHandler<CartConfirmOrderActivity> {

        public MyHandler(CartConfirmOrderActivity target) {
            super(target);
        }

        @Override
        public void handle(CartConfirmOrderActivity target, Message msg) {
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
        CartPaySuccessActivity.start(this, orderId, orderTotalPrice);
        finish();
    }

}
