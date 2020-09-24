package com.mall.sls.certify.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CerifyTipActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.bottom_tip_tv)
    ConventionalTextView bottomTipTv;
    @BindView(R.id.bottom_rl)
    RelativeLayout bottomRl;
    @BindView(R.id.confirm_bt)
    MediumThickTextView confirmBt;

    private String certifyAmount;

    public static void start(Context context,String certifyAmount) {
        Intent intent = new Intent(context, CerifyTipActivity.class);
        intent.putExtra(StaticData.CERTIFY_AMOUNT,certifyAmount);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_tip);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        String bottomTip = " 1:身份证信息仅供平台提供诚信保证使用。\n 2:我方承诺不向其他第三方透露您的个人信息";
        certifyAmount=getIntent().getStringExtra(StaticData.CERTIFY_AMOUNT);
        bottomTipTv.setText(bottomTip);
    }

    @OnClick({R.id.confirm_bt, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_bt:
                CerifyPayActivity.start(this,certifyAmount);
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
