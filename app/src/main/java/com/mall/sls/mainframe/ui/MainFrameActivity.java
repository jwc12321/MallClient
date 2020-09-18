package com.mall.sls.mainframe.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.mall.sls.cart.ui.CartFragment;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.MainStartManager;
import com.mall.sls.common.widget.viewpage.ViewPagerSlide;
import com.mall.sls.homepage.ui.HomepageFragment;
import com.mall.sls.local.ui.LocalTeamFragment;
import com.mall.sls.mainframe.adapter.MainPagerAdapter;
import com.mall.sls.mine.ui.MineFragment;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFrameActivity extends BaseActivity implements HomepageFragment.HomepageListener, MineFragment.MineListener, CartFragment.CartListener {


    @BindView(R.id.viewPager)
    ViewPagerSlide viewPager;
    @BindView(R.id.home_iv)
    ImageView homeIv;
    @BindView(R.id.home_tt)
    TextView homeTt;
    @BindView(R.id.home_rl)
    RelativeLayout homeRl;
    @BindView(R.id.sort_iv)
    ImageView sortIv;
    @BindView(R.id.sort_tt)
    TextView sortTt;
    @BindView(R.id.sort_rl)
    RelativeLayout sortRl;
    @BindView(R.id.cart_iv)
    ImageView cartIv;
    @BindView(R.id.cart_tt)
    TextView cartTt;
    @BindView(R.id.cart_rl)
    RelativeLayout cartRl;
    @BindView(R.id.mine_iv)
    ImageView mineIv;
    @BindView(R.id.mine_tt)
    TextView mineTt;
    @BindView(R.id.mine_rl)
    RelativeLayout mineRl;
    @BindView(R.id.bottom_ll)
    LinearLayout bottomLl;
    @BindView(R.id.main_rl)
    RelativeLayout mainRl;
    private RelativeLayout[] relativeLayouts;
    private BaseFragment[] fragments;
    private ImageView[] imageViews;
    private TextView[] textViews;
    private MainPagerAdapter adapter;

    private HomepageFragment homepageFragment;
    private MineFragment mineFragment;
    private CartFragment cartFragment;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainFrameActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainfram);
        ButterKnife.bind(this);
        initView();
    }

    private void initData() {
        Intent intent = getIntent();
        String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            Uri uri = intent.getData();
            if (uri != null) {
                String name = uri.getQueryParameter("name");
                String age = uri.getQueryParameter("age");
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (TextUtils.equals(StaticData.REFLASH_ONE, MainStartManager.getMainStart())) {
            viewPager.setCurrentItem(0, false);
            MainStartManager.saveMainStart(StaticData.REFLASH_ZERO);
        } else if (TextUtils.equals(StaticData.REFLASH_THREE, MainStartManager.getMainStart())) {
            viewPager.setCurrentItem(2, false);
            MainStartManager.saveMainStart(StaticData.REFLASH_ZERO);
        }
    }

    private void initView() {
        sActivityRef = new WeakReference<>(this);
        homepageFragment = new HomepageFragment();
        cartFragment = new CartFragment();
        mineFragment = new MineFragment();
        homepageFragment.setHomepageListener(this);
        cartFragment.setCartListener(this);
        mineFragment.setMineListener(this);
        fragments = new BaseFragment[4];
        fragments[0] = homepageFragment;
        fragments[1] = LocalTeamFragment.newInstance();
        fragments[2] = cartFragment;
        fragments[3] = mineFragment;
        relativeLayouts = new RelativeLayout[4];
        relativeLayouts[0] = homeRl;
        relativeLayouts[1] = sortRl;
        relativeLayouts[2] = cartRl;
        relativeLayouts[3] = mineRl;
        imageViews = new ImageView[4];
        imageViews[0] = homeIv;
        imageViews[1] = sortIv;
        imageViews[2] = cartIv;
        imageViews[3] = mineIv;
        textViews = new TextView[4];
        textViews[0] = homeTt;
        textViews[1] = sortTt;
        textViews[2] = cartTt;
        textViews[3] = mineTt;
        for (RelativeLayout relativeLayout : relativeLayouts) {
            relativeLayout.setOnClickListener(onClickListener);
        }
        viewPager.setOffscreenPageLimit(3);
        adapter = new MainPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(onPageChangeListener);
        viewPager.setCurrentItem(0);
        imageViews[0].setSelected(true);
        textViews[0].setSelected(true);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < relativeLayouts.length; i++) {
                if (v == relativeLayouts[i]) {
                    viewPager.setCurrentItem(i, false);
                    break;
                }
            }
        }
    };

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[i].setSelected(position == i);
                textViews[i].setSelected(position == i);
            }
        }
    };

    private static WeakReference<MainFrameActivity> sActivityRef;

    public static void finishActivity() {
        if (sActivityRef != null && sActivityRef.get() != null) {
            sActivityRef.get().finish();
        }

    }

    @Override
    public View getSnackBarHolderView() {
        return mainRl;
    }

    @Override
    public void goLocalTeam() {
        viewPager.setCurrentItem(1, false);
    }

    @Override
    public void goHomePage() {
        viewPager.setCurrentItem(0, false);
    }
}
