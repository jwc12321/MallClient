package com.mall.sls.coupon;

/**
 * @author jwc on 2020/5/15.
 * 描述：
 */

import com.mall.sls.ActivityScope;
import com.mall.sls.ApplicationComponent;
import com.mall.sls.coupon.ui.CouponExpiredFragment;
import com.mall.sls.coupon.ui.CouponUnusedFragment;
import com.mall.sls.coupon.ui.CouponUsedFragment;
import com.mall.sls.coupon.ui.SelectCouponActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {CouponModule.class})
public interface CouponComponent {
    void inject(SelectCouponActivity selectCouponActivity);
    void inject(CouponExpiredFragment couponExpiredFragment);
    void inject(CouponUnusedFragment couponUnusedFragment);
    void inject(CouponUsedFragment couponUsedFragment);
}
