package com.mall.sls.homepage.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.NumberFormatUnit;
import com.mall.sls.common.unit.SystemUtil;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.PayMethodInfo;
import com.mall.sls.homepage.DaggerHomepageComponent;
import com.mall.sls.homepage.HomepageContract;
import com.mall.sls.homepage.HomepageModule;
import com.mall.sls.homepage.presenter.PayMethodPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/11.
 * 描述：
 */
public class SelectPayTypeActivity extends BaseActivity implements HomepageContract.PayMethodView {
    @BindView(R.id.pay_amount)
    MediumThickTextView payAmount;
    @BindView(R.id.weixin_iv)
    ImageView weixinIv;
    @BindView(R.id.select_weixin_iv)
    ImageView selectWeixinIv;
    @BindView(R.id.ali_iv)
    ImageView aliIv;
    @BindView(R.id.select_ali_iv)
    ImageView selectAliIv;
    @BindView(R.id.confirm_bt)
    MediumThickTextView confirmBt;
    @BindView(R.id.item_rl)
    LinearLayout itemRl;
    @BindView(R.id.all_rl)
    RelativeLayout allRl;
    @BindView(R.id.close_iv)
    ImageView closeIv;
    @BindView(R.id.weixin_rl)
    RelativeLayout weixinRl;
    @BindView(R.id.ali_rl)
    RelativeLayout aliRl;
    @BindView(R.id.bank_iv)
    ImageView bankIv;
    @BindView(R.id.select_bank_iv)
    ImageView selectBankIv;
    @BindView(R.id.bank_rl)
    RelativeLayout bankRl;
    @BindView(R.id.wx_tv)
    ConventionalTextView wxTv;
    @BindView(R.id.wx_tip)
    ConventionalTextView wxTip;
    @BindView(R.id.ali_tv)
    ConventionalTextView aliTv;
    @BindView(R.id.ali_tip)
    ConventionalTextView aliTip;
    @BindView(R.id.bank_tv)
    ConventionalTextView bankTv;
    @BindView(R.id.bank_tip)
    ConventionalTextView bankTip;

    private String choiceType;
    private String amount;
    private String devicePlatform;
    private String paymentMethod;
    private String orderType;
    @Inject
    PayMethodPresenter payMethodPresenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, SelectPayTypeActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pay_type);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        choiceType = getIntent().getStringExtra(StaticData.CHOICE_TYPE);
        confirmBt.setSelected(TextUtils.equals(StaticData.REFRESH_ONE, choiceType) ? true : false);
        amount = getIntent().getStringExtra(StaticData.PAYMENT_AMOUNT);
        orderType=getIntent().getStringExtra(StaticData.ORDER_TYPE);
        payAmount.setText(getString(R.string.payment_amount) + NumberFormatUnit.goodsFormat(amount));
        devicePlatform = SystemUtil.getChannel(this);
        payMethodPresenter.getPayMethod(devicePlatform,orderType);
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

    @OnClick({R.id.all_rl, R.id.item_rl, R.id.close_iv, R.id.confirm_bt, R.id.weixin_rl, R.id.ali_rl,R.id.bank_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.all_rl:
            case R.id.close_iv:
                back();
                break;
            case R.id.item_rl:
                break;
            case R.id.confirm_bt:
                Intent backIntent = new Intent();
                backIntent.putExtra(StaticData.PAYMENT_METHOD, paymentMethod);
                setResult(Activity.RESULT_OK, backIntent);
                finish();
                break;
            case R.id.weixin_rl:
                paymentMethod = StaticData.WX_PAY;
                selectPayType();
                break;
            case R.id.ali_rl:
                paymentMethod = StaticData.ALI_PAY;
                selectPayType();
                break;
            case R.id.bank_rl:
                paymentMethod = StaticData.BAO_FU_PAY;
                selectPayType();
                break;
            default:
        }
    }

    private void selectPayType() {
        selectWeixinIv.setSelected(TextUtils.equals(StaticData.WX_PAY, paymentMethod));
        selectAliIv.setSelected(TextUtils.equals(StaticData.ALI_PAY, paymentMethod));
        selectBankIv.setSelected(TextUtils.equals(StaticData.BAO_FU_PAY, paymentMethod));
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
        finish();
    }


    @Override
    public void renderPayMethod(List<PayMethodInfo> payMethods) {
        if (payMethods != null) {
            for (PayMethodInfo payMethodInfo : payMethods) {
                if (TextUtils.equals(StaticData.WX_PAY, payMethodInfo.getPaymentMethod())) {
                    weixinRl.setVisibility(View.VISIBLE);
                    wxTip.setVisibility(TextUtils.isEmpty(payMethodInfo.getDescription())?View.GONE:View.VISIBLE);
                    wxTip.setText(payMethodInfo.getDescription());
                } else if (TextUtils.equals(StaticData.ALI_PAY, payMethodInfo.getPaymentMethod())) {
                    aliRl.setVisibility(View.VISIBLE);
                    aliTip.setVisibility(TextUtils.isEmpty(payMethodInfo.getDescription())?View.GONE:View.VISIBLE);
                    aliTip.setText(payMethodInfo.getDescription());
                } else if (TextUtils.equals(StaticData.BAO_FU_PAY, payMethodInfo.getPaymentMethod())) {
                    bankRl.setVisibility(View.VISIBLE);
                    bankTip.setVisibility(TextUtils.isEmpty(payMethodInfo.getDescription())?View.GONE:View.VISIBLE);
                    bankTip.setText(payMethodInfo.getDescription());
                }
            }
            if (weixinRl.getVisibility() == View.VISIBLE) {
                paymentMethod = StaticData.WX_PAY;
                selectPayType();
                return;
            }
            if (aliRl.getVisibility() == View.VISIBLE) {
                paymentMethod = StaticData.ALI_PAY;
                selectPayType();
                return;
            }
            if (bankRl.getVisibility() == View.VISIBLE) {
                paymentMethod = StaticData.BAO_FU_PAY;
                selectPayType();
                return;
            }
        }
    }

    @Override
    public void setPresenter(HomepageContract.PayMethodPresenter presenter) {

    }
}
