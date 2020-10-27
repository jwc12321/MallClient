package com.mall.sls.merchant.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.certify.ui.MerchantCertifyActivity;
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.IntegralPointsInfo;
import com.mall.sls.homepage.ui.CommonTipActivity;
import com.mall.sls.merchant.DaggerMerchantComponent;
import com.mall.sls.merchant.MerchantContract;
import com.mall.sls.merchant.MerchantModule;
import com.mall.sls.merchant.presenter.MerchantRightsPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/10/22.
 * 描述：商家权益
 */
public class MerchantRightsActivity extends BaseActivity implements MerchantContract.MerchantRightsView {
    @BindView(R.id.small_title)
    MediumThickTextView smallTitle;
    @BindView(R.id.small_title_rel)
    RelativeLayout smallTitleRel;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.right_bt)
    ConventionalTextView rightBt;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.channel_points_ll)
    LinearLayout channelPointsLl;
    @BindView(R.id.total_points)
    MediumThickTextView totalPoints;
    @BindView(R.id.last_points)
    MediumThickTextView lastPoints;
    @BindView(R.id.record_rl)
    RelativeLayout recordRl;
    @BindView(R.id.redeem_rl)
    RelativeLayout redeemRl;
    private String failReason;
    private String merchantStatus;
    private String tipBack;


    @Inject
    MerchantRightsPresenter merchantRightsPresenter;

    public static void start(Context context,String merchantStatus, String failReason) {
        Intent intent = new Intent(context, MerchantRightsActivity.class);
        intent.putExtra(StaticData.MERCHANT_STATUS, merchantStatus);
        intent.putExtra(StaticData.FAIL_REASON, failReason);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_rights);
        ButterKnife.bind(this);
        navigationBar();
        setHeight(null, smallTitle, null);
        initView();
    }

    private void initView(){
        failReason = getIntent().getStringExtra(StaticData.FAIL_REASON);
        merchantStatus = getIntent().getStringExtra(StaticData.MERCHANT_STATUS);
        merchantRightsPresenter.getIntegralPointsInfo();
    }

    @Override
    protected void initializeInjector() {
        DaggerMerchantComponent.builder()
                .applicationComponent(getApplicationComponent())
                .merchantModule(new MerchantModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.back,R.id.record_rl,R.id.redeem_rl,R.id.right_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.record_rl:
                PointsRecordActivity.start(this);
                break;
            case R.id.redeem_rl://可兑换
                RedeemActivity.start(this);
                break;
            case R.id.right_bt:
                Intent intent = new Intent(this, CommonTipActivity.class);
                intent.putExtra(StaticData.COMMON_TITLE, getString(R.string.cancel_merchant_certify));
                intent.putExtra(StaticData.CANCEL_TEXT, getString(R.string.no));
                intent.putExtra(StaticData.CONFIRM_TEXT, getString(R.string.yes));
                startActivityForResult(intent, RequestCodeStatic.TIP_PAGE);
                break;
            default:
        }
    }

    @Override
    public void renderMerchantRights(IntegralPointsInfo integralPointsInfo) {
        if (integralPointsInfo != null) {
            totalPoints.setText(integralPointsInfo.getTotalPoints());
            lastPoints.setText(integralPointsInfo.getLastPoints());
        }
    }

    @Override
    public void renderMerchantCancel(Boolean isBoolean) {
        if(isBoolean){
            showMessage(getString(R.string.merchant_cancel));
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodeStatic.TIP_PAGE://点击返回
                    if (data != null) {
                        tipBack = data.getStringExtra(StaticData.TIP_BACK);
                        if (TextUtils.equals(StaticData.REFRESH_ONE, tipBack)) {
                            merchantRightsPresenter.merchantCancel();
                        }
                    }
                    break;
                default:
            }
        }
    }

    @Override
    public void setPresenter(MerchantContract.MerchantRightsPresenter presenter) {

    }
}
