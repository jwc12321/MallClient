package com.mall.sls.order.ui;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.alipay.sdk.app.PayTask;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.BriefUnit;
import com.mall.sls.common.unit.FormatUtil;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.unit.PayResult;
import com.mall.sls.common.unit.PayTypeInstalledUtils;
import com.mall.sls.common.unit.QRCodeFileUtils;
import com.mall.sls.common.unit.StaticHandler;
import com.mall.sls.common.unit.WXShareManager;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MSTearDownView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.GoodsOrderDetails;
import com.mall.sls.data.entity.InvitationCodeInfo;
import com.mall.sls.data.entity.OrderGoodsVo;
import com.mall.sls.data.entity.OrderTimeInfo;
import com.mall.sls.data.entity.ShipOrderInfo;
import com.mall.sls.data.entity.WXPaySignResponse;
import com.mall.sls.data.entity.WebViewDetailInfo;
import com.mall.sls.data.event.PayAbortEvent;
import com.mall.sls.data.event.WXSuccessPayEvent;
import com.mall.sls.homepage.ui.ActivityGroupGoodsActivity;
import com.mall.sls.homepage.ui.OrdinaryGoodsDetailActivity;
import com.mall.sls.homepage.ui.SelectPayTypeActivity;
import com.mall.sls.mine.ui.SelectShareTypeActivity;
import com.mall.sls.order.DaggerOrderComponent;
import com.mall.sls.order.OrderContract;
import com.mall.sls.order.OrderModule;
import com.mall.sls.order.adapter.OrderInformationAdapter;
import com.mall.sls.order.presenter.OrderDetailsPresenter;
import com.mall.sls.webview.ui.WebViewActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/12.
 * 描述：订单详情
 */
