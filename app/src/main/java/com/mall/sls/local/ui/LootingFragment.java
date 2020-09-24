package com.mall.sls.local.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.mall.sls.BaseFragment;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.data.entity.LocalTeam;
import com.mall.sls.homepage.adapter.GoodsItemAdapter;
import com.mall.sls.homepage.ui.ActivityGroupGoodsActivity;
import com.mall.sls.homepage.ui.GeneralGoodsDetailsActivity;
import com.mall.sls.homepage.ui.OrdinaryGoodsDetailActivity;
import com.mall.sls.local.DaggerLocalComponent;
import com.mall.sls.local.LocalContract;
import com.mall.sls.local.LocalModule;
import com.mall.sls.local.presenter.RushBuyPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author jwc on 2020/5/11.
 * 描述：正在疯抢
 */
public class LootingFragment extends BaseFragment implements GoodsItemAdapter.OnItemClickListener, LocalContract.RushBuyView {


    @BindView(R.id.record_rv)
    RecyclerView recordRv;
    @BindView(R.id.no_record_ll)
    LinearLayout noRecordLl;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @Inject
    RushBuyPresenter rushBuyPresenter;
    @BindView(R.id.no_record_tv)
    TextView noRecordTv;

    private GoodsItemAdapter goodsItemAdapter;

    public static LootingFragment newInstance() {
        LootingFragment fragment = new LootingFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_looting, container, false);
        ButterKnife.bind(this, rootview);
        return rootview;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        refreshLayout.setOnMultiPurposeListener(simpleMultiPurposeListener);
        initAdapter();
        rushBuyPresenter.getRushBuy(StaticData.REFRESH_ONE);
    }

    private void initAdapter() {
        goodsItemAdapter = new GoodsItemAdapter(getActivity());
        goodsItemAdapter.setOnItemClickListener(this);
        recordRv.setAdapter(goodsItemAdapter);
    }


    SimpleMultiPurposeListener simpleMultiPurposeListener = new SimpleMultiPurposeListener() {
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            refreshLayout.finishRefresh(6000);
            rushBuyPresenter.getRushBuy(StaticData.REFRESH_ZERO);
        }

        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            rushBuyPresenter.getMoreRushBuy();
        }
    };

    @Override
    protected void initializeInjector() {
        DaggerLocalComponent.builder()
                .applicationComponent(getApplicationComponent())
                .localModule(new LocalModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            if (rushBuyPresenter != null) {
                rushBuyPresenter.getRushBuy(StaticData.REFRESH_ONE);
            }
        }
    }

    @Override
    public void renderRushBuy(LocalTeam localTeam) {
        refreshLayout.finishRefresh();
        if (localTeam != null) {
            if (localTeam.getGoodsItemInfos()!= null && localTeam.getGoodsItemInfos().size() > 0) {
                recordRv.setVisibility(View.VISIBLE);
                noRecordLl.setVisibility(View.GONE);
                if (localTeam.getGoodsItemInfos().size() == Integer.parseInt(StaticData.TEN_LIST_SIZE)) {
                    refreshLayout.resetNoMoreData();
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
                goodsItemAdapter.setData(localTeam.getGoodsItemInfos());
            } else {
                recordRv.setVisibility(View.GONE);
                noRecordTv.setText(getString(R.string.no_data));
                noRecordLl.setVisibility(View.VISIBLE);
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
        }
    }

    @Override
    public void renderMoreRushBuy(LocalTeam localTeam) {
        refreshLayout.finishLoadMore();
        if (localTeam != null && localTeam.getGoodsItemInfos() != null) {
            if (localTeam.getGoodsItemInfos().size() != Integer.parseInt(StaticData.TEN_LIST_SIZE)) {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            goodsItemAdapter.addMore(localTeam.getGoodsItemInfos());
        }
    }

    @Override
    public void setPresenter(LocalContract.RushBuyPresenter presenter) {

    }

    @Override
    public void goGoodsDetails(String goodsType, String goodsId) {
        if (TextUtils.equals(StaticData.REFRESH_ONE, goodsType)) {
            GeneralGoodsDetailsActivity.start(getActivity(), goodsId);
        } else if (TextUtils.equals(StaticData.REFRESH_TWO, goodsType)) {
            OrdinaryGoodsDetailActivity.start(getActivity(), goodsId);
        } else {
            ActivityGroupGoodsActivity.start(getActivity(), goodsId);
        }
    }


    public interface LootingListener {
        void lootingChoice(String type);
    }

    private LootingListener lootingListener;

    public void setLootingListener(LootingListener lootingListener) {
        this.lootingListener = lootingListener;
    }

    public void lootingRefresh() {
        if (rushBuyPresenter != null) {
            rushBuyPresenter.getRushBuy(StaticData.REFRESH_ONE);
        }
    }
}
