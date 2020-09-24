package com.mall.sls.bank.ui;

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
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/9/10.
 * 描述：
 */
public class OperationResultActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.right_tv)
    MediumThickTextView rightTv;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.bind_result_tv)
    ConventionalTextView bindResultTv;
    @BindView(R.id.result_iv)
    ImageView resultIv;

    private String bindResult;

    public static void start(Context context, String bindResult) {
        Intent intent = new Intent(context, OperationResultActivity.class);
        intent.putExtra(StaticData.BIND_RESULT, bindResult);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_result);
        ButterKnife.bind(this);
        setHeight(back, title, rightTv);
        initView();
    }

    private void initView() {
        bindResult = getIntent().getStringExtra(StaticData.BIND_RESULT);
        if (TextUtils.equals(StaticData.BANK_PAY_SUCCESS, bindResult)) {
            resultIv.setSelected(true);
            bindResultTv.setText(getString(R.string.add_bank_card_success));
        } else {
            resultIv.setSelected(false);
            bindResultTv.setText(getString(R.string.add_bank_card_processing));
        }
    }

    @OnClick({R.id.back, R.id.right_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
            case R.id.right_tv:
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
