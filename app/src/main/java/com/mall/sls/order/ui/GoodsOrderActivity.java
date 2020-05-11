package com.mall.sls.order.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.mall.sls.BaseActivity;
import com.mall.sls.BaseFragment;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.widget.textview.ConventionalTextView;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.mainframe.adapter.MainPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/11.
 * 描述：订单列表
 */
public class GoodsOrderActivity extends BaseActivity {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.all_orders_tv)
    ConventionalTextView allOrdersTv;
    @BindView(R.id.all_orders_iv)
    View allOrdersIv;
    @BindView(R.id.all_orders_ll)
    LinearLayout allOrdersLl;
    @BindView(R.id.pending_payment_tv)
    ConventionalTextView pendingPaymentTv;
    @BindView(R.id.pending_payment_iv)
    View pendingPaymentIv;
    @BindView(R.id.pending_payment_ll)
    LinearLayout pendingPaymentLl;
    @BindView(R.id.pending_delivery_tv)
    ConventionalTextView pendingDeliveryTv;
    @BindView(R.id.pending_delivery_iv)
    View pendingDeliveryIv;
    @BindView(R.id.pending_delivery_ll)
    LinearLayout pendingDeliveryLl;
    @BindView(R.id.shipping_tv)
    ConventionalTextView shippingTv;
    @BindView(R.id.shipping_iv)
    View shippingIv;
    @BindView(R.id.shipping_ll)
    LinearLayout shippingLl;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private String choiceType;
    private LinearLayout[] linearLayouts;
    private BaseFragment[] fragments;
    private View[] views;
    private TextView[] textViews;
    private MainPagerAdapter adapter;


    public static void start(Context context, String choiceType) {
        Intent intent = new Intent(context, GoodsOrderActivity.class);
        intent.putExtra(StaticData.CHOICE_TYPE, choiceType);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_order);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }


    private void initView() {
        choiceType = getIntent().getStringExtra(StaticData.CHOICE_TYPE);
        fragments = new BaseFragment[4];
        fragments[0] = AllOrdersFragment.newInstance(choiceType);
        fragments[1] = PendingPaymentFragment.newInstance(choiceType);
        fragments[2] = PendingDeliveryFragment.newInstance(choiceType);
        fragments[3] = ShippingFragment.newInstance(choiceType);
        linearLayouts = new LinearLayout[4];
        linearLayouts[0] = allOrdersLl;
        linearLayouts[1] = pendingPaymentLl;
        linearLayouts[2] = pendingDeliveryLl;
        linearLayouts[3] = shippingLl;
        views = new View[4];
        views[0] = allOrdersIv;
        views[1] = pendingPaymentIv;
        views[2] = pendingDeliveryIv;
        views[3] = shippingIv;
        textViews = new TextView[4];
        textViews[0] = allOrdersTv;
        textViews[1] = pendingPaymentTv;
        textViews[2] = pendingDeliveryTv;
        textViews[3] = shippingTv;
        for (LinearLayout linearLayout : linearLayouts) {
            linearLayout.setOnClickListener(onClickListener);
        }
        viewPager.setOffscreenPageLimit(3);
        adapter = new MainPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(onPageChangeListener);
        viewPager.setCurrentItem(Integer.parseInt(choiceType));
        if (TextUtils.equals("0", choiceType)) {
            views[0].setVisibility(View.VISIBLE);
            textViews[0].setSelected(true);
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < linearLayouts.length; i++) {
                if (v == linearLayouts[i]) {
                    viewPager.setCurrentItem(i);
                    break;
                }
            }
        }
    };

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < views.length; i++) {
                views[i].setVisibility(position == i ? View.VISIBLE : View.INVISIBLE);
                textViews[i].setSelected(position == i);
            }
        }
    };

    @Override
    public View getSnackBarHolderView() {
        return null;
    }

    @OnClick({R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back://
                finish();
                break;
            default:
        }
    }
}
