package com.mall.sls.local.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.BaseFragment;
import com.mall.sls.R;
import com.mall.sls.data.entity.GoodsItemInfo;
import com.mall.sls.homepage.adapter.GoodsItemAdapter;
import com.mall.sls.local.adapter.LootingSoonAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author jwc on 2020/5/11.
 * 描述：正在疯抢
 */
public class LootingSoonFragment extends BaseFragment implements LootingSoonAdapter.OnItemClickListener{


    @BindView(R.id.record_rv)
    RecyclerView recordRv;
    @BindView(R.id.no_address_ll)
    LinearLayout noAddressLl;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private LootingSoonAdapter lootingSoonAdapter;

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

    private void initAdapter(){
        lootingSoonAdapter=new LootingSoonAdapter(getActivity());
        lootingSoonAdapter.setOnItemClickListener(this);
        recordRv.setAdapter(lootingSoonAdapter);
        List<GoodsItemInfo> goodsItemInfos=new ArrayList<>();
        GoodsItemInfo goodsItemInfo=new GoodsItemInfo("苹果","哈想吃","12","14");
        GoodsItemInfo goodsItemInfo1=new GoodsItemInfo("香蕉","房价肯定就发的","15","22");
        GoodsItemInfo goodsItemInfo2=new GoodsItemInfo("橘子","开发了都JFK的肌肤","18","66");
        goodsItemInfos.add(goodsItemInfo);
        goodsItemInfos.add(goodsItemInfo1);
        goodsItemInfos.add(goodsItemInfo2);
        lootingSoonAdapter.setData(goodsItemInfos);
    }

    SimpleMultiPurposeListener simpleMultiPurposeListener = new SimpleMultiPurposeListener() {
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            refreshLayout.finishRefresh(6000);
        }

        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        }
    };

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            if (lootingSoonListener != null) {
                lootingSoonListener.lootingSoonChoice("1");
            }
        }
    }


    @Override
    public void goOrdinaryGoods() {

    }

    public interface LootingSoonListener {
        void lootingSoonChoice(String type);
    }

    private LootingSoonListener lootingSoonListener;

    public void setLootingSoonListener(LootingSoonListener lootingSoonListener) {
        this.lootingSoonListener = lootingSoonListener;
    }

    public void lootingSoonRefresh() {

    }
}
