package com.mall.sls.order.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.mall.sls.common.RequestCodeStatic;
import com.mall.sls.common.StaticData;
import com.mall.sls.common.unit.BriefUnit;
import com.mall.sls.common.unit.PayTypeInstalledUtils;
import com.mall.sls.common.unit.QRCodeFileUtils;
import com.mall.sls.common.unit.WXShareManager;
import com.mall.sls.data.entity.GoodsOrderInfo;
import com.mall.sls.data.entity.InvitationCodeInfo;
import com.mall.sls.data.entity.OrderGoodsVo;
import com.mall.sls.data.entity.OrderList;
import com.mall.sls.data.entity.WXPaySignResponse;
import com.mall.sls.homepage.ui.SelectPayTypeActivity;
import com.mall.sls.mine.ui.SelectShareTypeActivity;
import com.mall.sls.order.DaggerOrderComponent;
import com.mall.sls.order.OrderContract;
import com.mall.sls.order.OrderModule;
import com.mall.sls.order.adapter.GoodsOrderAdapter;
import com.mall.sls.order.presenter.OrderListPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author jwc on 2020/5/11.
 * 描述：待分享
 */
public class PendingShareFragment extends BaseFragment implements OrderContract.OrderListView,GoodsOrderAdapter.OnItemClickListener {

    @BindView(R.id.record_rv)
    RecyclerView recordRv;
    @BindView(R.id.no_record_ll)
    LinearLayout noRecordLl;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private String choiceType;

    private Boolean isActivity;
    private String goodsId;
    private String grouponId;
    private String goodsProductId;
    private String wxUrl;
    private String inviteCode;
    private WXShareManager wxShareManager;
    private Bitmap shareBitMap;
    private String nameText;
    private String briefText;
    private List<OrderGoodsVo> orderGoodsVos;
    private String orderTotalPrice;
    private String backType;

    private GoodsOrderAdapter goodsOrderAdapter;
    @Inject
    OrderListPresenter orderListPresenter;

    public static PendingShareFragment newInstance(String choiceType) {
        PendingShareFragment fragment = new PendingShareFragment();
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
        wxShareManager = WXShareManager.getInstance(getActivity());
        addAdapter();
        if(TextUtils.equals(StaticData.REFLASH_TWO,choiceType)) {
            orderListPresenter.getInvitationCodeInfo();
            orderListPresenter.getOrderList(StaticData.REFLASH_ONE,StaticData.REFLASH_TWO);
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
            orderListPresenter.getOrderList(StaticData.REFLASH_ZERO,StaticData.REFLASH_TWO);
        }

        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            orderListPresenter.getMoreOrderList(StaticData.REFLASH_TWO);
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
            orderListPresenter.getInvitationCodeInfo();
            orderListPresenter.getOrderList(StaticData.REFLASH_ONE,StaticData.REFLASH_TWO);
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
        if (goodsOrderInfo != null) {
            this.isActivity = goodsOrderInfo.getActivity();
            this.goodsId = goodsOrderInfo.getId();
            this.grouponId = goodsOrderInfo.getGrouponLinkId();
            orderGoodsVos = goodsOrderInfo.getOrderGoodsVos();
            if (orderGoodsVos != null && orderGoodsVos.size() > 0) {
                this.goodsProductId = orderGoodsVos.get(0).getProductId();
                nameText = BriefUnit.returnName(orderGoodsVos.get(0).getPrice(), orderGoodsVos.get(0).getGoodsName());
                briefText = BriefUnit.returnBrief(orderGoodsVos.get(0).getBrief());
            }
            orderTotalPrice = goodsOrderInfo.getActualPrice();
            shareBitMap = QRCodeFileUtils.createBitmap3(shareIv, shareIv.getWidth(), shareIv.getWidth());
            if (!PayTypeInstalledUtils.isWeixinAvilible(getActivity())) {
                showMessage(getString(R.string.install_weixin));
                return;
            }
            Intent intent = new Intent(getActivity(), SelectShareTypeActivity.class);
            startActivityForResult(intent, RequestCodeStatic.SELECT_SHARE_TYPE);
        }
    }

    private void shareActivityWx(boolean isFriend) {
//        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.app_icon);
        String url = wxUrl + "activity/" + goodsId + StaticData.WX_INVITE_CODE + inviteCode;
        wxShareManager.shareUrlToWX(isFriend, url, shareBitMap, nameText, briefText);
    }

    private void shareGroupWx(boolean isFriend) {
//        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.mipmap.app_icon);
        String url = wxUrl + "group/" + grouponId + "/" + goodsProductId + StaticData.WX_INVITE_CODE + inviteCode;
        wxShareManager.shareUrlToWX(isFriend, url, shareBitMap, nameText, briefText);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodeStatic.SELECT_SHARE_TYPE:
                    if (data != null) {
                        backType = data.getStringExtra(StaticData.BACK_TYPE);
                        if (!isActivity) {
                            shareGroupWx(TextUtils.equals(StaticData.REFLASH_ONE, backType));
                        } else {
                            shareActivityWx(TextUtils.equals(StaticData.REFLASH_ONE, backType));
                        }
                    }
                    break;
                default:
            }
        }
    }

    @Override
    public void renderOrderAliPay(String alipayStr) {

    }

    @Override
    public void renderOrderWxPay(WXPaySignResponse wxPaySignResponse) {

    }

    @Override
    public void renderCancelOrder() {

    }

    @Override
    public void renderInvitationCodeInfo(InvitationCodeInfo invitationCodeInfo) {
        if (invitationCodeInfo != null) {
            wxUrl = invitationCodeInfo.getBaseUrl();
            inviteCode = invitationCodeInfo.getInvitationCode();
        }
    }

    @Override
    public void setPresenter(OrderContract.OrderListPresenter presenter) {

    }

    //分享成功
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShareSuccess(String code) {
        if (getUserVisibleHint()) {
            showMessage(getString(R.string.share_success));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
