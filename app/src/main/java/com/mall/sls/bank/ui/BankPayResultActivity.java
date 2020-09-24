package com.mall.sls.bank.ui;

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
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.order.ui.GoodsOrderDetailsActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/9/11.
 * 描述：
 */
public class BankPayResultActivity extends BaseActivity {
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.result_iv)
    ImageView resultIv;
    @BindView(R.id.result_status)
    MediumThickTextView resultStatus;
    @BindView(R.id.process_tip)
    MediumThickTextView processTip;
    @BindView(R.id.confirm_bt)
    MediumThickTextView confirmBt;

    private String goodsOrderId;
    private String choiceType;//1:下单页过来 2：订单列表 3:详情过来
    private String payResult;

    public static void start(Context context, String goodsOrderId,String payResult, String choiceType) {
        Intent intent = new Intent(context, BankPayResultActivity.class);
        intent.putExtra(StaticData.GOODS_ORDER_ID, goodsOrderId);
        intent.putExtra(StaticData.CHOICE_TYPE, choiceType);
        intent.putExtra(StaticData.PAY_RESULT, payResult);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_pay_result);
        ButterKnife.bind(this);
        setHeight(null,title,null);
        initView();
    }

    private void initView() {
        goodsOrderId = getIntent().getStringExtra(StaticData.GOODS_ORDER_ID);
        choiceType = getIntent().getStringExtra(StaticData.CHOICE_TYPE);
        payResult=getIntent().getStringExtra(StaticData.PAY_RESULT);
        if(TextUtils.equals(StaticData.BANK_PAY_PROCESSING,payResult)){
            resultIv.setSelected(true);
            resultStatus.setText(getString(R.string.processing));
            processTip.setVisibility(View.VISIBLE);
        }else {
            resultIv.setSelected(false);
            resultStatus.setText(getString(R.string.order_pay_failed));
            processTip.setVisibility(View.GONE);
        }
    }


    @OnClick({R.id.confirm_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_bt:
                if (!TextUtils.equals(StaticData.REFRESH_THREE, choiceType)) {
                    GoodsOrderDetailsActivity.start(this, goodsOrderId);
                }
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
