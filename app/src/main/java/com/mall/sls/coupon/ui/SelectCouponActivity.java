package com.mall.sls.coupon.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.BaseActivity;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.coupon.CouponContract;
import com.mall.sls.coupon.CouponModule;
import com.mall.sls.coupon.DaggerCouponComponent;
import com.mall.sls.coupon.adapter.SelectCouponAdapter;
import com.mall.sls.coupon.presenter.CouponSelectPresenter;
import com.mall.sls.data.entity.CouponInfo;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/13.
 * 描述：选择优惠卷
 */
public class SelectCouponActivity extends BaseActivity implements CouponContract.CouponSelectView, SelectCouponAdapter.OnItemClickListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.select_iv)
    ImageView selectIv;
    @BindView(R.id.no_coupons_ll)
    RelativeLayout noCouponsLl;
    @BindView(R.id.record_rv)
    RecyclerView recordRv;
    @BindView(R.id.confirm_bt)
    MediumThickTextView confirmBt;
    @BindView(R.id.no_record_ll)
    LinearLayout noRecordLl;
    private SelectCouponAdapter selectCouponAdapter;

    @Inject
    CouponSelectPresenter couponSelectPresenter;
    private List<CouponInfo> couponInfos;

    private String cartIds;
    private String userCouponId;
    private String couponId;

    public static void start(Context context) {
        Intent intent = new Intent(context, SelectCouponActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_coupon);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        cartIds =getIntent().getStringExtra(StaticData.CART_IDS);
        userCouponId = getIntent().getStringExtra(StaticData.USER_COUPON_ID);
        selectCouponAdapter = new SelectCouponAdapter(userCouponId);
        selectCouponAdapter.setOnItemClickListener(this);
        recordRv.setAdapter(selectCouponAdapter);
        couponSelectPresenter.getCouponSelect(cartIds);
    }

    @Override
    protected void initializeInjector() {
        DaggerCouponComponent.builder()
                .applicationComponent(getApplicationComponent())
                .couponModule(new CouponModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.back, R.id.confirm_bt, R.id.select_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back://
                finish();
                break;
            case R.id.confirm_bt:
                confirm();
                break;
            case R.id.select_iv:
                couponId =StaticData.REFRESH_MINUS_ONE;;
                userCouponId =StaticData.REFRESH_MINUS_ONE;;
                selectIv.setSelected(true);
                selectCouponAdapter.setData(userCouponId);
                break;
            default:
        }
    }

    private void confirm() {
        if(TextUtils.isEmpty(couponId)){
            couponId=StaticData.REFRESH_MINUS_ONE;
        }
        if(TextUtils.isEmpty(userCouponId)){
            userCouponId=StaticData.REFRESH_MINUS_ONE;
        }
        Intent backIntent = new Intent();
        backIntent.putExtra(StaticData.COUPON_ID, couponId);
        backIntent.putExtra(StaticData.USER_COUPON_ID, userCouponId);
        setResult(Activity.RESULT_OK, backIntent);
        finish();
    }

    @Override
    public void renderCouponSelect(List<CouponInfo> couponInfos) {
        this.couponInfos = couponInfos;
        if (couponInfos != null && couponInfos.size() > 0) {
            recordRv.setVisibility(View.VISIBLE);
            noRecordLl.setVisibility(View.GONE);
        } else {
            recordRv.setVisibility(View.GONE);
            noRecordLl.setVisibility(View.VISIBLE);
        }
        selectCouponAdapter.setData(couponInfos);
    }

    @Override
    public void setPresenter(CouponContract.CouponSelectPresenter presenter) {

    }

    @Override
    public void selectWhat(String couponId, String userCouponId) {
        this.couponId = couponId;
        this.userCouponId = userCouponId;
        selectIv.setSelected(false);
        selectCouponAdapter.setData(userCouponId);
    }

    @Override
    public void upDownView(ImageView upIv, ImageView downIv, ConventionalTextView limitTv, int position) {
        CouponInfo couponInfo = couponInfos.get(position);
        if (couponInfo.isUp()) {
            couponInfo.setUp(false);
            upIv.setVisibility(View.GONE);
            downIv.setVisibility(View.VISIBLE);
            limitTv.setVisibility(View.GONE);
        } else {
            couponInfo.setUp(true);
            upIv.setVisibility(View.VISIBLE);
            downIv.setVisibility(View.GONE);
            limitTv.setVisibility(View.VISIBLE);
        }
    }
}
