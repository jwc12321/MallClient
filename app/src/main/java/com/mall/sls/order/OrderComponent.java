package com.mall.sls.order;

/**
 * @author jwc on 2020/5/18.
 * 描述：
 */

import com.mall.sls.ActivityScope;
import com.mall.sls.ApplicationComponent;
import com.mall.sls.order.ui.AllOrdersFragment;
import com.mall.sls.order.ui.CompletedFragment;
import com.mall.sls.order.ui.GoodsOrderDetailsActivity;
import com.mall.sls.order.ui.PendingDeliveryFragment;
import com.mall.sls.order.ui.PendingPaymentFragment;
import com.mall.sls.order.ui.PendingShareFragment;
import com.mall.sls.order.ui.ShippingFragment;
import com.mall.sls.order.ui.ViewFundActivity;
import com.mall.sls.order.ui.ViewLogisticsActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {OrderModule.class})
public interface OrderComponent {
    void inject(AllOrdersFragment allOrdersFragment);
    void inject(PendingPaymentFragment fragment);
    void inject(PendingDeliveryFragment fragment);
    void inject(ShippingFragment fragment);
    void inject(GoodsOrderDetailsActivity goodsOrderDetailsActivity);
    void inject(PendingShareFragment pendingShareFragment);
    void inject(CompletedFragment completedFragment);
    void inject(ViewLogisticsActivity viewLogisticsActivity);
    void inject(ViewFundActivity viewFundActivity);
}
