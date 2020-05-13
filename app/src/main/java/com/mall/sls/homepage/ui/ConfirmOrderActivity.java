package com.mall.sls.homepage.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.widget.textview.BlackDrawTextView;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.coupon.ui.SelectCouponActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/11.
 * 描述：确认下单
 */
public class ConfirmOrderActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.goods_iv)
    ImageView goodsIv;
    @BindView(R.id.goods_number)
    ConventionalTextView goodsNumber;
    @BindView(R.id.current_price)
    ConventionalTextView currentPrice;
    @BindView(R.id.original_price)
    BlackDrawTextView originalPrice;
    @BindView(R.id.goods_price)
    ConventionalTextView goodsPrice;
    @BindView(R.id.coupon)
    ConventionalTextView coupon;
    @BindView(R.id.goods_total_amount)
    ConventionalTextView goodsTotalAmount;
    @BindView(R.id.total_amount_tv)
    ConventionalTextView totalAmountTv;
    @BindView(R.id.total_amount)
    ConventionalTextView totalAmount;
    @BindView(R.id.confirm_bt)
    ConventionalTextView confirmBt;
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
    @BindView(R.id.coupon_rl)
    RelativeLayout couponRl;

    public static void start(Context context) {
        Intent intent = new Intent(context, ConfirmOrderActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        ButterKnife.bind(this);
        setHeight(back, title, null);
    }

    @OnClick({R.id.back, R.id.confirm_bt, R.id.coupon_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.confirm_bt://去支付
                SelectPayTypeActivity.start(this);
                break;
            case R.id.coupon_rl:
                SelectCouponActivity.start(this);
                break;
            default:
        }
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
