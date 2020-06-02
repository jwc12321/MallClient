package com.mall.sls.order;

import com.mall.sls.BasePresenter;
import com.mall.sls.BaseView;
import com.mall.sls.data.entity.GoodsOrderDetails;
import com.mall.sls.data.entity.OrderInfo;
import com.mall.sls.data.entity.OrderList;
import com.mall.sls.data.entity.WXPaySignResponse;

/**
 * @author jwc on 2020/5/18.
 * 描述：
 */
public interface OrderContract {
    interface OrderListPresenter extends BasePresenter{
        void getOrderList(String refreshType,String showType);
        void getMoreOrderList(String showType);
        void orderAliPay(String orderId,String type);
        void orderWxPay(String orderId,String type);
    }
    interface OrderListView extends BaseView<OrderListPresenter>{
        void renderOrderList(OrderList orderList);
        void renderMoreOrderList(OrderList orderList);
        void renderOrderAliPay(String alipayStr);
        void renderOrderWxPay(WXPaySignResponse wxPaySignResponse);
    }

    interface OrderDetailsPresenter extends BasePresenter{
        void getOrderDetails(String orderId);
        void orderAliPay(String orderId,String type);
        void orderWxPay(String orderId,String type);
    }

    interface OrderDetailsView extends BaseView<OrderDetailsPresenter>{
        void renderOrderDetails(GoodsOrderDetails goodsOrderDetails);
        void renderOrderAliPay(String alipayStr);
        void renderOrderWxPay(WXPaySignResponse wxPaySignResponse);
    }
}