public class GoodsOrderDetailsActivity extends BaseActivity implements OrderContract.OrderDetailsView, OrderInformationAdapter.OnItemClickListener, MSTearDownView.TimeOutListener {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.order_status)
    MediumThickTextView orderStatus;
    @BindView(R.id.remaining_payment_time_tv)
    ConventionalTextView remainingPaymentTimeTv;
    @BindView(R.id.count_down)
    MSTearDownView countDown;
    @BindView(R.id.count_down_ll)
    LinearLayout countDownLl;
    @BindView(R.id.status_iv)
    ImageView statusIv;
    @BindView(R.id.delivery_tv)
    ConventionalTextView deliveryTv;
    @BindView(R.id.delivery_info)
    ConventionalTextView deliveryInfo;
    @BindView(R.id.delivery_time)
    ConventionalTextView deliveryTime;
    @BindView(R.id.delivery_rl)
    RelativeLayout deliveryRl;
    @BindView(R.id.fen_ge_line)
    View fenGeLine;
    @BindView(R.id.address_tv)
    ConventionalTextView addressTv;
    @BindView(R.id.name_phone)
    ConventionalTextView namePhone;
    @BindView(R.id.receipt_address)
    ConventionalTextView receiptAddress;
    @BindView(R.id.goods_iv)
    ImageView goodsIv;
    @BindView(R.id.goods_name)
    MediumThickTextView goodsName;
    @BindView(R.id.sku)
    ConventionalTextView sku;
    @BindView(R.id.goods_price)
    ConventionalTextView goodsPrice;
    @BindView(R.id.goods_number)
    ConventionalTextView goodsNumber;
    @BindView(R.id.total_amount)
    ConventionalTextView totalAmount;
    @BindView(R.id.delivery_fee)
    ConventionalTextView deliveryFee;
    @BindView(R.id.coupon)
    ConventionalTextView coupon;
    @BindView(R.id.is_pay)
    ConventionalTextView isPay;
    @BindView(R.id.real_payment)
    ConventionalTextView realPayment;
    @BindView(R.id.info_rv)
    RecyclerView infoRv;
    @BindView(R.id.scrollview)
    NestedScrollView scrollview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.left_bt)
    ConventionalTextView leftBt;
    @BindView(R.id.right_bt)
    ConventionalTextView rightBt;
    @BindView(R.id.bt_rl)
    RelativeLayout btRl;
    private String goodsOrderId;
    private OrderInformationAdapter orderInformationAdapter;

    private ClipboardManager myClipboard;
    private ClipData myClip;
    private List<OrderGoodsVo> orderGoodsVos;
    private List<OrderTimeInfo> orderTimeInfos;
    private Handler mHandler = new MyHandler(this);
    private String orderTotalPrice;
    private String orderStatusText;
    private String nameText;
    private String briefText;
    private String wxUrl;
    private String inviteCode;
    private Boolean isActivity;
    private Bitmap shareBitMap;
    private String backType;
    private WXShareManager wxShareManager;
    private String grouponId;
    private String goodsProductId;
    private String goodsId;
    private List<ShipOrderInfo> shipOrderInfos;
    private WebViewDetailInfo webViewDetailInfo;
    private String sfH5Url;
    private String isOnSale;
    private String showMap;
    private String actualPrice;
    private String payModeText;
    private String refundTime="2010-09-08 19:00:99";
    private String arrivalTime;


    @Inject
    OrderDetailsPresenter orderDetailsPresenter;
    private String activityResult = StaticData.REFLASH_ZERO;//会列表是否需要刷新  ：不用 1：要


    public static void start(Context context, String goodsOrderId) {
        Intent intent = new Intent(context, GoodsOrderDetailsActivity.class);
        intent.putExtra(StaticData.GOODS_ORDER_ID, goodsOrderId);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        EventBus.getDefault().register(this);
        wxShareManager = WXShareManager.getInstance(this);
        refreshLayout.setOnMultiPurposeListener(simpleMultiPurposeListener);
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        goodsOrderId = getIntent().getStringExtra(StaticData.GOODS_ORDER_ID);
        orderTimeInfos = new ArrayList<>();
        addAdapter();
        orderDetailsPresenter.getOrderDetails(goodsOrderId);
        orderDetailsPresenter.getInvitationCodeInfo();
    }

    private void addAdapter() {
        orderInformationAdapter = new OrderInformationAdapter(this);
        orderInformationAdapter.setOnItemClickListener(this);
        infoRv.setAdapter(orderInformationAdapter);
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @Override
    public void copyOrderNo(String orderNo) {
        myClip = ClipData.newPlainText("text", orderNo);
        myClipboard.setPrimaryClip(myClip);
        showMessage(getString(R.string.copy_successfully));
    }

    @OnClick({R.id.back, R.id.left_bt, R.id.right_bt, R.id.delivery_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back://
                back();
                break;
            case R.id.right_bt:
                if (TextUtils.equals(StaticData.TO_PAY, orderStatusText)) {
                    Intent intent = new Intent(this, SelectPayTypeActivity.class);
                    intent.putExtra(StaticData.CHOICE_TYPE, StaticData.REFLASH_TWO);
                    intent.putExtra(StaticData.PAYMENT_AMOUNT, orderTotalPrice);
                    startActivityForResult(intent, RequestCodeStatic.PAY_TYPE);
                } else if (TextUtils.equals(StaticData.TO_BE_SHARE, orderStatusText)) {
                    if (!PayTypeInstalledUtils.isWeixinAvilible(GoodsOrderDetailsActivity.this)) {
                        showMessage(getString(R.string.install_weixin));
                        return;
                    }
                    shareBitMap = QRCodeFileUtils.createBitmap3(goodsIv, goodsIv.getWidth(), goodsIv.getWidth());//直接url转bitmap背景白色变成黑色，后面想到方法可以改善
                    Intent intent = new Intent(this, SelectShareTypeActivity.class);
                    startActivityForResult(intent, RequestCodeStatic.SELECT_SHARE_TYPE);
                } else if (TextUtils.equals(StaticData.PENDING_REFUND, orderStatusText)||TextUtils.equals(StaticData.REFUNDED, orderStatusText)) {
                    RefundProgressActivity.start(this,orderStatusText,actualPrice,payModeText,refundTime,arrivalTime);
                } else {
                    webViewDetailInfo = new WebViewDetailInfo();
                    webViewDetailInfo.setTitle(getString(R.string.logistics_details));
                    webViewDetailInfo.setUrl(sfH5Url);
                    WebViewActivity.start(this, webViewDetailInfo);
                }
                break;
            case R.id.left_bt:
                if (TextUtils.equals(StaticData.TO_PAY, orderStatusText)) {
                    orderDetailsPresenter.cancelOrder(goodsOrderId);
                } else {
                    if (isActivity) {
                        ActivityGroupGoodsActivity.start(this, goodsId);
                    } else {
                        OrdinaryGoodsDetailActivity.start(this, goodsId);
                    }
                }
                break;
            case R.id.delivery_rl://查看物流信息
                DeliveryinfoActivity.start(this, shipOrderInfos);
                break;
            default:
        }
    }

    SimpleMultiPurposeListener simpleMultiPurposeListener = new SimpleMultiPurposeListener() {
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            refreshLayout.finishRefresh(6000);
            orderDetailsPresenter.getOrderDetails(goodsOrderId);
        }

        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodeStatic.PAY_TYPE:
                    if (data != null) {
                        String selectType = data.getStringExtra(StaticData.SELECT_TYPE);
                        if (TextUtils.equals(StaticData.REFLASH_ZERO, selectType)) {
                            //微信
                            if (PayTypeInstalledUtils.isWeixinAvilible(GoodsOrderDetailsActivity.this)) {
                                orderDetailsPresenter.orderWxPay(goodsOrderId, StaticData.REFLASH_ZERO);
                            } else {
                                showMessage(getString(R.string.install_weixin));
                            }
                        } else if (TextUtils.equals(StaticData.REFLASH_ONE, selectType)) {
                            if (PayTypeInstalledUtils.isAliPayInstalled(GoodsOrderDetailsActivity.this)) {
                                orderDetailsPresenter.orderAliPay(goodsOrderId, StaticData.REFLASH_ONE);
                            } else {
                                showMessage(getString(R.string.install_alipay));
                            }
                        }
                    }
                    break;
                case RequestCodeStatic.SELECT_SHARE_TYPE:
                    if (data != null) {
                        backType = data.getStringExtra(StaticData.BACK_TYPE);
                        if (!isActivity) {
                            shareGroupWx(TextUtils.equals(StaticData.REFLASH_ONE, backType));
                        } else {
                            shareActivityWx(TextUtils.equals(StaticData.REFLASH_ONE, backType));
                        }
                    }
                    break;
                default:
            }
        }
    }

    private void shareActivityWx(boolean isFriend) {
//        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.app_icon);
        String url = wxUrl + "activity/" + goodsId + StaticData.WX_INVITE_CODE + inviteCode;
        wxShareManager.shareUrlToWX(isFriend, url, shareBitMap, nameText, briefText);
    }

    private void shareGroupWx(boolean isFriend) {
//        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.app_icon);
        String url = wxUrl + "group/" + grouponId + "/" + goodsProductId + StaticData.WX_INVITE_CODE + inviteCode;
        wxShareManager.shareUrlToWX(isFriend, url, shareBitMap, nameText, briefText);
    }

    @Override
    protected void initializeInjector() {
        DaggerOrderComponent.builder()
                .applicationComponent(getApplicationComponent())
                .orderModule(new OrderModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void renderOrderDetails(GoodsOrderDetails goodsOrderDetails) {
        refreshLayout.finishRefresh();
        if (goodsOrderDetails != null) {
            orderStatusText = goodsOrderDetails.getOrderStatus();
            receiptAddress.setText(goodsOrderDetails.getAddress());
            namePhone.setText(goodsOrderDetails.getConsignee() + " " + goodsOrderDetails.getMobile());
            orderGoodsVos = goodsOrderDetails.getOrderGoodsVos();
            showMap=goodsOrderDetails.getShowMap();
            if (orderGoodsVos != null && orderGoodsVos.size() > 0) {
                GlideHelper.load(this, orderGoodsVos.get(0).getPicUrl(), R.mipmap.icon_default_goods, goodsIv);
                goodsName.setText(orderGoodsVos.get(0).getGoodsName());
                goodsPrice.setText("¥" + NumberFormatUnit.twoDecimalFormat(orderGoodsVos.get(0).getPrice()));
                goodsNumber.setText("x" + orderGoodsVos.get(0).getNumber());
                sku.setText(orderGoodsVos.get(0).getSpecifications());
                nameText = BriefUnit.returnName(orderGoodsVos.get(0).getPrice(), orderGoodsVos.get(0).getGoodsName());
                briefText = BriefUnit.returnBrief(orderGoodsVos.get(0).getBrief());
                goodsId = orderGoodsVos.get(0).getGoodsId();
                goodsProductId = orderGoodsVos.get(0).getProductId();
                isOnSale=orderGoodsVos.get(0).getIsOnSale();
            }
            setOrderStatus(orderStatusText);
            totalAmount.setText("¥" + NumberFormatUnit.twoDecimalFormat(goodsOrderDetails.getGoodsPrice()));
            deliveryFee.setText("¥" + NumberFormatUnit.twoDecimalFormat(goodsOrderDetails.getFreightPrice()));
            coupon.setText("-¥" + NumberFormatUnit.twoDecimalFormat(goodsOrderDetails.getCouponPrice()));
            realPayment.setText("¥" + NumberFormatUnit.twoDecimalFormat(goodsOrderDetails.getActualPrice()));
            actualPrice=goodsOrderDetails.getActualPrice();
            payModeText=goodsOrderDetails.getPayModeText();
            orderTotalPrice = goodsOrderDetails.getActualPrice();
            orderTimeInfos.clear();
            if (!TextUtils.isEmpty(goodsOrderDetails.getOrderSn())) {
                orderTimeInfos.add(new OrderTimeInfo(getString(R.string.order_number), goodsOrderDetails.getOrderSn()));
            }
            if (!TextUtils.isEmpty(goodsOrderDetails.getAddTime())) {
                orderTimeInfos.add(new OrderTimeInfo(getString(R.string.create_time), goodsOrderDetails.getAddTime()));
            }
            if (!TextUtils.isEmpty(goodsOrderDetails.getPayTime())) {
                orderTimeInfos.add(new OrderTimeInfo(getString(R.string.payment_time), goodsOrderDetails.getPayTime()));
            }
            if (!TextUtils.isEmpty(goodsOrderDetails.getPayModeText())) {
                orderTimeInfos.add(new OrderTimeInfo(getString(R.string.payment_method), goodsOrderDetails.getPayModeText()));
            }
            if (!TextUtils.isEmpty(goodsOrderDetails.getTradeNo())) {
                orderTimeInfos.add(new OrderTimeInfo(getString(R.string.payment_number), goodsOrderDetails.getTradeNo()));
            }
            orderInformationAdapter.setData(orderTimeInfos);
            if (TextUtils.equals(StaticData.TO_PAY, goodsOrderDetails.getOrderStatus()) && !TextUtils.isEmpty(goodsOrderDetails.getSystemTime()) && !TextUtils.isEmpty(goodsOrderDetails.getPayLimitTime())) {
                long now = FormatUtil.dateToStamp(goodsOrderDetails.getSystemTime());
                long groupExpireTime = FormatUtil.dateToStamp(goodsOrderDetails.getPayLimitTime());
                if (now < groupExpireTime) {
                    countDown.setTimeOutListener(this);
                    countDown.startTearDown(groupExpireTime / 1000, now / 1000);
                    countDown.setVisibility(View.VISIBLE);
                } else {
                    countDown.setVisibility(View.GONE);
                }
                isPay.setText(getString(R.string.to_be_paid));
            } else {
                isPay.setText(getString(R.string.actually_apaid));
            }
            isActivity = goodsOrderDetails.getActivity();
            grouponId = goodsOrderDetails.getGrouponLinkId();
            shipOrderInfos = goodsOrderDetails.getShipOrderInfos();
            if (shipOrderInfos != null && shipOrderInfos.size() > 0) {
                deliveryInfo.setText(shipOrderInfos.get(0).getStatusDesc());
                deliveryTime.setText(shipOrderInfos.get(0).getStatusTime());
            }
            sfH5Url = goodsOrderDetails.getSfH5Url();
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
    public void renderCancelOrder() {
        activityResult = StaticData.REFLASH_ONE;
        orderDetailsPresenter.getOrderDetails(goodsOrderId);
    }

    @Override
    public void renderInvitationCodeInfo(InvitationCodeInfo invitationCodeInfo) {
        if (invitationCodeInfo != null) {
            wxUrl = invitationCodeInfo.getBaseUrl();
            inviteCode = invitationCodeInfo.getInvitationCode();
        }
    }

    //状态 101-待支付 102 -取消 103-系统自动取消 "202-待退款","203-已退款,"204-待分享 206-待发货 301-待收获 401-完成 402-完成(系统)
    private void setOrderStatus(String status) {
        switch (status) {
            case StaticData.TO_PAY://待支付
                orderStatus.setText(getString(R.string.pending_payment));
                countDownLl.setVisibility(View.VISIBLE);
                remainingPaymentTimeTv.setText(getString(R.string.pay_remaining_time));
                btRl.setVisibility(View.VISIBLE);
                leftBt.setVisibility(View.VISIBLE);
                rightBt.setVisibility(View.VISIBLE);
                rightBt.setText(getString(R.string.to_pay));
                leftBt.setText(getString(R.string.cancel_order));
                deliveryRl.setVisibility(View.GONE);
                fenGeLine.setVisibility(View.GONE);
                statusIv.setBackgroundResource(R.mipmap.icon_to_pay);
                break;
            case StaticData.TO_BE_SHARE://待分享
                orderStatus.setText(getString(R.string.pending_share));
                countDownLl.setVisibility(View.GONE);
                btRl.setVisibility(View.VISIBLE);
                leftBt.setVisibility(View.GONE);
                rightBt.setVisibility(View.VISIBLE);
                rightBt.setText(getString(R.string.invitation));
                deliveryRl.setVisibility(View.GONE);
                fenGeLine.setVisibility(View.GONE);
                statusIv.setBackgroundResource(R.mipmap.icon_to_share);
                break;
            case StaticData.TO_BE_DELIVERED://代发货
                orderStatus.setText(getString(R.string.pending_delivery));
                countDownLl.setVisibility(View.GONE);
                btRl.setVisibility(View.GONE);
                deliveryRl.setVisibility(View.GONE);
                fenGeLine.setVisibility(View.GONE);
                statusIv.setBackgroundResource(R.mipmap.icon_to_delivered);
                break;
            case StaticData.TO_BE_RECEIVED://待收货
                orderStatus.setText(getString(R.string.shipping));
                countDownLl.setVisibility(View.GONE);
                btRl.setVisibility(View.VISIBLE);
                leftBt.setVisibility(TextUtils.equals(StaticData.REFLASH_ONE,isOnSale)?View.VISIBLE:View.GONE);
                rightBt.setVisibility(TextUtils.equals(StaticData.REFLASH_ONE,showMap)?View.VISIBLE:View.GONE);
                leftBt.setText(getString(R.string.one_more_order));
                rightBt.setText(getString(R.string.check_map));
                deliveryRl.setVisibility(View.VISIBLE);
                fenGeLine.setVisibility(View.VISIBLE);
                statusIv.setBackgroundResource(R.mipmap.icon_to_received);
                break;
            case StaticData.RECEIVED://已完成
            case StaticData.SYS_RECEIVED:
                orderStatus.setText(getString(R.string.completed));
                countDownLl.setVisibility(View.GONE);
                btRl.setVisibility(View.VISIBLE);
                leftBt.setVisibility(TextUtils.equals(StaticData.REFLASH_ONE,isOnSale)?View.VISIBLE:View.GONE);
                rightBt.setVisibility(View.GONE);
                leftBt.setText(getString(R.string.one_more_order));
                deliveryRl.setVisibility(View.VISIBLE);
                fenGeLine.setVisibility(View.VISIBLE);
                statusIv.setBackgroundResource(R.mipmap.icon_order_compled);
                break;
            case StaticData.CANCELLED://取消
            case StaticData.SYS_CANCELLED:
                orderStatus.setText(getString(R.string.transaction_cancel));
                countDownLl.setVisibility(View.GONE);
                btRl.setVisibility(View.VISIBLE);
                leftBt.setVisibility(TextUtils.equals(StaticData.REFLASH_ONE,isOnSale)?View.VISIBLE:View.GONE);
                rightBt.setVisibility(View.GONE);
                leftBt.setText(getString(R.string.one_more_order));
                deliveryRl.setVisibility(View.GONE);
                fenGeLine.setVisibility(View.GONE);
                statusIv.setBackgroundResource(R.mipmap.icon_order_cancel);
                break;
            case StaticData.PENDING_REFUND://待退款
                orderStatus.setText(getString(R.string.shipping));
                countDownLl.setVisibility(View.GONE);
                statusIv.setBackgroundResource(R.mipmap.icon_to_received);
                btRl.setVisibility(View.VISIBLE);
                leftBt.setVisibility(TextUtils.equals(StaticData.REFLASH_ONE,isOnSale)?View.VISIBLE:View.GONE);
                rightBt.setVisibility(View.VISIBLE);
                leftBt.setText(getString(R.string.one_more_order));
                rightBt.setText(getString(R.string.where_money_goes));
                deliveryRl.setVisibility(View.VISIBLE);
                fenGeLine.setVisibility(View.VISIBLE);
                break;
            case StaticData.REFUNDED://已退款
                orderStatus.setText(getString(R.string.refunded));
                countDownLl.setVisibility(View.GONE);
                statusIv.setBackgroundResource(R.mipmap.icon_refunded);
                btRl.setVisibility(View.VISIBLE);
                leftBt.setVisibility(TextUtils.equals(StaticData.REFLASH_ONE,isOnSale)?View.VISIBLE:View.GONE);
                rightBt.setVisibility(View.VISIBLE);
                leftBt.setText(getString(R.string.one_more_order));
                rightBt.setText(getString(R.string.where_money_goes));
                deliveryRl.setVisibility(View.GONE);
                fenGeLine.setVisibility(View.GONE);
                break;
            default:
        }
    }


    @Override
    public void setPresenter(OrderContract.OrderDetailsPresenter presenter) {

    }

    private void startAliPay(String sign) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                PayTask payTask = new PayTask(GoodsOrderDetailsActivity.this);
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
    public void timeOut() {
        orderDetailsPresenter.getOrderDetails(goodsOrderId);
    }

    public static class MyHandler extends StaticHandler<GoodsOrderDetailsActivity> {

        public MyHandler(GoodsOrderDetailsActivity target) {
            super(target);
        }

        @Override
        public void handle(GoodsOrderDetailsActivity target, Message msg) {
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
            activityResult = StaticData.REFLASH_ONE;
            orderDetailsPresenter.getOrderDetails(goodsOrderId);
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
        activityResult = StaticData.REFLASH_ONE;
        orderDetailsPresenter.getOrderDetails(goodsOrderId);
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

    //分享成功
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShareSuccess(String code) {
        showMessage(getString(R.string.share_success));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            back();
            return true;

        }
        return super.onKeyDown(keyCode, event);
    }

    private void back() {
        if (TextUtils.equals(StaticData.REFLASH_ONE, activityResult)) {
            Intent backIntent = new Intent();
            setResult(Activity.RESULT_OK, backIntent);
            finish();
        } else {
            finish();
        }
    }

}
