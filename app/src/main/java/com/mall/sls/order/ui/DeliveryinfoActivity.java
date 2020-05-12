package com.mall.sls.order.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.widget.textview.ConventionalTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/12.
 * 描述：配送信息
 */
public class DeliveryinfoActivity extends BaseActivity {


    @BindView(R.id.submit_time_iv)
    ImageView submitTimeIv;
    @BindView(R.id.submit_time)
    ConventionalTextView submitTime;
    @BindView(R.id.order_paid_iv)
    ImageView orderPaidIv;
    @BindView(R.id.order_paid)
    ConventionalTextView orderPaid;
    @BindView(R.id.merchant_packaging_iv)
    ImageView merchantPackagingIv;
    @BindView(R.id.merchant_packaging)
    ConventionalTextView merchantPackaging;
    @BindView(R.id.delivery_shipping_iv)
    ImageView deliveryShippingIv;
    @BindView(R.id.delivery_shipping)
    ConventionalTextView deliveryShipping;
    @BindView(R.id.order_delivered_iv)
    ImageView orderDeliveredIv;
    @BindView(R.id.order_delivered)
    ConventionalTextView orderDelivered;
    @BindView(R.id.close_iv)
    ImageView closeIv;

    public static void start(Context context) {
        Intent intent = new Intent(context, DeliveryinfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_info);
        ButterKnife.bind(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.close_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_iv://
                finish();
                break;
            default:
        }
    }
}
