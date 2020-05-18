package com.mall.sls.homepage.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.ConventionalTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BackTipActivity extends BaseActivity {

    @BindView(R.id.title)
    ConventionalTextView title;
    @BindView(R.id.cancel_bt)
    ConventionalTextView cancelBt;
    @BindView(R.id.confirm_bt)
    ConventionalTextView confirmBt;
    @BindView(R.id.all_rl)
    RelativeLayout allRl;
    private String commonTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_tip);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        commonTitle = getIntent().getStringExtra(StaticData.COMMON_TITLE);
        title.setText(commonTitle);
    }

    @OnClick({R.id.cancel_bt, R.id.confirm_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_bt:
                finish();
                break;
            case R.id.confirm_bt:
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
                break;
            default:
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
