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
import com.mall.sls.homepage.adapter.GoodsItemAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author jwc on 2020/5/11.
 * 描述：正在疯抢
 */
public class LootingFragment extends BaseFragment implements GoodsItemAdapter.OnItemClickListener{


    @BindView(R.id.record_rv)
    RecyclerView recordRv;
    @BindView(R.id.no_address_ll)
    LinearLayout noAddressLl;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

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
    }

    private void initAdapter(){
        goodsItemAdapter=new GoodsItemAdapter(getActivity());
        goodsItemAdapter.setOnItemClickListener(this);
        recordRv.setAdapter(goodsItemAdapter);
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
            if (lootingListener != null) {
                lootingListener.lootingChoice("0");
            }
        }
    }

    @Override
    public void goOrdinaryGoodsDetails(String goodsId) {

    }


    public interface LootingListener {
        void lootingChoice(String type);
    }

    private LootingListener lootingListener;

    public void setLootingListener(LootingListener lootingListener) {
        this.lootingListener = lootingListener;
    }

    public void lootingRefresh() {

    }
}
