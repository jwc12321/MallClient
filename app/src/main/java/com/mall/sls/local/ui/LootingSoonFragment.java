package com.mall.sls.local.ui;

import android.os.Bundle;
import android.text.TextUtils;
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
import com.mall.sls.common.ErrorCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.data.RemoteDataException;
import com.mall.sls.data.entity.GoodsItemInfo;
import com.mall.sls.data.entity.LocalTeam;
import com.mall.sls.homepage.ui.ActivityGoodsDetailActivity;
import com.mall.sls.local.DaggerLocalComponent;
import com.mall.sls.local.LocalContract;
import com.mall.sls.local.LocalModule;
import com.mall.sls.local.adapter.LootingSoonAdapter;
import com.mall.sls.local.presenter.LocalTeamPresenter;
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
public class LootingSoonFragment extends BaseFragment implements LootingSoonAdapter.OnItemClickListener, LocalContract.LocalTeamView {


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
    LocalTeamPresenter localTeamPresenter;

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
            localTeamPresenter.getLocalTeam(StaticData.REFLASH_ZERO, StaticData.REFLASH_TWO);
        }

        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            localTeamPresenter.getMoreLocalTeam(StaticData.REFLASH_TWO);
        }
    };

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            if (localTeamPresenter != null) {
                localTeamPresenter.getLocalTeam(StaticData.REFLASH_ONE, StaticData.REFLASH_TWO);
            }
            if (lootingSoonListener != null) {
                lootingSoonListener.lootingSoonChoice("1");
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
    public void renderLocalTeam(LocalTeam localTeam) {
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
    public void renderMoreLocalTeam(LocalTeam localTeam) {
        refreshLayout.finishLoadMore();
        if (localTeam != null && localTeam.getGoodsItemInfos() != null) {
            if (localTeam.getGoodsItemInfos().size() != Integer.parseInt(StaticData.TEN_LIST_SIZE)) {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            lootingSoonAdapter.addMore(localTeam.getGoodsItemInfos());
        }
    }

    @Override
    public void renderError(Throwable throwable) {
        if (throwable instanceof RemoteDataException) {
            if (TextUtils.equals(ErrorCodeStatic.CODE_ONE_ZERO_ZERO_ONE_ZERO, ((RemoteDataException) throwable).getRetCode())) {
                recordRv.setVisibility(View.GONE);
                noRecordLl.setVisibility(View.VISIBLE);
                refreshLayout.finishLoadMoreWithNoMoreData();
                noRecordTv.setText(getString(R.string.not_in_opening_area));
            }
        }
    }

    @Override
    public void renderGroupRemind() {
        showMessage(getString(R.string.remind_to_you));
        remindIv.setEnabled(false);
    }

    @Override
    public void setPresenter(LocalContract.LocalTeamPresenter presenter) {

    }

    @Override
    public void goActivityGoodsDetail(String goodsId) {
        ActivityGoodsDetailActivity.start(getActivity(), goodsId);
    }

    @Override
    public void remind(ImageView remindIv, int position) {
        this.remindIv = remindIv;
        goodsItemInfo = goodsItemInfos.get(position);
        localTeamPresenter.groupRemind(goodsItemInfo.getGrouponRulesId());
    }

    public interface LootingSoonListener {
        void lootingSoonChoice(String type);
    }

    private LootingSoonListener lootingSoonListener;

    public void setLootingSoonListener(LootingSoonListener lootingSoonListener) {
        this.lootingSoonListener = lootingSoonListener;
    }

    public void lootingSoonRefresh() {
        if (localTeamPresenter != null) {
            localTeamPresenter.getLocalTeam(StaticData.REFLASH_ONE, StaticData.REFLASH_TWO);
        }
    }
}
