package com.mall.sls.order;

import dagger.Module;
import dagger.Provides;

/**
 * @author jwc on 2020/5/11.
 * 描述：
 */
@Module
public class OrderModule {
    private OrderContract.OrderListView orderListView;
    private OrderContract.OrderDetailsView orderDetailsView;
    private OrderContract.OrderLogisticsView orderLogisticsView;
    private OrderContract.RefundView refundView;

    public OrderModule(OrderContract.OrderListView orderListView) {
        this.orderListView = orderListView;
    }

    public OrderModule(OrderContract.OrderDetailsView orderDetailsView) {
        this.orderDetailsView = orderDetailsView;
    }

    public OrderModule(OrderContract.OrderLogisticsView orderLogisticsView) {
        this.orderLogisticsView = orderLogisticsView;
    }

    public OrderModule(OrderContract.RefundView refundView) {
        this.refundView = refundView;
    }

    @Provides
    OrderContract.OrderListView provideOrderListView(){
        return orderListView;
    }

    @Provides
    OrderContract.OrderDetailsView provideOrderDetailsView(){
        return orderDetailsView;
    }

    @Provides
    OrderContract.OrderLogisticsView provideOrderLogisticsView(){
        return orderLogisticsView;
    }

    @Provides
    OrderContract.RefundView provideRefundView(){
        return refundView;
    }
}
