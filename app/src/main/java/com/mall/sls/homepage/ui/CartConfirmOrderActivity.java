package com.mall.sls.homepage.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.address.ui.AddressManageActivity;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.MainStartManager;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.unit.TCAgentUnit;
import com.mall.sls.common.widget.edittextview.SoftKeyBoardListener;
import com.mall.sls.common.widget.textview.ConventionalEditTextView;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.coupon.ui.SelectCouponActivity;
import com.mall.sls.data.entity.AddressInfo;
import com.mall.sls.data.entity.ConfirmCartOrderDetail;
import com.mall.sls.data.entity.HiddenItemCartInfo;
import com.mall.sls.homepage.DaggerHomepageComponent;
import com.mall.sls.homepage.HomepageContract;
import com.mall.sls.homepage.HomepageModule;
import com.mall.sls.homepage.adapter.ConfirmGoodsItemAdapter;
import com.mall.sls.homepage.adapter.HiddenCartGoodsAdapter;
import com.mall.sls.homepage.presenter.CartConfirmOrderPresenter;
import com.mall.sls.mainframe.ui.MainFrameActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    private ConfirmGoodsItemAdapter confirmGoodsItemAdapter;
    private HiddenCartGoodsAdapter hiddenCartGoodsAdapter;
    private List<String> ids;
    private String addressId = "0";
    private String userCouponId = "0";
    private AddressInfo addressInfo;
    private String purchaseType;//1:直接购买 2：购物车购买
    private List<HiddenItemCartInfo> hiddenItemCartInfos;
    private List<String> cartIds;
    private String selectType;
    private String tipBack;


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
        ids = (List<String>) getIntent().getSerializableExtra(StaticData.CART_ITEM_IDS);
        purchaseType = getIntent().getStringExtra(StaticData.PURCHASE_TYPE);
        cartIds = new ArrayList<>();
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


    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void renderCartGeneralChecked(ConfirmCartOrderDetail confirmCartOrderDetail) {
        if (confirmCartOrderDetail != null) {
            addressInfo = confirmCartOrderDetail.getAddressInfo();
            address();
            confirmGoodsItemAdapter.setData(confirmCartOrderDetail.getCartItemInfos());
            goodsTotalPrice.setText("¥" + NumberFormatUnit.twoDecimalFormat(confirmCartOrderDetail.getGoodsTotalPrice()));
            totalAmount.setText("¥" + NumberFormatUnit.twoDecimalFormat(confirmCartOrderDetail.getOrderTotalPrice()));
            userCouponId = confirmCartOrderDetail.getCouponUserId();
            cartIds = confirmCartOrderDetail.getCartIds();
            if (TextUtils.equals(StaticData.REFLASH_ZERO, confirmCartOrderDetail.getCouponCount())) {
                coupon.setText(getString(R.string.no_available));
            } else {
                if (TextUtils.equals("-1", userCouponId)) {
                    coupon.setText(confirmCartOrderDetail.getCouponCount() + "张优惠券可用");
                } else {
                    coupon.setText("-¥" + confirmCartOrderDetail.getCouponPrice());
                }
            }
            deliveryFee.setText("¥" + NumberFormatUnit.twoDecimalFormat(confirmCartOrderDetail.getFreightPrice()));
            deliveryMethod.setText(confirmCartOrderDetail.getPeiSongType());
            hiddenItemCartInfos = confirmCartOrderDetail.getHiddenItemCartInfos();
            if (hiddenItemCartInfos != null && hiddenItemCartInfos.size() > 0) {
                if (TextUtils.equals(StaticData.REFLASH_ONE, purchaseType)) {
                    directBoxRl.setVisibility(View.VISIBLE);
                } else {
                    cartBoxRl.setVisibility(View.VISIBLE);
                    hiddenCartGoodsAdapter.setData(hiddenItemCartInfos);
                }
            }
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

    @OnClick({R.id.back, R.id.confirm_bt, R.id.coupon_rl, R.id.address_all, R.id.i_know,R.id.go_homepage, R.id.continue_order,R.id.back_cart})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                back();
                break;
            case R.id.confirm_bt://去支付
                TCAgentUnit.setEventId(this, getString(R.string.payment));
                if (TextUtils.isEmpty(addressId)) {
                    showMessage(getString(R.string.select_address));
                    return;
                }
//                if(TextUtils.equals(StaticData.REFLASH_ZERO,orderTotalPrice)||TextUtils.equals("0.00",orderTotalPrice)){
//                    confirmOrderPresenter.orderSubmit(addressId, cartId, couponId, userCouponId, message);
//                }else {
//                    Intent intent = new Intent(this, SelectPayTypeActivity.class);
//                    intent.putExtra(StaticData.CHOICE_TYPE, StaticData.REFLASH_TWO);
//                    intent.putExtra(StaticData.PAYMENT_AMOUNT, orderTotalPrice);
//                    startActivityForResult(intent, RequestCodeStatic.PAY_TYPE);
//                }
                break;
            case R.id.coupon_rl:
                Intent couponIntent = new Intent(this, SelectCouponActivity.class);
                couponIntent.putExtra(StaticData.CART_IDS, (Serializable) cartIds);
                couponIntent.putExtra(StaticData.USER_COUPON_ID, userCouponId);
                startActivityForResult(couponIntent, RequestCodeStatic.SELECT_COUPON);
                break;
            case R.id.address_all://选择地址
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
//                        selectType = data.getStringExtra(StaticData.SELECT_TYPE);
//                        if (TextUtils.equals(StaticData.REFLASH_ZERO, selectType)) {
//                            //微信
//                            if (PayTypeInstalledUtils.isWeixinAvilible(CartConfirmOrderActivity.this)) {
//                                confirmOrderPresenter.orderSubmit(addressId, cartId, couponId, userCouponId, message);
//                            } else {
//                                showMessage(getString(R.string.install_weixin));
//                            }
//                        } else if(TextUtils.equals(StaticData.REFLASH_ONE, selectType)){
//                            if (PayTypeInstalledUtils.isAliPayInstalled(CartConfirmOrderActivity.this)) {
//                                confirmOrderPresenter.orderSubmit(addressId, cartId, couponId, userCouponId, message);
//                            } else {
//                                showMessage(getString(R.string.install_alipay));
//                            }
//                        }
                    }
                    break;
                case RequestCodeStatic.TIP_PAGE://点击返回
//                    if (data != null) {
//                        tipBack = data.getStringExtra(StaticData.TIP_BACK);
//                        if (TextUtils.equals(StaticData.REFLASH_ONE, tipBack)) {
//                            if(TextUtils.isEmpty(addressId)){
//                                showMessage(getString(R.string.select_address));
//                                return;
//                            }
//                            if(TextUtils.equals(StaticData.REFLASH_ZERO,orderTotalPrice)||TextUtils.equals("0.00",orderTotalPrice)){
//                                confirmOrderPresenter.orderSubmit(addressId, cartId, couponId, userCouponId, message);
//                            }else {
//                                Intent intent = new Intent(this, SelectPayTypeActivity.class);
//                                intent.putExtra(StaticData.CHOICE_TYPE, StaticData.REFLASH_TWO);
//                                intent.putExtra(StaticData.PAYMENT_AMOUNT, orderTotalPrice);
//                                startActivityForResult(intent, RequestCodeStatic.PAY_TYPE);
//                            }
//                        } else {
//                            finish();
//                        }
//                    }
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
}
