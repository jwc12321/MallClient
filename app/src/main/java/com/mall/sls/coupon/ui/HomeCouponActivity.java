package com.mall.sls.coupon.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.coupon.adapter.HomeCouponReceiveAdapter;
import com.mall.sls.data.entity.CouponInfo;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/9/7.
 * 描述：
 */
public class HomeCouponActivity extends BaseActivity {
    @BindView(R.id.close_iv)
    ImageView closeIv;
    @BindView(R.id.congratulations_getting)
    MediumThickTextView congratulationsGetting;
    @BindView(R.id.coupon_number)
    MediumThickTextView couponNumber;
    @BindView(R.id.record_rv)
    RecyclerView recordRv;
    @BindView(R.id.item_rl)
    RelativeLayout itemRl;

    private List<CouponInfo> couponInfos;
    private HomeCouponReceiveAdapter homeCouponAdapter;

    public static void start(Context context, List<CouponInfo> couponInfos) {
        Intent intent = new Intent(context, HomeCouponActivity.class);
        intent.putExtra(StaticData.COUPON_INFO_S, (Serializable) couponInfos);
        context.startActivity(intent);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_coupon);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        couponInfos = (List<CouponInfo>) getIntent().getSerializableExtra(StaticData.COUPON_INFO_S);
        homeCouponAdapter=new HomeCouponReceiveAdapter();
        recordRv.setAdapter(homeCouponAdapter);
        homeCouponAdapter.setData(couponInfos);
        couponNumber.setText(couponInfos.size()+"张优惠券");

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
