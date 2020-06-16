package com.mall.sls.order.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.certify.ui.CerifyTipActivity;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/6/16.
 * 描述：
 */
public class RefundProgressActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.pay_amount)
    MediumThickTextView payAmount;
    @BindView(R.id.return_where)
    ConventionalTextView returnWhere;
    @BindView(R.id.refund_processing_iv)
    ImageView refundProcessingIv;
    @BindView(R.id.successful_iv)
    ImageView successfulIv;
    @BindView(R.id.order_refund_time)
    ConventionalTextView orderRefundTime;
    @BindView(R.id.order_arrival_time)
    ConventionalTextView orderArrivalTime;

    private String orderStatus;
    private String amount;
    private String payType;
    private String refundTime;
    private String arrivalTime;

    public static void start(Context context, String orderStatus, String amount, String payType,String refundTime,String arrivalTime) {
        Intent intent = new Intent(context, CerifyTipActivity.class);
        intent.putExtra(StaticData.ORDER_STATUS, orderStatus);
        intent.putExtra(StaticData.PAYMENT_AMOUNT, amount);
        intent.putExtra(StaticData.PAY_TYPE, payType);
        intent.putExtra(StaticData.REFUND_TIME, refundTime);
        intent.putExtra(StaticData.ARRIVAL_TIME, arrivalTime);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_progress);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        orderStatus = getIntent().getStringExtra(StaticData.ORDER_STATUS);
        amount = getIntent().getStringExtra(StaticData.PAYMENT_AMOUNT);
        payType = getIntent().getStringExtra(StaticData.PAY_TYPE);
        refundTime=getIntent().getStringExtra(StaticData.REFUND_TIME);
        arrivalTime=getIntent().getStringExtra(StaticData.ARRIVAL_TIME);
        payAmount.setText("¥" + NumberFormatUnit.twoDecimalFormat(amount));
        refundProcessingIv.setSelected(true);
        successfulIv.setSelected(TextUtils.equals(StaticData.REFUNDED, orderStatus));
        returnWhere.setText("（退款将会原路返回到您的" + payType + "账户中）");
        orderRefundTime.setText(refundTime);
        orderArrivalTime.setText(arrivalTime);
        orderArrivalTime.setVisibility(TextUtils.equals(StaticData.REFUNDED, orderStatus)?View.VISIBLE:View.INVISIBLE);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({ R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            default:
        }
    }
}
