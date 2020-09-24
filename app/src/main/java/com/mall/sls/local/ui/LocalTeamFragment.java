package com.mall.sls.local.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.mall.sls.BaseFragment;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.SpikeManager;
import com.mall.sls.common.unit.TCAgentUnit;
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
public class LocalTeamFragment extends BaseFragment implements LootingFragment.LootingListener, LootingSoonFragment.LootingSoonListener {


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
    private BaseFragment[] fragments;
    private LinearLayout[] linearLayouts;
    private TextView[] textViews;
    private View[] views;
    private MainPagerAdapter adapter;
    private LootingFragment lootingFragment;
    private LootingSoonFragment lootingSoonFragment;
    private String type = "0";

    public static LocalTeamFragment newInstance() {
        LocalTeamFragment fragment = new LocalTeamFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_local_team, container, false);
        ButterKnife.bind(this, rootview);
        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHeight(null,title,null);
        initView();
    }


    private void initView() {
        lootingFragment = new LootingFragment();
        lootingSoonFragment = new LootingSoonFragment();
        lootingFragment.setLootingListener(this);
        lootingSoonFragment.setLootingSoonListener(this);
        fragments = new BaseFragment[2];
        fragments[0] = lootingFragment;
        fragments[1] = lootingSoonFragment;
        linearLayouts = new LinearLayout[2];
        linearLayouts[0] = lootingLl;
        linearLayouts[1]=soonLl;
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
        adapter = new MainPagerAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(onPageChangeListener);
//        viewPager.setCurrentItem(0);
        textViews[0].setSelected(true);
        lootingIv.setVisibility(View.VISIBLE);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < linearLayouts.length; i++) {
                if (v == linearLayouts[i]) {
                    viewPager.setCurrentItem(i,false);
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
                views[i].setVisibility(position==i?View.VISIBLE:View.INVISIBLE);
                if(position==i){
                    TextViewttf.setTextMediumBlack(textViews[i]);
                }else {
                    TextViewttf.setTextConventional(textViews[i]);
                }
            }
        }
    };


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            if(TextUtils.equals(StaticData.REFRESH_ONE, SpikeManager.getSpike())){
                viewPager.setCurrentItem(1,false);
                SpikeManager.saveSpike(StaticData.REFRESH_ZERO);
            }else {
                if (TextUtils.equals(StaticData.REFRESH_ZERO, type)) {
                    lootingFragment.lootingRefresh();
                } else if (TextUtils.equals(StaticData.REFRESH_ONE, type)) {
                    lootingSoonFragment.lootingSoonRefresh();
                }
            }
        }
        if (getUserVisibleHint()&&getActivity()!=null&&!getActivity().isDestroyed()) {
            TCAgentUnit.pageStart(getActivity(), getString(R.string.fight_together));
        } else if(getActivity()!=null&&!getActivity().isDestroyed()){
            TCAgentUnit.pageEnd(getActivity(), getString(R.string.fight_together));
        }
    }

    @Override
    public void lootingChoice(String type) {
        this.type=type;
    }

    @Override
    public void lootingSoonChoice(String type) {
        this.type=type;
    }


}
