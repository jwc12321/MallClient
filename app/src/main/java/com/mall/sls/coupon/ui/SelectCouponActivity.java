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
import com.mall.sls.common.widget.textview.MediumThickTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/13.
 * 描述：选择优惠卷
 */
public class SelectCouponActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.select_weixin_iv)
    ImageView selectWeixinIv;
    @BindView(R.id.no_coupons_ll)
    RelativeLayout noCouponsLl;
    @BindView(R.id.record_rv)
    RecyclerView recordRv;
    @BindView(R.id.confirm_bt)
    MediumThickTextView confirmBt;

    public static void start(Context context) {
        Intent intent = new Intent(context, SelectCouponActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_coupon);
        ButterKnife.bind(this);
        setHeight(back,title,null);
    }

    private void initView(){

    }



    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.back,R.id.confirm_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back://
                finish();
                break;
            case R.id.confirm_bt:
                break;
            default:
        }
    }
}
