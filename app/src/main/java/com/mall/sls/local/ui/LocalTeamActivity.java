package com.mall.sls.local.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.mall.sls.common.unit.TextViewttf;
import com.mall.sls.common.widget.textview.MediumThickTextView;
import com.mall.sls.mainframe.adapter.MainPagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author jwc on 2020/5/9.
 * 描述：本地拼团
 */
public class LocalTeamActivity extends BaseActivity {


    @BindView(R.id.title)
    MediumThickTextView title;
    @BindView(R.id.title_rel)
    RelativeLayout titleRel;
    @BindView(R.id.looting_tv)
    TextView lootingTv;
    @BindView(R.id.looting_iv)
    View lootingIv;
    @BindView(R.id.looting_ll)
    LinearLayout lootingLl;
    @BindView(R.id.soon_tv)
    TextView soonTv;
    @BindView(R.id.soon_iv)
    View soonIv;
    @BindView(R.id.soon_ll)
    LinearLayout soonLl;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.back)
    ImageView back;
    private BaseFragment[] fragments;
    private LinearLayout[] linearLayouts;
    private TextView[] textViews;
    private View[] views;
    private MainPagerAdapter adapter;
    private LootingFragment lootingFragment;
    private LootingSoonFragment lootingSoonFragment;

    public static void start(Context context) {
        Intent intent = new Intent(context, LocalTeamActivity.class);
        context.startActivity(intent);
    }


    @Override
    public View getSnackBarHolderView() {
        return null;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_team);
        ButterKnife.bind(this);
        setHeight(back, title, null);
        initView();
    }

    private void initView() {
        lootingFragment = new LootingFragment();
        lootingSoonFragment = new LootingSoonFragment();
        fragments = new BaseFragment[2];
        fragments[0] = lootingFragment;
        fragments[1] = lootingSoonFragment;
        linearLayouts = new LinearLayout[2];
        linearLayouts[0] = lootingLl;
        linearLayouts[1] = soonLl;
        textViews = new TextView[2];
        textViews[0] = lootingTv;
        textViews[1] = soonTv;
        views = new View[2];
        views[0] = lootingIv;
        views[1] = soonIv;
        for (LinearLayout linearLayout : linearLayouts) {
            linearLayout.setOnClickListener(onClickListener);
        }
        viewPager.setOffscreenPageLimit(1);
        adapter = new MainPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(onPageChangeListener);
        textViews[0].setSelected(true);
        lootingIv.setVisibility(View.VISIBLE);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < linearLayouts.length; i++) {
                if (v == linearLayouts[i]) {
                    viewPager.setCurrentItem(i, false);
                    break;
                }
            }
        }
    };

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < textViews.length; i++) {
                textViews[i].setSelected(position == i);
                views[i].setVisibility(position == i ? View.VISIBLE : View.INVISIBLE);
                if (position == i) {
                    TextViewttf.setTextMediumBlack(textViews[i]);
                } else {
                    TextViewttf.setTextConventional(textViews[i]);
                }
            }
        }
    };


    @OnClick({R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            default:
        }
    }


}
