package com.mall.sls.homepage;



import com.mall.sls.BasePresenter;
import com.mall.sls.BaseView;
import com.mall.sls.certify.CertifyContract;
import com.mall.sls.data.entity.ConfirmOrderDetail;
import com.mall.sls.data.entity.GoodsDetailsInfo;
import com.mall.sls.data.entity.HomePageInfo;
import com.mall.sls.data.entity.InvitationCodeInfo;
import com.mall.sls.data.entity.OrderSubmitInfo;
import com.mall.sls.data.entity.WXGoodsDetailsInfo;
import com.mall.sls.data.entity.WXPaySignResponse;

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
        void getInvitationCodeInfo();
    }

    interface GoodsDetailsView extends BaseView<GoodsDetailsPresenter>{
        void renderGoodsDetails(GoodsDetailsInfo goodsDetailsInfo);
        void renderConsumerPhone(String consumerPhone);
        void renderCartFastAdd(ConfirmOrderDetail confirmOrderDetail);
        void renderGroupRemind();
        void renderInvitationCodeInfo(InvitationCodeInfo invitationCodeInfo);

    }

    interface ConfirmOrderPresenter extends BasePresenter{
        void cartCheckout(String addressId, String cartId, String couponId, String userCouponId);
        void orderSubmit(String addressId, String cartId, String couponId, String userCouponId, String message);
        void orderAliPay(String orderId,String type);
        void orderWxPay(String orderId,String type);
    }

    interface ConfirmOrderView extends BaseView<ConfirmOrderPresenter>{
        void renderCartCheckout(ConfirmOrderDetail confirmOrderDetail);
        void renderOrderSubmit(OrderSubmitInfo orderSubmitInfo);
        void renderOrderAliPay(String alipayStr);
        void renderOrderWxPay(WXPaySignResponse wxPaySignResponse);
    }

    interface WXGoodsDetailsPresenter extends BasePresenter{
        void getWXGoodsDetailsInfo(String goodsProductId,String grouponId);
        void cartFastAdd(String goodsId, String productId, boolean isGroup, String number, String groupId, String groupRulesId);
        void getInvitationCodeInfo();
    }

    interface WXGoodsDetailsView extends BaseView<WXGoodsDetailsPresenter>{
        void renderWXGoodsDetailsInfo(GoodsDetailsInfo goodsDetailsInfo);
        void renderCartFastAdd(ConfirmOrderDetail confirmOrderDetail);
        void renderInvitationCodeInfo(InvitationCodeInfo invitationCodeInfo);
    }

}
