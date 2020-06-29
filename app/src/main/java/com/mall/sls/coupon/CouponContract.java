package com.mall.sls.coupon;

import com.mall.sls.BasePresenter;
import com.mall.sls.BaseView;
import com.mall.sls.data.entity.CouponInfo;
import com.mall.sls.data.entity.MyCouponInfo;

import java.util.List;

/**
 * @author jwc on 2020/5/15.
 * 描述：
 */
public interface CouponContract {
    interface CouponSelectPresenter extends BasePresenter{
        void getCouponSelect(String cartIds);
    }


    interface CouponSelectView extends BaseView<CouponSelectPresenter>{
        void renderCouponSelect(List<CouponInfo> couponInfos);
    }

    interface CouponListPresenter extends BasePresenter{
        void getCouponInfos(String refreshType,String status);
        void getMoreCouponInfos(String status);
    }

    interface CouponListView extends BaseView<CouponListPresenter>{
        void renderCouponInfos(MyCouponInfo myCouponInfo);
        void renderMoreCouponInfos(MyCouponInfo myCouponInfo);
    }



}
