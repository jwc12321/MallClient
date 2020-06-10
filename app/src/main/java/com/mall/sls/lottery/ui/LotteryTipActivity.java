package com.mall.sls.lottery.ui;

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
import com.mall.sls.homepage.ui.CityNotOpenActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/6/10.
 * 描述：
 */
public class LotteryTipActivity extends BaseActivity {

    @BindView(R.id.close_iv)
    ImageView closeIv;
    @BindView(R.id.content)
    ConventionalTextView content;

    private String commonContent;

    public static void start(Context context,String commonContent) {
        Intent intent = new Intent(context, LotteryTipActivity.class);
        intent.putExtra(StaticData.COMMON_CONTENT,commonContent);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery_tip);
        ButterKnife.bind(this);
        commonContent=getIntent().getStringExtra(StaticData.COMMON_CONTENT);
        content.setText(commonContent);
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


    @Override
    public View getSnackBarHolderView() {
        return null;
    }
}
