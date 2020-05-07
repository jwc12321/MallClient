package com.mall.sls.mainframe.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.mall.sls.BaseActivity;
import com.mall.sls.BaseFragment;
import com.mall.sls.R;
import com.mall.sls.homepage.ui.HomepageFragment;
import com.mall.sls.mainframe.adapter.MainPagerAdapter;
import com.mall.sls.mine.ui.MineFragment;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFrameActivity extends BaseActivity{
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.home_iv)
    ImageView homeIv;
    @BindView(R.id.home_tt)
    TextView homeTt;
    @BindView(R.id.home_rl)
    RelativeLayout homeRl;
    @BindView(R.id.share_iv)
    ImageView shareIv;
    @BindView(R.id.share_tt)
    TextView shareTt;
    @BindView(R.id.share_rl)
    RelativeLayout shareRl;
    @BindView(R.id.mine_iv)
    ImageView mineIv;
    @BindView(R.id.mine_tt)
    TextView mineTt;
    @BindView(R.id.mine_rl)
    RelativeLayout mineRl;
    @BindView(R.id.main_rl)
    RelativeLayout mainRl;

    private RelativeLayout[] relativeLayouts;
    private BaseFragment[] fragments;
    private ImageView[] imageViews;
    private TextView[] textViews;
    private MainPagerAdapter adapter;

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

    private void initView() {
        sActivityRef = new WeakReference<>(this);
        fragments = new BaseFragment[3];
        fragments[0] = HomepageFragment.newInstance();
        fragments[1] = MineFragment.newInstance();
        fragments[2] = MineFragment.newInstance();
        relativeLayouts = new RelativeLayout[3];
        relativeLayouts[0] = homeRl;
        relativeLayouts[1] = shareRl;
        relativeLayouts[2] = mineRl;
        imageViews = new ImageView[3];
        imageViews[0] = homeIv;
        imageViews[1] = shareIv;
        imageViews[2] = mineIv;
        textViews = new TextView[3];
        textViews[0] = homeTt;
        textViews[1] = shareTt;
        textViews[2] = mineTt;
        for (RelativeLayout relativeLayout : relativeLayouts) {
            relativeLayout.setOnClickListener(onClickListener);
        }
        viewPager.setOffscreenPageLimit(2);
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
                    viewPager.setCurrentItem(i);
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
}
