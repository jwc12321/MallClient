package com.mall.sls.homepage;



import com.mall.sls.BasePresenter;
import com.mall.sls.BaseView;
import com.mall.sls.certify.CertifyContract;
import com.mall.sls.data.entity.ConfirmOrderDetail;
import com.mall.sls.data.entity.GoodsDetailsInfo;
import com.mall.sls.data.entity.HomePageInfo;
import com.mall.sls.data.entity.OrderSubmitInfo;

public interface HomepageContract {
    interface HomePagePresenter extends BasePresenter{
        void getHomePageInfo(String refreshType);
    }

    interface HomePageView extends BaseView<HomePagePresenter>{
        void renderHomePageInfo(HomePageInfo homePageInfo);
    }

    interface GoodsDetailsPresenter extends BasePresenter{
        void getGoodsDetails(String goodsId);
        void getConsumerPhone();
        void cartFastAdd(String goodsId, String productId, boolean isGroup, String number, String groupId, String groupRulesId);
        void groupRemind(String ruleId);
    }

    interface GoodsDetailsView extends BaseView<GoodsDetailsPresenter>{
        void renderGoodsDetails(GoodsDetailsInfo goodsDetailsInfo);
        void renderConsumerPhone(String consumerPhone);
        void renderCartFastAdd(ConfirmOrderDetail confirmOrderDetail);
        void renderGroupRemind();

    }

    interface ConfirmOrderPresenter extends BasePresenter{
        void cartCheckout(String addressId, String cartId, String couponId, String userCouponId);
        void orderSubmit(String addressId, String cartId, String couponId, String userCouponId, String message);
    }

    interface ConfirmOrderView extends BaseView<ConfirmOrderPresenter>{
        void renderCartCheckout(ConfirmOrderDetail confirmOrderDetail);
        void renderOrderSubmit(OrderSubmitInfo orderSubmitInfo);
    }

}
