package com.mall.sls.order.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.mall.sls.BaseFragment;
import com.mall.sls.R;
import com.mall.sls.common.StaticData;
import com.mall.sls.data.entity.AiNongPay;
import com.mall.sls.data.entity.AliPay;
import com.mall.sls.data.entity.BaoFuPay;
import com.mall.sls.data.entity.BaoFuPayInfo;
import com.mall.sls.data.entity.GoodsOrderInfo;
import com.mall.sls.data.entity.InvitationCodeInfo;
import com.mall.sls.data.entity.OrderList;
import com.mall.sls.data.entity.WXPaySignResponse;
import com.mall.sls.data.entity.WxPay;
import com.mall.sls.order.DaggerOrderComponent;
import com.mall.sls.order.OrderContract;
import com.mall.sls.order.OrderModule;
import com.mall.sls.order.adapter.GoodsOrderAdapter;
import com.mall.sls.order.presenter.OrderListPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author jwc on 2020/5/11.
 * 描述：待发货
 */
public class PendingDeliveryFragment extends BaseFragment implements OrderContract.OrderListView,GoodsOrderAdapter.OnItemClickListener {

    @BindView(R.id.record_rv)
    RecyclerView recordRv;
    @BindView(R.id.no_record_ll)
    LinearLayout noRecordLl;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private String choiceType;
    private String showType;

    private GoodsOrderAdapter goodsOrderAdapter;
    @Inject
    OrderListPresenter orderListPresenter;

    public static PendingDeliveryFragment newInstance(String choiceType) {
        PendingDeliveryFragment fragment = new PendingDeliveryFragment();
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
        showType=StaticData.REFRESH_THREE;
        addAdapter();
        if(TextUtils.equals(StaticData.REFRESH_THREE,choiceType)) {
            orderListPresenter.getOrderList(StaticData.REFRESH_ONE,showType);
        }
    }

    private void addAdapter() {
        goodsOrderAdapter = new GoodsOrderAdapter(getActivity());
        goodsOrderAdapter.setOnItemClickListener(this);
        recordRv.setAdapter(goodsOrderAdapter);

    }


    SimpleMultiPurposeListener simpleMultiPurposeListener = new SimpleMultiPurposeListener() {
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            refreshLayout.finishRefresh(6000);
            orderListPresenter.getOrderList(StaticData.REFRESH_ZERO,showType);
        }

        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            orderListPresenter.getMoreOrderList(showType);
        }
    };

    @Override
    protected void initializeInjector() {
        DaggerOrderComponent.builder()
                .applicationComponent(getApplicationComponent())
                .orderModule(new OrderModule(this))
                .build()
                .inject(this);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()&&orderListPresenter!=null) {
            orderListPresenter.getOrderList(StaticData.REFRESH_ONE,showType);
        }
    }


    @Override
    public void cancelOrder(String id) {

    }

    @Override
    public void payOrder(String id, String amount) {

    }

    @Override
    public void confirmOrder(String id) {

    }

    @Override
    public void goOrderDetail(String id) {
        GoodsOrderDetailsActivity.start(getActivity(),id);
    }

    @Override
    public void wxShare(GoodsOrderInfo goodsOrderInfo, ImageView shareIv) {

    }


    @Override
    public void renderOrderList(OrderList orderList) {
        refreshLayout.finishRefresh();
        if (orderList != null) {
            if (orderList.getGoodsOrderInfos()!= null && orderList.getGoodsOrderInfos().size() > 0) {
                recordRv.setVisibility(View.VISIBLE);
                noRecordLl.setVisibility(View.GONE);
                if (orderList.getGoodsOrderInfos().size() == Integer.parseInt(StaticData.TEN_LIST_SIZE)) {
                    refreshLayout.resetNoMoreData();
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
                goodsOrderAdapter.setData(orderList.getGoodsOrderInfos());
            } else {
                recordRv.setVisibility(View.GONE);
                noRecordLl.setVisibility(View.VISIBLE);
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
        }
    }

    @Override
    public void renderMoreOrderList(OrderList orderList) {
        refreshLayout.finishLoadMore();
        if (orderList != null && orderList.getGoodsOrderInfos() != null) {
            if (orderList.getGoodsOrderInfos().size() != Integer.parseInt(StaticData.TEN_LIST_SIZE)) {
                refreshLayout.finishLoadMoreWithNoMoreData();
            }
            goodsOrderAdapter.addMore(orderList.getGoodsOrderInfos());
        }
    }

    @Override
    public void renderCancelOrder() {

    }

    @Override
    public void renderInvitationCodeInfo(InvitationCodeInfo invitationCodeInfo) {

    }

    @Override
    public void renderWxPay(WxPay wxPay) {
    }

    @Override
    public void renderAliPay(AliPay aliPay) {
    }


    @Override
    public void renderBaoFuPay(BaoFuPay baoFuPay) {

    }

    @Override
    public void renderAiNongPay(AiNongPay aiNongPay) {

    }

    @Override
    public void setPresenter(OrderContract.OrderListPresenter presenter) {

    }
}
