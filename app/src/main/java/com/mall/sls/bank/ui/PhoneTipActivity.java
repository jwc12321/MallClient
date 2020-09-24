package com.mall.sls.bank.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.widget.textview.ConventionalTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/9/9.
 * 描述：
 */
public class PhoneTipActivity extends BaseActivity {
    @BindView(R.id.confirm_bt)
    ConventionalTextView confirmBt;
    @BindView(R.id.item_ll)
    RelativeLayout itemLl;
    @BindView(R.id.item_rl)
    RelativeLayout itemRl;

    public static void start(Context context) {
        Intent intent = new Intent(context, PhoneTipActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_tip);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.confirm_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_bt:
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
