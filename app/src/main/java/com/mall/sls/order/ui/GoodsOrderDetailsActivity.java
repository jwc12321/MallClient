package com.mall.sls.order.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.OrderTimeInfo;
import com.mall.sls.order.adapter.OrderInformationAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/12.
 * 描述：订单详情
 */
public class GoodsOrderDetailsActivity extends BaseActivity implements OrderInformationAdapter.OnItemClickListener{
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
        setHeight(back,title,null);
        initView();
    }

    private void initView(){
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        goodsOrderId = getIntent().getStringExtra(StaticData.GOODS_ORDER_ID);
        addAdapter();
    }

    private void addAdapter(){
        orderInformationAdapter=new OrderInformationAdapter(this);
        orderInformationAdapter.setOnItemClickListener(this);
        infoRv.setAdapter(orderInformationAdapter);
        List<OrderTimeInfo> orderTimeInfos =new ArrayList<>();
        OrderTimeInfo orderTimeInfo=new OrderTimeInfo(getString(R.string.order_number),"8rer9e8r9e8r9e8re");
        OrderTimeInfo orderTimeInfo1=new OrderTimeInfo(getString(R.string.order_time),"2019-09-09");
        OrderTimeInfo orderTimeInfo2=new OrderTimeInfo(getString(R.string.payment_method),"微信");
        OrderTimeInfo orderTimeInfo3=new OrderTimeInfo(getString(R.string.payment_time),"2019-09-09");
        OrderTimeInfo orderTimeInfo4=new OrderTimeInfo(getString(R.string.order_notes),"马上送来");
        orderTimeInfos.add(orderTimeInfo);
        orderTimeInfos.add(orderTimeInfo1);
        orderTimeInfos.add(orderTimeInfo2);
        orderTimeInfos.add(orderTimeInfo3);
        orderTimeInfos.add(orderTimeInfo4);
        orderInformationAdapter.setData(orderTimeInfos);

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

    @OnClick({R.id.back,R.id.order_status})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back://
                finish();
                break;
            case R.id.order_status:
                DeliveryinfoActivity.start(this);
                break;
            default:
        }
    }
}
