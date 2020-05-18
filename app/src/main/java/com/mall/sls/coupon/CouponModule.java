package com.mall.sls.coupon;

import dagger.Module;
import dagger.Provides;

/**
 * @author jwc on 2020/5/12.
 * 描述：
 */
@Module
public class CouponModule {
    private CouponContract.CouponSelectView couponSelectView;
    private CouponContract.CouponListView couponListView;

    public CouponModule(CouponContract.CouponSelectView couponSelectView) {
        this.couponSelectView = couponSelectView;
    }

    public CouponModule(CouponContract.CouponListView couponListView) {
        this.couponListView = couponListView;
    }

    @Provides
    CouponContract.CouponSelectView provideCouponSelectView(){
        return couponSelectView;
    }

    @Provides
    CouponContract.CouponListView provideCouponListView(){
        return couponListView;
    }
}
