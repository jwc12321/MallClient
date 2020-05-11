package com.mall.sls.order.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.BaseFragment;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.data.entity.GoodsOrderInfo;
import com.mall.sls.order.adapter.GoodsOrderAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author jwc on 2020/5/11.
 * 描述：待付款
 */
public class PendingPaymentFragment extends BaseFragment implements GoodsOrderAdapter.OnItemClickListener {

    @BindView(R.id.record_rv)
    RecyclerView recordRv;
    @BindView(R.id.no_address_ll)
    LinearLayout noAddressLl;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private String choiceType;

    private GoodsOrderAdapter goodsOrderAdapter;

    public static PendingPaymentFragment newInstance(String choiceType) {
        PendingPaymentFragment fragment = new PendingPaymentFragment();
        Bundle args = new Bundle();
        args.putString(StaticData.CHOICE_TYPE, choiceType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            choiceType = getArguments().getString(StaticData.CHOICE_TYPE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_goods_order, container, false);
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
        addAdapter();
    }

    private void addAdapter() {
        goodsOrderAdapter = new GoodsOrderAdapter(getActivity());
        goodsOrderAdapter.setOnItemClickListener(this);
        recordRv.setAdapter(goodsOrderAdapter);
        if (TextUtils.equals("1", choiceType)) {
            addData();
        }

    }

    private void addData() {
        List<GoodsOrderInfo> goodsOrderInfos = new ArrayList<>();
        GoodsOrderInfo goodsOrderInfo = new GoodsOrderInfo("16", "10", "1", "苹果");
        GoodsOrderInfo goodsOrderInfo1 = new GoodsOrderInfo("26", "10", "3", "香蕉");
        GoodsOrderInfo goodsOrderInfo2 = new GoodsOrderInfo("36", "10", "4", "橘子");
        GoodsOrderInfo goodsOrderInfo3 = new GoodsOrderInfo("46", "10", "4", "橘子");
        goodsOrderInfos.add(goodsOrderInfo);
        goodsOrderInfos.add(goodsOrderInfo1);
        goodsOrderInfos.add(goodsOrderInfo2);
        goodsOrderInfos.add(goodsOrderInfo3);
        goodsOrderAdapter.setData(goodsOrderInfos);
    }

    SimpleMultiPurposeListener simpleMultiPurposeListener = new SimpleMultiPurposeListener() {
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        }

        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        }
    };

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()&&goodsOrderAdapter!=null) {
            addData();
        }
    }


    @Override
    public void payOrder(String id, String amount) {

    }

    @Override
    public void confirmOrder(String id) {

    }

    @Override
    public void goOrderDetail(String id) {

    }
}
