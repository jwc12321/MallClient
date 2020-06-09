package com.mall.sls.homepage.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.ConventionalTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TitleContentActivity extends BaseActivity {


    @BindView(R.id.title)
    ConventionalTextView title;
    @BindView(R.id.content)
    ConventionalTextView content;
    @BindView(R.id.cancel_bt)
    ConventionalTextView cancelBt;
    @BindView(R.id.confirm_bt)
    ConventionalTextView confirmBt;
    @BindView(R.id.all_rl)
    RelativeLayout allRl;


    private String commonTitle;
    private String commonContent;
    private String cancelText;
    private String confirmText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_content);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        commonTitle = getIntent().getStringExtra(StaticData.COMMON_TITLE);
        commonContent=getIntent().getStringExtra(StaticData.COMMON_CONTENT);
        cancelText=getIntent().getStringExtra(StaticData.CANCEL_TEXT);
        confirmText=getIntent().getStringExtra(StaticData.CONFIRM_TEXT);
        title.setText(commonTitle);
        content.setText(commonContent);
        cancelBt.setText(cancelText);
        confirmBt.setText(confirmText);
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
