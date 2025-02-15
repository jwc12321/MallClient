package com.mall.sls.mine.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.ConventionalTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/13.
 * 描述：客服
 */
public class CustomerServiceActivity extends BaseActivity {
    @BindView(R.id.close_iv)
    ImageView closeIv;
    @BindView(R.id.phone)
    ConventionalTextView phone;
    private String consumerPhone;


    public static void start(Context context, String consumerPhone) {
        Intent intent = new Intent(context, CustomerServiceActivity.class);
        intent.putExtra(StaticData.CONSUMER_PHONE, consumerPhone);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cusiomer_service);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        consumerPhone = getIntent().getStringExtra(StaticData.CONSUMER_PHONE);
        phone.setText(consumerPhone);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.close_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_iv:
                finish();
                break;
            default:
        }
    }


}
