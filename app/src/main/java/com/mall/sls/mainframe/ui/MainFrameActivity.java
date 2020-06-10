package com.mall.sls.mainframe.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.mall.sls.common.unit.MainStartManager;
import com.mall.sls.discount.ui.WholeDiscountFragment;
import com.mall.sls.homepage.ui.HomepageFragment;
import com.mall.sls.local.ui.LocalTeamFragment;
import com.mall.sls.mainframe.adapter.MainPagerAdapter;
import com.mall.sls.mine.ui.MineFragment;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFrameActivity extends BaseActivity implements HomepageFragment.HomepageListener,MineFragment.MineListener{


    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.home_iv)
    ImageView homeIv;
    @BindView(R.id.home_tt)
    TextView homeTt;
    @BindView(R.id.home_rl)
    RelativeLayout homeRl;
    @BindView(R.id.team_iv)
    ImageView teamIv;
    @BindView(R.id.team_tt)
    TextView teamTt;
    @BindView(R.id.team_rl)
    RelativeLayout teamRl;
    @BindView(R.id.discount_iv)
    ImageView discountIv;
    @BindView(R.id.discount_tt)
    TextView discountTt;
    @BindView(R.id.discount_rl)
    RelativeLayout discountRl;
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
        if(Intent.ACTION_VIEW.equals(action)){
            Uri uri = intent.getData();
            if(uri != null){
                String name = uri.getQueryParameter("name");
                String age= uri.getQueryParameter("age");
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(TextUtils.equals(StaticData.REFLASH_ONE, MainStartManager.getMainStart())){
            viewPager.setCurrentItem(0,false);
            MainStartManager.saveMainStart(StaticData.REFLASH_ZERO);
        }
    }

    private void initView() {
        sActivityRef = new WeakReference<>(this);
        homepageFragment=new HomepageFragment();
        mineFragment=new MineFragment();
        homepageFragment.setHomepageListener(this);
        mineFragment.setMineListener(this);
        fragments = new BaseFragment[3];
        fragments[0] = homepageFragment;
        fragments[1] = LocalTeamFragment.newInstance();
//        fragments[2] = WholeDiscountFragment.newInstance();
        fragments[2] = mineFragment;
        relativeLayouts = new RelativeLayout[3];
        relativeLayouts[0] = homeRl;
        relativeLayouts[1] = teamRl;
//        relativeLayouts[2] = discountRl;
        relativeLayouts[2] = mineRl;
        imageViews = new ImageView[3];
        imageViews[0] = homeIv;
        imageViews[1] = teamIv;
//        imageViews[2] = discountIv;
        imageViews[2] = mineIv;
        textViews = new TextView[3];
        textViews[0] = homeTt;
        textViews[1] = teamTt;
//        textViews[2] = discountTt;
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
                    viewPager.setCurrentItem(i,false);
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
        viewPager.setCurrentItem(1,false);
    }
}
