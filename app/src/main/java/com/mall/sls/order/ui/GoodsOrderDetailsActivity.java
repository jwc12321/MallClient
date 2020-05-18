package com.mall.sls.order.ui;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.GlideHelper;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.GoodsOrderDetails;
import com.mall.sls.data.entity.OrderGoodsVo;
import com.mall.sls.data.entity.OrderInfo;
import com.mall.sls.data.entity.OrderTimeInfo;
import com.mall.sls.order.DaggerOrderComponent;
import com.mall.sls.order.OrderContract;
import com.mall.sls.order.OrderModule;
import com.mall.sls.order.adapter.OrderInformationAdapter;
import com.mall.sls.order.presenter.OrderDetailsPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.remaining_payment_time)
    ConventionalTextView remainingPaymentTime;
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
    private String goodsOrderId;
    private OrderInformationAdapter orderInformationAdapter;

    private ClipboardManager myClipboard;
    private ClipData myClip;
    private List<OrderGoodsVo> orderGoodsVos;
    private List<OrderTimeInfo> orderTimeInfos;
    private GoodsOrderDetails goodsOrderDetails;

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
        orderTimeInfos=new ArrayList<>();
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

    @OnClick({R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back://
                finish();
                break;
            default:
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
    public void renderOrderDetails(OrderInfo orderInfo) {
        if (orderInfo != null&&orderInfo.getGoodsOrderDetails()!=null) {
            goodsOrderDetails=orderInfo.getGoodsOrderDetails();
//            setOrderStatus(goodsOrderDetails.getOrderStatus());
            receiptAddress.setText(goodsOrderDetails.getAddress());
            namePhone.setText(goodsOrderDetails.getConsignee() + " " + goodsOrderDetails.getMobile());
            orderGoodsVos=goodsOrderDetails.getOrderGoodsVos();
            if (orderGoodsVos != null&&orderGoodsVos.size()>0) {
                GlideHelper.load(this, orderGoodsVos.get(0).getPicUrl(), R.mipmap.icon_default_goods, goodsIv);
                goodsName.setText(orderGoodsVos.get(0).getGoodsName());
                goodsPrice.setText("¥" + NumberFormatUnit.twoDecimalFormat(orderGoodsVos.get(0).getPrice()));
                goodsNumber.setText("x"+orderGoodsVos.get(0).getNumber());
            }
            goodsPrice.setText("¥" + NumberFormatUnit.twoDecimalFormat(goodsOrderDetails.getGoodsPrice()));
            coupon.setText("-¥" + NumberFormatUnit.twoDecimalFormat(goodsOrderDetails.getCouponPrice()));
            realPayment.setText("¥" + NumberFormatUnit.twoDecimalFormat(goodsOrderDetails.getActualPrice()));
            orderTimeInfos.clear();
            if(!TextUtils.isEmpty(goodsOrderDetails.getOrderSn())){
                orderTimeInfos.add(new OrderTimeInfo(getString(R.string.order_number),goodsOrderDetails.getOrderSn()));
            }
            if(!TextUtils.isEmpty(goodsOrderDetails.getAddTime())){
                orderTimeInfos.add(new OrderTimeInfo(getString(R.string.order_time),goodsOrderDetails.getAddTime()));
            }
            if(!TextUtils.isEmpty(goodsOrderDetails.getPayModeText())){
                orderTimeInfos.add(new OrderTimeInfo(getString(R.string.payment_method),goodsOrderDetails.getPayModeText()));
            }
            if(!TextUtils.isEmpty(goodsOrderDetails.getPayTime())){
                orderTimeInfos.add(new OrderTimeInfo(getString(R.string.payment_time),goodsOrderDetails.getPayTime()));
            }
            if(!TextUtils.isEmpty(goodsOrderDetails.getMessage())){
                orderTimeInfos.add(new OrderTimeInfo(getString(R.string.order_notes),goodsOrderDetails.getMessage()));
            }
            orderInformationAdapter.setData(orderTimeInfos);
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
}
