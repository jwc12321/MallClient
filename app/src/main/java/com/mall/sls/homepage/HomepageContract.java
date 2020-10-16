package com.mall.sls.homepage;




import com.mall.sls.BasePresenter;
import com.mall.sls.BaseView;
import com.mall.sls.data.entity.AliPay;
import com.mall.sls.data.entity.AppUrlInfo;
import com.mall.sls.data.entity.BaoFuPay;
import com.mall.sls.data.entity.ConfirmCartOrderDetail;
import com.mall.sls.data.entity.ConfirmOrderDetail;
import com.mall.sls.data.entity.CouponInfo;
import com.mall.sls.data.entity.GeneralGoodsDetailsInfo;
import com.mall.sls.data.entity.GoodsDetailsInfo;
import com.mall.sls.data.entity.HomePageInfo;
import com.mall.sls.data.entity.HomeSnapUp;
import com.mall.sls.data.entity.InvitationCodeInfo;
import com.mall.sls.data.entity.OrderSubmitInfo;
import com.mall.sls.data.entity.PayMethodInfo;
import com.mall.sls.data.entity.TokenRefreshInfo;
import com.mall.sls.data.entity.WxPay;

import java.util.List;

public interface HomepageContract {
    interface HomePagePresenter extends BasePresenter{
        void getHomePageInfo(String refreshType);
        void bindWx(String code);
        void getAppUrlInfo();
        void couponReceive(String type);
        void getHomeSnapUp();
        void getTokenRefreshInfo();
    }

    interface HomePageView extends BaseView<HomePagePresenter>{
        void renderHomePageInfo(HomePageInfo homePageInfo);
        void renderBindWx();
        void renderAppUrlInfo(AppUrlInfo appUrlInfo);
        void renderCouponReceive(List<CouponInfo> couponInfos);
        void renderHomeSnapUp(HomeSnapUp homeSnapUp);
        void renderTokenRefreshInfo(TokenRefreshInfo tokenRefreshInfo);
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
        void cartCheckout(String addressId, String cartId, String couponId, String userCouponId,String shipChannel);
        void orderSubmit(String addressId, String cartId, String couponId, String userCouponId, String message,String shipChannel);
        void getWxPay(String orderId, String orderType, String paymentMethod);
        void getAliPay(String orderId, String orderType, String paymentMethod);
        void getBaoFuPay(String orderId, String orderType, String paymentMethod);
    }

    interface ConfirmOrderView extends BaseView<ConfirmOrderPresenter>{
        void renderCartCheckout(ConfirmOrderDetail confirmOrderDetail);
        void renderOrderSubmit(OrderSubmitInfo orderSubmitInfo);
        void renderWxPay(WxPay wxPay);
        void renderAliPay(AliPay aliPay);
        void renderBaoFuPay(BaoFuPay baoFuPay);
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

    interface GeneralGoodsDetailsPresenter extends BasePresenter{
        void getCartCount();
        void cartAdd(String productId, String number);
        void getGeneralGoodsDetailsInfo(String goodsId);
        void buyNow(String productId, String number);
    }

    interface GeneralGoodsDetailsView extends BaseView<GeneralGoodsDetailsPresenter>{
        void renderCartCount(String number);
        void renderCartAdd();
        void renderGeneralGoodsDetailsInfo(GeneralGoodsDetailsInfo generalGoodsDetailsInfo);
        void renderBuyNow(ConfirmCartOrderDetail confirmCartOrderDetail);
    }

    interface CartConfirmOrderPresenter extends BasePresenter{
        void cartGeneralChecked(String addressId, List<String> ids, String userCouponId,String shipChannel);
        void cartOrderSubmit(String addressId, List<String> ids, String userCouponId, String message,String shipChannel);
        void getWxPay(String orderId, String orderType, String paymentMethod);
        void getAliPay(String orderId, String orderType, String paymentMethod);
        void getBaoFuPay(String orderId, String orderType, String paymentMethod);
    }

    interface CartConfirmOrderView extends BaseView<CartConfirmOrderPresenter>{
        void renderCartGeneralChecked(ConfirmCartOrderDetail confirmCartOrderDetail);
        void renderCartOrderSubmit(OrderSubmitInfo orderSubmitInfo);
        void renderWxPay(WxPay wxPay);
        void renderAliPay(AliPay aliPay);
        void renderBaoFuPay(BaoFuPay baoFuPay);
    }
    interface PayMethodPresenter extends BasePresenter{
        void getPayMethod(String devicePlatform,String orderType);
    }

    interface PayMethodView extends BaseView<PayMethodPresenter>{
        void renderPayMethod(List<PayMethodInfo> payMethods);
    }
}
