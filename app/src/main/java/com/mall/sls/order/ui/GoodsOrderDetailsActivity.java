package com.mall.sls.order.ui;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
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
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.alipay.sdk.app.PayTask;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.FormatUtil;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.unit.PayResult;
import com.mall.sls.common.unit.PayTypeInstalledUtils;
import com.mall.sls.common.unit.StaticHandler;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MSTearDownView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.AddressInfo;
import com.mall.sls.data.entity.GoodsOrderDetails;
import com.mall.sls.data.entity.OrderGoodsVo;
import com.mall.sls.data.entity.OrderTimeInfo;
import com.mall.sls.homepage.ui.ConfirmOrderActivity;
import com.mall.sls.homepage.ui.SelectPayTypeActivity;
import com.mall.sls.order.DaggerOrderComponent;
import com.mall.sls.order.OrderContract;
import com.mall.sls.order.OrderModule;
import com.mall.sls.order.adapter.OrderInformationAdapter;
import com.mall.sls.order.presenter.OrderDetailsPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

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
public class GoodsOrderDetailsActivity extends BaseActivity implements OrderContract.OrderDetailsView, OrderInformationAdapter.OnItemClickListener {


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
    @BindView(R.id.payTimeoutMinute)
    ConventionalTextView payTimeoutMinute;
    @BindView(R.id.pay_rl)
    RelativeLayout payRl;
    @BindView(R.id.delivery_time)
    ConventionalTextView deliveryTime;
    @BindView(R.id.time_line)
    View timeLine;
    @BindView(R.id.receipt_address)
    ConventionalTextView receiptAddress;
    @BindView(R.id.name_phone)
    ConventionalTextView namePhone;
    @BindView(R.id.goods_iv)
    ImageView goodsIv;
    @BindView(R.id.goods_name)
    ConventionalTextView goodsName;
    @BindView(R.id.goods_number)
    ConventionalTextView goodsNumber;
    @BindView(R.id.goods_price)
    ConventionalTextView goodsPrice;
    @BindView(R.id.total_amount)
    ConventionalTextView totalAmount;
    @BindView(R.id.delivery_fee)
    ConventionalTextView deliveryFee;
    @BindView(R.id.coupon)
    ConventionalTextView coupon;
    @BindView(R.id.real_payment)
    ConventionalTextView realPayment;
    @BindView(R.id.info_rv)
    RecyclerView infoRv;
    @BindView(R.id.scrollview)
    NestedScrollView scrollview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.pay_bt)
    ConventionalTextView payBt;
    private String goodsOrderId;
    private OrderInformationAdapter orderInformationAdapter;

    private ClipboardManager myClipboard;
    private ClipData myClip;
    private List<OrderGoodsVo> orderGoodsVos;
    private List<OrderTimeInfo> orderTimeInfos;
    private Handler mHandler = new MyHandler(this);
    private String orderTotalPrice;

    @Inject
    OrderDetailsPresenter orderDetailsPresenter;


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
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        goodsOrderId = getIntent().getStringExtra(StaticData.GOODS_ORDER_ID);
        orderTimeInfos = new ArrayList<>();
        addAdapter();
        orderDetailsPresenter.getOrderDetails(goodsOrderId);
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

    @OnClick({R.id.back,R.id.pay_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back://
                finish();
                break;
            case R.id.pay_bt:
                Intent intent = new Intent(this, SelectPayTypeActivity.class);
                intent.putExtra(StaticData.CHOICE_TYPE, StaticData.REFLASH_TWO);
                intent.putExtra(StaticData.PAYMENT_AMOUNT,orderTotalPrice);
                startActivityForResult(intent, RequestCodeStatic.PAY_TYPE);
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
                        String selectType = data.getStringExtra(StaticData.SELECT_TYPE);
                        if (TextUtils.equals(StaticData.REFLASH_ZERO, selectType)) {
                            //微信
                            if (PayTypeInstalledUtils.isWeixinAvilible(GoodsOrderDetailsActivity.this)) {

                            } else {
                                showMessage(getString(R.string.install_weixin));
                            }
                        } else {
                            if (PayTypeInstalledUtils.isAliPayInstalled(GoodsOrderDetailsActivity.this)) {
                                orderDetailsPresenter.orderAliPay(goodsOrderId,StaticData.REFLASH_ONE);
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
        DaggerOrderComponent.builder()
                .applicationComponent(getApplicationComponent())
                .orderModule(new OrderModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void renderOrderDetails(GoodsOrderDetails goodsOrderDetails) {
        if (goodsOrderDetails != null) {
            setOrderStatus(goodsOrderDetails.getOrderStatus());
            payTimeoutMinute.setText(goodsOrderDetails.getPayTimeoutMinute() + "分钟内未支付，订单将自动取消");
            if (TextUtils.equals(StaticData.TO_PAY, goodsOrderDetails.getOrderStatus()) && !TextUtils.isEmpty(goodsOrderDetails.getSystemTime()) && !TextUtils.isEmpty(goodsOrderDetails.getPayLimitTime())) {
                long now = FormatUtil.dateToStamp(goodsOrderDetails.getSystemTime());
                long groupExpireTime = FormatUtil.dateToStamp(goodsOrderDetails.getPayLimitTime());
                if (now < groupExpireTime) {
                    countDown.startTearDown(groupExpireTime / 1000, now / 1000);
                }
            }
            receiptAddress.setText(goodsOrderDetails.getAddress());
            namePhone.setText(goodsOrderDetails.getConsignee() + " " + goodsOrderDetails.getMobile());
            orderGoodsVos = goodsOrderDetails.getOrderGoodsVos();
            if (orderGoodsVos != null && orderGoodsVos.size() > 0) {
                GlideHelper.load(this, orderGoodsVos.get(0).getPicUrl(), R.mipmap.icon_default_goods, goodsIv);
                goodsName.setText(orderGoodsVos.get(0).getGoodsName());
                goodsPrice.setText("¥" + NumberFormatUnit.twoDecimalFormat(orderGoodsVos.get(0).getPrice()));
                goodsNumber.setText("x" + orderGoodsVos.get(0).getNumber());
            }
            totalAmount.setText("¥" + NumberFormatUnit.twoDecimalFormat(goodsOrderDetails.getGoodsPrice()));
            coupon.setText("-¥" + NumberFormatUnit.twoDecimalFormat(goodsOrderDetails.getCouponPrice()));
            realPayment.setText("¥" + NumberFormatUnit.twoDecimalFormat(goodsOrderDetails.getActualPrice()));
            orderTotalPrice=goodsOrderDetails.getActualPrice();
            orderTimeInfos.clear();
            if (!TextUtils.isEmpty(goodsOrderDetails.getOrderSn())) {
                orderTimeInfos.add(new OrderTimeInfo(getString(R.string.order_number), goodsOrderDetails.getOrderSn()));
            }
            if (!TextUtils.isEmpty(goodsOrderDetails.getAddTime())) {
                orderTimeInfos.add(new OrderTimeInfo(getString(R.string.order_time), goodsOrderDetails.getAddTime()));
            }
            if (!TextUtils.isEmpty(goodsOrderDetails.getPayModeText())) {
                orderTimeInfos.add(new OrderTimeInfo(getString(R.string.payment_method), goodsOrderDetails.getPayModeText()));
            }
            if (!TextUtils.isEmpty(goodsOrderDetails.getPayTime())) {
                orderTimeInfos.add(new OrderTimeInfo(getString(R.string.payment_time), goodsOrderDetails.getPayTime()));
            }
            if (!TextUtils.isEmpty(goodsOrderDetails.getMessage())) {
                orderTimeInfos.add(new OrderTimeInfo(getString(R.string.order_notes), goodsOrderDetails.getMessage()));
            }
            orderInformationAdapter.setData(orderTimeInfos);
        }
    }

    @Override
    public void renderOrderAliPay(String alipayStr) {
        if (!TextUtils.isEmpty(alipayStr)) {
            startAliPay(alipayStr);
        }
    }

    //101:待付款 201:待发货 301:待收货 401:确认收货 102:已取消
    private void setOrderStatus(String status) {
        switch (status) {
            case StaticData.TO_PAY:
                orderStatus.setText(getString(R.string.pending_payment));
                payRl.setVisibility(View.VISIBLE);
                break;
            case StaticData.TO_BE_DELIVERED:
                orderStatus.setText(getString(R.string.pending_delivery));
                payRl.setVisibility(View.GONE);
                break;
            case StaticData.TO_BE_RECEIVED:
                orderStatus.setText(getString(R.string.shipping));
                payRl.setVisibility(View.GONE);
                break;
            case StaticData.RECEIVED:
                orderStatus.setText(getString(R.string.received));
                payRl.setVisibility(View.GONE);
                break;
            case StaticData.CANCELLED:
                orderStatus.setText(getString(R.string.closed));
                payRl.setVisibility(View.GONE);
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
        Log.d("111", "数据" + payResult.getResult() + "==" + payResult.getResultStatus());
        if (TextUtils.equals(resultStatus, "9000")) {
            orderDetailsPresenter.getOrderDetails(goodsOrderId);
        } else if (TextUtils.equals(resultStatus, "6001")) {
            showMessage(getString(R.string.pay_cancel));
        } else {
            showMessage(getString(R.string.pay_failed));
        }
    }

}
