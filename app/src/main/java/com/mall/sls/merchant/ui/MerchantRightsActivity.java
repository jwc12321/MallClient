package com.mall.sls.merchant.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/10/22.
 * 描述：商家权益
 */
public class MerchantRightsActivity extends BaseActivity {
    @BindView(R.id.small_title)
    MediumThickTextView smallTitle;
    @BindView(R.id.small_title_rel)
    RelativeLayout smallTitleRel;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.right_bt)
    ConventionalTextView rightBt;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.channel_points_ll)
    LinearLayout channelPointsLl;
    @BindView(R.id.accumulated_points_number)
    MediumThickTextView accumulatedPointsNumber;
    @BindView(R.id.redeem_number)
    MediumThickTextView redeemNumber;

    public static void start(Context context) {
        Intent intent = new Intent(context, MerchantRightsActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_rights);
        ButterKnife.bind(this);
        navigationBar();
        setHeight(null,smallTitle,null);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({ R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            default:
        }
    }
}
