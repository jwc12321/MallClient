package com.mall.sls.homepage.ui;

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
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.FormatUtil;
import com.mall.sls.common.unit.MainStartManager;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.unit.TCAgentUnit;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.UserPayInfo;
import com.mall.sls.mainframe.ui.MainFrameActivity;
import com.mall.sls.order.ui.GoodsOrderDetailsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/6/29.
 * 描述：
 */
public class CartPaySuccessActivity extends BaseActivity {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.actualPrice)
    MediumThickTextView actualPrice;
    @BindView(R.id.subAmount)
    MediumThickTextView subAmount;
    @BindView(R.id.order_iv)
    ImageView orderIv;
    @BindView(R.id.home_iv)
    ImageView homeIv;
    private String goodsOrderId;
    private UserPayInfo userPayInfo;

    public static void start(Context context, String goodsOrderId, UserPayInfo userPayInfo) {
        Intent intent = new Intent(context, CartPaySuccessActivity.class);
        intent.putExtra(StaticData.GOODS_ORDER_ID, goodsOrderId);
        intent.putExtra(StaticData.USER_PAY_INFO, userPayInfo);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_pay_success);
        ButterKnife.bind(this);
        setHeight(back, null, null);
        initView();
    }

    private void initView() {
        goodsOrderId = getIntent().getStringExtra(StaticData.GOODS_ORDER_ID);
        userPayInfo = (UserPayInfo) getIntent().getSerializableExtra(StaticData.USER_PAY_INFO);
        if (userPayInfo != null) {
            actualPrice.setText("实付：" + NumberFormatUnit.goodsFormat(userPayInfo.getActualPrice()));
            subAmount.setVisibility(NumberFormatUnit.isZero(userPayInfo.getSubAmount())?View.INVISIBLE:View.VISIBLE);
            subAmount.setText("（已优惠"+NumberFormatUnit.goodsFormat(userPayInfo.getSubAmount())+"）");
        }
    }

    @OnClick({R.id.home_iv, R.id.back, R.id.order_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_iv://首页
                TCAgentUnit.setEventId(this, getString(R.string.pay_success_home));
                MainStartManager.saveMainStart(StaticData.REFRESH_ZERO);
                MainFrameActivity.start(this);
                finish();
                break;
            case R.id.back://返回
                finish();
                break;
            case R.id.order_iv:
                TCAgentUnit.setEventId(this, getString(R.string.pay_success_goods_details));
                GoodsOrderDetailsActivity.start(this, goodsOrderId);
                finish();
                break;
            default:
        }
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
