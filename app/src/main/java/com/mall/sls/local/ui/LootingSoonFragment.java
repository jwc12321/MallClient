package com.mall.sls.local.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.mall.sls.BaseFragment;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.TCAgentUnit;
import com.mall.sls.data.entity.GoodsItemInfo;
import com.mall.sls.data.entity.LocalTeam;
import com.mall.sls.homepage.ui.ActivityGoodsDetailActivity;
import com.mall.sls.local.DaggerLocalComponent;
import com.mall.sls.local.LocalContract;
import com.mall.sls.local.LocalModule;
import com.mall.sls.local.adapter.LootingSoonAdapter;
import com.mall.sls.local.presenter.WaitBuyPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author jwc on 2020/5/11.
 * 描述：正在疯抢
 */
public class LootingSoonFragment extends BaseFragment implements LootingSoonAdapter.OnItemClickListener, LocalContract.WaitBuyView {


    @BindView(R.id.record_rv)
    RecyclerView recordRv;
    @BindView(R.id.no_record_ll)
    LinearLayout noRecordLl;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.no_record_tv)
    TextView noRecordTv;

    private LootingSoonAdapter lootingSoonAdapter;
    private List<GoodsItemInfo> goodsItemInfos;
    private GoodsItemInfo goodsItemInfo;
    private ImageView remindIv;

    @Inject
    WaitBuyPresenter waitBuyPresenter;

    public static LootingSoonFragment newInstance() {
        LootingSoonFragment fragment = new LootingSoonFragment();
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
    }

    private void initAdapter() {
        lootingSoonAdapter = new LootingSoonAdapter(getActivity());
        lootingSoonAdapter.setOnItemClickListener(this);
        recordRv.setAdapter(lootingSoonAdapter);
    }

    SimpleMultiPurposeListener simpleMultiPurposeListener = new SimpleMultiPurposeListener() {
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            refreshLayout.finishRefresh(6000);
            waitBuyPresenter.getWaitBuy(StaticData.REFRESH_ZERO);
        }

        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            waitBuyPresenter.getMoreWaitBuy();
        }
    };

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            if (waitBuyPresenter != null) {
                waitBuyPresenter.getWaitBuy(StaticData.REFRESH_ONE);
            }
        }
    }

    @Override
    protected void initializeInjector() {
        DaggerLocalComponent.builder()
                .applicationComponent(getApplicationComponent())
                .localModule(new LocalModule(this))
                .build()
                .inject(this);
    }


    @Override
    public void goActivityGoodsDetail(String goodsId) {
        TCAgentUnit.setEventId(getActivity(),getString(R.string.fight_together_goods));
        ActivityGoodsDetailActivity.start(getActivity(), goodsId);
    }

    @Override
    public void remind(ImageView remindIv, int position) {
        this.remindIv = remindIv;
        goodsItemInfo = goodsItemInfos.get(position);
    }

    @Override
    public void renderWaitBuy(LocalTeam localTeam) {
        refreshLayout.finishRefresh();
        if (localTeam != null) {
            if (localTeam.getGoodsItemInfos()!= null && localTeam.getGoodsItemInfos().size() > 0) {
                this.goodsItemInfos = localTeam.getGoodsItemInfos();
                recordRv.setVisibility(View.VISIBLE);
                noRecordLl.setVisibility(View.GONE);
                if (localTeam.getGoodsItemInfos().size() == Integer.parseInt(StaticData.TEN_LIST_SIZE)) {
                    refreshLayout.resetNoMoreData();
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
                lootingSoonAdapter.setData(localTeam.getGoodsItemInfos());
            } else {
                recordRv.setVisibility(View.GONE);
                noRecordLl.setVisibility(View.VISIBLE);
                noRecordTv.setText(getString(R.string.no_data));
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
        }
    }

    @Override
    public void renderMoreWaitBuy(LocalTeam localTeam) {
        refreshLayout.finishLoadMore();
        if (localTeam != null && localTeam.getGoodsItemInfos() != null) {
            if (localTeam.getGoodsItemInfos().size() != Integer.parseInt(StaticData.TEN_LIST_SIZE)) {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            lootingSoonAdapter.addMore(localTeam.getGoodsItemInfos());
        }
    }

    @Override
    public void setPresenter(LocalContract.WaitBuyPresenter presenter) {

    }

    public interface LootingSoonListener {
        void lootingSoonChoice(String type);
    }

    private LootingSoonListener lootingSoonListener;

    public void setLootingSoonListener(LootingSoonListener lootingSoonListener) {
        this.lootingSoonListener = lootingSoonListener;
    }

    public void lootingSoonRefresh() {
        if (waitBuyPresenter != null) {
            waitBuyPresenter.getWaitBuy(StaticData.REFRESH_ONE);
        }
    }
}
