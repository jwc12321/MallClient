package com.mall.sls.homepage.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.MediumThickTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/19.
 * 描述：拼单成功
 */
public class SuccessfulOrderActivity extends BaseActivity {
    @BindView(R.id.close_iv)
    ImageView closeIv;
    @BindView(R.id.up_bt)
    MediumThickTextView upBt;
    @BindView(R.id.down_bt)
    MediumThickTextView downBt;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_order);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.close_iv, R.id.up_bt,R.id.down_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_iv:
            case R.id.down_bt:
                selectBack(StaticData.REFLASH_ONE);
                break;
            case R.id.up_bt:
                selectBack(StaticData.REFLASH_ZERO);
                break;
            default:
        }
    }

    private void selectBack(String  type){
        Intent backIntent = new Intent();
        backIntent.putExtra(StaticData.TIP_BACK, type);
        setResult(Activity.RESULT_OK, backIntent);
        finish();
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
