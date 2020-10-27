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
import com.mall.sls.certify.CertifyContract;
import com.mall.sls.certify.CertifyModule;
import com.mall.sls.certify.DaggerCertifyComponent;
import com.mall.sls.certify.presenter.MerchantCertifyTipPresenter;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.data.entity.MineInfo;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MerchantCertifyTipActivity extends BaseActivity implements CertifyContract.MerchantCertifyTipView {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.confirm_bt)
    MediumThickTextView confirmBt;
    private String userLevel;
    private String failReason;
    private String merchantStatus;

    @Inject
    MerchantCertifyTipPresenter presenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, MerchantCertifyTipActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_certify_tip);
        ButterKnife.bind(this);
        setHeight(back, null, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getMineInfo();
    }

    @Override
    protected void initializeInjector() {
        DaggerCertifyComponent.builder()
                .applicationComponent(getApplicationComponent())
                .certifyModule(new CertifyModule(this))
                .build()
                .inject(this);

    }


    @OnClick({R.id.confirm_bt, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_bt:
                if (TextUtils.equals(StaticData.REFRESH_ZERO, userLevel)) {
                    NameVerifiedActivity.start(this,StaticData.REFRESH_ONE);
                } else {
                    MerchantCertifyActivity.start(this, merchantStatus, failReason);
                }
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

    @Override
    public void renderMineInfo(MineInfo mineInfo) {
        if(mineInfo!=null&&mineInfo.getUserInfo()!=null){
            userLevel=mineInfo.getUserInfo().getUserLevel();
            failReason=mineInfo.getFailReason();
            merchantStatus=mineInfo.getMerchantStatus();
        }
    }

    @Override
    public void setPresenter(CertifyContract.MerchantCertifyTipPresenter presenter) {

    }
}
