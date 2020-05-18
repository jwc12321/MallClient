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
 * @author jwc on 2020/5/12.
 * 描述：优惠卷
 */
public class CouponActivity extends BaseActivity implements CouponUnusedFragment.CouponUnusedListener,CouponUsedFragment.CouponUsedListener,CouponExpiredFragment.CouponExpiredListener{
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.unused_tv)
    ConventionalTextView unusedTv;
    @BindView(R.id.unused_iv)
    View unusedIv;
    @BindView(R.id.unused_ll)
    LinearLayout unusedLl;
    @BindView(R.id.used_tv)
    ConventionalTextView usedTv;
    @BindView(R.id.used_iv)
    View usedIv;
    @BindView(R.id.used_ll)
    LinearLayout usedLl;
    @BindView(R.id.expired_tv)
    ConventionalTextView expiredTv;
    @BindView(R.id.expired_iv)
    View expiredIv;
    @BindView(R.id.expired_ll)
    LinearLayout expiredLl;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private CouponUnusedFragment couponUnusedFragment;
    private CouponUsedFragment couponUsedFragment;
    private CouponExpiredFragment couponExpiredFragment;

    public static void start(Context context) {
        Intent intent = new Intent(context, CouponActivity.class);
        context.startActivity(intent);
    }

    private LinearLayout[] linearLayouts;
    private BaseFragment[] fragments;
    private View[] views;
    private TextView[] textViews;
    private MainPagerAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        ButterKnife.bind(this);
        setHeight(back,title,null);
        initView();
    }

    private void initView() {
        couponUnusedFragment=new CouponUnusedFragment();
        couponUsedFragment=new CouponUsedFragment();
        couponExpiredFragment=new CouponExpiredFragment();
        couponUnusedFragment.setCouponUnusedListener(this);
        couponUsedFragment.setCouponUsedListener(this);
        couponExpiredFragment.setCouponExpiredListener(this);
        fragments = new BaseFragment[3];
        fragments[0] =couponUnusedFragment;
        fragments[1] = couponUsedFragment;
        fragments[2] = couponExpiredFragment;
        linearLayouts = new LinearLayout[3];
        linearLayouts[0] = unusedLl;
        linearLayouts[1] = usedLl;
        linearLayouts[2] = expiredLl;
        views = new View[3];
        views[0] = unusedIv;
        views[1] = usedIv;
        views[2] = expiredIv;
        textViews = new TextView[3];
        textViews[0] = unusedTv;
        textViews[1] = usedTv;
        textViews[2] = expiredTv;
        for (LinearLayout linearLayout : linearLayouts) {
            linearLayout.setOnClickListener(onClickListener);
        }
        viewPager.setOffscreenPageLimit(2);
        adapter = new MainPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(onPageChangeListener);
        viewPager.setCurrentItem(0);
        views[0].setVisibility(View.VISIBLE);
        textViews[0].setSelected(true);
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

    @Override
    public void goLocalTeam() {
        Intent backIntent = new Intent();
        setResult(Activity.RESULT_OK, backIntent);
        finish();
    }

    @Override
    public void returnUsedNumebr(String number) {
        usedTv.setText(getString(R.string.used)+"("+number+")");
    }

    @Override
    public void returnUnusedNumebr(String number) {
        unusedTv.setText(getString(R.string.unused)+"("+number+")");
    }

    @Override
    public void returnExpiredNumebr(String number) {
        expiredTv.setText(getString(R.string.expired)+"("+number+")");
    }
}
