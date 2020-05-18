package com.mall.sls.order;

import com.mall.sls.BasePresenter;
import com.mall.sls.BaseView;
import com.mall.sls.data.entity.GoodsOrderDetails;
import com.mall.sls.data.entity.OrderInfo;
import com.mall.sls.data.entity.OrderList;

/**
 * @author jwc on 2020/5/18.
 * 描述：
 */
public interface OrderContract {
    interface OrderListPresenter extends BasePresenter{
        void getOrderList(String refreshType,String showType);
        void getMoreOrderList(String showType);
    }
    interface OrderListView extends BaseView<OrderListPresenter>{
        void renderOrderList(OrderList orderList);
        void renderMoreOrderList(OrderList orderList);
    }

    interface OrderDetailsPresenter extends BasePresenter{
        void getOrderDetails(String orderId);
    }

    interface OrderDetailsView extends BaseView<OrderDetailsPresenter>{
        void renderOrderDetails(OrderInfo orderInfo);
    }
}
