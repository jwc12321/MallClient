package com.mall.sls.homepage.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/28.
 * 描述：
 */
public class CommonTipActivity extends BaseActivity {

    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.cancel_bt)
    ConventionalTextView cancelBt;
    @BindView(R.id.confirm_bt)
    ConventionalTextView confirmBt;

    private String commonTitle;
    private String cancelText;
    private String confirmText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_tip);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        commonTitle=getIntent().getStringExtra(StaticData.COMMON_TITLE);
        cancelText=getIntent().getStringExtra(StaticData.CANCEL_TEXT);
        confirmText=getIntent().getStringExtra(StaticData.CONFIRM_TEXT);
        title.setText(commonTitle);
        cancelBt.setText(cancelText);
        confirmBt.setText(confirmText);
    }

    private void selectBack(String  type){
        Intent backIntent = new Intent();
        backIntent.putExtra(StaticData.TIP_BACK, type);
        setResult(Activity.RESULT_OK, backIntent);
        finish();
    }

    @OnClick({R.id.cancel_bt,R.id.confirm_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_bt:
                selectBack(StaticData.REFRESH_ZERO);
                break;
            case R.id.confirm_bt:
                selectBack(StaticData.REFRESH_ONE);
                break;
            default:
        }
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
