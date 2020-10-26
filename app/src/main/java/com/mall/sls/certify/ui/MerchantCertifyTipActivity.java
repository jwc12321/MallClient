package com.mall.sls.certify.ui;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MerchantCertifyTipActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.confirm_bt)
    MediumThickTextView confirmBt;
    private String failReason;
    private String merchantStatus;
    private String userLevel;
    private Boolean certifyPay;
    private String certifyAmount;

    public static void start(Context context, String userLevel, Boolean certifyPay, String certifyAmount) {
        Intent intent = new Intent(context, MerchantCertifyTipActivity.class);
        intent.putExtra(StaticData.USER_LEVEL, userLevel);
        intent.putExtra(StaticData.CERTIFY_AMOUNT, certifyAmount);
        intent.putExtra(StaticData.CERTIFY_PAY, certifyPay);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_certify_tip);
        ButterKnife.bind(this);
        setHeight(back, null, null);
        initView();
    }

    private void initView() {
        userLevel = getIntent().getStringExtra(StaticData.USER_LEVEL);
        certifyPay = getIntent().getBooleanExtra(StaticData.CERTIFY_PAY, false);
        certifyAmount = getIntent().getStringExtra(StaticData.CERTIFY_AMOUNT);
    }

    @OnClick({R.id.confirm_bt, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_bt:
                if (TextUtils.equals(StaticData.REFRESH_ZERO, userLevel)) {
                    CerifyTipActivity.start(this, certifyAmount);
                } else {
                    MerchantCertifyActivity.start(this, merchantStatus, failReason);
                }
                finish();
                break;
            case R.id.back:
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
