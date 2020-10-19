package com.mall.sls.order;

import com.mall.sls.BasePresenter;
import com.mall.sls.BaseView;
import com.mall.sls.data.entity.AiNongPay;
import com.mall.sls.data.entity.AliPay;
import com.mall.sls.data.entity.BaoFuPay;
import com.mall.sls.data.entity.BaoFuPayInfo;
import com.mall.sls.data.entity.GoodsOrderDetails;
import com.mall.sls.data.entity.InvitationCodeInfo;
import com.mall.sls.data.entity.OrderAddCartInfo;
import com.mall.sls.data.entity.OrderInfo;
import com.mall.sls.data.entity.OrderList;
import com.mall.sls.data.entity.OrderPackageInfo;
import com.mall.sls.data.entity.RefundInfo;
import com.mall.sls.data.entity.WXPaySignResponse;
import com.mall.sls.data.entity.WxPay;
import com.mall.sls.order.ui.ViewLogisticsActivity;

import java.util.List;

/**
 * @author jwc on 2020/5/18.
 * 描述：
 */
public interface OrderContract {
    interface OrderListPresenter extends BasePresenter{
        void getOrderList(String refreshType,String showType);
        void getMoreOrderList(String showType);
        void cancelOrder(String orderId);
        void getInvitationCodeInfo();
        void getWxPay(String orderId, String orderType, String paymentMethod);
        void getAliPay(String orderId, String orderType, String paymentMethod);
        void getBaoFuPay(String orderId, String orderType, String paymentMethod);
        void getAiNongPay(String orderId, String orderType, String paymentMethod);

    }
    interface OrderListView extends BaseView<OrderListPresenter>{
        void renderOrderList(OrderList orderList);
        void renderMoreOrderList(OrderList orderList);
        void renderCancelOrder();
        void renderInvitationCodeInfo(InvitationCodeInfo invitationCodeInfo);
        void renderWxPay(WxPay wxPay);
        void renderAliPay(AliPay aliPay);
        void renderBaoFuPay(BaoFuPay baoFuPay);
        void renderAiNongPay(AiNongPay aiNongPay);
    }

    interface OrderDetailsPresenter extends BasePresenter{
        void getOrderDetails(String orderId);
        void cancelOrder(String orderId);
        void getInvitationCodeInfo();
        void addCartBatch(String orderId);
        void orderAddCart(String orderId, Boolean forceAdd);
        void getWxPay(String orderId, String orderType, String paymentMethod);
        void getAliPay(String orderId, String orderType, String paymentMethod);
        void getBaoFuPay(String orderId, String orderType, String paymentMethod);
        void getAiNongPay(String orderId, String orderType, String paymentMethod);
    }

    interface OrderDetailsView extends BaseView<OrderDetailsPresenter>{
        void renderOrderDetails(GoodsOrderDetails goodsOrderDetails);
        void renderCancelOrder();
        void renderInvitationCodeInfo(InvitationCodeInfo invitationCodeInfo);
        void renderAddCartBatch(Boolean isBoolean);
        void renderOrderAddCart(OrderAddCartInfo orderAddCartInfo);
        void renderWxPay(WxPay wxPay);
        void renderAliPay(AliPay aliPay);
        void renderBaoFuPay(BaoFuPay baoFuPay);
        void renderAiNongPay(AiNongPay aiNongPay);
    }

    interface OrderLogisticsPresenter extends BasePresenter{
        void getOrderLogistics(String id);
    }

    interface OrderLogisticsView extends BaseView<OrderLogisticsPresenter>{
        void renderOrderLogistics(List<OrderPackageInfo> orderPackageInfos);
    }

    interface RefundPresenter extends BasePresenter{
        void getRefundInfo(String refreshType,String orderId);
    }

    interface RefundView extends BaseView<RefundPresenter>{
        void renderRefundInfo(List<RefundInfo> refundInfos);
    }

}
